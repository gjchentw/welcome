# Feature Specification: Update Git Origin and Ensure Consistency

**Feature Branch**: `009-update-git-origin`  
**Created**: 2026-02-16  
**Version**: v1.0.0  
**Status**: Draft  
**Input**: User description: "將 git origin 修改為 git@github.com:gjchentw/Welcome.git 要確保 CI/CD 運作 , @README.md 資訊一致性"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## Clarifications

### Session 2026-02-16
- Q: 更新 origin 後，是否需要執行 prune 清理過時的遠端追蹤分支？ → A: 是，執行 `git remote prune origin` 進行清理。
- Q: 除了 README.md，是否需要掃描並更正其他文件中的拼寫一致性？ → A: 是，包含 README.md 與 GEMINI.md。
- Q: README.md 的替換範圍是否應包含文字描述中的專案名稱？ → A: 是，全面替換（URL + 文字描述），確保拼寫統一為 "Welcome"。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 更新遠端倉庫地址 (優先級: P1)

作為一名開發者，我希望將專案的 git origin 修改為正確的 SSH 地址 `git@github.com:gjchentw/Welcome.git`，以便能夠安全地推送和拉取代碼。

**優先級原因**: 這是開發基礎設施的核心變更，影響所有後續的代碼同步。

**獨立測試方法**: 執行 `git remote -v` 驗證地址，並嘗試執行 `git fetch origin` 確保連通性。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 當前 git origin 地址為舊地址, **When (當)** 執行修改指令後, **Then (則)** `git remote -v` 應顯示 `git@github.com:gjchentw/Welcome.git`。
2. **Given (已知)** 已更新為新地址, **When (當)** 執行連通性測試時, **Then (則)** 應能成功與遠端伺服器建立連線。

---

### 使用者故事 2 - 確保 README 資訊一致性 (優先級: P2)

作為一名使用者或開發者，我希望 `README.md` 中的所有連結（如 Badge、Repo 連結、安裝指引）都指向新的倉庫地址，避免誤導。

**優先級原因**: 保持文件準確性是「專案憲法」的要求，直接影響專案的專業形象。

**獨立測試方法**: 掃描 `README.md` 中的關鍵字並手動點擊連結驗證。

**驗收場景**:

1. **Given** `README.md` 包含舊的 repository 連結, **When** 執行更新後, **Then** 所有連結應改為指向 `gjchentw/Welcome`。

---

### 使用者故事 3 - 驗證 CI/CD 流程 (優先級: P1)

作為一名維運人員，我希望在更改 origin 後，GitHub Actions 仍然能正常觸發並成功執行建置與測試任務。

**優先級原因**: 確保開發流程的自動化品質門禁不受基礎設施變更影響。

**獨立測試方法**: 推送一個測試 commit 並觀察 GitHub Actions 頁面的執行結果。

**驗收場景**:

1. **Given** git origin 已變更, **When** 推送代碼至 `main` 或功能分支時, **Then** GitHub Actions 應能正確啟動並完成 `gradle test` 任務。

---

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須將本地 git 倉庫的 `origin` 遠端地址更新為 `git@github.com:gjchentw/Welcome.git`。
- **FR-002**: 系統必須檢查並更新 `.github/workflows/` 下的所有 YAML 檔案，確保其中的 repository 參考（如有硬編碼）已同步。
- **FR-003**: 系統必須掃描 `README.md` 並將所有 `gjchentw/welcome` (不分大小寫) 的連結更新為 `gjchentw/Welcome`，同時將文字內容中的 "wellcome" 替換為 "Welcome"。
- **FR-004**: 系統必須確保 CI/CD 中的 Status Badge 連結在 `README.md` 中指向正確的分支與路徑。
- **FR-005**: 系統必須掃描 `GEMINI.md` 並同步修正專案名稱的拼寫一致性。
- **FR-006**: 系統必須在更新地址後執行 `git remote prune origin` 以維持本地分支狀態整潔。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: `git remote get-url origin` 回傳值精確匹配 `git@github.com:gjchentw/Welcome.git`。
- **SC-002**: `README.md` 中 100% 的舊 repository 連結已被替換。
- **SC-003**: 在變更地址後，至少一次 GitHub Action 執行成功且無因路徑錯誤導致的失效。
