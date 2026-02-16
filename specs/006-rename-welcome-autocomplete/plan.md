# Implementation Plan: Rename Welcome Command & Add Autocomplete

**Branch**: `006-rename-welcome-autocomplete` | **Date**: 2026-02-16 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `/specs/006-rename-welcome-autocomplete/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

本功能旨在將插件主指令更名為 `/welcome` (v1.0.1) 並提供基於最近活跃玩家的非同步自動補全功能。技術核心在於實作 `PlayerCacheManager`，利用 `Bukkit Scheduler` 定期 (預設 30 分鐘) 從 `Bukkit.getOfflinePlayers()` 中過濾出最近 3 天上線且不在白名單內的玩家，並將結果快取於執行緒安全的記憶體結構中，以確保 Tab 補全能在 100ms 內回應。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21.1+
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5** (透過 Gradle 執行)
**主要相依性 (Dependencies)**: 無
**資料儲存 (Storage)**: 記憶體快取 (Volatile List) + `config.yml`
**設定檔 (Configuration)**: `config.yml` (新增 `autocomplete.max-players`, `autocomplete.update-interval`)
**指令結構 (Commands)**: `/welcome <player>`
**權限節點 (Permissions)**: `welcome.use` (預設: true)
**效能目標**: 在 1,000 名離線玩家規模下，Tab 補全回應 < 100ms；非同步任務不阻塞主執行緒。

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: `Bukkit.getOfflinePlayers()` 的過濾與快取更新完全在非同步執行緒執行。
*   [x] **配置驅動**: 快取上限與更新頻率皆可透過 `config.yml` 修改。
*   [x] **權限控管**: 已規劃 `welcome.use` 權限，且補全邏輯會檢查權限。
*   [x] **資源生命週期**: `onDisable` 必須顯式取消 `BukkitTask`。
*   [x] **核心邏輯可測試**: 過濾與排序演算法抽離至可單元測試的 Manager。
*   [x] **文件完整性**: 規劃更新 `README.md` 指令說明與版本資訊。
*   [x] **CI/CD 設定**: GitHub Actions 將執行新增的單元測試與效能測試。
*   [x] **規格驅動**: 嚴格遵循 Spec-Kit SDD 流程。

## Project Structure

### Documentation (this feature)

```text
specs/006-rename-welcome-autocomplete/
├── plan.md              # This file (/speckit.plan command output)
├── research.md          # Phase 0 output (/speckit.plan command)
├── data-model.md        # Phase 1 output (/speckit.plan command)
├── quickstart.md        # Phase 1 output (/speckit.plan command)
├── contracts/           # Phase 1 output (/speckit.plan command)
└── tasks.md             # Phase 2 output (/speckit.tasks command - NOT created by /speckit.plan)
```

### 專案結構 (Project Structure)

```text
src/main/java/com/wellcome/
├── commands/             # WelcomeCommand (實作 TabCompleter)
├── configuration/        # ConfigManager (新增設定讀取)
├── managers/             # PlayerCacheManager (處理非同步快取與過濾)
├── utils/                # WhitelistUtils, MessageUtils
└── WellcomePlugin.java   # 註冊指令與管理管理器生命週期

src/main/resources/
├── plugin.yml            # 更新指令定義與權限，更新版本 1.0.1
└── config.yml            # 新增 autocomplete 設定項

tests/                    # JUnit 測試 (模擬大量 OfflinePlayer 數據)
```

**結構決策**: 沿用現有的包結構，新增 `managers` 包來存放 `PlayerCacheManager`，以符合憲法「資源生命週期管理」與「核心邏輯可測試」原則。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |
