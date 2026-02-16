# Tasks: Dynamic Player Cache for Autocomplete

**Input**: Design documents from `/specs/010-dynamic-player-cache/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**測試**: 根據專案憲法，測試是 **強制性的**，並且必須在實作之前撰寫 (測試優先原則)。使用 **JUnit 5** 撰寫測試，並透過 `./gradlew test` 執行。

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

---

## 第一階段：設置 (Setup) - 基礎變更

**目的**: 調整設定檔與專案結構以配合動態快取機制。

- [X] T001 修改 `src/main/resources/config.yml` 移除 `autocomplete.update_interval` 並更新說明註解
- [X] T002 [P] 更新 `src/main/java/com/welcome/configuration/ConfigManager.java` 移除 `autocomplete_update_interval` 相關變數與讀取邏輯

---

## 第二階段：基礎建設 (Foundational) - 核心快取邏輯重構

**目的**: 重構 `PlayerCacheManager` 以支援手動更新與 LRU 邏輯。

**⚠️ 關鍵**: 本階段完成後，快取將不再定時更新。

- [X] T003 修改 `src/main/java/com/welcome/managers/PlayerCacheManager.java` 移除 `BukkitTask` 與 `startUpdateTask` 定時邏輯
- [X] T004 實作 `addPlayer(String name)` 方法，包含 LRU 檢查與不可變清單更新邏輯，於 `src/main/java/com/welcome/managers/PlayerCacheManager.java`
- [X] T005 實作 `removePlayer(String name)` 方法，用於手動移除快取項，於 `src/main/java/com/welcome/managers/PlayerCacheManager.java`

---

## 第三階段：使用者故事 1 - 即時更新補全清單 (優先級: P1) 🎯 MVP

**目標**: 玩家登入時立即將其加入補全快取。

**獨立測試**: 使用 MockBukkit 模擬玩家登入，驗證 `PlayerCacheManager` 的快取內容。

### 測試 (Test-First) ⚠️

- [X] T006 [P] [US1] 撰寫 `PlayerCacheManager.addPlayer` 的單元測試 (含 LRU 邊界測試)，於 `src/test/java/com/welcome/managers/PlayerCacheManagerTest.java`
- [X] T007 [P] [US1] 撰寫玩家登入觸發快取更新的整合測試，於 `src/test/java/com/welcome/listeners/PlayerJoinListenerTest.java`

### 實作 (Implementation)

- [X] T008 [US1] 建立 `PlayerJoinListener` 監聽 `PlayerJoinEvent` 並非同步呼叫 `addPlayer`，於 `src/main/java/com/welcome/listeners/PlayerJoinListener.java`
- [X] T009 [US1] 在 `src/main/java/com/welcome/WelcomePlugin.java` 的 `onEnable` 中註冊 `PlayerJoinListener`
- [X] T010 [US1] 修改 `src/main/java/com/welcome/managers/PlayerCacheManager.java` 的 `updateCache` (若仍保留作為初始載入) 或初始化邏輯，確保啟動時快取為空

**檢查點**: 玩家登入後，其名稱應立即出現在 Tab 補全清單中。

---

## 第四階段：使用者故事 2 - 自動過濾白名單玩家 (優先級: P2)

**目標**: 確保白名單玩家不出現或即時從快取移除。

**獨立測試**: 模擬玩家被加入白名單，驗證快取中該玩家名稱被移除。

### 測試 (Test-First) ⚠️

- [X] T011 [P] [US2] 撰寫 `PlayerCacheManager.removePlayer` 的單元測試，於 `src/test/java/com/welcome/managers/PlayerCacheManagerTest.java`

### 實作 (Implementation)

- [X] T012 [US2] 修改 `src/main/java/com/welcome/commands/WelcomeCommand.java` 在成功投票並加入白名單後，呼叫 `playerCacheManager.removePlayer(target.getName())`
- [X] T013 [US2] 在 `src/main/java/com/welcome/listeners/PlayerJoinListener.java` 中加入檢查邏輯，僅對非白名單玩家執行 `addPlayer`

**檢查點**: 被加入白名單的玩家應立即從補全清單中消失。

---

## 第五階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與文件更新。

- [X] T014 [P] 更新 `README.md` 中關於「自動補全機制」的說明，強調其即時性
- [X] T015 執行 `./gradlew test` 確保所有新舊測試皆通過
- [X] T016 檢查 `src/main/java/com/welcome/managers/PlayerCacheManager.java` 的執行緒安全性，確保多執行緒讀寫無併發問題

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，可立即開始。
- **Foundational (Phase 2)**: 依賴 Phase 1，為後續使用者故事提供 API 支援。
- **User Story 1 (Phase 3)**: 依賴 Phase 2。
- **User Story 2 (Phase 4)**: 依賴 Phase 3。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### User Story Dependencies

- **US1 (P1)**: 必須完成以確保動態更新功能可用。
- **US2 (P2)**: 依賴 US1 完成後的基礎設施。

### Parallel Opportunities

- T002, T003 (設定與重構)
- T006, T007 (US1 測試)
- T011 (US2 測試)
- T014 (文件更新) 可隨時進行

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成 Phase 1 & 2。
2. 完成 Phase 3 (US1)。
3. **驗證**: 測試玩家登入後 Tab 補全是否即時生效。

### Incremental Delivery

1. 完成基礎設施重構。
2. 實作登入即時更新 (US1) -> 交付 MVP。
3. 實作白名單連動移除 (US2) -> 提升準確性。
4. 完成最終測試與打磨。
