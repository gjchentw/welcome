# Data Model: 014-fix-welcome-whitelist

## Entities

### PlayerCacheEntry (InMemory)
代表最近嘗試登入或已在線的非白名單玩家。

| 欄位 | 類型 | 描述 | 驗證規則 |
| :--- | :--- | :--- | :--- |
| name | String | 玩家 ID | 區分大小寫，不可為空 |
| uuid | UUID | 玩家唯一識別碼 | 標準 UUID 格式 |
| timestamp | Long | 最後嘗試登入的時間 | 用於 LRU 排序 |

## 狀態轉換
- **Capture**: 玩家發起連線 -> `AsyncPlayerPreLoginEvent` -> 判定非白名單 -> 加入/更新 `PlayerCacheManager`。
- **Voting**: 在線玩家執行 `/welcome <name>` -> 檢查 `PlayerCacheManager` 存在性 -> 累加投票。
- **Cleanup**: 投票達標 -> 加入白名單 -> 從 `PlayerCacheManager` 移除。
