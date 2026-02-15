# Quickstart: Fix Missing config.yml Resource

## 驗證步驟

### 1. 建立資源檔案
在 `src/main/resources/` 目錄下建立一個基本的 `config.yml`。

### 2. 建置插件
執行以下指令來建置專案：
```bash
./gradlew build
```

### 3. 檢查 JAR 內容
驗證產出的 JAR 檔案中包含 `config.yml`：
```bash
jar tf build/libs/wellcome-1.0.0-SNAPSHOT.jar | grep config.yml
```

### 4. 伺服器測試
1. 將 JAR 放入測試伺服器的 `plugins` 資料夾。
2. 啟動伺服器。
3. 觀察控制台是否出現 `IllegalArgumentException: The embedded resource 'config.yml' cannot be found`。
4. 確認 `plugins/wellcome/config.yml` 已成功產出。
