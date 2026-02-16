# Tasks: 修正 Welcome 系統效能瓶頸與專案清理

**Feature**: 修正 Welcome 系統效能瓶頸與專案清理
**Branch**: `015-fix-performance-hygiene`
**Implementation Strategy**: 採用「基礎先行，優先優化」策略。首先重構快取管理器的資料結構，接著優化關鍵的連線攔截邏輯，最後修復指令中的效能瓶頸，確保每一階段皆可透過測試驗證效能提升。

## Phase 1: Setup (Project Hygiene)

目標：清理過時的專案規格文件，確保工作目錄整潔。

- [x] T001 刪除過時的規格目錄 `specs/013-fix-welcome-issue/`

## Phase 2: Foundational (Cache Infrastructure)

目標：重構快取管理器，使其支援同時儲存玩家名稱與 UUID。

- [x] T002 修改 `src/main/java/com/welcome/managers/PlayerCacheManager.java`：將 `cachedNames` 改為支援 LRU 的 `Map<String, UUID>`
- [x] T003 更新 `PlayerCacheManager.java` 的 `addPlayer` 方法以接受 `UUID` 參數
- [x] T004 在 `PlayerCacheManager.java` 中新增 `getUuid(String name)` 方法

## Phase 3: [US2] 系統身分識別處理 (優先級: P2)

目標：在玩家連線時擷取完整身分資訊。

- [x] T005 [US2] 修改 `src/main/java/com/welcome/listeners/LoginAttemptListener.java`：在 `onPlayerPreLogin` 中擷取 `UUID` 並傳遞給快取管理器
- [x] T006 [P] [US2] 更新相關測試案例以驗證 UUID 是否正確存入快取

## Phase 4: [US1] 指令效能優化 (優先級: P1)

目標：移除主執行緒阻塞呼叫，改用高效的快取查詢。

- [x] T007 [US1] 修改 `src/main/java/com/welcome/commands/WelcomeCommand.java`：在 `handleVote` 中優先從快取獲取 `UUID`
- [x] T008 [US1] 重構 `WelcomeCommand.java`：將 `Bukkit.getOfflinePlayer(targetName)` 替換為 `Bukkit.getOfflinePlayer(uuid)` 邏輯
- [x] T009 [US1] 確保 `onTabComplete` 仍能正確從新結構的快取中獲取玩家名稱清單
- [x] T010 [US1] 更新單元測試，模擬大批量查詢情境以驗證非阻塞行為

## Phase 5: [US3] 代碼冗餘清理 (優先級: P3)

目標：根據 Code Review 意見移除未使用的代碼。

- [x] T011 [US3] 修改 `src/main/java/com/welcome/listeners/LoginAttemptListener.java`：移除未使用的 `JavaPlugin plugin` 欄位與建構子參數
- [x] T012 [P] [US3] 檢查並移除 `WelcomePlugin.java` 或其他類別中不再需要的過時 import

## Phase 6: Polish & Cross-Cutting Concerns

目標：最終驗證與性能檢核。

- [x] T013 執行全體單元測試 `gradle test` 確保無功能衰退
- [x] T014 驗證 `SC-001`：在高負載模擬下 `/welcome` 指令無延遲感

## Dependencies

1. **Foundational (T002-T004)** 必須在 **US1 (T007-T010)** 與 **US2 (T005-T006)** 之前完成。
2. **US2** 建議在 **US1** 之前完成，以提供更完整的快取數據供指令使用。

## Parallel Execution Examples

- **UI/Cleanup**: T001 與 T011 可以並行。
- **Testing**: T006 與 T010 可在對應邏輯完成後並行。
