# Implementation Plan: Rename Project from Wellcome to Welcome

**Branch**: `007-rename-wellcome` | **Date**: 2026-02-16 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/007-rename-wellcome/spec.md`

## Summary

本功能旨在將專案內所有「Wellcome」拼寫錯誤更正為「Welcome」。範圍涵蓋 Java Package 路徑 (`com.wellcome` -> `com.welcome`)、類別名稱、檔案系統路徑（包含啟動時自動遷移資料目錄）以及所有文字檔案內容。最終目標是實現 100% 的編譯與測試通過率，並確保專案品牌形象一致。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21+
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5** (透過 Gradle 執行)
**主要相依性 (Dependencies)**: 無
**資料儲存 (Storage)**: 啟動時遷移 `/plugins/Wellcome/` 到 `/plugins/Welcome/`
**設定檔 (Configuration)**: `plugin.yml`, `config.yml`, `messages.yml`
**指令結構 (Commands)**: `/welcome` (原有指令更名或保持一致)
**權限節點 (Permissions)**: `welcome.use`, `welcome.admin`
**效能目標**: `./gradlew build` 成功，無 Regression。

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 資料夾遷移雖屬 I/O，但在 `onLoad` 或 `onEnable` 早期執行單次重新命名，風險較低。
*   [x] **配置驅動**: 確保 `plugin.yml` 與程式碼中的預設字串同步更新。
*   [x] **權限控管**: 權限節點由 `wellcome.*` 遷移至 `welcome.*`。
*   [x] **資源生命週期**: 遷移邏輯需在其他管理器啟動前完成。
*   [x] **核心邏輯可測試**: 更名後必須通過所有 JUnit 單元測試。
*   [x] **文件完整性**: 已規劃更新 README.md 的所有說明。
*   [x] **CI/CD 設定**: 變更將透過 GitHub Actions 驗證。
*   [x] **規格驅動**: 嚴格遵循 Spec-Kit SDD 開發流程。

## Project Structure

### Documentation (this feature)

```text
specs/007-rename-wellcome/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/           # Phase 1 output
└── tasks.md             # Phase 2 output
```

### 專案結構 (Project Structure)

```text
src/main/java/com/welcome/
├── commands/             # WelcomeCommand
├── configuration/        # ConfigManager
├── managers/             # VoteManager, PlayerCacheManager
├── utils/                # MessageUtils, WhitelistUtils
└── WelcomePlugin.java    # 主要類別 (Renamed from WellcomePlugin)

src/main/resources/
├── plugin.yml            # 更新 main 路徑與 name
├── config.yml
└── messages.yml

tests/                    # 同步更名測試套件
```

**結構決策**: 套件路徑全面更名為 `com.welcome`，主類別更名為 `WelcomePlugin`。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |
