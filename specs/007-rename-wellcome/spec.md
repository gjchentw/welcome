# Feature Specification: Rename Project from Wellcome to Welcome

**Feature Branch**: `007-rename-wellcome`  
**Created**: 2026-02-16  
**Version**: v1.1.0  
**Status**: Draft  
**Input**: User description: "更換 package name 或 class name 從 wellcome 替換為 welcome，掃描所有文件 wellcome 更換為 welcome"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## Clarifications

### Session 2026-02-16
- Q: 文字替換時的大小寫處理規則為何？ → A: 文字替換將採用精確的大小寫映射（如 wellcome → welcome, Wellcome → Welcome），以防止誤傷其他字串並維持命名規範。
- Q: 是否應同步更新 settings.gradle 中的專案名稱？ → A: 專案將同步更新 settings.gradle 中的專案名稱為 welcome，以確保構建產物名稱的一致性。
- Q: 系統應如何處理舊有的「Wellcome」資料資料夾？ → A: 系統將在啟動時自動偵測舊有的「Wellcome」資料夾，並將其更名為「Welcome」以完成數據遷移。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 統一拼寫為 Welcome (優先級: P1)

作為一名開發者與伺服器管理員，我希望專案中所有的程式碼、套件路徑、類別名稱與文件都使用正確的 "Welcome" 拼寫（而非舊有的 "Wellcome"），以提升專案的專業度並減少拼寫錯誤導致的混淆。

**優先級原因**: 這是提升程式碼品質與可維護性的基礎，且涉及整個專案的重構。

**獨立測試方法**: 執行 `./gradlew build` 確保專案在更名後仍能正常編譯並通過所有現有測試。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 專案中存在多個以 `com.wellcome` 開頭的 package, **When (當)** 執行更名程序後, **Then (則)** 所有的 package 應更新為 `com.welcome` 且引用關係正確。
2. **Given (已知)** 專案中存在 `WellcomePlugin` 或 `WellcomeCommand` 等類別, **When (當)** 執行更名程序後, **Then (則)** 這些類別應分別更名為 `WelcomePlugin` 與 `WelcomeCommand`。
3. **Given (已知)** 檔案內容（註釋、日誌訊息、README）中包含 "wellcome" 字串, **When (當)** 掃描並替換後, **Then (則)** 所有合適的出現處皆應變更為 "welcome"。

---

### 邊際情況 (Edge Cases)

- **大小寫保留**: 替換時應區分大小寫，採用一對一變體映射（如 wellcome -> welcome, Wellcome -> Welcome）。
- **外部依賴**: 確保更名不影響 build.gradle 中的外部依賴關係。
- **設定檔路徑**: 系統啟動時將自動將舊有的 `/plugins/Wellcome/` 目錄遷移（更名）至 `/plugins/Welcome/`。

## 指令與權限 (Commands & Permissions) *(必填)*

### 指令 (Syntax)

- `/welcome <action>`: (維持現狀，但確保內部邏輯一致)

### 權限 (Permission Nodes)

- `welcome.use`: 允許使用基本功能 (預設: true)
- `welcome.admin`: 允許使用管理指令與重載 (預設: op)

## 設定需求 (Configuration Requirements) *(必填)*

- **外掛名稱變更**: 在 `plugin.yml` 中將 `name` 從 `Wellcome` 更改為 `Welcome`。
- **專案名稱同步**: 在 `settings.gradle` 中將 `rootProject.name` 從 `wellcome` 更改為 `welcome`。

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須將所有 Java 套件路徑從 `com.wellcome.*` 遷移至 `com.welcome.*`。
- **FR-002**: 系統必須將所有類別名稱中的 "Wellcome" 前綴替換為 "Welcome"。
- **FR-003**: 系統必須掃描專案內所有文字檔案與「檔案名稱」（如 wellcome.yml），根據 Session 2026-02-16 決議之變體映射規則替換所有出現的關鍵字。
- **FR-004**: 系統必須在 `plugin.yml` 中同步更新 `name` 與 `main` 類別路徑。
- **FR-005**: 系統必須實作啟動時的資料夾遷移邏輯 (Wellcome -> Welcome)。
- **FR-006**: 系統必須同步更新 `settings.gradle` 中的專案名稱。
- **FR-007**: 系統必須確保 `README.md` 與其他文件中的專案名稱引用已全數更新。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 專案執行 `./gradlew build` 與 `./gradlew test` 通過率為 100%。
- **SC-002**: 專案目錄下不再存在任何包含 "wellcome" 拼寫的目錄或 Java 類別檔案。
- **SC-003**: 掃描所有原始碼檔案，除授權聲明（如有）外，不應存在 "wellcome" 關鍵字。
