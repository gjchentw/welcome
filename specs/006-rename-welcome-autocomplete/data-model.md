# Data Model: 006-rename-welcome-autocomplete

## PlayerCache
用於儲存符合補全條件的玩家名稱清單。

| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `names` | `List<String>` | (Volatile) 已排序且過濾後的玩家名稱清單。 |
| `lastUpdated` | `long` | 上次成功更新的時間戳記。 |

## Configuration (config.yml)
新增的設定項。

| 鍵值 | 型別 | 預設值 | 說明 |
| :--- | :--- | :--- | :--- |
| `autocomplete.max-players` | `int` | `100` | 補全建議的最大數量。 |
| `autocomplete.update-interval` | `int` | `30` | 快取更新頻率（分鐘）。 |

## Validation Rules
- `max-players`: 若 <= 0，自動回退至 100 並輸出警告。
- `update-interval`: 必須 > 0。
