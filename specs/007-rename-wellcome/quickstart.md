# Quickstart: Rename Project from Wellcome to Welcome

## 實作步驟指引

### 1. 檔案與目錄重構
- 使用 IDE 重命名 `com.wellcome` 套件為 `com.welcome`。
- 重命名 `WellcomePlugin` 為 `WelcomePlugin`。
- 重命名所有包含 `Wellcome` 字樣的類別（如 `WellcomeCommand`）。

### 2. 文字全域替換
- 執行全域掃描，將 `wellcome` (小寫) 替換為 `welcome`。
- 將 `Wellcome` (首字母大寫) 替換為 `Welcome`。
- 特別檢查 `build.gradle`, `settings.gradle`, `plugin.yml` 以及 `README.md`。

### 3. 資料夾遷移邏輯
- 在 `WelcomePlugin#onLoad` 中實作以下邏輯：
  ```java
  File oldFolder = new File(getDataFolder().getParentFile(), "Wellcome");
  File newFolder = getDataFolder();
  if (oldFolder.exists() && !newFolder.exists()) {
      oldFolder.renameTo(newFolder);
  }
  ```

### 4. 驗證
- 執行 `./gradlew clean test`。
- 執行 `./gradlew build` 並檢查 `build/libs/` 下生成的 JAR 名稱。
