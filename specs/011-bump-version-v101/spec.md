# Feature Specification: Ensure Version Consistency at v1.1.0

**Feature Branch**: `011-bump-version-v110`  
**Created**: 2026-02-16  
**Status**: Draft  
**Input**: User description: "版本更新為 v1.1.0"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 統一專案版本資訊 (優先級: P1)

作為一名開發者，我希望專案的所有版本標示（包括 `build.gradle` 與 `config.yml` 內的註解）都統一為 `1.1.0`，以避免混淆並確保發布資訊的專業性。

**優先級原因**: 保持版本號一致是維護專案信譽與技術支援的基礎。

**獨立測試方法**: 
1. 檢查 `build.gradle` 內容。
2. 檢查 `src/main/resources/config.yml` 的版本註解。
3. 執行 `./gradlew build` 驗證 JAR 檔案名稱。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** `config.yml` 中仍標示為舊版本, **When (當)** 執行更新後, **Then (則)** 該檔案註解應顯示 `Version: 1.1.0`。
2. **Given (已知)** 版本已統一, **When (當)** 進行專案建置時, **Then (則)** 產出的 JAR 檔案名稱應為 `Welcome-1.1.0.jar`。

---

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須確保 `build.gradle` 中的 `version` 屬性為 `1.1.0`。
- **FR-002**: 系統必須將 `src/main/resources/config.yml` 檔案開頭的 `# Version:` 註解更新為 `1.1.0`。
- **FR-003**: 系統必須確保 `README.md` 中提到的版本遷移說明與當前版本一致。

### Key Entities

- **Project Version**: 專案的全域版本識別碼。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: `build.gradle` 檔案中包含字串 `version = '1.1.0'`。
- **SC-002**: `src/main/resources/config.yml` 包含 `# Version: 1.1.0`。
- **SC-003**: 執行建置後，產出的 Artifact 檔案名稱為 `Welcome-1.1.0.jar`。
