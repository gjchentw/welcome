# Tasks: Ensure Version Consistency at v1.1.0

**Input**: Design documents from `/specs/011-bump-version-v101/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, quickstart.md

**測試**: 透過檢查檔案內容與建置結果驗證。

**Organization**: Tasks are grouped by logical steps to ensure consistency.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

---

## 第一階段：設置 (Setup)

**目的**: 確認核心版本定義。

- [X] T001 確認 `build.gradle` 中的 `version` 為 `1.1.0`

---

## 第二階段：基礎建設 (Foundational)

**目的**: 同步各處版本資訊。

- [X] T002 [P] 更新 `src/main/resources/config.yml` 開頭的 `# Version:` 註解為 `1.1.0`
- [X] T003 [P] 更新 `src/test/resources/plugin.yml` 中的 `version:` 為 `1.1.0`
- [X] T004 更新 `README.md` 中的版本遷移說明文字，確保與 `1.1.0` 一致

---

## 第三階段：使用者故事 1 - 統一專案版本資訊 (優先級: P1) 🎯 MVP

**目標**: 確保建置產出符合版本規範。

**獨立測試**: 執行 `./gradlew clean build` 並檢查 `build/libs/` 下的 JAR 檔案名稱。

### 實作 (Implementation)

- [X] T005 [US1] 執行 `./gradlew clean build` 進行完整建置驗證
- [X] T006 [US1] 檢查建置後的 `build/libs/Welcome-1.1.0.jar` 是否存在且名稱正確
- [X] T007 [US1] 使用解壓縮工具或指令檢查 JAR 內的 `plugin.yml` 版本欄位是否為 `1.1.0`

**檢查點**: 版本號已在全專案達成一致。

---

## 第四階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [X] T008 [P] 執行 `grep -r "1.1.0" .` 確保無遺漏的舊版本參考 (排除 specs 與 build 目錄)

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，可立即開始。
- **Foundational (Phase 2)**: 依賴 Setup 完成。
- **User Story 1 (US1)**: 依賴 Foundational 完成以進行驗證。
- **Polish (Final Phase)**: 依賴所有任務完成。

### Parallel Opportunities

- T002, T003 可以並行執行。

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成版本號同步。
2. 建置並驗證 Artifact。

---

## Notes

- 專案名稱統一為 "Welcome"。
- 版本號嚴格遵循 `1.1.0`。
