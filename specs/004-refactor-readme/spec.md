# Feature Specification: 重構 README.md 文件

**Feature Branch**: `004-refactor-readme`  
**Created**: 2026-02-15  
**Status**: Draft  
**Input**: User description: "重構 @README.md 文件，專案目的就是在建立一個投票自動加入白名單的 Plugin，透過 CI/CD 自動測試和 build release asset 提供信任度。請根據憲法的條件進行文件重構。"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 作為使用者閱讀 README (優先級: P1)

作為一名對本插件感興趣的伺服器管理員，我希望一進入專案首頁就能清楚了解專案目的、目前的健康狀態 (CI/CD) 以及如何安裝使用，以便我能快速決定是否採用。

**優先級原因**: README 是專案的面面，直接影響使用者的第一印象與信任度。

**獨立測試方法**: 
1. 閱讀 README.md 檔案。
2. 確認是否包含 Build/Test Badge。
3. 確認是否清晰描述「投票自動加入白名單」的核心功能。
4. 確認是否包含安裝與配置說明。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 專案根目錄, **When (當)** 開啟 `README.md`, **Then (則)** 頂部應顯示 GitHub Actions 的 Build/Test 狀態標章。
2. **Given (已知)** `README.md`, **When (當)** 閱讀「專案目的」章節, **Then (則)** 應明確提及「投票自動加入白名單」功能。
3. **Given (已知)** `README.md`, **When (當)** 檢查「開發流程」章節, **Then (則)** 應包含關於 Spec-Kit SDD 工作流的說明。

## 澄清 (Clarifications)

### Session 2026-02-15
- Q: GitHub 儲存庫 URL 確認？ → A: `https://github.com/gjchentw/welcome`

## 需求規格 (Requirements) *(必填)*

### 功能需求 (Functional Requirements)

- **FR-001**: **狀態標章 (Status Badges)**: 必須在頂部加入 GitHub Actions 的建置與測試狀態標章。
- **FR-002**: **專案目的說明**: 必須清楚描述專案為一個投票自動加入白名單的 Spigot 插件。
- **FR-003**: **開發環境配置**: 必須更新開發環境說明，強調使用 Java 25 和 Gradle。
- **FR-004**: **安裝配置說明**: 必須包含 Wellcome Spigot plugin 的安裝與 `config.yml`、`messages.yml` 的配置說明。
- **FR-005**: **開發流程說明**: 必須新增「開發流程 (Development Workflow)」章節，說明本專案採用 Spec-Kit 進行 SDD 的流程。
- **FR-006**: **信任度強調**: 應在適當位置強調透過 CI/CD 自動測試與 Build Release Asset 提供的穩定性與信任度。

### 關鍵實體 (Key Entities)

- **README.md**: 本次重構的主要對象。

## 成功標準 (Success Criteria) *(必填)*

### 可衡量成果

- **SC-001**: `README.md` 完整包含憲法規定的四個必要項目：狀態標章、專案目的、開發環境、安裝配置。
- **SC-002**: 標章連結正確且能顯示目前的 CI/CD 狀態。
- **SC-003**: 文案內容精確反映目前已實作的「投票白名單」功能。
- **SC-004**: 包含開發流程章節，符合憲法原則七。
