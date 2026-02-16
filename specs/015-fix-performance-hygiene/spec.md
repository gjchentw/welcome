# Feature Specification: 修正 Welcome 系統效能瓶頸與專案清理

**Feature Branch**: `015-fix-performance-hygiene`  
**Created**: 2026-02-16  
**Status**: Draft  
**Input**: User description: "標題：修正 Welcome 系統效能瓶頸與專案清理 Context: 目前的 v1.1.1 實作雖然實現了緩存非白名單玩家的功能，但被指出存在嚴重的效能風險，且專案目錄中留有過時的規格文件。我們需要根據 Code Review 的意見進行重構。 Tasks: 清理過時規格 (Project Hygiene): 直接刪除 specs/013-fix-welcome-issue/ 整個目錄，因為該版本已過時，目前以 specs/014-fix-welcome-whitelist/ 為準 。 +1 更新規格文件 (Update Spec): 修改 specs/014-fix-welcome-whitelist/data-model.md（或相關規格）：明確要求 PlayerCacheManager 必須儲存 Map<String, UUID>，而不僅僅是玩家名稱 。 在實作計畫中加入：在 AsyncPlayerPreLoginEvent 期間同時擷取玩家的 Name 與 UUID 。 重構代碼 (Refactoring): LoginAttemptListener: 修改監聽器，將 UUID 存入 PlayerCacheManager 。 WelcomeCommand: * 移除 Bukkit.getOfflinePlayer(targetName) 這個會導致主線程阻塞的呼叫 。 改為從 PlayerCacheManager 獲取 UUID 後，使用 Bukkit.getOfflinePlayer(UUID) 來取得玩家物件 。 +1 清理冗餘: 檢查 LoginAttemptListener 的建構子，移除未使用的 JavaPlugin plugin 欄位以簡化代碼 。 +1 驗證: 更新並執行單元測試，確保 WelcomeCommand 在玩家不在緩存中且未曾加入伺服器時，能正確處理且不造成延遲 。 +1"

**注意**: 本規格文件的所有內容都應遵循 `.specify/memory/constitution.md` 中定義的專案憲法原則。


## 使用者情境與測試 (User Scenarios & Testing) *(必填)*

### 使用者故事 1 - 管理員執行 Welcome 指令查詢未加入過的玩家 (優先級: P1)

身為伺服器管理員，當我針對一個從未加入過伺服器的玩家執行 Welcome 相關指令時，系統應能正確處理且不造成伺服器瞬間卡頓（Lag）。

**優先級原因**: 避免指令執行導致的主線程阻塞是維持伺服器穩定性的關鍵，且能修復目前已知的效能風險。

**獨立測試方法**: 在測試環境中執行 Welcome 指令查詢隨機名稱，監控伺服器主線程是否有毫秒級以上的停頓。

**驗收場景 (Acceptance Scenarios)**:

1. **Given (已知)** 玩家名稱不存在於伺服器快取中，**When (當)** 管理員執行 Welcome 指令，**Then (則)** 指令應能透過快取的 UUID 或非阻塞方式完成查詢，不造成伺服器停頓。
2. **Given (已知)** 玩家從未加入過伺服器，**When (當)** 查詢該玩家，**Then (則)** 系統應能安全地返回「查無此人」或預設訊息，而非嘗試執行高耗能的阻塞式查找。

---

### 使用者故事 2 - 系統在玩家連線時處理身份識別資訊 (優先級: P2)

身為系統，當玩家嘗試連線時，我應該完整擷取並快取玩家的唯一識別碼（UUID）與名稱，以便後續指令能高效且精確地找到玩家。

**優先級原因**: 建立正確的資料模型基礎，確保後續功能的效能與正確性。

**獨立測試方法**: 模擬玩家連線，驗證快取管理器中是否正確儲存了對應的 UUID。

**驗收場景**:

1. **Given** 玩家開始連線流程，**When** 系統攔截連線資訊，**Then** 系統應同時取得玩家名稱與 UUID 並更新至快取中。

---

### 使用者故事 3 - 專案文件與代碼清理 (優先級: P3)

身為開發者，我希望專案目錄中只保留最新且相關的規格文件與精簡的代碼，以減少維護負擔。

**優先級原因**: 提升開發效率，避免過時文檔誤導開發方向。

**獨立測試方法**: 檢查 `specs/` 目錄結構，確認過時目錄已移除。

**驗收場景**:

1. **Given** 專案中存在過時的 `013` 規格，**When** 執行專案清理，**Then** 該目錄應被完全移除。

## 指令與權限 (Commands & Permissions) *(必填)*

### 指令 (Syntax)

- `/welcome <player>`: 查詢或歡迎特定玩家（現有指令，本功能重構其效能表現）。

### 權限 (Permission Nodes)

- `welcome.use`: 允許使用基本歡迎功能。

## 設定需求 (Configuration Requirements) *(必填)*

- 無新增配置需求，但需確保快取邏輯不影響現有 `config.yml` 的運作。

## 需求規格 (Requirements) *(必填)*

### Functional Requirements

- **FR-001**: 系統必須在快取管理器中同時儲存玩家名稱與其對應的唯一識別碼（UUID）。
- **FR-002**: 系統必須在玩家連線的非同步階段擷取並更新身分識別資訊。
- **FR-003**: 所有涉及離線玩家查詢的指令邏輯必須移除會導致主線程阻塞的同步呼叫。
- **FR-004**: 系統必須從快取中優先獲取 UUID，並利用 UUID 進行玩家物件的檢索。
- **FR-005**: 專案目錄必須移除編號為 `013` 的所有過時規格文件。
- **FR-006**: 代碼實作應移除冗餘的依賴與未使用的建構子欄位。

### Key Entities

- **Player Identification Cache**: 負責媒合玩家名稱與 UUID 的高效能資料結構，確保 O(1) 或 O(log n) 的查詢效率。

## Success Criteria *(mandatory)*

### Measurable Outcomes

- **SC-001**: 執行 Welcome 指令時，伺服器主線程的阻塞時間減少至可忽略不計（趨近於 0ms）。
- **SC-002**: 玩家快取管理器成功轉型為以 UUID 為核心的儲存模式。
- **SC-003**: 專案 `specs/` 目錄結構清晰，僅保留生效中的規格版本。
- **SC-004**: 現有的單元測試在重構後仍能全數通過，且新增針對延遲風險的測試案例。
