# Tasks: Rename Project from Wellcome to Welcome

**Input**: Design documents from `/specs/007-rename-wellcome/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md

**測試**: 根據專案憲法，測試是 **強制性的**。使用 **JUnit 5** 撰寫測試，並透過 `./gradlew test` 執行。

## 第一階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 更新專案層級的配置檔案。

- [x] T001 更新 `settings.gradle` 中的 `rootProject.name` 為 `welcome`
- [x] T002 更新 `src/main/resources/plugin.yml`:
    - `name: Welcome`
    - `main: com.welcome.WelcomePlugin`
- [x] T003 更新 `src/main/resources/config.yml` 與 `src/main/resources/messages.yml` 中的預設字串 (wellcome -> welcome)

---

## 第二階段：使用者故事 1 - 統一拼寫為 Welcome (優先級: P1) 🎯 MVP

**目標**: 完成全專案的代碼與套件重構，並包含資料遷移邏輯。

**獨立測試**: 執行 `./gradlew clean test` 並確認 100% 通過。

### 測試 (Test-First) ⚠️

- [x] T004 [P] [US1] 確保現有所有測試在重構前皆為通過狀態 (驗證基準)
- [x] T005 [P] [US1] 準備重構後的測試套件 (同步更新套件引用)

### 實作 (Implementation)

- [x] T006 [US1] 執行 Java 套件重構: `com.wellcome` -> `com.welcome`
- [x] T007 [US1] 執行類別名稱重構: 所有以 `Wellcome` 為前綴的類別改為 `Welcome`
- [x] T008 [US1] 執行全域文字與檔案名稱替換: 掃描專案內所有檔案，執行大小寫敏感的映射替換 (wellcome->welcome, Wellcome->Welcome)
- [x] T009 [US1] 更新 `build.gradle` 中的 `group` 或相關引用
- [x] T010 [US1] 在 `src/main/java/com/welcome/WelcomePlugin.java` 實作啟動時的資料夾遷移邏輯 (Wellcome -> Welcome)
- [x] T010b [US1] 執行 `./gradlew dependencies` 驗證重構後的 build.gradle 外部依賴解析正常

**檢查點**: 重構完成，專案應能成功編譯並通過所有單元測試。

---

## 第三階段：打磨與優化 (Polish)

**目的**: 修正文件與清理遺留產物。

- [x] T011 [P] 更新 `README.md` 中的所有專案名稱引用
- [x] T012 清理 `build/` 目錄並重新執行 `./gradlew build` 確認 JAR 檔案名稱正確
- [x] T013 檢查所有原始碼註釋與日誌訊息，確保無遺漏的舊拼寫

---

## Dependencies & Execution Order

1. **Foundational (T001-T003)**: 必須先完成配置更新，以確保重構後的類別路徑與 `plugin.yml` 一致。
2. **User Story 1 (T004-T010b)**: 核心重構階段。
3. **Polish (T011-T013)**: 最後的文件與產物檢查。

## Implementation Strategy

- **MVP First**: 優先確保 `plugin.yml` 與套件路徑一致，使外掛能正常載入。
- **Variant Mapping**: 嚴格遵守 `wellcome` -> `welcome` 與 `Wellcome` -> `Welcome` 的大小寫映射。
- **Atomic Commits**: 每完成一個階段的重構後立即提交，以防回溯困難。
