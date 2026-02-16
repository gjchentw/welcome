# Tasks: Internationalization (i18n) Support

**Input**: Design documents from `/specs/012-i18n-support/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**測試**: 根據專案憲法，測試是 **強制性的**。使用 **JUnit 5** 撰寫測試，並透過 `./gradlew test` 執行。

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

---

## 第一階段：設置 (Setup) - 資源初始化

**目的**: 建立語系資料夾結構並準備預設語系檔。

- [X] T001 建立 `src/main/resources/lang/` 目錄用於存放語系檔
- [X] T002 [P] 建立 `src/main/resources/lang/en_US.yml` 並遷移現有 `messages.yml` 內容
- [X] T003 [P] 建立其他支援語系檔：`zh_TW.yml`, `zh_CN.yml`, `ja_JP.yml`, `la_US.yml` 並完成翻譯
- [X] T004 修改 `src/main/resources/config.yml` 加入 `language: en_US` 選項

---

## 第二階段：基礎建設 (Foundational) - i18n 核心實作

**目的**: 建立 `LanguageManager` 並重構訊息檢索邏輯。

**⚠️ 關鍵**: 在此階段完成前，無法開始實作多語言切換功能。

### 測試 (Test-First) ⚠️

- [X] T005 [P] 撰寫 `LanguageManager` 的單元測試 (包含 Fallback 邏輯)，於 `src/test/java/com/welcome/managers/LanguageManagerTest.java`

### 實作 (Implementation)

- [X] T006 實作 `LanguageManager.java` 負責語系檔載入與 Fallback 檢索，於 `src/main/java/com/welcome/managers/LanguageManager.java`
- [X] T007 修改 `ConfigManager.java` 以支援讀取 `language` 設定並初始化 `LanguageManager`
- [X] T008 修改 `WelcomePlugin.java` 註冊 `LanguageManager` 並處理重載邏輯

---

## 第三階段：使用者故事 1 - 多語言訊息顯示 (優先級: P1) 🎯 MVP

**目標**: 使插件訊息能根據設定顯示對應語言。

**獨立測試**: 透過 `/welcome reload` 後切換語言，觀察訊息輸出。

### 實作 (Implementation)

- [X] T009 [US1] 修改 `WelcomeCommand.java` 使用 `LanguageManager` 檢索訊息，取代原有的 `configManager.getMessage`
- [X] T010 [US1] 修改 `PlayerJoinListener.java` 使用 `LanguageManager` 檢索歡迎訊息
- [X] T011 [US1] 確保所有的訊息變數（如 `{target}`）在替換時能正確運作

**檢查點**: 遊戲內訊息已成功實現多語言切換。

---

## 第四階段：使用者故事 2 - 全球化的專案說明文件 (優先級: P2)

**目標**: 建立英文版 README 並保留中文版。

**獨立測試**: 檢查 GitHub 上的檔案連結與內容正確性。

### 實作 (Implementation)

- [X] T012 [US2] 建立 `README_zh_TW.md` 並移動現有的正體中文內容
- [X] T013 [US2] 重寫 `README.md` 為美式英文，並在頂部加入 `[繁體中文](README_zh_TW.md)` 連結

**檢查點**: 專案文件已符合全球化需求。

---

## 第五階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [X] T014 [P] 移除 `src/main/resources/messages.yml` (已由 `lang/` 取代)
- [X] T015 更新 `/welcome help` 的指令描述與說明文字的多語言支援
- [X] T016 執行全專案測試 `./gradlew test` 確保變更後無退化問題

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，可立即開始。
- **Foundational (Phase 2)**: 依賴 Setup 完成，提供核心 API。
- **User Stories (Phase 3+)**: 依賴 Foundational 完成以應用多語言邏輯。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### Parallel Opportunities

- T002, T003 (語系檔準備)
- T005 (測試撰寫)
- T012, T013 (README 翻譯)

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成 Phase 1 & 2。
2. 實作 US1 (訊息切換)。
3. **驗證**: 確保 `en_US` 與 `zh_TW` 可正常切換。

---

## Notes

- 翻譯內容請確保精準，尤其是美式拉丁文 (`la_US`)。
- Fallback 機制必須穩定，確保缺漏鍵值時不會導致空指標。
- 專案名稱統一為 "Welcome"。
