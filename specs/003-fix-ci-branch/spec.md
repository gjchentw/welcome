# Feature Specification: 修正 CI/CD 的 branch 為 main

**Feature Branch**: `003-fix-ci-branch`  
**Created**: 2026-02-15  
**Status**: Draft  
**Input**: User description: "修正 CI/CD 的 branch 是 main"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

<!--
  重要：使用者故事應根據重要性進行優先排序 (P1, P2, P3)。
  每個使用者故事必須是 **可獨立測試的 (Independently Testable)**。
  這意味著如果你只實作其中一個故事，你仍然應該擁有一個可交付價值的小型可行性產品 (MVP)。
-->

### 使用者故事 1 - 修正 GitHub Actions 觸發分支 (優先級: P1)

作為一名開發者，我希望 GitHub Actions 能在 `main` 分支有變動時自動執行建置與測試，而不是已經不存在或不再使用的 `main` 分支。

**優先級原因**: 目前專案的主要分支已改為 `main`，若不修正 CI/CD 設定，自動化流程將無法運作。

**獨立測試方法**: 
1. 將變更 push 到 `main` 分支。
2. 觀察 GitHub Actions 頁面，確認 workflow 正確啟動。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** CI 設定檔 `.github/workflows/ci.yml`, **When (當)** 檢查 `on.push.branches` 設定, **Then (則)** 應包含 `main` 且移除或取代 `main`。
2. **Given (已知)** 專案憲法 `.specify/memory/constitution.md`, **When (當)** 檢查 CI/CD 策略說明, **Then (則)** 應將 `main` 改為 `main`。

---

### 使用者故事 2 - 修正文件與指令中的分支參考 (優先級: P2)

作為一名開發者，我希望所有的文件 (如 README.md) 都能正確引導使用者使用 `main` 分支進行複製或參考。

**優先級原因**: 保持文件一致性，避免新手誤用。

**獨立測試方法**: 
1. 閱讀 README.md 及其他相關文件。
2. 確認所有關於 `main` 的引用都已更正為 `main`。

**驗收場景**:

1. **Given** `README.md`, **When** 檢查「快速開始」中的 git clone 指令, **Then** 應確保其符合目前的預設分支 (通常 clone 不需要指定，但若有提到分支則需更正)。

## 澄清 (Clarifications)

### Session 2026-02-15
- Q: 修改範圍確認？ → A: 全域搜尋並取代。檢查整個專案（包含程式碼註解、指令碼、所有 Markdown 文件）並將 `main` 改為 `main`。

---

### 邊際情況 (Edge Cases)

- 如果目前仍有其他分支依賴 `main` 的標籤或環境變數，需一併確認是否受影響。
- 確保 `git tag` 發布正式版的流程不受影響。

## 指令與權限 (Commands & Permissions) *(必填)*

本功能不涉及新的遊戲內指令。

## 設定需求 (Configuration Requirements) *(必填)*

本功能不涉及 `config.yml` 或 `messages.yml` 的變動。

## 需求規格 (Requirements) *(必填)*

### 功能需求 (Functional Requirements)

- **FR-001**: 修改 `.github/workflows/ci.yml`，將 `on.push.branches` 和 `on.pull_request.branches` 中的 `main` 改為 `main`。
- **FR-002**: 修改 `.specify/memory/constitution.md`，將「開發工作流程」章節中的 `main` 分支參考改為 `main`。
- **FR-003**: 執行全域搜尋並取代，將專案中所有提及 `main` 分支的文字（包含註解、指令碼與文件）更正為 `main`。

### 關鍵實體 (Key Entities)

- **CI/CD Workflow**: GitHub Actions 的自動化流程定義。
- **專案憲法**: 定義開發規範的文件。

## 成功標準 (Success Criteria) *(必填)*

### 可衡量成果

- **SC-001**: GitHub Actions 在 `main` 分支 push 時成功觸發。
- **SC-002**: 專案中不再存在指向舊有 `main` 分支的過時文件說明。
