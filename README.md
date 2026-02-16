# Welcome

[![Build and Release](https://github.com/gjchentw/Welcome/actions/workflows/ci.yml/badge.svg)](https://github.com/gjchentw/Welcome/actions/workflows/ci.yml)

**Welcome** 是一個為現代 Minecraft 伺服器設計的 Spigot/Paper 插件，旨在實現「社區驅動」的白名單管理機制。透過投票系統，讓線上玩家共同決定新成員的加入，並透過自動化 CI/CD 確保程式碼品質與發布可信度。

---

## 專案目的 (Project Purpose)

在許多半開放式的伺服器中，白名單審核往往耗費管理員大量時間。**Welcome** 改變了這一點：
- **投票白名單**: 線上玩家可以對新玩家進行「歡迎 (投票)」。
- **即時自動補全**: 當非白名單玩家登入時，系統會自動且即時地將其加入指令補全清單，無需等待。
- **自動化管理**: 當歡迎比例達到設定門檻（複用伺服器睡眠比例 `playersSleepingPercentage`），系統自動將目標玩家加入白名單。
- **無痛遷移**: 從舊版本 (Welcome) 升級時，系統會自動遷移資料夾內容（v1.1.0+）。

---

## 開發流程 (Development Workflow)

本專案嚴格採用 **Spec-Kit** 進行 **規格驅動開發 (Specification-Driven Development, SDD)**，確保每一項功能從設計到實作皆有跡可循：

1.  **規格制定 (`/speckit.specify`)**: 定義使用者故事與驗收標準。
2.  **技術計畫 (`/speckit.plan`)**: 進行技術研究、架構設計與憲法檢查。
3.  **實作執行 (`/speckit.implement`)**: 根據任務清單進行 TDD 實作。

---

## 開發環境 (Development Environment)

- **JDK**: Java 25 (最低需求)
- **Build System**: Gradle (Groovy DSL)
- **API**: Paper API 1.21.4 (向下相容 Spigot)
- **Testing**: JUnit 5 + MockBukkit + Mockito

### 本地建置
```bash
git clone https://github.com/gjchentw/Welcome.git
cd welcome
./gradlew clean build
```

---

## 安裝配置 (Installation & Configuration)

1.  從 [Releases](https://github.com/gjchentw/Welcome/releases) 下載最新版本的 JAR。
2.  放入伺服器的 `plugins/` 資料夾。
3.  重新啟動伺服器以生成預設設定檔。

### 版本遷移說明 (v1.1.0+)
如果您是從舊版本（名稱為 `Welcome` 的時期）升級，插件在啟動時會自動偵測舊有的 `/plugins/Welcome/` 目錄，並將其更名為 `/plugins/Welcome/`。您不需要手動移動任何設定檔。

### 關鍵配置

#### `config.yml`
```yaml
# 是否檢查目標玩家不在白名單中
check-whitelist: true
# 成功加入後是否全服公告
broadcast-on-whitelist: true
# 自動補全設定
autocomplete:
  max-players: 100
```

#### `messages.yml`
所有系統訊息（包含歡迎成功、權限不足等）皆支援顏色代碼與 Hex Color (#RRGGBB)。

---

## 指令使用 (Usage)

| 指令 | 權限節點 | 說明 |
| :--- | :--- | :--- |
| `/welcome <player>` | `welcome.use` | 對指定玩家投下一張歡迎票 (支援玩家名稱自動補全) |
| `/welcome help` | `welcome.use` | 顯示指令幫助 |

---

## 品質與信任 (Trust & Quality)

- **自動化測試**: 核心邏輯（如投票計算、比例檢查、快取過濾、資料遷移）皆由 JUnit 5 覆蓋。
- **效能保證**: 自動補全回應時間保證在 100ms 內，且採用非同步快取不影響伺服器 TPS。
- **持續整合**: 使用 GitHub Actions 在每次 Push 時執行測試，確保 `main` 分支永遠處於可發布狀態。

---

## 授權 (License)

本專案採用 [MIT License](LICENSE) 授權。
