# Feature Specification: Fix Missing config.yml Resource

**Feature Branch**: `005-fix-config-resource`  
**Created**: 2026-02-15  
**Status**: Draft  
**Input**: User description: "修正問題：插件啟用時找不到內嵌的 config.yml 資源檔案"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 插件正常啟動 (優先級: P1)

當伺服器管理員將插件安裝到伺服器並啟動時，插件應能正確加載而不會拋出錯誤，並在插件資料夾中生成預設的設定檔。

**優先級原因**: 這是插件運作的基礎。如果插件無法啟動，則所有功能都無法使用。

**獨立測試方法**: 透過啟動伺服器並檢查控制台日誌，確認沒有 `IllegalArgumentException` 錯誤，且 `plugins/wellcome/config.yml` 已成功生成。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 插件 JAR 檔案已放置在 `plugins` 資料夾中, **When (當)** 伺服器啟動時, **Then (則)** 插件應顯示為 `Enabled` 且控制台中無相關錯誤。
2. **Given (已知)** `plugins/wellcome/` 資料夾中不存在 `config.yml`, **When (當)** 插件啟用時, **Then (則)** 系統應自動從 JAR 中提取並建立該檔案。

---

### 使用者故事 2 - 讀取預設設定 (優先級: P2)

插件啟用後，應能正確讀取 `config.yml` 中的預設數值，供後續功能使用。

**優先級原因**: 確保配置管理系統（ConfigManager）能正常工作。

**獨立測試方法**: 在插件程式碼中列印出讀取到的設定值，或觀察依賴於設定的功能是否運作。

**驗收場景**:

1. **Given** 預設的 `config.yml` 已生成, **When** 插件讀取設定時, **Then** 讀取到的數值應與內嵌的資源檔案一致。

---

### 邊際情況 (Edge Cases)

- **config.yml 已存在**: 如果檔案已存在於資料夾中，系統不應覆蓋它。
- **JAR 損壞**: 如果 JAR 中確實缺少資源，應有友好的錯誤處理（雖然本規格的目標就是修復此點）。

## 指令與權限 (Commands & Permissions) *(必填)*

本修復不涉及新指令或權限。

## 設定需求 (Configuration Requirements) *(必填)*

- **config.yml**: 需要包含基本的設定結構，例如版本號或功能開關。

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須在插件 JAR 檔案的根目錄中包含 `config.yml` 資源。
- **FR-002**: `ConfigManager` 必須能成功調用 `JavaPlugin.saveDefaultConfig()`。
- **FR-003**: 系統必須確保在 `onEnable` 階段完成設定檔的初始化。

### Key Entities

- **Config File**: 儲存於磁碟的實體檔案，用於持久化設定。
- **Embedded Resource**: 封裝在 JAR 內的預設檔案。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 插件在 Paper/Spigot 伺服器上啟用時不再拋出 `IllegalArgumentException`。
- **SC-002**: 插件資料夾內成功生成正確的 `config.yml`。
- **SC-003**: 啟動時間不受資源加載影響（維持在標準範圍內）。
