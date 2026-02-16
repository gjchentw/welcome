# Contracts: Message Schema and Localization

## 1. 語系檔規範 (YAML Schema)

所有語系檔（如 `en_US.yml`）必須包含以下基礎鍵值：

```yaml
# General Messages
no-permission: "You don't have permission to execute this command."
reload-success: "Configuration and languages reloaded successfully."

# Voting Messages
welcome-success: "You have voted for {target}. ({current_votes}/{required_votes})"
target-invalid: "Player {target} has not visited the server."
target-already-whitelisted: "Player {target} is already on the whitelist."
already-voted: "You have already voted for {target}."

# Broadcasts
broadcast-message: "Player {target} has been welcomed to the whitelist!"
```

## 2. API 介面合約 (LanguageManager)

- `String getMessage(String key)`: 檢索翻譯文本，支援 Fallback 到 `en_US`。
- `void loadLanguages()`: 掃描 `lang/` 資料夾並載入所有 `.yml` 檔案到記憶體。
- `void setLanguage(String locale)`: 切換當前使用語系。

## 3. README 分佈合約

- **README.md**: 必須以全英文撰寫，並在頂部包含 `[繁體中文](README_zh_TW.md)` 連結。
- **README_zh_TW.md**: 維持現有的正體中文內容架構。
