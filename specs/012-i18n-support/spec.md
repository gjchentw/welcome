# Feature Specification: Internationalization (i18n) Support

**Feature Branch**: `012-i18n-support`  
**Created**: 2026-02-16  
**Status**: Draft  
**Input**: User description: "導入 i18n ，顯示給玩家看的訊息要支援美式英文(預設)/正體中文/簡體中文/日文/美式拉丁文，同時更新 @README.md 以美式英文面向全球使用者"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 多語言訊息顯示 (優先級: P1)

作為一名來自不同國家的玩家，我希望在遊戲中看到的插件訊息（如投票成功、歡迎訊息等）能以我熟悉的語言顯示，或者至少有美式英文作為通用預設。

**優先級原因**: 提升專案的國際化程度，擴大使用者群。

**獨立測試方法**: 
1. 在 `config.yml` 中切換不同語系設定。
2. 執行插件指令並觀察聊天視窗輸出的語言是否與設定相符。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 設定檔語系設為 `en_US`, **When (當)** 觸發歡迎訊息時, **Then (則)** 訊息應以美式英文顯示。
2. **Given (已知)** 設定檔語系設為 `zh_TW`, **When (當)** 觸發歡迎訊息時, **Then (則)** 訊息應以正體中文顯示。
3. **Given (已知)** 設定檔語系設為 `la_US` (美式拉丁文), **When (當)** 觸發歡迎訊息時, **Then (則)** 訊息應以拉丁文顯示。

---

### 使用者故事 2 - 全球化的專案說明文件 (優先級: P2)

作為一名全球開發者或伺服器管理員，當我瀏覽 GitHub 倉庫時，我希望看到美式英文編寫的 `README.md`，以便我能快速理解專案目的與安裝流程。

**優先級原因**: 英文是國際開發社群的通用語言，有助於專案的全球推廣。

**獨立測試方法**: 打開 `README.md` 並檢查其內容是否已全部翻譯為準確的美式英文。

**驗收場景**:

1. **Given** 舊版 `README.md` 為中文內容, **When** 執行更新後, **Then** 所有的標題、描述、指令表格與指引均應為美式英文。

---

## 指令與權限 (Commands & Permissions) *(必填)*

### 指令 (Syntax)

- `/welcome reload`: [描述] 重新載入設定檔與語系檔。

### 權限 (Permission Nodes)

- `welcome.admin`: 允許使用重載指令 (預設: op)。

## 設定需求 (Configuration Requirements) *(必填)*

- **語系設定**: `language: en_US` (預設值)。
- **支援代碼**: `en_US`, `zh_TW`, `zh_CN`, `ja_JP`, `la_US`。

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須支援外掛訊息的多語言切換。
- **FR-002**: 系統必須提供以下語言的預設語系檔：`en_US` (美式英文), `zh_TW` (正體中文), `zh_CN` (簡體中文), `ja_JP` (日文), `la_US` (美式拉丁文)。
- **FR-003**: 系統必須在 `config.yml` 中提供語系切換選項。
- **FR-004**: 當請求的語系檔不存在或訊息缺失時，系統必須回退 (Fallback) 到 `en_US`。
- **FR-005**: 系統必須將 `README.md` 的內容更換為美式英文，並建立 `README_zh_TW.md` 存放正體中文說明，同時在主文件的顯眼位置提供導覽連結。
- **FR-006**: 系統必須確保所有的訊息變數（如 `{target}`, `{voter}`）在所有語言中都能正確解析。

### Key Entities

- **Locale**: 語言與地區的定義（如 `en_US`）。
- **Message Bundle**: 包含特定語言所有訊息鍵值對的集合。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 訊息切換後的語言正確率達 100%。
- **SC-002**: 若設定了不支援的語言，系統能自動回退至英文且不發生崩潰。
- **SC-003**: `README.md` 內容在英文環境下的語法與專業詞彙正確性。
