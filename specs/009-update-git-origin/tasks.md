# Tasks: Update Git Origin and Ensure Consistency

**Input**: Design documents from `/specs/009-update-git-origin/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**測試**: 本功能涉及 Git 基礎設施與文件修正，測試將透過 CLI 指令（如 `git remote -v`, `grep`, `git fetch`）與 GitHub Actions 執行結果進行驗證。

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## 第一階段：設置 (Setup) - 共用基礎設施

**目的**: 初始化與備份，確保變更前有還原點。

- [X] T001 建立當前配置備份（`.git/config`, `README.md`, `GEMINI.md`）至臨時目錄

---

## 第二階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 本功能無特定程式碼層級的基礎建設。

- [X] T002 確認具備 `git` 與 `sed` 指令執行環境

---

## 第三階段：使用者故事 1 - 更新遠端倉庫地址 (優先級: P1) 🎯 MVP

**目標**: 將 Git Origin 切換至正確的 SSH 地址並清理分支。

**獨立測試**: 執行 `git remote -v` 驗證地址，並執行 `git fetch origin` 測試連通性。

### 實作 (Implementation)

- [X] T003 [US1] 執行 `git remote set-url origin git@github.com:gjchentw/Welcome.git` 更新遠端地址
- [X] T004 [US1] 執行 `git remote prune origin` 清理本地過時的遠端追蹤分支
- [X] T005 [US1] 執行 `git fetch origin` 驗證新地址的連通性與存取權限

**檢查點**: Origin 地址已成功更新且可通訊。

---

## 第四階段：使用者故事 2 - 確保 README 資訊一致性 (優先級: P2)

**目標**: 修正 `README.md` 與 `GEMINI.md` 中的拼寫錯誤與 Repository 連結。

**獨立測試**: 使用 `grep` 掃描關鍵字，確保無 "wellcome" 或舊版連結。

### 實作 (Implementation)

- [X] T006 [P] [US2] 在 `README.md` 中將所有 `gjchentw/welcome` 連結替換為 `gjchentw/Welcome`
- [X] T007 [P] [US2] 在 `README.md` 中將所有 "wellcome" (不分大小寫) 替換為 "Welcome"
- [X] T008 [P] [US2] 在 `GEMINI.md` 中將標題與內容中的 "wellcome" (不分大小寫) 替換為 "Welcome"
- [X] T009 [US2] 驗證 `README.md` 與 `GEMINI.md` 的文字拼寫一致性

**檢查點**: 所有對外文件資訊已更正。

---

## 第五階段：使用者故事 3 - 驗證 CI/CD 流程 (優先級: P1)

**目標**: 確保 GitHub Actions 參考正確且 Status Badge 運作正常。

**獨立測試**: 推送 Commit 並觀察 GitHub Actions 的執行狀態。

### 實作 (Implementation)

- [X] T010 [US3] 掃描並更新 `.github/workflows/` 下 YAML 檔案中的硬編碼 repository 參考
- [X] T011 [US3] 確保 `README.md` 中的 Status Badge 連結指向新的 repository 路徑與正確分支
- [X] T012 [US3] 提交變更並推送至新 origin 的 `009-update-git-origin` 分支
- [X] T013 [US3] 於遠端伺服器觀察並確認 CI/CD (GitHub Actions) 成功執行

**檢查點**: 自動化流程不受變更影響，Badge 顯示正常。

---

## 第六階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [X] T014 [P] 移除 T001 建立的臨時備份檔案
- [X] T015 再次執行 `git remote -v` 進行最後確認

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: 無依賴，可立即開始。
- **Foundational (Phase 2)**: 依賴 Setup 完成。
- **User Story 1 (US1)**: 基礎設施核心，必須優先完成。
- **User Story 2 (US2)**: 依賴基礎環境，與 US1 無嚴格順序但建議在 US1 後。
- **User Story 3 (US3)**: 依賴 US1 (Repo 地址) 與 US2 (Badge 修正)。
- **Polish (Final Phase)**: 依賴所有使用者故事完成。

### User Story Dependencies

- **US1 (P1)**: 基礎變更。
- **US2 (P2)**: 獨立文件修正。
- **US3 (P1)**: 依賴 US1。

### Parallel Opportunities

- T006, T007, T008 (US2) 涉及不同檔案或獨立替換規則，可並行執行。
- T014 (Polish) 可在確認穩定後隨時執行。

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. 完成 Phase 1 & 2。
2. 完成 US1 (更新 Origin)。
3. **驗證**: `git remote -v` 顯示正確。

### Incremental Delivery

1. 完成 Origin 更新 (US1) -> 基礎設施就緒。
2. 完成文件修正 (US2) -> 資訊一致性達成。
3. 完成 CI 驗證 (US3) -> 自動化流程確認。

---

## Notes

- 變更 Git Origin 前請確保具有目標倉庫的推送權限 (SSH Key)。
- `sed` 替換時需注意大小寫敏感度，建議使用 `[Ww]ellcome` 模式。
- 專案名稱統一為 "Welcome"，避免混用 "Wellcome"。
