# Implementation Plan: Fix Missing config.yml Resource

**Branch**: `005-fix-config-resource` | **Date**: 2026-02-15 | **Spec**: [specs/005-fix-config-resource/spec.md](spec.md)
**Input**: Feature specification from `/specs/005-fix-config-resource/spec.md`

## Summary
本計畫旨在修復插件在啟用時因缺少 `config.yml` 資源檔案而導致的 `IllegalArgumentException`。技術手段包括在資源目錄中新增該檔案，並確保 Gradle 正確將其打包。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21.4 (根據 build.gradle)
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5**
**主要相依性 (Dependencies)**: Paper-API
**資料儲存 (Storage)**: config.yml
**設定檔 (Configuration)**: config.yml
**指令結構 (Commands)**: 無變更
**權限節點 (Permissions)**: 無變更
**效能目標**: 啟動時間無感增加

## 憲法檢查 (Constitution Check)

*   [x] **非同步優先**: I/O 操作由 Spigot 的 `saveDefaultConfig` 處理，通常在啟用時執行，符合生命週期。
*   [x] **配置驅動**: 此任務本身就是為了解決配置載入問題。
*   [x] **權限控管**: 不涉及新功能。
*   [x] **資源生命週期**: 設定檔將在 `onEnable` 中初始化。
*   [x] **核心邏輯可測試**: 雖然 `JavaPlugin` 的 API 難以測試，但 `ConfigManager` 的邏輯應與資源載入解耦。
*   [x] **文件完整性**: 將確認 README.md 的開發與安裝說明依然適用。
*   [x] **CI/CD 設定**: 現有的 CI 會在 build 階段失敗，若資源缺失則測試可能無法通過（如果有相關測試）。
*   [x] **規格驅動**: 已依循規格流程。

## Project Structure

### Documentation (this feature)

```text
specs/005-fix-config-resource/
├── plan.md              # 本文件
├── research.md          # 研究結果：確認資源缺失原因
└── quickstart.md        # 驗證步驟
```

### 專案結構 (Project Structure)

```text
src/main/resources/
└── config.yml            # [新增] 預設設定檔
```

**結構決策**: 在標準的 Maven/Gradle 資源目錄中新增檔案，無需修改程式碼結構。

## Complexity Tracking

*無違規事項。*
