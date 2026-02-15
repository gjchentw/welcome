---

description: "Task list for refactoring README.md based on constitution v1.8.0"
---

# Tasks: 重構 README.md 文件

**Input**: Design documents from `specs/004-refactor-readme/`
**Prerequisites**: spec.md, plan.md, research.md

## 第一階段：準備工作 (Setup)

**目的**: 確保重構過程安全可控

- [X] T001 備份現有的 README.md 檔案 在 `README.md.bak`

---

## 第二階段：重構執行 (Implementation) - 使用者故事 1 🎯 MVP

**目的**: 依照憲法原則完成文件重組

**獨立測試**: 
1. 標章連結可正常點擊並顯示狀態。
2. 文件內容包含所有憲法要求的必要章節。

- [X] T002 [US1] 在 `README.md` 頂部加入建置與測試狀態標章 (GitHub Actions)
- [X] T003 [US1] 撰寫「專案目的 (Project Purpose)」章節，強調投票白名單機制
- [X] T004 [US1] 新增「開發流程 (Development Workflow)」章節，說明 Spec-Kit SDD 工作流
- [X] T005 [US1] 更新「開發環境 (Development Environment)」章節，標註 Java 25 與 Gradle
- [X] T006 [US1] 擴充「安裝配置 (Installation & Configuration)」章節，含設定檔範例
- [X] T007 [US1] 新增關於 CI/CD 信任度與 Release Asset 的說明
- [X] T008 [US1] 確保「授權 (License)」章節正確標示為 MIT License

---

## 第 N 階段：打磨與優化 (Polish)

**目的**: 確保最終文件品質

- [X] T009 驗證 `README.md` 中的所有連結與圖片標章是否顯示正常
- [X] T010 檢查 Markdown 排版與標題層級是否符合專案風格
- [X] T011 移除備份檔案 `README.md.bak`

---

## 依賴關係與執行順序 (Dependencies & Execution Order)

### 階段依賴

- **準備工作 (Phase 1)**: 必須最先執行。
- **重構執行 (Phase 2)**: 核心任務，可依優先序 P1 執行。
- **打磨 (Phase 3)**: 最後進行。

### 使用者故事依賴

- **使用者故事 1 (P1)**: 包含 T002 至 T008。

---

## 實作策略 (Implementation Strategy)

直接對根目錄的 `README.md` 執行區塊替換或重寫。確保文字風格專業且符合正體中文習慣。

---

## 備註 (Notes)

- 標章 URL 請使用研究文件中決策的格式。
- 開發流程章節需特別提及 `/speckit.*` 系列指令的使用。
