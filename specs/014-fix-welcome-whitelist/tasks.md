# Tasks: 修正無法 welcome 玩家的問題並進版 v1.1.1

**Input**: Design documents from `/specs/014-fix-welcome-whitelist/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, quickstart.md

**測試**: 根據專案憲法，測試是 **強制性的**，並且必須在實作之前撰寫 (測試優先原則)。使用 **JUnit 5** 與 **MockBukkit** 撰寫測試，並透過 `./gradlew test` 執行。

## 第一階段：設置 (Setup)

**目的**: 更新外掛版本資訊。

- [x] T001 [P] 更新 `build.gradle` 中的版本號至 `1.1.1`
- [x] T002 [P] 更新 `src/main/resources/plugin.yml` 中的版本號至 `1.1.1`

---

## 第二階段：使用者故事 1 - 歡迎被白名單阻擋的新玩家 (優先級: P1) 🎯 MVP

**目標**: 讓不在白名單且未登入過的玩家能被捕捉並進行投票。

**獨立測試**: 
1. 模擬 `AsyncPlayerPreLoginEvent`（未白名單玩家）。
2. 驗證 `PlayerCacheManager` 是否包含該玩家。
3. 執行 `/welcome <玩家名>` 並驗證投票是否成功啟動。

### 測試 (Test-First) ⚠️

- [x] T003 [P] [US1] 撰寫 `LoginAttemptListener` 的單元測試於 `src/test/java/com/welcome/listeners/LoginAttemptListenerTest.java` (驗證非白名單玩家被加入快取)
- [x] T004 [P] [US1] 更新 `WelcomeCommandTest.java` 於 `src/test/java/com/welcome/commands/WelcomeCommandTest.java` (驗證快取中的玩家可被投票，即使 hasPlayedBefore 為 false)

### 實作 (Implementation)

- [x] T005 [P] [US1] 實作 `src/main/java/com/welcome/listeners/LoginAttemptListener.java` 以捕捉 `AsyncPlayerPreLoginEvent`
- [x] T006 [US1] 在 `src/main/java/com/welcome/WelcomePlugin.java` 中註冊 `LoginAttemptListener`
- [x] T007 [US1] 修改 `src/main/java/com/welcome/commands/WelcomeCommand.java` 中的 `handleVote` 方法，加入對 `playerCacheManager` 的檢查邏輯

---

## 第三階段：使用者故事 2 - 外掛版本更新至 v1.1.1 (優先級: P2)

**目標**: 確保版本資訊正確顯示。

- [x] T008 [US2] 驗證 `./gradlew build` 產出的 Jar 檔名與內部的 `plugin.yml` 版本一致

---

## 第四階段：打磨與驗證 (Polish)

- [x] T009 執行 `./gradlew clean build` 確保所有測試通過且建置成功
- [x] T010 檢查 `README.md` 的版本說明（如需）

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，可優先執行。
- **US1 (Phase 2)**: 核心功能，依賴基礎設施（已存在）。
- **US2 (Phase 3)**: 驗證性質。

### Implementation Strategy

1. 先執行 T001-T002 完成版本更新。
2. 實作 T003-T004 (測試優先)。
3. 實作 T005-T007 完成核心邏輯。
4. 最後執行 T009 進行全面驗證。
