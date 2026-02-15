---

description: "Task list for Whitelist Welcome voting feature implementation"
---

# Tasks: 白名單歡迎指令功能

**Input**: Design documents from `specs/002-whitelist-welcome/`
**Prerequisites**: plan.md, spec.md, data-model.md, contracts/commands.yml, research.md

**測試**: 根據專案憲法，測試是 **強制性的**，並且必須在實作之前撰寫 (測試優先原則)。使用 **JUnit 5** 撰寫測試，並透過 `./gradlew test` 執行。下面的範例包含了測試任務，應根據此原則進行規劃。

## 第一階段：設置 (Setup) - 共用基礎設施

**目的**: 更新配置與管理類以支援投票功能

- [X] T001 [P] 更新 `src/main/resources/config.yml` 加入 `check-whitelist` 與 `broadcast-on-whitelist` 欄位
- [X] T002 [P] 更新 `src/main/resources/messages.yml` 加入投票相關的所有訊息範本
- [X] T003 更新 `src/main/java/com/wellcome/configuration/ConfigManager.java` 增加新設定的 Getter 方法

---

## 第二階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 實作核心投票管理與白名單工具

- [X] T004 實作 `VoteManager` 管理記憶體中的投票記錄 在 `src/main/java/com/wellcome/managers/VoteManager.java`
- [X] T005 [P] 實作 `WhitelistUtils` 封裝 Spigot 白名單 API 操作 在 `src/main/java/com/wellcome/utils/WhitelistUtils.java`
- [X] T006 在 `WellcomePlugin` 中初始化 `VoteManager` 並傳遞給指令執行器 在 `src/main/java/com/wellcome/WellcomePlugin.java`

---

## 第三階段：測試與實作 (User Story 1 & 2) 🎯 MVP

**目標**: 實作 `/wellcome` 指令邏輯、投票機制與自動白名單

**獨立測試**: 使用 `./gradlew test` 驗證 `VoteManager` 邏輯，並在伺服器中使用 `/wellcome` 驗證功能。

### 測試 (Test-First) ⚠️

- [X] T007 [P] [US1/2] 撰寫 `VoteManager` 的單元測試 (含投票、重複投票、清除) 在 `tests/VoteManagerTest.java`
- [ ] T008 [P] [US1/2] 撰寫 `WhitelistUtils` 的單元測試 (MockBukkit) 在 `tests/WhitelistUtilsTest.java` (因依賴問題暫緩，邏輯已併入指令測試)

### 指令實作 (Implementation)

- [X] T009 [US1] 在 `WellcomeCommand` 中更新 `onCommand` 處理 `/wellcome <player>` 分支 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T010 [US1] 實作權限驗證與白名單開啟狀態檢查 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T011 [US1] 實作目標玩家合法性檢查 (本地已知玩家) 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T012 [US1/2] 整合 `VoteManager` 執行投票並獲取目前票數 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T013 [US2] 查詢主世界 `playersSleepingPercentage` 並計算投票門檻 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T014 [US2] 實作達標後的自動加入白名單與全服廣播邏輯 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`
- [X] T015 [US1/2] 實作投票後的訊息回饋 (顯示進度或錯誤) 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`

**檢查點**: 指令可執行，達到門檻後玩家自動加入白名單並廣播。

---

## 第 N 階段：打磨與優化 (Polish)

**目的**: 完善文件與生命週期管理

- [X] T016 更新 `README.md` 加入 `/wellcome` 指令說明與投票機制介紹
- [X] T017 在 `WellcomePlugin.onDisable` 中明確呼叫投票資料清理
- [X] T018 執行 `./gradlew build` 確保產出 JAR 檔包含最新設定

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 前置作業，可先執行。
- **Foundational (Phase 2)**: 依賴 Phase 1 完成。
- **Testing (T007-T008)**: 必須在實作 T009-T015 之前或並行，確保符合測試優先。
- **Implementation (T009-T015)**: 依賴 Phase 2 完成。

---

## Parallel Opportunities

- T001, T002 可以並行。
- T005, T007, T008 (測試類) 可以在基礎類實作後並行撰寫。

---

## Notes

- 確保 `MessageUtils` 支援 `{current_votes}` 與 `{required_votes}` 變數替換（或在指令類中手動替換）。
- 投票進度計算需注意線上人數為 0 的極端情況 (雖然執行者在線，分母至少為 1)。
