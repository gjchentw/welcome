# Phase 0: Research & Decisions

**Feature**: 初始化 Spigot 插件核心架構
**Date**: 2026-02-15

## 1. 建置系統 (Build System)

### Decision: Gradle (Groovy DSL)
- **選擇**: 使用 Gradle 搭配 Groovy DSL (`build.gradle`)。
- **原因**: 根據規格文件 FR-001 要求。Groovy DSL 是 Gradle 的預設且最廣泛使用的格式，教學資源豐富。
- **依賴**: 使用 `io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT` 作為主要 API 依賴。
  - *注意*: 雖然規格只提到 Spigot，但 Paper API 是 Spigot 的超集，提供更好的開發體驗與效能，且完全相容 Spigot 插件。這是現代插件開發的最佳實務。
- **Java 版本**: 強制設定 `toolchain` 使用 Java 25。

### Alternative Considered
- **Maven**: 雖然是經典選擇，但規格明確指定 Gradle。
- **Spigot API**: 直接使用 Spigot API 需要執行 BuildTools，設定繁瑣。Paper API 可直接從 Maven Central (via Paper Repo) 下載，更適合 CI/CD。

## 2. CI/CD 流程 (GitHub Actions)

### Decision: Standard Java Workflow
- **Trigger**: 
  - `push` branches: `[ "main" ]` -> 產生 `nightly-${hash}`
  - `push` tags: `[ "v*" ]` -> 產生 Release
- **Environment**: `ubuntu-latest`
- **Steps**:
  1. Checkout code
  2. Set up JDK 25 (`actions/setup-java@v4`, `distribution: 'temurin'`)
  3. Setup Gradle
  4. Build with Gradle (`./gradlew build`)
  5. Upload Artifact (JAR file)

## 3. 設定檔管理 (Configuration Management)

### Decision: Native Bukkit Configuration
- **選擇**: 使用 Bukkit 內建的 `FileConfiguration` API。
- **實作**: 建立 `ConfigManager` 單例類別。
  - `loadConfig()`: 載入/重載 config.yml 與 messages.yml。
  - `getMessage(String key)`: 獲取訊息並自動處理顏色代碼 (Hex colors)。
- **原因**: 保持輕量，不引入額外複雜度 (如 Annotation-based config 庫)，符合「核心邏輯可測試」原則 (將 Config 邏輯封裝，方便 Mock)。

## 4. 專案結構 (Project Structure)

### Decision: Standard Layout
- **Group ID**: `com.wellcome`
- **Artifact ID**: `wellcome`
- **Main Class**: `com.wellcome.WellcomePlugin`
- **Package Structure**:
  - `com.wellcome.commands`: 指令處理
  - `com.wellcome.configuration`: 設定管理
  - `com.wellcome.utils`: 工具類 (顏色處理等)
