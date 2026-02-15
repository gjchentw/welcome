---

description: "Task list for Spigot plugin initialization"
---

# Tasks: 初始化 Spigot 插件核心架構

**Input**: Design documents from `specs/001-spigot-init/`
**Prerequisites**: plan.md, spec.md, data-model.md, contracts/commands.yml

## 第一階段：設置 (Setup) - 共用基礎設施

**目的**: 初始化專案結構與基礎設定

- [ ] T000 建立 `LICENSE` 檔案 (採用 MIT License)
- [ ] T000b 建立基礎 `README.md` (含專案目的、開發環境、安裝配置)
- [ ] T000c 設定 GitHub Actions Workflow (Push -> Nightly-${hash}, Tag -> Release) 在 `.github/workflows/ci.yml`
- [ ] T001 建立專案結構 (src/main/java/com/wellcome, src/main/resources)
- [ ] T002 初始化 Gradle 專案 (使用 JDK 25) 並加入 Paper API 依賴 在 `build.gradle` 與 `settings.gradle`
- [ ] T003 [P] 設定 `plugin.yml` (main, version, api-version, commands, permissions) 在 `src/main/resources/plugin.yml`

---

## 第二階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 核心基礎建設 (Config, CommandHandler, MainPlugin)

- [ ] T004 建立 `ConfigManager` 以讀取 config.yml 與 messages.yml 在 `src/main/java/com/wellcome/configuration/ConfigManager.java`
- [ ] T005 [P] 建立預設 `config.yml` 在 `src/main/resources/config.yml`
- [ ] T006 [P] 建立預設 `messages.yml` 在 `src/main/resources/messages.yml`
- [ ] T007 [P] 建立 `MessageUtils` 支援 Hex Color 與 Placeholders 在 `src/main/java/com/wellcome/utils/MessageUtils.java`
- [ ] T008 建立基礎 `WellcomeCommand` (包含權限檢查與 reload/help 邏輯) 在 `src/main/java/com/wellcome/commands/WellcomeCommand.java`

---

## 第三階段：使用者故事 1 - 基礎建置與 CI/CD (優先級: P1) 🎯 MVP

**目標**: 確保專案可建置並自動化發布

**獨立測試**: 
1. `gradlew build` 成功產出 JAR
2. GitHub Actions 成功執行

### 實作 (Implementation)

- [ ] T009 [US1] 實作 `WellcomePlugin` 主類別並在 onEnable 初始化 ConfigManager 與註冊指令 在 `src/main/java/com/wellcome/WellcomePlugin.java`
- [ ] T010 [US1] 驗證 `build.gradle` 設定正確 (Java 25 toolchain, resource filtering)

**檢查點**: 本機與 CI 建置成功，產出 JAR 檔包含 plugin.yml

---

## 第四階段：使用者故事 2 - 基礎設定檔管理 (優先級: P1)

**目標**: 確保插件能載入設定並支援重載

**獨立測試**: 
1. 啟動伺服器生成設定檔
2. 修改設定檔後 `/wellcome reload` 生效

### 實作 (Implementation)

- [ ] T011 [US2] 整合 `WellcomeCommand` 的 reload 邏輯呼叫 `ConfigManager.reloadConfig()`
- [ ] T012 [US2] 確保 `MessageUtils` 使用 `ConfigManager` 讀取 prefix

**檢查點**: `/wellcome reload` 正常運作，設定值更新

---

## 第 N 階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [ ] T013 確保 `onDisable` 正確釋放資源 (如需)
- [ ] T014 完善 javadoc (特別是 ConfigManager)
