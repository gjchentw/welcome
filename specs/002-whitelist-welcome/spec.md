# Feature Specification: 白名單歡迎指令功能

**Feature Branch**: `002-whitelist-welcome`  
**Created**: 2026-02-15  
**Status**: Draft  
**Input**: User description: "實作 /welcome 功能，每個線上的 Players 都可以下指令 /wellcome PlayerName，其中 PlayerName 是不在目前白名單中的玩家"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。

## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

<!--
  重要：使用者故事應根據重要性進行優先排序 (P1, P2, P3)。
  每個使用者故事必須是 **可獨立測試的 (Independently Testable)**。
  這意味著如果你只實作其中一個故事，你仍然應該擁有一個可交付價值的小型可行性產品 (MVP)。
-->

### 使用者故事 1 - 歡迎非白名單玩家 (優先級: P1)

作為一名線上玩家，我希望能夠對尚未在白名單中的玩家輸入歡迎指令，以便表達友善並觸發後續的白名單添加流程（未來功能）。

**優先級原因**: 這是功能的核心需求，允許玩家互動並識別非白名單成員。

**獨立測試方法**: 
1. 準備一個不在白名單的測試帳號名稱 (例如 `TestNoWhite`)。
2. 以線上玩家身分輸入 `/wellcome TestNoWhite`。
3. 驗證是否顯示成功訊息。
4. 輸入一個已在白名單的玩家名稱 (例如 `TestWhite`)，驗證是否顯示錯誤訊息。

**驗收場景 (Acceptance Scenarios)**:

1.  **Given (已知)** 玩家 `PlayerA` 在線上, 且 `NewUser` 不在伺服器白名單中, **When (當)** `PlayerA` 輸入 `/wellcome NewUser`, **Then (則)** `PlayerA` 收到「成功歡迎 NewUser」的訊息。
2.  **Given (已知)** `OldUser` 已經在伺服器白名單中, **When (當)** `PlayerA` 輸入 `/wellcome OldUser`, **Then (則)** `PlayerA` 收到「該玩家已在白名單中」的錯誤訊息。
3.  **Given (已知)** `PlayerA` 輸入指令但不帶參數 `/wellcome`, **Then (則)** 應顯示正確的用法提示 `/wellcome <PlayerName>`。

---

### 使用者故事 2 - 投票白名單機制 (優先級: P1)

作為伺服器管理員，我希望透過玩家的集體歡迎 (投票) 來自動將新玩家加入白名單，以減少人工審核的工作量。

**優先級原因**: 這是本功能的核心機制，實現自動化管理。

**獨立測試方法**: 
1. 設定 `playersSleepingPercentage` 為 50%。
2. 讓 2 位玩家上線。
3. 玩家 A 對 `TestNoWhite` 執行 `/wellcome` (50% 達成)。
4. 驗證 `TestNoWhite` 是否被加入白名單並全服公告。

**驗收場景**:

1.  **Given** 線上有 2 位玩家, `playersSleepingPercentage` 為 50%, **When** 玩家 A 執行 `/wellcome NewUser`, **Then** 系統顯示「投票成功 (1/2)」，且 `NewUser` 被加入白名單並全服公告。
2.  **Given** 線上有 3 位玩家, `playersSleepingPercentage` 為 50%, **When** 玩家 A 執行 `/wellcome NewUser`, **Then** 系統顯示「投票成功 (1/3)」，`NewUser` **尚未** 被加入白名單。
3.  **Given** 玩家 A 已經對 `NewUser` 投過票, **When** 再次執行, **Then** 顯示「你已經歡迎過該玩家」。
4.  **Given** 伺服器重啟, **Then** 所有投票記錄應被清除。

---

### 邊際情況 (Edge Cases)

- 當伺服器未開啟白名單 (`white-list=false`) 時，指令應顯示「伺服器未開啟白名單功能」並拒絕執行。
- 當目標玩家已經在白名單中，指令應拒絕執行。
- 當玩家下線時，已投的票數保留在記憶體中，但計算比例時分母 (線上人數) 會改變。本系統僅在 **投票當下** 檢查比例。

## 澄清 (Clarifications)

### Session 2026-02-15
- Q: 冷卻機制? → A: 永久性限制 (一次性投票)，比例達成自動加入白名單。
- Q: 投票比例? → A: 依據主世界 `playersSleepingPercentage` GameRule。
- Q: 資料儲存? → A: 純記憶體 (Memory Only)，重啟後重置。
- Q: 成功通知? → A: 全服公告。
- Q: 白名單關閉時? → A: 禁用指令。
- Q: 玩家查詢? → A: 僅限本地已知玩家。

## 指令與權限 (Commands & Permissions) *(必填)*

### 指令 (Syntax)

- `/wellcome <player_name>`: 歡迎 (投票) 指定玩家。

### 權限 (Permission Nodes)

- `wellcome.use`: 允許使用歡迎指令 (預設: true)。
- `wellcome.admin`: 允許強制加入 (未來功能) 或管理投票 (預設: op)。

## 設定需求 (Configuration Requirements) *(必填)*

### config.yml
- **check-whitelist**: (boolean) 是否檢查目標玩家不在白名單中。預設: true。
- **broadcast-on-whitelist**: (boolean) 當玩家成功加入白名單時是否全服公告。預設: true。

### messages.yml
- **welcome-success**: (string) 投票成功訊息。支援變數 `{target}`, `{current_votes}`, `{required_votes}`。
- **target-already-whitelisted**: (string) 目標已在白名單的錯誤訊息。
- **target-invalid**: (string) 目標名稱無效訊息 (伺服器未知玩家)。
- **already-voted**: (string) 重複投票的提示訊息。
- **whitelist-disabled**: (string) 白名單功能未開啟的提示訊息。
- **broadcast-message**: (string) 全服公告訊息。支援變數 `{target}`。

## 需求規格 (Requirements) *(必填)*

### 功能需求 (Functional Requirements)

- **FR-001**: 系統必須提供 `/wellcome <player_name>` 指令。
- **FR-002**: 執行指令時，若 `white-list=false`，必須拒絕執行並回傳錯誤。
- **FR-003**: 必須檢查 `<player_name>` 是否已在白名單中，若是則拒絕。
- **FR-004**: 必須在記憶體中維護 `Target -> Set<Voter>` 的投票記錄。
- **FR-005**: 必須檢查執行者是否已對該目標投過票，若是則拒絕。
- **FR-006**: 投票成功後，計算 `(目前票數 / 線上人數 * 100)` 是否大於等於主世界的 `playersSleepingPercentage`。
- **FR-007**: 若比例達成，執行 `Target.setWhitelisted(true)`，清除該目標的投票記錄，並發送全服公告 (若設定開啟)。
- **FR-008**: 若比例未達成，僅發送成功訊息給執行者，顯示當前進度。
- **FR-009**: 僅限本地已知玩家 (`Bukkit.getOfflinePlayer`)，不進行 Mojang API 查詢。

### 關鍵實體 (Key Entities)

- **WelcomeCommand**: 處理指令邏輯。
- **WhitelistValidator**: 負責檢查玩家白名單狀態的邏輯組件。

## 成功標準 (Success Criteria) *(必填)*

### 可衡量成果

- **SC-001**: 玩家輸入 `/wellcome <非白名單玩家>` 時，100% 成功觸發歡迎訊息。
- **SC-002**: 玩家輸入 `/wellcome <白名單玩家>` 時，100% 被攔截並顯示錯誤。
- **SC-003**: 訊息內容完全符合 `messages.yml` 的設定。
