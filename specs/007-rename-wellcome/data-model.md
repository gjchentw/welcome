# Data Model: Rename Project from Wellcome to Welcome

本功能不涉及資料實體結構的變更，僅涉及名稱映射與儲存路徑的遷移。

## 路徑映射 (Path Mapping)

| 舊路徑 (Old) | 新路徑 (New) | 說明 |
| :--- | :--- | :--- |
| `src/main/java/com/wellcome/` | `src/main/java/com/welcome/` | 套件根目錄 |
| `/plugins/Wellcome/` | `/plugins/Welcome/` | 外掛資料目錄 |

## 配置映射 (Config Mapping)

| 檔案 | 舊鍵值 (Old Key) | 新鍵值 (New Key) |
| :--- | :--- | :--- |
| `plugin.yml` | `name: Wellcome` | `name: Welcome` |
| `plugin.yml` | `main: com.wellcome.WellcomePlugin` | `main: com.welcome.WelcomePlugin` |
| `settings.gradle` | `rootProject.name = 'wellcome'` | `rootProject.name = 'welcome'` |
| `config.yml` | `prefix: "&8[&bWellcome&8] "` | `prefix: "&8[&bWelcome&8] "` |
