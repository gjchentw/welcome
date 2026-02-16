# Data Model: Localization Structures

本文件定義多語言支援相關的數據實體及其關聯。

## 1. 語系實體 (Locale Entity)

| 屬性 | 類型 | 描述 |
| :--- | :--- | :--- |
| `code` | `String` | ISO 語系代碼，如 `en_US`, `zh_TW`。 |
| `messages` | `Map<String, String>` | 該語系下所有訊息鍵與翻譯內容的映射。 |

## 2. 訊息解析規則 (Message Resolution)

### 鍵值尋找優先級
1.  **Level 1**: `active_locale.messages[key]`
2.  **Level 2**: `fallback_locale.messages[key]` (其中 `fallback_locale` 為 `en_US`)
3.  **Level 3**: `key` (原始鍵名)

### 變數替換 (Placeholders)
- 支援 `{target}`, `{voter}`, `{current}`, `{required}` 等動態標籤。
- 替換操作發生在多語言文本檢索之後。

## 3. 檔案映射 (File Mapping)

- `src/main/resources/lang/{locale}.yml` -> `LanguageManager` -> `active_locale.messages`
