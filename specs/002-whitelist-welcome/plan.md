# Implementation Plan: 白名單歡迎指令功能

**Branch**: `002-whitelist-welcome` | **Date**: 2026-02-15 | **Spec**: [specs/002-whitelist-welcome/spec.md](../spec.md)
**Input**: Feature specification from `/specs/002-whitelist-welcome/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

實作 `/wellcome <player>` 指令，允許線上玩家透過投票將新玩家加入白名單。系統將複用 `playersSleepingPercentage` Gamerule 作為門檻，並使用純記憶體存儲投票記錄。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21.4 (Paper API)
**JDK 版本**: **Java 25 (必須/Required)** [最低編譯與執行需求]
**Testing**: **JUnit 5** (透過 Gradle 執行) [必須]
**主要相依性 (Dependencies)**: Paper API, JUnit 5, MockBukkit
**資料儲存 (Storage)**: 記憶體 (ConcurrentHashMap)
**設定檔 (Configuration)**: config.yml, messages.yml
**指令結構 (Commands)**: /wellcome <player>
**權限節點 (Permissions)**: wellcome.use, wellcome.admin
**效能目標**: 投票計算 < 1ms, 無阻塞 I/O

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 投票儲存於記憶體，查詢 OfflinePlayer 限制在本地快取，避免阻塞主線程。
*   [x] **配置驅動**: 所有回傳訊息與行為開關皆可透過設定檔調整。
*   [x] **權限控管**: 指令受 `wellcome.use` 保護。
*   [x] **資源生命週期**: 資料僅存於記憶體，重啟即重置，符合需求。
*   [x] **核心邏輯可測試**: 業務邏輯抽離至 `VoteManager`，並使用 **JUnit** 進行單元測試。
*   [x] **文件完整性**: 已規劃更新 README.md (包含專案目的、開發環境、安裝配置、**CI Status Badge**)。
*   [x] **CI/CD 設定**: 設定 GitHub Actions 執行 `gradle test`。
*   [x] **規格驅動**: 已遵循 `/speckit.specify` 與 `/speckit.plan` 流程。

## Project Structure

### Documentation (this feature)

```text
specs/002-whitelist-welcome/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── contracts/           # Phase 1 output
└── tasks.md             # Phase 2 output
```

### 專案結構 (Project Structure)

```text
src/main/java/com/wellcome/
├── commands/             # WellcomeCommand.java (更新投票邏輯)
├── managers/             # VoteManager.java (處理記憶體投票)
├── utils/                # WhitelistUtils.java, MessageUtils.java
└── WellcomePlugin.java

src/main/resources/
├── plugin.yml
├── config.yml
└── messages.yml

tests/                    # JUnit 單元測試
```

**結構決策**: 使用 Manager 模式封裝投票邏輯，確保 Command 層職責單一。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| 無 | N/A | N/A |
