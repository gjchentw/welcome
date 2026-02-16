# Data Model: 玩家辨識快取

## PlayerCacheEntry (內部模型)

負責在記憶體中媒合玩家名稱與 UUID，並維持 LRU (Least Recently Used) 順序。

### 欄位 (Fields)

| 欄位名 | 類型 | 描述 |
|--------|------|------|
| name   | String | 玩家登入時使用的名稱 (Key) |
| uuid   | UUID   | 玩家唯一的識別碼 (Value) |

### 狀態與變更 (State Transitions)

1. **連線攔截 (On Async Login)**: 
   - 觸發：`AsyncPlayerPreLoginEvent`
   - 行動：擷取 Name & UUID，更新至快取最前端。
2. **手動移除 (On Whitelist)**:
   - 觸發：玩家被加入白名單後
   - 行動：從快取中移除該 Entry 以釋放空間。
3. **容量限制 (Eviction)**:
   - 觸發：快取達到 `config.yml` 定義的上限
   - 行動：移除最久未活動 (尾端) 的 Entry。

## 查詢效能目標

- **名稱查詢 UUID**: O(1)
- **快取更新**: O(1)
