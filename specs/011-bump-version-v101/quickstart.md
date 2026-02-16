# Quickstart: Version Synchronization at v1.1.0

本指南說明如何執行全專案的版本資訊同步。

## 步驟 1: 確認主版本號
確保 `build.gradle` 中的版本為預期的 `1.1.0`：
```groovy
version = '1.1.0'
```

## 步驟 2: 同步設定檔註解
更新 `src/main/resources/config.yml` 開頭的註解：
```yaml
# Version: 1.1.0
```

## 步驟 3: 同步測試資源
更新 `src/test/resources/plugin.yml` 中的版本：
```yaml
version: '1.1.0'
```

## 步驟 4: 驗證建置
執行完整建置並確認檔名：
```bash
./gradlew clean build
ls build/libs/Welcome-1.1.0.jar
```

## 步驟 5: 文件一致性檢查
確保 `README.md` 中提及的版本號正確反映當前狀態。
```bash
grep "1.1.0" README.md
```
