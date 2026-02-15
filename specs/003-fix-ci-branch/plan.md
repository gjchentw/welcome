# Implementation Plan: 修正 CI/CD 的 branch 為 main

**Branch**: `003-fix-ci-branch` | **Date**: 2026-02-15 | **Spec**: [specs/003-fix-ci-branch/spec.md](../spec.md)
**Input**: Feature specification from `specs/003-fix-ci-branch/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

將專案中所有對 `main` 分支的引用 (包含 GitHub Actions Workflow、README、專案憲法與過往紀錄文件) 統一修正為 `main` 分支，以符合目前的開發環境設定並確保自動化流程能正確觸發。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: [N/A]
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5** (透過 Gradle 執行) [必須]
**主要相依性 (Dependencies)**: GitHub Actions
**資料儲存 (Storage)**: [N/A]
**設定檔 (Configuration)**: .github/workflows/ci.yml
**指令結構 (Commands)**: [N/A]
**權限節點 (Permissions)**: [N/A]
**效能目標**: [N/A]

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 不涉及世界修改或阻塞操作。
*   [x] **配置驅動**: 修正 CI 設定檔。
*   [x] **權限控管**: 不涉及遊戲指令。
*   [x] **資源生命週期**: 不涉及插件生命週期。
*   [x] **核心邏輯可測試**: 此項變更為基礎設施修正，不涉及業務邏輯測試。
*   [x] **文件完整性**: 確保 README.md 資訊正確。
*   [x] **CI/CD 設定**: 直接修正核心 CI 設定。
*   [x] **規格驅動**: 已遵循 SDD 流程。

## Project Structure

### Documentation (this feature)

```text
specs/003-fix-ci-branch/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── quickstart.md        # Phase 1 output
└── tasks.md             # Phase 2 output
```

### 專案結構 (Project Structure)

```text
.github/workflows/
└── ci.yml                # 修正觸發分支

.specify/memory/
└── constitution.md       # 修正分支參考

README.md                 # 修正分支參考
```

**結構決策**: 執行全域搜尋取代，確保一致性。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| 無 | N/A | N/A |
