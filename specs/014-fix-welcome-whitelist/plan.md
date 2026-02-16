# Implementation Plan: 修正無法 welcome 玩家的問題並進版 v1.1.1

**Branch**: `014-fix-welcome-whitelist` | **Date**: 2026-02-16 | **Spec**: [specs/014-fix-welcome-whitelist/spec.md]
**Input**: Feature specification from `/specs/014-fix-welcome-whitelist/spec.md`

## Summary

本功能旨在解決 `/welcome` 指令無法對尚未成功登入伺服器（因不在白名單而被踢出）的新玩家進行投票的問題。技術上將透過 `AsyncPlayerPreLoginEvent` 捕捉這些玩家的資訊並加入動態快取，同時將外掛版本提升至 v1.1.1。

## 技術背景 (Technical Context)

**Spigot/Paper 版本**: 1.21.4 (LATEST)
**JDK 版本**: **Java 25 (必須/Required)**
**Testing**: **JUnit 5**
**主要相依性 (Dependencies)**: Spigot API
**資料儲存 (Storage)**: 記憶體快取 (LRU)
**設定檔 (Configuration)**: config.yml, messages.yml
**指令結構 (Commands)**: /welcome <player>
**權限節點 (Permissions)**: welcome.use
**效能目標**: 快取操作為 O(1) 或 O(n) (n 為快取大小)，完全非同步或非阻塞。

## 憲法檢查 (Constitution Check)

*   [x] **非同步優先**: `AsyncPlayerPreLoginEvent` 本身即為非同步。快取操作使用同步鎖或原子操作確保執行緒安全。
*   [x] **配置驅動**: 版本資訊透過 Gradle 與 plugin.yml 驅動。
*   [x] **權限控管**: 維持現有的 `welcome.use` 權限。
*   [x] **資源生命週期**: 快取在 `onDisable` 時會自動隨物件銷毀，無持續性資源洩漏風險。
*   [x] **核心邏輯可測試**: 計畫為 `PlayerCacheManager` 撰寫或更新單元測試。
*   [x] **文件完整性**: 執行後將檢查 README.md 版本資訊。
*   [x] **CI/CD 設定**: GitHub Actions 將自動處理 v1.1.1 的建置。
*   [x] **規格驅動**: 已完成 spec.md 與 research.md。

## Project Structure

### Documentation (this feature)

```text
specs/014-fix-welcome-whitelist/
├── plan.md              # 本檔案
├── research.md          # 研究結果：使用 AsyncPlayerPreLoginEvent
├── data-model.md        # 定義 PlayerCacheEntry
├── quickstart.md        # 實作步驟
└── tasks.md             # (待產生) 具體任務
```

### 專案結構 (Project Structure)

```text
src/main/java/com/welcome/
├── commands/
│   └── WelcomeCommand.java    # 修改驗證邏輯
├── listeners/
│   ├── PlayerJoinListener.java
│   └── LoginAttemptListener.java # 新增監聽器
├── managers/
│   └── PlayerCacheManager.java  # 強化搜尋能力
└── WelcomePlugin.java         # 註冊新監聽器

src/main/resources/
└── plugin.yml                 # 更新版本
```

**結構決策**: 保持現有的 `com.welcome` 套件結構，新增一個專門處理登入嘗試的監聽器。

## Complexity Tracking

> 無違反憲法之處。
