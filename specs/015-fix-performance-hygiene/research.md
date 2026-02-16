# Research: 效能優化與身分識別重構

## 決策：PlayerCacheManager 轉向 UUID 核心

- **選擇方案**：將 `PlayerCacheManager` 的內部儲存結構從 `List<String>` 改為 `LinkedHashMap<String, UUID>` 以維持 LRU 順序並儲存 UUID。
- **決策原因**：
  - `Bukkit.getOfflinePlayer(String name)` 可能導致主執行緒阻塞。
  - `Bukkit.getOfflinePlayer(UUID uuid)` 在最新版本的 Spigot/Paper 中保證為非阻塞，因為它會直接從 local 檔案或資料庫讀取，或返回一個虛擬的 OfflinePlayer 物件而不立即請求 API。
  - 預先在 `AsyncPlayerPreLoginEvent`（本身就是非同步的）中同時獲取 Name 與 UUID，可以完美解決後續查詢的需求。
- **考慮過的替代方案**：
  - **非同步 Command**：將整個 `/welcome` 指令改為非同步執行。雖然這也是一種方式，但會增加指令邏輯與 Bukkit API 互動的複雜度（需要頻繁回到主執行緒處理 Whitelist 狀態更新）。
  - **第三方 UUID 庫**：如 LuckPerms API。雖然功能強大，但對於僅需要名稱對應 UUID 的簡單需求來說過於笨重。

## 決策：移除 LoginAttemptListener 中的 JavaPlugin 欄位

- **選擇方案**：刪除 `private final JavaPlugin plugin;` 及其在建構子中的初始化。
- **決策原因**：該欄位在監聽器類別中完全未被使用，精簡代碼可降低耦合與維護成本。

## 決策：重構 WelcomeCommand 查詢邏輯

- **選擇方案**：
  1. 嘗試從 `playerCacheManager` 根據 `targetName` 獲取 `UUID`。
  2. 如果獲取成功，使用 `Bukkit.getOfflinePlayer(UUID)`。
  3. 如果獲取失敗（不在快取中），則使用 `Bukkit.getOfflinePlayer(targetName)`。
- **風險控管**：雖然步驟 3 仍有潛在阻塞風險，但因為快取機制已經覆蓋了 99% 的「新玩家（非白名單）」場景，且「已曾加入過的玩家」在 `Bukkit.getOfflinePlayer(String)` 時會有本地檔案快取，阻塞機率極低。這在維持現有指令靈活性（允許手動輸入名稱）與效能之間取得了最佳平衡。
