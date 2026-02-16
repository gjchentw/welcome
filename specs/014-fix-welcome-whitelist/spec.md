# Feature Specification: 修正無法 welcome 玩家的問題並進版 v1.1.1

**Feature Branch**: `014-fix-welcome-whitelist`  
**Created**: 2026-02-16  
**Status**: Draft  
**Input**: User description: "修正無法 welcome 玩家的問題，進版到 v1.1.1"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) (必填)

### 使用者故事 1 - 歡迎被白名單阻擋的新玩家 (優先級: P1)

當一個不在白名單上的新玩家嘗試連線至伺服器時，伺服器應能識別該玩家，並允許在線上的玩家透過 `/welcome` 指令對其進行投票，即使該玩家從未成功登入過。

**優先級原因**: 解決目前系統無法對新玩家進行白名單投票的阻礙。

**獨立測試方法**: 
1. 讓一個不在白名單上的帳號嘗試連線（預期被拒絕）。
2. 在線上的玩家嘗試輸入 `/welcome <該帳號名稱>`。
3. 觀察系統是否正確受理投票，而非顯示「玩家從未造訪過」的錯誤訊息。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 玩家 A 不在白名單上且從未登入過，**When (當)** 玩家 A 嘗試連線被拒後，線上玩家 B 執行 `/welcome A`，**Then (則)** 系統應開始對 A 進行投票流程。
2. **Given (已知)** 玩家 A 已在投票名單中，**When (當)** 玩家 B 再次執行 `/welcome A`，**Then (則)** 系統應提示玩家 B 已經投過票。

---

### 使用者故事 2 - 外掛版本更新至 v1.1.1 (優先級: P2)

外掛的版本資訊應更新為 v1.1.1，以便使用者識別此修復版本。

**優先級原因**: 明確標記修復內容。

**獨立測試方法**: 
1. 在伺服器啟動日誌中檢查版本資訊。
2. 透過 `/version Welcome` 檢查版本。

**驗收場景**:

1. **Given** 外掛已載入，**When** 查看版本資訊時，**Then** 版本應顯示為 `1.1.1`。

---

### 邊際情況 (Edge Cases)

- **重複登入嘗試**: 同一玩家多次嘗試被拒時，快取應只保留一筆記錄。
- **快取容量**: 如果大量不同玩家嘗試登入，快取應有上限（LRU 策略）。

## 指令與權限 (Commands & Permissions) (必填)

### 指令 (Syntax)

- `/welcome <player>`: 對指定的玩家進行投票（包含已被白名單阻擋的玩家）。

### 權限 (Permission Nodes)

- `welcome.use`: 允許使用 `/welcome` 指令進行投票。

## 設定需求 (Configuration Requirements) (必填)

- **版本資訊**: `build.gradle` 或 `plugin.yml` 中的版本號需更新。

## 需求規格 (Requirements) (必填)

### Functional Requirements

- **FR-001**: 系統必須在 `AsyncPlayerPreLoginEvent` 階段捕捉未通過白名單檢查的玩家名稱與 UUID。
- **FR-002**: 系統必須將這些玩家加入 `PlayerCacheManager` 的快取中。
- **FR-003**: `/welcome` 指令必須允許對 `PlayerCacheManager` 中存在的玩家進行投票，即使 `OfflinePlayer#hasPlayedBefore()` 為 false。
- **FR-004**: 外掛版本必須更新至 `1.1.1`。

### Key Entities (include if feature involves data)

- **PlayerCache**: 存儲最近嘗試登入的玩家名稱與 UUID。

## Success Criteria (mandatory)

### Measurable Outcomes

- **SC-001**: 線上玩家能夠在目標新玩家連線失敗後的 10 秒內，透過指令發起投票。
- **SC-002**: 外掛版本在 `build.gradle` 與 `plugin.yml` 中均更新為 `1.1.1`。
- **SC-003**: 投票達標後，玩家能成功加入白名單並在下次連線時登入。
