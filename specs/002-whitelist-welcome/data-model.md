# Data Model: 投票與配置結構 - 白名單歡迎指令功能

## 1. 投票狀態 (Vote Status - In Memory)

### VoteRegistry (Singleton/Manager)
| 欄位 | 類型 | 說明 |
| :--- | :--- | :--- |
| `activeVotes` | `Map<UUID, Set<UUID>>` | 目標 UUID -> 投票者 UUID 集合 |

## 2. 配置擴充 (Configuration Extensions)

### config.yml
| 鍵名 | 類型 | 預設值 | 說明 |
| :--- | :--- | :--- | :--- |
| `check-whitelist` | Boolean | `true` | 是否檢查目標是否已在白名單 |
| `broadcast-on-whitelist` | Boolean | `true` | 加入成功時是否全服廣播 |

### messages.yml
| 鍵名 | 類型 | 說明 |
| :--- | :--- | :--- |
| `welcome-success` | String | 投票成功訊息 (支援 `{target}`, `{current_votes}`, `{required_votes}`) |
| `target-already-whitelisted`| String | 目標已在白名單的錯誤提示 |
| `target-invalid` | String | 找不到該玩家的錯誤提示 |
| `already-voted` | String | 重複投票的錯誤提示 |
| `whitelist-disabled` | String | 伺服器未開啟白名單的錯誤提示 |
| `broadcast-message` | String | 全服公告內容 |
