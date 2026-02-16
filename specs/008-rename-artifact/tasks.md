# Tasks: Rename Build Artifact to Welcome.jar

**Input**: Design documents from `/specs/008-rename-artifact/`
**Prerequisites**: plan.md, spec.md, research.md, data-model.md

**測試**: 本功能不涉及業務邏輯變更，測試重點在於驗證檔案系統中的產出物名稱。

## 第一階段：基礎建設 (Foundational) - 必要前置作業

**目的**: 修改 Gradle 建置設定以變更產出物名稱。

- [x] T001 修改 `build.gradle`：使用 `base { archivesName = 'Welcome' }` 進行現代化配置

---

## 第二階段：使用者故事 1 - 規範化建置產物名稱 (優先級: P1) 🎯 MVP

**目標**: 確保所有建置產物（含 CI/CD 流程）皆更新為大寫 `Welcome` 開頭。

**獨立測試**: 執行 `./gradlew clean build` 並檢查 `build/libs/` 下是否存在 `Welcome-*.jar` 且不存在 `welcome-*.jar`。

### 實作 (Implementation)

- [x] T002 執行全域掃描並更新 `.github/workflows/` 下所有引用到 `welcome` 的路徑為 `Welcome`
- [x] T003 更新 `.github/workflows/ci.yml` 中上傳產出物或發布 Release 的檔案名稱樣式

---

## 第三階段：打磨與優化 (Polish)

**目的**: 驗證端到端建置流程。

- [x] T004 執行 `./gradlew clean build` 驗證所有產出物名稱 (含主 JAR、-sources.jar、-javadoc.jar) 皆正確以 Welcome- 開頭 (SC-001, SC-002)
- [x] T005 檢查是否有其他隱藏腳本 (如 Makefile, scripts/, deploy.sh) 硬編碼了舊名稱並予以修正
