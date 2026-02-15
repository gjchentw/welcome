# Quickstart: 安裝與驗證指南

**Feature**: 初始化 Spigot 插件核心架構
**Date**: 2026-02-15

## 1. 系統需求 (System Requirements)

- **Java Runtime**: JDK 25 (必須)
- **Server Software**: Spigot/Paper 1.21.4 (推薦 Paper)
- **Memory**: 最低 2GB RAM (建議 4GB+)

## 2. 建置與安裝 (Build & Install)

### 步驟 1: 建置專案

在專案根目錄執行以下指令：

```bash
# Linux / macOS
./gradlew clean build

# Windows
gradlew.bat clean build
```

成功後，JAR 檔案將位於 `build/libs/wellcome-1.0.0.jar` (版本號可能不同)。

### 步驟 2: 安裝至伺服器

1.  將生成的 JAR 檔案複製到伺服器的 `plugins/` 資料夾中。
2.  啟動或重啟伺服器。

## 3. 功能驗證 (Verification)

### 驗證設定檔生成

1.  檢查伺服器 `plugins/Wellcome/` 資料夾是否存在。
2.  確認 `config.yml` 與 `messages.yml` 是否包含預設內容。

### 驗證指令功能

在遊戲內或控制台執行：

1.  `/wellcome help`
    - **預期結果**: 顯示幫助選單 (如 `messages.yml` 定義)。
2.  `/wellcome reload` (需 OP 或 `wellcome.admin` 權限)
    - **預期結果**: 顯示重載成功訊息 (`reload-success`)。
3.  `/wellcome invalid`
    - **預期結果**: 顯示幫助選單或指令錯誤提示。

### 驗證除錯模式

1.  修改 `config.yml` 將 `debug-mode` 設為 `true`。
2.  執行 `/wellcome reload`。
3.  檢查伺服器控制台是否顯示類似 `[Wellcome] Debug mode enabled` 的日誌。

## 4. 常見問題 (Troubleshooting)

- **Q: 插件無法載入，顯示 Java 版本錯誤？**
  - **A:** 請確認伺服器啟動腳本使用的是 Java 25 (`java -version`)。

- **Q: 指令無反應？**
  - **A:** 請確認是否擁有 `wellcome.admin` 權限或為 OP。

- **Q: `config.yml` 修改無效？**
  - **A:** 修改後必須執行 `/wellcome reload` 或重啟伺服器才能生效。
