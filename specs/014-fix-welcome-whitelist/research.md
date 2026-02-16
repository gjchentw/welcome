# Research: 修正無法 welcome 玩家的問題並進版 v1.1.1

## 技術挑戰與決策

### 1. 捕捉未白名單玩家
- **問題**: `PlayerJoinEvent` 只在玩家成功登入後觸發。被白名單阻擋的玩家會觸發 `AsyncPlayerPreLoginEvent`。
- **決策**: 在 `AsyncPlayerPreLoginEvent` 監聽器中，透過 `event.getUniqueId()` 獲取玩家資訊。
- **實作細節**: 
    - 使用 `Bukkit.getOfflinePlayer(uuid)` 檢查該玩家是否已在白名單中。
    - 如果玩家不在白名單且未被封禁 (Banned)，則將其名稱與 UUID 加入 `PlayerCacheManager`。
- **Rationale**: 這樣即使玩家無法進入伺服器，其資料也會出現在快取中，供其他玩家投票。

### 2. `/welcome` 指令邏輯調整
- **問題**: 目前 `WelcomeCommand` 使用 `target.hasPlayedBefore()` 來驗證目標。
- **決策**: 修改驗證邏輯，改為檢查該玩家是否在 `PlayerCacheManager` 的快取名單中。
- **實作細節**: `PlayerCacheManager` 應提供一個方法來根據名稱搜尋快取中的 UUID，或返回當前可投票的所有名稱。
- **Rationale**: `hasPlayedBefore()` 對於從未成功登入的玩家會回傳 `false`，因此必須改用我們自定義的動態快取。

### 3. 版本更新自動化
- **問題**: 需要同步更新多處版本號。
- **決策**: 手動更新 `build.gradle` 中的 `version` 屬性，並確保 `plugin.yml` 使用 Gradle 佔位符（如果已設定）或同步手動更新。
- **實作細節**: 檢查 `src/main/resources/plugin.yml` 是否包含 `version: ${version}`。如果沒有，則需手動更新兩者。

## 最佳實踐參考
- **Spigot 事件優先級**: 使用 `EventPriority.MONITOR` 於 `AsyncPlayerPreLoginEvent` 來純粹觀察登入嘗試，確保不影響其他插件的處理。
- **LRU 快取策略**: `PlayerCacheManager` 已實作 LRU，確保在高流量下不會溢出記憶體。

## 替代方案評估
- **方案 A**: 使用資料庫永久儲存嘗試登入的紀錄。
    - **評價**: 太過沉重。此功能僅需暫存，且在伺服器重啟後消失是可接受的（甚至是理想的）。
- **方案 B**: 攔截踢出訊息。
    - **評價**: 不穩定且難以維護。直接使用 API 事件更為可靠。
