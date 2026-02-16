# Research: Dynamic Cache Management and Event Integration

## 1. Event Integration
- **選擇事件**: `org.bukkit.event.player.PlayerJoinEvent`。
- **觸發條件**: 僅當 `!WhitelistUtils.isWhitelisted(player)` 時進行處理。
- **並行處理**: 使用 `Bukkit.getScheduler().runTaskAsynchronously` 以避免阻塞主執行緒。

## 2. 快取結構與 LRU 實現
- **現有結構**: `List<String> cachedNames` (Volatile)。
- **動態更新挑戰**: 簡單的 `List` 在頻繁寫入時效率較低，且難以維護 LRU。
- **改進建議**: 
  - 使用 `LinkedHashSet` 或 `CopyOnWriteArrayList`。
  - 考量到讀取頻率遠高於寫入，且 `max_players` 通常較小（預設 100），使用 `List` 配合 `Collections.synchronizedList` 並在更新時重新封裝為不可變清單是可行的。
- **LRU 邏輯**: 
  - 當新玩家訪問：如果已存在，先移除再加到最前面；如果不存在且已滿，移除最後一個再加到最前面。

## 3. 設定檔遷移
- **變更**: 移除 `ConfigManager` 中與 `autocomplete_update_interval` 相關的程式碼。
- **相容性**: 在 `config.yml` 中保留該鍵值不會出錯，但程式碼不再讀取它。

## 決策 Rationale
- 為了保持 `PlayerCacheManager` 的執行緒安全且不頻繁分配記憶體，更新時將建立新的不可變清單副本供 `WelcomeCommand` 讀取。
- LRU 實現將手動在 `addPlayer(String name)` 方法中處理。
