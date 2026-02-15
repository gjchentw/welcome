# Phase 0: Research & Decisions - README.md 重構

## 1. 內容架構規劃

根據憲法與規格書，重構後的 `README.md` 將採用以下結構：

1.  **專案名稱與標章 (Status Badges)**:
    - 標題: `Wellcome`
    - 標章: Build Status (GitHub Actions), Test Status (GitHub Actions)。
2.  **專案目的 (Project Purpose)**:
    - 強調核心功能：投票自動加入白名單。
    - 核心機制描述。
3.  **開發流程 (Development Workflow)**:
    - 說明採用 Spec-Kit SDD。
    - 列出核心指令：`/speckit.specify`, `/speckit.plan`, `/speckit.implement`。
4.  **開發環境 (Development Environment)**:
    - 強調最低需求：Java 25, Gradle。
5.  **安裝配置 (Installation & Configuration)**:
    - 包含快速開始、`config.yml` 與 `messages.yml` 的關鍵配置。
6.  **信任度與品質 (Trust & Quality)**:
    - 提及 CI/CD 自動化測試確保穩定性。
7.  **授權 (License)**:
    - MIT License。

## 2. 決策與行動方案

### Decision: 標章 URL 格式
- **方案**: 使用 `https://github.com/gjchentw/welcome/actions/workflows/ci.yml/badge.svg`。
- **原因**: 符合 GitHub Actions 標準標章路徑。

### Decision: 文案風格
- **方案**: 專業、直觀且以正體中文為主。
- **原因**: 符合使用者溝通偏好。
