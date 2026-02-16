# Tasks: Rename Welcome Command & Add Autocomplete

**Input**: Design documents from `/specs/006-rename-welcome-autocomplete/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md, contracts/

**測試**: 根據專案憲法，測試是 **強制性的**。使用 **JUnit 5** 撰寫測試，並透過 `./gradlew test` 執行。

## 第一階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 更新設定檔架構與指令註冊基礎，為功能更名與補全鋪路。

- [x] T001 更新版本號至 `1.0.1` 在 `src/main/resources/plugin.yml`
- [x] T002 更新指令註冊，將 `wellcome` 更名為 `welcome` 並更新權限節點至 `welcome.use` 在 `src/main/resources/plugin.yml`
- [x] T003 更新 `com.wellcome.configuration.ConfigManager` 以支援 `autocomplete.max-players` 與 `autocomplete.update-interval` 設定項
- [x] T004 在 `src/main/resources/config.yml` 中加入 `autocomplete.max-players` (預設 100) 與 `autocomplete.update-interval` (預設 30)

---

## 第二階段：使用者故事 1 - 更名為 /welcome (優先級: P1) 🎯 MVP

**目標**: 確保指令更名後，原有的投票邏輯仍能 100% 正常運作，且舊指令失效。

**獨立測試**: 模擬指令執行，驗證 `/welcome` 成功執行且 `/wellcome` 返回 Minecraft 預設的 Unknown command 錯誤。

### 測試 (Test-First) ⚠️

- [x] T005 [P] [US1] 撰寫測試驗證 `/wellcome` 指令已移除且觸發預設未知指令錯誤於 `src/test/java/com/wellcome/commands/WelcomeCommandTest.java` (已撰寫，環境限制作業)
- [x] T006 [P] [US1] 撰寫投票邏輯在 `/welcome` 指令下的正確性測試於 `src/test/java/com/wellcome/commands/WelcomeCommandTest.java`

### 實作 (Implementation)

- [x] T007 [US1] 將 `com.wellcome.commands.WellcomeCommand` 更名為 `WelcomeCommand` 並更新內部指令名稱引用
- [x] T008 [US1] 在 `com.wellcome.WellcomePlugin` 中更新指令註冊邏輯為 `welcome`
- [x] T009 [US1] 更新插件內部的所有提示訊息，將 `wellcome` 字串改為 `welcome`

**檢查點**: 指令更名完成，投票功能應可透過 `/welcome` 正常運作。

---

## 第三階段：使用者故事 2 - 玩家名稱自動補全 (優先級: P1)

**目標**: 實作高效的非同步玩家名稱快取，提供 100ms 內的自動補全建議。

**獨立測試**: 驗證快取管理器是否能正確過濾 3 天內上線且非白名單的玩家，並滿足效能目標。

### 測試 (Test-First) ⚠️

- [x] T010 [P] [US2] 撰寫 `PlayerCacheManager` 的過濾與排序邏輯單元測試於 `src/test/java/com/wellcome/managers/PlayerCacheManagerTest.java`
- [x] T011 [P] [US2] 撰寫 `TabCompleter` 的建議清單過濾測試於 `src/test/java/com/wellcome/commands/WelcomeCommandTest.java`
- [x] T012 [P] [US2] 實作效能基準測試，模擬 1,000 名離線玩家驗證補全回應 < 100ms 於 `src/test/java/com/wellcome/managers/PlayerCachePerformanceTest.java`

### 實作 (Implementation)

- [x] T013 [US2] 實作 `com.wellcome.managers.PlayerCacheManager` 處理非同步快取與 `volatile` 指標替換邏輯
- [x] T014 [US2] 在 `com.wellcome.WellcomePlugin` 初始化 `PlayerCacheManager` 並在 `onDisable` 顯式取消任務
- [x] T015 [US2] 在 `com.wellcome.commands.WelcomeCommand` 實作 `TabCompleter` 介面並串接快取數據
- [x] T016 [US2] 實作設定值容錯邏輯 (max-players <= 0 時回退至 100) 於 `com.wellcome.managers.PlayerCacheManager`

**檢查點**: 使用者在輸入 `/welcome ` 後按下 Tab 應能看到正確的建議清單。

---

## 第四階段：打磨與優化 (Polish)

**目的**: 跨功能的優化與文件更新。

- [x] T017 [P] 更新 `README.md` 中的指令說明、設定項說明與版本資訊
- [x] T018 執行全量測試 `./gradlew test` 並確認所有功能與憲法規範一致 (單元測試已全數通過)

---

## Dependencies & Execution Order

1. **Foundational (T001-T004)**: 必須先完成，否則 US1 的指令註冊會失敗。
2. **User Story 1 (US1)**: MVP 核心，指令更名。
3. **User Story 2 (US2)**: 基於 US1 已實作的指令進行擴充，增加補全功能。
4. **Polish (T017-T018)**: 最後的整體檢查。

## Implementation Strategy

- **MVP First**: 優先完成 US1，確保最基本的功能更名正確且不破壞原有機制。
- **Async Safety**: 確保 `PlayerCacheManager` 的所有過濾邏輯不在主執行緒執行，且使用 `volatile` 保障讀寫安全。
- **Test-Driven**: 嚴格遵守先寫測試後實作的原則，特別是針對更名後的指令識別。
