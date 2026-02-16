# Implementation Plan: Dynamic Player Cache for Autocomplete

**Branch**: `010-dynamic-player-cache` | **Date**: 2026-02-16 | **Spec**: [/specs/010-dynamic-player-cache/spec.md]
**Input**: Feature specification from `/specs/010-dynamic-player-cache/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/commands/plan.md` for the execution workflow.

## Summary

本功能旨在將 `PlayerCacheManager` 的補全快取機制從定時任務驅動改為事件驅動。系統將監聽 `PlayerJoinEvent`，當非白名單玩家登入時，即時且非同步地將其加入記憶體快取。同時，將移除 `autocomplete.update_interval` 設定，並確保快取遵循 `autocomplete.max_players` 限制。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21+
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5** (透過 Gradle 執行)
**主要相依性 (Dependencies)**: 無
**資料儲存 (Storage)**: 記憶體快取 (Volatile List/LRU Cache)
**設定檔 (Configuration)**: `config.yml` (移除 update_interval, 保留 max_players)
**指令結構 (Commands)**: `/welcome <Tab>` (受影響的功能)
**權限節點 (Permissions)**: 無變動
**效能目標**: 快取更新延遲 < 1s, 消除定時掃描造成的非同步執行緒佔用

## 憲法檢查 (Constitution Check)

*必要關卡：必須在 Phase 0 研究前通過。Phase 1 設計後重新檢查。*

*   [x] **非同步優先**: 已規劃將 `PlayerJoinEvent` 中的快取檢查與寫入操作移至非同步執行緒。
*   [x] **配置驅動**: 保留 `max_players` 限制，移除不再需要的 `update_interval`。
*   [x] **權限控管**: 無新指令，維持現有權限體系。
*   [x] **資源生命週期**: 確保在插件關閉或重載時正確清理監聽器與快取。
*   [x] **核心邏輯可測試**: `PlayerCacheManager` 的快取邏輯將與 Bukkit Event 解耦，方便進行 JUnit 測試。
*   [x] **文件完整性**: 規劃更新 README.md 中關於自動補全機制的說明。
*   [x] **CI/CD 設定**: 現有 GitHub Actions 已覆蓋測試需求。
*   [x] **規格驅動**: 遵循 Spec-Kit SDD 流程。

## Project Structure

### Documentation (this feature)

```text
specs/010-dynamic-player-cache/
├── plan.md              # This file
├── research.md          # Phase 0 output
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
├── checklists/          # Requirements checklist
└── contracts/           # Event listeners design
```

### 專案結構 (Project Structure)

```text
src/main/java/com/welcome/
├── listeners/            # 新增 PlayerJoinListener
├── managers/             # 修改 PlayerCacheManager (移除定時任務, 改為手動觸發)
└── WelcomePlugin.java    # 註冊 Listener
```

**結構決策**: 將快取更新邏輯保留在 `PlayerCacheManager` 中，但改由 `PlayerJoinListener` 調用。

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| 無 | N/A | N/A |
