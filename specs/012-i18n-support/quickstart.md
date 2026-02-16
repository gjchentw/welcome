# Quickstart: i18n Implementation Steps

本指南說明如何實作與測試 Welcome 插件的多語言支援。

## 步驟 1: 配置語言
在 `config.yml` 中設定您偏好的語言代碼：
```yaml
language: zh_TW
```

## 步驟 2: 新增自定義翻譯
1. 前往插件資料夾下的 `lang/` 目錄。
2. 複製 `en_US.yml` 並重新命名（例如 `fr_FR.yml`）。
3. 修改檔案內的翻譯文字。
4. 執行 `/welcome reload` 套用變更。

## 步驟 3: 驗證多語言
1. 切換語系為 `ja_JP`。
2. 執行 `/welcome help`。
3. **預期結果**: 指令幫助訊息應以日文顯示。

## 步驟 4: 驗證 Fallback 機制
1. 切換語系為 `la_US`。
2. 刻意刪除 `la_US.yml` 中的某個鍵值（如 `no-permission`）。
3. 使用無權限帳號執行指令。
4. **預期結果**: 訊息應顯示 `en_US.yml` 中的英文內容，而非原始鍵名。
