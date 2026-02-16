# Data Model: Player Cache State

本功能不涉及持久化數據存儲，僅涉及記憶體中的狀態管理。

## 1. 快取狀態 (PlayerCacheState)

| 屬性 | 類型 | 描述 |
| :--- | :--- | :--- |
| `cachedNames` | `List<String>` | 已排序的非白名單玩家名稱清單。 |
| `maxSize` | `int` | 快取最大容量 (來自 `config.yml`)。 |

## 2. 狀態轉換 (State Transitions)

### 玩家登入 (On PlayerJoin)
- **條件**: `player` 不在 `whitelist` 中。
- **動作**: 
  1. 如果 `player` 已在 `cachedNames` 中 -> 移至首位。
  2. 如果 `player` 不在 `cachedNames` 中:
     - 如果 `cachedNames.size() >= maxSize` -> 移除末尾項，插入首位。
     - 否則 -> 插入首位。

### 玩家被加入白名單 (On Whitelist Add)
- **動作**: 從 `cachedNames` 中移除該玩家。
