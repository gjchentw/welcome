# Implementation Plan: Rename Build Artifact to Welcome.jar

**Branch**: `008-rename-artifact` | **Date**: 2026-02-16 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/008-rename-artifact/spec.md`

## Summary

本功能旨在將 Gradle 建置產出的檔案名稱從 `welcome.jar` 更改為 `Welcome.jar`（包含版本號，例如 `Welcome-1.1.0.jar`）。實作方式為修改 `build.gradle` 中的 `base.archivesName` 設定，並同步更新 GitHub Actions 工作流文件，確保自動化發布流程的一致性。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21+
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5**
**主要相依性 (Dependencies)**: 無
**資料儲存 (Storage)**: 無
**設定檔 (Configuration)**: `build.gradle (base.archivesName)`, `.github/workflows/ci.yml`
**指令結構 (Commands)**: `./gradlew build`
**權限節點 (Permissions)**: 無
**效能目標**: 建置過程無額外效能損耗。

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 不適用（建置腳本變更）。
*   [x] **配置驅動**: 產出物名稱透過 `build.gradle` 進行中央管理。
*   [x] **權限控管**: 不適用。
*   [x] **資源生命週期**: 不適用。
*   [x] **核心邏輯可測試**: 透過檢查檔案系統產出物名稱進行驗證。
*   [x] **文件完整性**: 不適用（內部名稱規範）。
*   [x] **CI/CD 設定**: 必須同步更新 GitHub Actions 的發布腳本。
*   [x] **規格驅動**: 遵循 Spec-Kit 流程。

## Project Structure

### Documentation (this feature)

```text
specs/008-rename-artifact/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/           # Phase 1 output
└── tasks.md             # Phase 2 output
```

### 專案結構 (Project Structure)

```text
.
├── build.gradle          # 修改 base.archivesName
├── settings.gradle       
└── .github/
    └── workflows/
        └── ci.yml        # 更新產出物引用名稱
```

**結構決策**: 僅修改建置與工作流設定檔。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |
