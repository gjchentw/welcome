# Feature Specification: 初始化 Spigot 插件核心架構

**Feature Branch**: `001-spigot-init`  
**Created**: 2026-02-15  
**Status**: Draft  
**Input**: User description: "初始化 Spigot 插件核心架構，包含 Java 25 Maven 設定、GitHub Actions CI/ 流程、MIT License、以及基礎的設定檔管理系統 (ConfigManager)。"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

<!--
  重要：使用者故事應根據重要性進行優先排序 (P1, P2, P3)。
  每個使用者故事必須是 **可獨立測試的 (Independently Testable)**。
  這意味著如果你只實作其中一個故事，你仍然應該擁有一個可交付價值的小型可行性產品 (MVP)。
  
  將每個故事視為獨立的功能切片：
  - 可獨立開發
  - 可獨立測試 (無需依賴其他未完成的故事)
  - 可獨立部署 (或透過設定檔開關控制)
-->

### 使用者故事 1 - 基礎建置與 CI/CD (優先級: P1)

作為一名開發者，我需要一個可以正確編譯並自動化發布的基礎專案結構，以便開始開發具體功能。

**優先級原因**: 這是所有後續開發的基礎，沒有正確的建置與 CI 流程，無法進行有效率的開發。

**獨立測試方法**: 
1.  在本機執行 `mvn clean package` (或 Gradle 等效指令)，確認使用 Java 25 編譯成功並產出 JAR 檔。
2.  推送到 GitHub 後，確認 GitHub Actions Workflow 成功執行並產出 `nightly-` 版本。

**驗收場景 (Acceptance Scenarios)**:

1.  **Given (已知)** 專案剛被 checkout 下來, **When (當)** 執行建置指令, **Then (則)** 應成功產出包含 plugin.yml 的 JAR 檔案。
2.  **Given (已知)** 有新的 commit 被 push 到 master, **When (當)** GitHub Actions 觸發, **Then (則)** 應自動建置並上傳 `nightly-sha` 構建產物。
3.  **Given (已知)** 專案根目錄, **When (當)** 檢查檔案列表, **Then (則)** 應存在 LICENSE 檔案且內容為 MIT License。

---

### 使用者故事 2 - 基礎設定檔管理 (優先級: P1)

作為一名伺服器管理員，我需要插件在首次執行時自動生成設定檔，並能透過指令重載設定，以便調整插件行為。

**優先級原因**: 符合憲法「配置與指令驅動」原則，確保所有後續功能都有統一的設定管理機制。

**獨立測試方法**: 
1.  將插件放入伺服器 plugins 資料夾並啟動，確認 `config.yml` 與 `messages.yml` 自動生成。
2.  修改設定檔後執行 `/wellcome reload`，確認新設定生效。

**驗收場景**:

1.  **Given** 插件首次安裝, **When** 伺服器啟動, **Then** `plugins/Wellcome/config.yml` 與 `messages.yml` 應自動建立。
2.  **Given** 伺服器運行中, **When** 修改 `config.yml` 並執行重載指令, **Then** 插件應讀取新的設定值並回傳成功訊息。
3.  **Given** 執行無效指令, **When** 玩家輸入 `/wellcome invalid`, **Then** 應顯示正確的指令幫助訊息。

---

### 邊際情況 (Edge Cases)

<!--
  需要採取行動：請填寫與此功能相關的邊際情況。
-->

- 當設定檔格式錯誤 (YAML syntax error) 時，插件應記錄錯誤並使用預設值，或是安全地停止載入，避免崩潰。
- 當權限不足的使用者嘗試執行重載指令時，應顯示無權限訊息。

## 澄清 (Clarifications)

### Session 2026-02-15
- Q: 建置工具選擇? → A: Gradle (build.gradle)
- Q: Spigot API 版本? → A: 1.21.4

## 指令與權限 (Commands & Permissions) *(必填)*

<!--
  定義此功能引入的新指令與對應權限節點。
-->

### 指令 (Syntax)

- `/wellcome reload`: 重載設定檔 (config.yml, messages.yml)。
- `/wellcome help`: 顯示幫助訊息。

### 權限 (Permission Nodes)

- `wellcome.admin`: 允許使用所有管理指令 (包含 reload)。預設: op。

## 設定需求 (Configuration Requirements) *(必填)*

<!--
  定義需要在 config.yml 或 messages.yml 中暴露的可配置項目。
-->

### config.yml
- **debug-mode**: (boolean) 是否開啟除錯模式。預設: `false`。
- **prefix**: (string) 插件訊息的前綴。預設: `&8[&bWellcome&8] `。

### messages.yml
- **reload-success**: (string) 重載成功訊息。
- **no-permission**: (string) 無權限訊息。
- **help-message**: (string list) 幫助訊息內容。

## 需求規格 (Requirements) *(必填)*

### 功能需求 (Functional Requirements)

- **FR-001**: 專案必須使用 **Gradle** 建置系統 (build.gradle)。
- **FR-002**: 專案必須設定 **Java 25** 為編譯與目標版本。
- **FR-003**: 必須包含 `.github/workflows/ci.yml`，定義 `push` 與 `tag` 的觸發流程。
- **FR-004**: 專案根目錄必須包含 **MIT License** 的 `LICENSE` 檔案。
- **FR-005**: 必須實作 `ConfigManager` 類別，負責載入、儲存與獲取設定值。
- **FR-006**: 必須實作主類別 (繼承 `JavaPlugin`)，並在 `onEnable` 初始化 ConfigManager。
- **FR-007**: 必須實作 `plugin.yml`，包含 `main`, `version`, `api-version` (1.21), `commands`, `permissions`。

### 關鍵實體 (Key Entities)

- **ConfigManager**: 單例模式或依賴注入，管理 `FileConfiguration`。
- **MainPlugin**: 插件進入點。

## 成功標準 (Success Criteria) *(必填)*

<!--
  需要採取行動：定義可衡量的成功標準。
  這些標準必須是與技術無關且可衡量的。
-->

### 可衡量成果

- **SC-001**: 在安裝 Java 25 的環境下，執行建置指令能成功產出 JAR 檔。
- **SC-002**: GitHub Actions 流程能成功執行並顯示綠色勾勾 (Pass)。
- **SC-003**: 插件安裝到 Spigot 伺服器後，能正確載入無錯誤，並生成設定檔。
- **SC-004**: `/wellcome reload` 指令能正確更新記憶體中的設定值。
