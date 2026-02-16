# Quickstart: Rename Build Artifact

## 實作步驟

### 1. 修改 Gradle 設定
編輯 `build.gradle`：
```gradle
base {
    archivesName = 'Welcome'
}
```

### 2. 更新 CI 工作流
搜尋 `.github/workflows/ci.yml` 中的 `welcome`：
- 將所有 `welcome` 替換為 `Welcome` (注意大小寫)。

### 3. 本地驗證
執行建置指令：
```bash
./gradlew clean build
```
確認 `build/libs/` 下存在 `Welcome-*.jar`。
