# Data Model: Configuration and Text Patterns

本文件定義了更新過程中涉及的實體模式（Pattern Entities）及其轉換規則。

## 1. 遠端倉庫模式 (Remote Patterns)

| 屬性 | 舊值 (Old Pattern) | 新值 (New Pattern) |
| :--- | :--- | :--- |
| SSH Origin | `git@github.com:gjchentw/welcome.git` | `git@github.com:gjchentw/Welcome.git` |
| HTTPS Origin | `https://github.com/gjchentw/welcome.git` | `https://github.com/gjchentw/Welcome.git` |
| GitHub Repo Path | `gjchentw/welcome` | `gjchentw/Welcome` |

## 2. 文字顯示模式 (Text Display Patterns)

| 屬性 | 舊值 (Regex Pattern) | 新值 (Replacement) | 適用檔案 |
| :--- | :--- | :--- | :--- |
| Project Title | `(?i)wellcome` | `Welcome` | `GEMINI.md`, `README.md` |
| Status Badge | `gjchentw/welcome/actions` | `gjchentw/Welcome/actions` | `README.md` |

## 3. 驗證實體 (Validation Entities)

- **Connectivity**: 確保 `git fetch` 成功。
- **Consistency**: 確保所有文件（README, GEMINI）中的名稱拼寫一致。
- **CI Trigger**: 確保 `.github/workflows/` 中的設定在變更後仍能觸發。
