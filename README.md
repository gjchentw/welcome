# Wellcome

Spigot 伺服器歡迎插件 (Welcome Plugin)

## 專案目的 (Project Purpose)

**Wellcome** 是一個專為 Spigot/Paper 伺服器設計的輕量級歡迎插件。
它提供高度可自訂的歡迎訊息、指令重載功能，並嚴格遵循非同步處理原則以確保伺服器效能。
所有訊息與設定皆可透過設定檔進行管理。

## 開發環境 (Development Environment)

本專案使用以下技術棧進行開發：

*   **JDK**: Java 25 (必須)
*   **Build System**: Gradle (Groovy DSL)
*   **API**: Paper API 1.21.4 (相容 Spigot)
*   **IDE**: IntelliJ IDEA (推薦) 或 VS Code

### 快速開始

1.  確認已安裝 JDK 25。
2.  複製專案：
    ```bash
    git clone https://github.com/your-repo/wellcome.git
    cd wellcome
    ```
3.  建置專案：
    ```bash
    ./gradlew clean build
    ```
4.  建置完成後，插件 JAR 檔位於 `build/libs/` 目錄。

## 安裝配置 (Installation & Configuration)

### 安裝步驟

1.  下載最新版本的 `wellcome-x.x.x.jar`。
2.  將 JAR 檔案放入伺服器的 `plugins/` 資料夾。
3.  重新啟動伺服器。

### 設定說明

插件首次啟動後，會在 `plugins/Wellcome/` 目錄下生成以下設定檔：

#### config.yml (主要設定)

```yaml
# 是否啟用除錯模式
debug-mode: false

# 訊息前綴 (支援 Hex Color)
prefix: "&8[&bWellcome&8] "
```

#### messages.yml (訊息內容)

```yaml
# 重載成功訊息
reload-success: "&a設定已成功重載！"

# 無權限訊息
no-permission: "&c你沒有權限執行此指令！"
```

修改設定後，請執行 `/wellcome reload` 指令以套用變更。

## CI/CD 狀態

本專案採用 GitHub Actions 進行自動化建置：
- **Nightly Build**: 每次 push 到 master 分支時觸發。
- **Release Build**: 每次建立 git tag (v*) 時觸發。

## 授權 (License)

本專案採用 [MIT License](LICENSE) 授權。
