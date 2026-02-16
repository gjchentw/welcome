# Quickstart: 效能重構實作指南

## 核心變更步驟

### 1. 重構 PlayerCacheManager
- 將 `List<String> cachedNames` 改為 `Map<String, UUID> playerCache`。
- 建議使用 `Collections.synchronizedMap(new LinkedHashMap<>(...))` 或手動同步塊來維持 LRU 順序。
- 更新 `addPlayer(String name)` 為 `addPlayer(String name, UUID uuid)`。

### 2. 優化 LoginAttemptListener
- 移除 `plugin` 欄位。
- 修改 `onPlayerPreLogin`：
  ```java
  String name = event.getName();
  UUID uuid = event.getUniqueId();
  // ... 檢查白名單 ...
  playerCacheManager.addPlayer(name, uuid);
  ```

### 3. 修復 WelcomeCommand 效能瓶頸
- 尋找 `handleVote` 方法。
- 修改玩家檢索邏輯：
  ```java
  UUID targetUuid = playerCacheManager.getUuid(targetName);
  OfflinePlayer target = (targetUuid != null) ? Bukkit.getOfflinePlayer(targetUuid) : Bukkit.getOfflinePlayer(targetName);
  ```

### 4. 專案清理
- 刪除 `specs/013-fix-welcome-issue/` 目錄。
- 確保所有測試案例均通過（特別是 MockBukkit 模擬連線後執行指令的流程）。

## 驗證清單
- [ ] 執行 `/welcome` 時，伺服器日誌無 `Nag` 警告（主執行緒阻塞警告）。
- [ ] 補登入的新玩家能正確出現在 Tab 自動補全中。
- [ ] `specs/013` 已從檔案系統消失。
