# Phase 0: Research & Decisions - 白名單歡迎指令功能

## 1. 投票門檻獲取 (Gamerule Integration)

### Decision: 查詢主世界 Gamerule
- **方案**: 使用 `Bukkit.getWorlds().get(0).getGameRuleValue(GameRule.PLAYERS_SLEEPING_PERCENTAGE)` 作為投票比例門檻。
- **原因**: 使用者明確要求與 `playersSleepingPercentage` 共用。雖然該 Gamerule 原意為睡眠比例，但在本插件中將其複用為「達成白名單所需的玩家共識比例」。
- **預設處理**: 若環境不支援或獲取失敗，預設為 100%。

## 2. 投票存儲 (In-Memory Storage)

### Decision: ConcurrentHashMap with Set
- **方案**: 使用 `ConcurrentHashMap<UUID, Set<UUID>>` 存儲投票。
  - Key: 目標玩家 (Target) 的 UUID。
  - Value: 已投票玩家 (Voters) 的 UUID 集合。
- **並發處理**: 使用 `Collections.synchronizedSet` 或 `ConcurrentHashMap.newKeySet()` 確保線程安全。
- **原因**: 根據使用者要求，資料不需持久化，重啟丟失即可。記憶體操作符合「非同步優先」原則中的效能要求。

## 3. 玩家辨識 (Player Resolution)

### Decision: OfflinePlayer API
- **方案**: 使用 `Bukkit.getOfflinePlayer(name)` 獲取目標。
- **限制**: 僅限本地已知玩家（曾連接過伺服器或已在白名單/快取中）。不查詢 Mojang API 以避免阻塞。
- **驗證**: 檢查 `OfflinePlayer.hasPlayedBefore()` 或 `OfflinePlayer.isOnline()`。

## 4. 測試策略 (Testing Strategy)

### Decision: JUnit 5 + MockBukkit
- **方案**: 使用 JUnit 5 撰寫單元測試。
- **組件**:
  - `VoteManagerTest`: 測試投票累加、重複投票阻擋、比例計算。
  - `WhitelistUtilsTest`: 測試白名單檢查邏輯。
- **原因**: 符合憲法原則五，確保核心邏輯在無伺服器環境下可測試。
