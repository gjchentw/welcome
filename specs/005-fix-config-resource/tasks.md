# Tasks: Fix Missing config.yml Resource

**Input**: Design documents from `/specs/005-fix-config-resource/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, quickstart.md

**測試**: 根據專案憲法，測試是 **強制性的**。雖然此修復主要在於資源檔案缺失，但我們仍需確保 `ConfigManager` 在資源存在的情況下能正確運作。

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)

---

## 第一階段：設置 (Setup) - 共用基礎設施

**目的**: 確認資源打包設定

- [x] T001 驗證 `build.gradle` 的 `processResources` 任務是否包含 `src/main/resources` 下的所有檔案

---

## 第二階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 核心基礎建設，確保 Config 管理邏輯健全

- [x] T002 檢查 `src/main/java/com/wellcome/configuration/ConfigManager.java` 中的 `loadConfig` 方法，確保其在 `saveDefaultConfig()` 失敗時有適當的日誌記錄

---

## 第三階段：使用者故事 1 - 插件正常啟動 (優先級: P1) 🎯 MVP

**目標**: 修復 `IllegalArgumentException` 並確保 `config.yml` 成功生成

**獨立測試**: 啟動伺服器並檢查控制台，確認無資源缺失錯誤且檔案已產出

### 實作 (Implementation)

- [x] T003 [P] [US1] 在 `src/main/resources/config.yml` 建立基本的預設設定檔
- [x] T004 [US1] 執行 `./gradlew build` 並驗證 JAR 內容是否包含 `config.yml`
- [x] T005 [US1] 在測試環境啟動插件，驗證 `plugins/wellcome/config.yml` 是否成功自動生成

---

## 第四階段：使用者故事 2 - 讀取預設設定 (優先級: P2)

**目標**: 確保 ConfigManager 能正確讀取生成後的設定值

**獨立測試**: 透過日誌或功能驗證讀取到的設定值

### 測試 (Test-First) ⚠️

- [x] T006 [P] [US2] 在 `src/test/java/com/wellcome/configuration/ConfigManagerTest.java` (如存在) 或新增測試類別，模擬資源讀取邏輯

### 實作 (Implementation)

- [x] T007 [US2] 驗證 `ConfigManager` 在讀取 `config.yml` 時能正確取得預設數值

---

## 第 N 階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [x] T008 [P] 更新 `README.md` 的安裝配置章節（如有變動）
- [x] T009 執行最終整合測試，確保插件啟用流程順暢

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴。
- **Foundational (Phase 2)**: 依賴 Phase 1。
- **User Stories (Phase 3+)**: 依賴 Phase 2 完成。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### Parallel Opportunities

- T003 與 T006 可以並行執行，因為它們涉及不同的檔案。

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成 T003 (建立資源檔案)。
2. 完成 T004 (建置驗證)。
3. 完成 T005 (啟動驗證)。
4. **STOP and VALIDATE**: 這是解決目前啟動錯誤的核心。
