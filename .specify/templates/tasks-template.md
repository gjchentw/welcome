---

description: "Task list template for feature implementation"
---

# Tasks: [FEATURE NAME]

**Input**: Design documents from `/specs/[###-feature-name]/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**測試**: 根據專案憲法，測試是 **強制性的**，並且必須在實作之前撰寫 (測試優先原則)。下面的範例包含了測試任務，應根據此原則進行規劃。

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Path Conventions

- **Single project**: `src/`, `tests/` at repository root
- **Web app**: `backend/src/`, `frontend/src/`
- **Mobile**: `api/src/`, `ios/src/` or `android/src/`
- Paths shown below assume single project - adjust based on plan.md structure

<!-- 
  ============================================================================
  IMPORTANT: The tasks below are SAMPLE TASKS for illustration purposes only.
  
  The /speckit.tasks command MUST replace these with actual tasks based on:
  - User stories from spec.md (with their priorities P1, P2, P3...)
  - Feature requirements from plan.md
  - Entities from data-model.md
  - Endpoints from contracts/
  
  Tasks MUST be organized by user story so each story can be:
  - Implemented independently
  - Tested independently
  - Delivered as an MVP increment
  
  DO NOT keep these sample tasks in the generated tasks.md file.
  ============================================================================
-->

## 第一階段：設置 (Setup) - 共用基礎設施

**目的**: 初始化專案結構與基礎設定 (plugin.yml, Maven/Gradle)

- [ ] T000 建立 `LICENSE` 檔案 (採用 MIT License)
- [ ] T000b 建立基礎 `README.md` (含專案目的、開發環境、安裝配置)
- [ ] T000c 設定 GitHub Actions Workflow (Push -> Nightly-${hash}, Tag -> Release)
- [ ] T001 建立專案結構 (src/main/java, src/main/resources)
- [ ] T002 初始化 [Maven/Gradle] 專案 (使用 JDK 25) 並加入 Spigot/Paper API 依賴
- [ ] T003 [P] 設定 `plugin.yml` (main class, version, api-version)
- [ ] T004 [P] 設定 checkstyle/spotless 程式碼風格工具

---

## 第二階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 核心基礎建設，所有功能都依賴此階段 (Config, Database, Utils)

**⚠️ 關鍵**: 在此階段完成前，無法開始實作具體功能

- [ ] T005 實作 `ConfigManager` 以讀取 config.yml 與 messages.yml
- [ ] T006 [P] 建立 `MessageUtils` 支援 Hex Color 與 Placeholders
- [ ] T007 [P] 設定資料庫連線池 (HikariCP) 或本地存檔機制 (JSON/YAML)
- [ ] T008 建立基礎 `CommandExecutor` 抽象類別 (包含權限檢查與錯誤處理)
- [ ] T009 設定日誌 (Logging) 與除錯模式 (Debug Mode) 開關

**檢查點**: 基礎設施就緒 - 可開始並行開發各個功能模組

---

## 第三階段：使用者故事 1 - [標題] (優先級: P1) 🎯 MVP

**目標**: [簡述此故事交付的價值]

**獨立測試**: [如何驗證此功能 (例如：輸入指令 /test)]

### 測試 (Test-First) ⚠️

> **注意：請先撰寫 Mock Bukkit 測試或單元測試**

- [ ] T010 [P] [US1] 撰寫業務邏輯的單元測試 (不依賴 Bukkit API)
- [ ] T011 [P] [US1] 撰寫指令輸入輸出的整合測試

### 實作 (Implementation)

- [ ] T012 [P] [US1] 在 `plugin.yml` 註冊指令與權限節點
- [ ] T013 [P] [US1] 建立資料模型 (POJO) 與 DAO 層
- [ ] T014 [US1] 實作 `CommandExecutor` 處理指令邏輯 (依賴 T013)
- [ ] T015 [US1] 實作 `Listener` 監聽相關遊戲事件 (如 PlayerJoinEvent)
- [ ] T016 [US1] 在 `MainPlugin` 的 onEnable 註冊指令與監聽器
- [ ] T017 [US1] 加入設定檔參數與訊息文字至 `config.yml`

**檢查點**: 使用者故事 1 應可獨立運作並通過測試

---

## 第四階段：使用者故事 2 - [標題] (優先級: P2)

**目標**: [簡述此故事交付的價值]

**獨立測試**: [如何驗證此功能]

### 測試 (Test-First) ⚠️

- [ ] T018 [P] [US2] 撰寫相關邏輯測試

### 實作 (Implementation)

- [ ] T019 [P] [US2] 在 `plugin.yml` 註冊新指令與權限
- [ ] T020 [US2] 實作相關 `Manager` 或 `Service` (非同步處理)
- [ ] T021 [US2] 實作指令或事件監聽器
- [ ] T022 [US2] 整合 US1 的組件 (如需)

**檢查點**: US1 與 US2 皆可獨立運作

---

## 第 N 階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與檢查

- [ ] TXXX [P] 完善 javadoc 與 README 文件
- [ ] TXXX 確保 `onDisable` 正確釋放資源 (無 Memory Leak)
- [ ] TXXX 測試 `/plugin reload` 指令是否正常重載設定
- [ ] TXXX 優化資料庫查詢 (確保為 Async)
- [ ] TXXX 檢查權限節點是否覆蓋所有敏感操作

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: All depend on Foundational phase completion
  - User stories can then proceed in parallel (if staffed)
  - Or sequentially in priority order (P1 → P2 → P3)
- **Polish (Final Phase)**: Depends on all desired user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Can start after Foundational (Phase 2) - No dependencies on other stories
- **User Story 2 (P2)**: Can start after Foundational (Phase 2) - May integrate with US1 but should be independently testable
- **User Story 3 (P3)**: Can start after Foundational (Phase 2) - May integrate with US1/US2 but should be independently testable

### Within Each User Story

- Tests (if included) MUST be written and FAIL before implementation
- Models before services
- Services before endpoints
- Core implementation before integration
- Story complete before moving to next priority

### Parallel Opportunities

- All Setup tasks marked [P] can run in parallel
- All Foundational tasks marked [P] can run in parallel (within Phase 2)
- Once Foundational phase completes, all user stories can start in parallel (if team capacity allows)
- All tests for a user story marked [P] can run in parallel
- Models within a story marked [P] can run in parallel
- Different user stories can be worked on in parallel by different team members

---

## Parallel Example: User Story 1

```bash
# Launch all tests for User Story 1 together (if tests requested):
Task: "Contract test for [endpoint] in tests/contract/test_[name].py"
Task: "Integration test for [user journey] in tests/integration/test_[name].py"

# Launch all models for User Story 1 together:
Task: "Create [Entity1] model in src/models/[entity1].py"
Task: "Create [Entity2] model in src/models/[entity2].py"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete Phase 2: Foundational (CRITICAL - blocks all stories)
3. Complete Phase 3: User Story 1
4. **STOP and VALIDATE**: Test User Story 1 independently
5. Deploy/demo if ready

### Incremental Delivery

1. Complete Setup + Foundational → Foundation ready
2. Add User Story 1 → Test independently → Deploy/Demo (MVP!)
3. Add User Story 2 → Test independently → Deploy/Demo
4. Add User Story 3 → Test independently → Deploy/Demo
5. Each story adds value without breaking previous stories

### Parallel Team Strategy

With multiple developers:

1. Team completes Setup + Foundational together
2. Once Foundational is done:
   - Developer A: User Story 1
   - Developer B: User Story 2
   - Developer C: User Story 3
3. Stories complete and integrate independently

---

## Notes

- [P] tasks = different files, no dependencies
- [Story] label maps task to specific user story for traceability
- Each user story should be independently completable and testable
- Verify tests fail before implementing
- Commit after each task or logical group
- Stop at any checkpoint to validate story independently
- Avoid: vague tasks, same file conflicts, cross-story dependencies that break independence
