# Quickstart: 006-rename-welcome-autocomplete

## 開發者實作指引

### 1. 更新設定檔
在 `config.yml` 加入 `autocomplete` 區塊，並更新 `ConfigManager` 以支援新欄位的讀取與驗證。

### 2. 實作 PlayerCacheManager
建立 `com.wellcome.managers.PlayerCacheManager` 類別：
- 使用 `volatile List<String> cachedNames`。
- 在建構子啟動 `runTaskTimerAsynchronously` 任務。
- 實作過濾邏輯：獲取 `Bukkit.getOfflinePlayers()`，過濾最近 3 天上線且不在白名單的玩家，限制數量後排序。
- 提供 `getCachedNames()` 方法供指令讀取。

### 3. 指令更名與補全
- 將 `WellcomeCommand` 更名為 `WelcomeCommand`。
- 在 `onTabComplete` 中呼叫 `PlayerCacheManager.getCachedNames()`。
- 確保 `plugin.yml` 中的指令註冊名稱已更新為 `welcome`。

### 4. 單元測試
實作 `PlayerCacheManagerTest`：
- 模擬 1,000 名玩家並驗證過濾邏輯。
- 驗證 `volatile` 替換的原子性。
- 驗證錯誤處理（API 回傳空值時清除快取）。
