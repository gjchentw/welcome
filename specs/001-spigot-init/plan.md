# Implementation Plan: 初始化 Spigot 插件核心架構

**Branch**: `001-spigot-init` | **Date**: 2026-02-15 | **Spec**: [specs/001-spigot-init/spec.md](../spec.md)
**Input**: Feature specification from `specs/001-spigot-init/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

建立專案的核心基礎架構，包含使用 Gradle 進行依賴管理與建置 (Java 25)，設定 GitHub Actions 實現 CI/CD (Nightly/Release)，並實作基礎的設定檔管理系統 (ConfigManager) 與指令處理器。這將為後續功能開發提供一個標準化、自動化的開發環境。

## 技術背景 (Technical Context)

<!--
  需要採取行動：請根據此 Spigot 插件專案替換以下內容。
  這裡的結構是作為迭代過程的指導建議。
-->

**Spigot/Paper 版本**: 1.21.4 (使用 Paper API)
**JDK 版本**: **Java 25 (必須/Required)** [最低編譯與執行需求]
**主要相依性 (Dependencies)**: Paper API (io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT)
**資料儲存 (Storage)**: config.yml, messages.yml (YAML)
**設定檔 (Configuration)**: config.yml, messages.yml
**指令結構 (Commands)**: /wellcome reload, /wellcome help
**權限節點 (Permissions)**: wellcome.admin (default: op)
**效能目標**: 建置時間 < 1min (Gradle Cache), 插件啟動 < 100ms

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: ConfigManager 雖然主要在啟動時執行，但在 reload 指令中可考慮 Async IO (雖 config.yml 通常小，同步亦可接受，但原則上 reload async 更好)。
*   [x] **配置驅動**: 所有參數皆透過 config.yml 管理，無 Hardcode。
*   [x] **權限控管**: reload 指令有 wellcome.admin 權限保護。
*   [x] **資源生命週期**: MainPlugin.onDisable 將正確釋放資源。
*   [x] **核心邏輯可測試**: ConfigManager 將設計為 POJO 風格，方便單元測試。
*   [x] **文件完整性**: 包含 README.md (目的、環境、安裝) 建立任務。
*   [x] **CI/CD 設定**: 包含 GitHub Actions workflow 設定任務。

## Project Structure

### Documentation (this feature)

```text
specs/001-spigot-init/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output (Config structure)
├── quickstart.md        # Phase 1 output (Install guide)
├── contracts/           # Phase 1 output (Commands)
└── tasks.md             # Phase 2 output
```

### 專案結構 (Project Structure)

```text
src/main/java/com/wellcome/
├── commands/             # WellcomeCommand.java (處理 reload, help)
├── configuration/        # ConfigManager.java (管理 config.yml, messages.yml)
├── utils/                # MessageUtils.java (顏色處理)
└── WellcomePlugin.java   # 主要類別 (extends JavaPlugin)

src/main/resources/
├── plugin.yml            # 插件描述檔
├── config.yml            # 預設設定
└── messages.yml          # 預設訊息

tests/                    # 單元測試 (Mock Bukkit)
.github/workflows/        # CI/CD
├── ci.yml                # Nightly/Release workflow
```

**結構決策**: 採用標準 Gradle 專案結構，包名為 `com.wellcome`。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| None | N/A | N/A |
