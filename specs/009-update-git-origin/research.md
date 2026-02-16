# Research: Update Git Origin and Text Consistency

## 1. Git Remote Update
- **指令**: `git remote set-url origin git@github.com:gjchentw/Welcome.git`
- **清理**: `git remote prune origin`
- **驗證**: `git remote -v` 應顯示正確的地址（區分大小寫）。

## 2. 文件掃描與替換 (README.md, GEMINI.md)
- **Repo 連結**: 
  - 舊: `gjchentw/welcome`
  - 新: `gjchentw/Welcome`
  - 注意: GitHub 連結雖然不區分大小寫，但為了一致性應更新。
- **文字替換**:
  - `wellcome` (不分大小寫) -> `Welcome`
  - 涉及項目: `GEMINI.md` 標題、`README.md` 中的專案描述與歷史說明。
- **Status Badge**:
  - 連結: `https://github.com/gjchentw/welcome/actions/workflows/ci.yml/badge.svg` -> `https://github.com/gjchentw/Welcome/actions/workflows/ci.yml/badge.svg`
  - 點擊跳轉連結也需更新。

## 3. CI/CD 流程驗證
- **檔案**: `.github/workflows/ci.yml` (或其他 YAML)
- **檢查點**: 檢查是否有硬編碼的 repository 名稱。
- **測試方式**: 修改後推送，觀察 GitHub Actions。

## 決策 Rationale
- 選擇使用 `sed` 進行批量替換，因為變更模式固定且風險低。
- 對於 `README.md` 中提到「舊版本 (Wellcome)」的文字，根據 FR-003 與 Clarification，統一改為 "Welcome" 以符合專案新命名規範，避免拼寫混亂。

## 待確認事項 (Resolved)
- 是否需要 prune？ 是。
- 是否包含文字描述？ 是，全面替換。
- 拼寫統一為 "Welcome"。
