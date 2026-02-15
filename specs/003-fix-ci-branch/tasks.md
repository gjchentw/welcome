---

description: "Task list for fixing CI/CD branch references"
---

# Tasks: 修正 CI/CD 的 branch 為 main

**Input**: Design documents from `specs/003-fix-ci-branch/`
**Prerequisites**: spec.md, plan.md, research.md

## 第一階段：核心設定修正 (Core Configuration)

**目的**: 確保 CI/CD 在正確的分支觸發

- [x] T001 修改 GitHub Actions 觸發分支，將 `main` 改為 `main` 在 `.github/workflows/ci.yml`
- [x] T002 更新專案憲法中的 CI/CD 說明，明確標註 `main` 分支 在 `.specify/memory/constitution.md`

---

## 第二階段：文件一致性修正 (Documentation Alignment)

**目的**: 更新所有文件中的分支參考

- [x] T003 [P] 更新 README.md 中的分支參考 在 `README.md`
- [x] T004 執行全域搜尋並取代專案中殘留的 `main` 為 `main` (包含歷史規格文件) 在整個專案目錄

---

## 第三階段：驗證 (Verification)

**目的**: 確保變更後系統仍可正常運作

- [x] T005 [US1] 執行 `./gradlew build` 確保本地環境無誤 在根目錄
- [x] T006 [US1] 確認 `.github/workflows/ci.yml` 語法正確 在根目錄執行 `action-validator` (若環境支援) 或手動檢視

---

## 依賴關係與執行順序 (Dependencies & Execution Order)

### 階段依賴

- **核心設定 (Phase 1)**: 優先執行，修復自動化流程。
- **文件修正 (Phase 2)**: 可並行執行。
- **驗證 (Phase 3)**: 最後執行。

### 使用者故事依賴

- **使用者故事 1 (P1)**: 依賴 T001, T002, T004, T005。
- **使用者故事 2 (P2)**: 依賴 T003。

---

## 實作策略 (Implementation Strategy)

### 全域掃描

使用 `grep` 或編輯器的取代功能，將除了 `.git` 目錄以外的所有 `main` 字眼更正為 `main`。特別注意 `specs/` 目錄下的歷史紀錄。

---

## 備註 (Notes)

- 執行全域取代時，請小心不要誤傷可能存在的其他含 `main` 的詞彙 (例如 `main-worker` 架構，雖然本專案目前沒有)。
- `T002` 的憲法更新是為了讓未來的功能開發都能遵循新的分支規範。
