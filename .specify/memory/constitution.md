<!--
SYNC IMPACT REPORT
- Version: 1.7.0 → 1.8.0
- List of modified principles:
  - 原則六：文件完整性 (Comprehensive Documentation): [更新] 新增 Build/Test Badge 要求。
  - 開發工作流程 (Development Workflow): [更新] 明確 CI/CD 必須包含 gradle test。
- Templates Requiring Updates:
  - ✅ .specify/templates/tasks-template.md (更新 T000b 和 T000c)
  - ✅ .specify/templates/plan-template.md (加入 Badge 檢查)
-->
# wellcome 憲法 (Spigot Plugin)

## 核心原則

### 原則一：非同步優先 (Async-First)
**絕對禁止阻塞主執行緒 (Main Thread)**。所有 I/O 操作（資料庫存取、網路請求）與繁重計算任務，**必須** 在非同步執行緒 (Async Thread) 中執行，僅在需要呼叫 Bukkit API 修改世界/玩家狀態時才同步回主執行緒。

### 原則二：配置與指令驅動 (Config & Command Driven)
所有遊戲機制參數、訊息文字 (Messages) 與功能開關，**必須** 透過設定檔 (如 `config.yml`, `messages.yml`) 進行管理，不可寫死 (Hardcode)。所有功能操作皆需提供指令介面，並支援控制台 (Console) 執行。

### 原則三：權限控管 (Strict Permissions)
所有指令、功能開關與特殊操作，**必須** 定義對應的權限節點 (Permission Nodes)。預設權限應為 `OP` 或 `False` (拒絕)，確保伺服器安全性。

### 原則四：資源生命週期管理 (Resource Lifecycle)
必須嚴格管理插件生命週期。`onDisable` 必須確保取消所有排程任務 (Tasks)、關閉資料庫連線、並儲存所有暫存資料。禁止發生重載 (Reload) 後導致的記憶體洩漏 (Memory Leak) 或資源佔用。

### 原則五：核心邏輯可測試 (Testable Core Logic)
業務邏輯 (Business Logic) 應與 Spigot/Bukkit API 盡量分離。每個模組與 Library **必須** 撰寫 **JUnit** 單元測試，並透過 **Gradle** 指令 (`gradle test`, `gradle build`) 進行自動化測試與建置。

### 原則六：文件完整性 (Comprehensive Documentation)
專案根目錄的 `README.md` **必須** 隨時保持最新狀態，並至少包含以下四個必要項目：
1.  **狀態標章 (Status Badges)**: 顯示最近一次 Build 和 Test 的狀態 (GitHub Actions Badge)。
2.  **專案目的**: 清楚說明此插件的功能與用途。
3.  **開發環境**: 明確列出開發需求 (JDK 25, Maven/Gradle, IDE 設定)。
4.  **安裝配置**: 詳細說明如何安裝此插件 (Spigot plugin install) 及設定方式 (config.yml)。

### 原則七：規格驅動開發 (Spec-Driven Development)
本專案優先使用 **Spec-Kit** 進行規格驅動開發 (SDD)。任何功能開發必須先撰寫規格 (`/speckit.specify`)，再制定計畫 (`/speckit.plan`)，最後才進行實作。`README.md` **必須** 包含「開發流程」章節，說明本專案採用 Spec-Kit 進行 SDD 的工作流。

## 額外限制
*   **授權 (License)**: 此專案採用 **MIT License** 進行授權。所有原始碼檔案應在適當位置包含授權聲明，並必須在專案根目錄包含 `LICENSE` 檔案。
*   **Java 版本 (Java Version)**: 此專案 **最低限度必須使用 Java 25 (JDK 25)** 進行開發、編譯與建置。請確保開發環境與目標伺服器皆支援此版本。
*   **版本相容性**: 優先使用 Spigot API，避免使用 NMS (net.minecraft.server) 程式碼，除非有封裝良好的版本適配層。
*   **相依性**: 清楚定義 `plugin.yml` 中的 `depend` 與 `softdepend`。

## 開發工作流程
*   **CI/CD 策略**: 採用 GitHub Actions 進行自動化。
    *   **流程**: 每個 workflow 執行時 **必須** 包含 `gradle test` 步驟以確保測試通過。
    *   **Nightly Build**: 每次 `push` 自動建置並發布 `nightly-${git-commit-hash}` 版本。
    *   **Release Build**: 每次 `git tag` (格式 `v*.*.*`) 自動建置並發布正式版。
*   PR 必須包含設定檔變更說明。
*   任何涉及 NMS 的變更都需要額外的相容性審查。

## 治理
本憲法高於所有其他實踐；任何修訂都需要文件記錄、批准和遷移計畫。所有提取請求(PR)和審查都必須驗證是否符合本憲法。複雜性必須有充分理由。

**版本**: 1.8.0 | **批准日期**: 2026-02-15 | **最後修訂**: 2026-02-15
