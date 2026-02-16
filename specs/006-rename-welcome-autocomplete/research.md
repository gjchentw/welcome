# Research: 006-rename-welcome-autocomplete

## 1. 非同步快取實作 (Async Cache)
- **Decision**: 使用 `Bukkit.getScheduler().runTaskTimerAsynchronously()`。
- **Rationale**: Spigot 憲法要求「非同步優先」。定期更新快取可避免每次補全時執行耗時的過濾操作。使用 `volatile List<String>` 進行原子性替換，確保執行緒安全且效能極高。
- **Alternatives considered**: 每次 `onTabComplete` 即時過濾（效能差）、Java `ScheduledExecutorService`（需手動管理生命週期）。

## 2. 活躍玩家過濾邏輯 (Active Player Filter)
- **Decision**: 使用 `OfflinePlayer.getLastPlayed()`。
- **Rationale**: 這是獲取玩家最後上線時間的標準方法。
- **Note**: 時間基準是以「快取更新執行當下的系統時間」為準計算 3 天窗口。

## 3. 指令更名處理
- **Decision**: 完全移除 `/wellcome`，不保留 Alias。
- **Rationale**: 根據釐清會議決議，保持指令集整潔。

## 4. 效能測試基準
- **Decision**: 模擬 1,000 名離線玩家。
- **Rationale**: 這是釐清會議中確定的量測基準，足以驗證快取機制的效能優勢。
