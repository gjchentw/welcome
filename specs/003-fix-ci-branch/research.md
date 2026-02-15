# Phase 0: Research & Decisions - 修正 CI/CD 分支參考

## 1. 搜尋結果摘要

經全域搜尋，目前專案中提及 `main` 的位置如下：

- **CI/CD 設定**: `.github/workflows/ci.yml` (push 與 pull_request 的觸發分支)。
- **專案文件**: `README.md` (CI/CD 狀態說明)。
- **過往規格/研究文件**: `specs/001-spigot-init/spec.md`, `specs/001-spigot-init/research.md` (僅作紀錄，原則上不修改歷史文件，除非使用者要求全域取代)。
- **專案憲法**: `.specify/memory/constitution.md` (目前內容為泛指 `push`，但應明確標註 `main`)。

## 2. 決策與行動方案

### Decision: 全域取代策略
- **方案**: 將所有與「目前開發分支」相關的 `main` 關鍵字取代為 `main`。
- **例外**: 歷史文件 (`specs/001-*`, `specs/002-*`) 若僅作為過去紀錄可不修改，但為求一致性，本任務將執行全域搜尋取代。

### Decision: CI/CD 觸發修正
- **方案**: 直接修改 `.github/workflows/ci.yml` 中的 `branches` 陣列。
