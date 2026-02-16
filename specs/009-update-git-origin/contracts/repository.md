# Contracts: Repository and Documentation Updates

## 1. Git Infrastructure Contract

### 更新 Origin 地址
- **命令**: `git remote set-url origin git@github.com:gjchentw/Welcome.git`
- **Pre-condition**: 當前已配置 `origin`。
- **Post-condition**: `git remote -v` 輸出包含 `gjchentw/Welcome.git`。

### 清理遠端分支
- **命令**: `git remote prune origin`
- **Purpose**: 刪除本地過時的遠端追蹤分支。

## 2. Documentation Consistency Contract

### README.md 替換規則
- **模式 1 (Repo Link)**: `gjchentw/welcome` -> `gjchentw/Welcome`
- **模式 2 (Text)**: `wellcome` (不分大小寫) -> `Welcome`
- **例外**: 確保不影響其他無關字組（如專案路徑中已正確的部分）。

### GEMINI.md 替換規則
- **標題修正**: `# wellcome` -> `# Welcome`

### CI/CD YAML 修正
- **路徑檢查**: 掃描 `.github/workflows/*.yml` 確保無硬編碼的 `gjchentw/welcome`。

## 3. Verification Protocol

1. 執行 `git remote -v`。
2. 執行 `grep -ri "wellcome" README.md GEMINI.md` (應無結果，除非在排除清單中)。
3. 執行 `grep -ri "gjchentw/welcome" README.md .github/workflows/` (應無結果)。
