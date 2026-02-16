# Feature Specification: Rename Build Artifact to Welcome.jar

**Feature Branch**: `008-rename-artifact`  
**Created**: 2026-02-16  
**Version**: v1.0.0  
**Status**: Draft  
**Input**: User description: "產出物請從 welcome.jar 更換為 Welcome.jar"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## Clarifications

### Session 2026-02-16
- Q: 是否應同步更新專案內所有 CI/CD 腳本中對產出物名稱的引用？ → A: 是，同步更新專案內所有 CI/CD 腳本引用，確保發布流程不中斷。
- Q: 產出的 JAR 檔案名稱是否應包含版本號？ → A: 是，產出的 JAR 檔案名稱應包含版本號，遵循 `Welcome-<version>.jar` 的格式。
- Q: 更名範圍是否應涵蓋所有建置產物（如原始碼包、文件包）？ → A: 是，所有相關產物皆應統一更名為以 `Welcome` 為基礎名稱。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 規範化建置產物名稱 (優先級: P1)

作為一名開發者或維運人員，我希望建置外掛後產出的 JAR 檔案名稱為 `Welcome.jar` 而不是 `welcome.jar`，以符合專案命名的品牌一致性。

**優先級原因**: 這是使用者明確要求的名稱變更，直接影響交付產物的識別度。

**獨立測試方法**: 執行 `./gradlew build` 並檢查 `build/libs/` 目錄下生成的檔案名稱。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 建置環境已就緒, **When (當)** 執行建置指令時, **Then (則)** 產出的 JAR 檔案名稱必須符合 `Welcome-<version>.jar` 模式。
2. **Given (已知)** 舊有的建置產物為 `welcome.jar` 或 `welcome-<version>.jar`, **When (當)** 執行清理並重新建置後, **Then (則)** 不應再出現以小寫 `welcome` 開頭的 JAR 檔案。

---

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 建置系統必須設定產出物的基礎名稱 (archivesBaseName) 為 `Welcome`。
- **FR-002**: 產出的主程式檔名稱必須遵循 `Welcome-<version>.jar` 格式。
- **FR-003**: 產出物名稱必須區分大小寫，首字母必須為大寫 'W'。
- **FR-004**: 所有的附屬建置產物（如 `-sources.jar`, `-javadoc.jar`）也必須同步更名為以 `Welcome` 開頭。
- **FR-005**: 系統必須掃描並更新所有 CI/CD 工作流文件（如 `.github/workflows/*.yml`），將舊的產出物名稱引用更新為新名稱。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 執行建置後，產出的檔案名稱符合 `Welcome-<version>.jar` 模式的比例為 100%。
- **SC-002**: 建置產出的目錄中不再包含以小寫 `welcome` 開頭的 JAR 檔案。
