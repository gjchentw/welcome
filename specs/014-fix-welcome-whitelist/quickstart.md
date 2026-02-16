# Quickstart: 014-fix-welcome-whitelist

## 實作步驟摘要

### 1. 更新版本號 (進版 v1.1.1)
- 修改 `build.gradle`：`version = '1.1.1'`。
- 修改 `src/main/resources/plugin.yml`：`version: 1.1.1`。

### 2. 建立 LoginAttemptListener
- 在 `com.welcome.listeners` 下建立 `LoginAttemptListener`。
- 監聽 `AsyncPlayerPreLoginEvent`。
- 檢查玩家是否不在白名單中，若是，則呼叫 `playerCacheManager.addPlayer(name)`。

### 3. 修改 WelcomeCommand
- 移除對 `target.hasPlayedBefore()` 的強依賴。
- 在 `handleVote` 中，改為檢查輸入的 `targetName` 是否存在於 `playerCacheManager.getCachedNames()` 中。
- 如果存在，則繼續投票流程。

### 4. 驗證
- 執行 `./gradlew clean build` 確保編譯成功。
- 執行 `gradle test` 執行單元測試（如果有的話）。
