# Implementation Plan: Internationalization (i18n) Support

**Branch**: `012-i18n-support` | **Date**: 2026-02-16 | **Spec**: [/specs/012-i18n-support/spec.md]
**Input**: Feature specification from `/specs/012-i18n-support/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

本計畫旨在為 Welcome 插件導入完整的多語言支援（i18n）。核心技術方案包括建立 `LanguageManager` 以管理不同語言的 YAML 語系檔，並修改現有的 `ConfigManager` 以支援語系切換與回退機制（Fallback to en_US）。同時，專案說明文件將進行全球化調整：`README.md` 更新為美式英文，並另建 `README_zh_TW.md` 提供正體中文說明。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21+
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5** (透過 Gradle 執行)
**主要相依性 (Dependencies)**: 無
**資料儲存 (Storage)**: 檔案系統 (YAML 語系檔)
**設定檔 (Configuration)**: `config.yml` (新增 language 選項), `lang/` 目錄下的多個語系檔
**指令結構 (Commands)**: `/welcome reload` (重載語系配置)
**權限節點 (Permissions)**: `welcome.admin`
**效能目標**: 訊息檢索延遲 < 1ms (記憶體快取)

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 語系檔讀取僅在插件啟動或重載時執行，訊息檢索為記憶體操作，不涉及阻塞。
*   [x] **配置驅動**: 所有顯示訊息將從硬編碼改為由 `lang/*.yml` 驅動。
*   [x] **權限控管**: 重載語系指令已規劃 `welcome.admin` 權限。
*   [x] **資源生命週期**: 確保在插件停用時清理語系快取。
*   [x] **核心邏輯可測試**: `LanguageManager` 將設計為獨立於 Bukkit API，以便進行單元測試。
*   [x] **文件完整性**: 規劃建立 `README_zh_TW.md` 並連結至主 `README.md` (美式英文)。
*   [x] **CI/CD 設定**: 無需額外設定。
*   [x] **規格驅動**: 遵循 Spec-Kit 流程。

## Project Structure

### Documentation (this feature)

```text
specs/012-i18n-support/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── checklists/          # Requirements checklist
└── contracts/           # Localization file structure
```

### 專案結構 (Project Structure)

```text
src/main/java/com/welcome/
├── configuration/
│   └── ConfigManager.java    # 修改以讀取語言設定
├── managers/
│   └── LanguageManager.java  # 新增：管理語系載入與檢索
└── WelcomePlugin.java        # 註冊新 Manager

src/main/resources/
├── config.yml                # 新增 language 設定
└── lang/                     # 新增：語系檔目錄
    ├── en_US.yml             # 美式英文 (預設)
    ├── zh_TW.yml             # 正體中文
    ├── zh_CN.yml             # 簡體中文
    ├── ja_JP.yml             # 日文
    └── la_US.yml             # 美式拉丁文
```

**結構決策**: 採用獨立的 `lang/` 資料夾儲存語系檔，並透過 `LanguageManager` 進行封裝，以保持與 `ConfigManager` 的職責分離。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| 無 | N/A | N/A |
