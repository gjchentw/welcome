# Quickstart: Updating Git Origin and Ensuring Consistency

本指南說明如何執行專案倉庫地址更新與文件一致性修正。

## 步驟 1: 更新 Git 遠端倉庫

執行以下指令以更新 origin 地址並清理過時分支：

```bash
git remote set-url origin git@github.com:gjchentw/Welcome.git
git remote prune origin
```

## 步驟 2: 更新專案文件

使用 `sed` 指令（或編輯器的批量替換功能）更新 `README.md` 與 `GEMINI.md`：

### 更新 README.md
```bash
# 替換 Repository 連結與 Status Badge
sed -i 's/gjchentw\/welcome/gjchentw\/Welcome/g' README.md

# 替換文字描述中的拼寫 (wellcome -> Welcome)
# 注意：使用 -i.bak 以防萬一，或直接替換
sed -i 's/[Ww]ellcome/Welcome/g' README.md
```

### 更新 GEMINI.md
```bash
sed -i 's/[Ww]ellcome/Welcome/g' GEMINI.md
```

## 步驟 3: 驗證變更

1. **檢查 Git 狀態**:
   ```bash
   git remote -v
   ```

2. **掃描舊關鍵字**:
   ```bash
   grep -riE "gjchentw/welcome|wellcome" README.md GEMINI.md .github/workflows/
   ```
   *預期結果應為空（或僅顯示非相關內容）。*

3. **測試連通性**:
   ```bash
   git fetch origin
   ```

4. **推送並驗證 CI**:
   ```bash
   git add .
   git commit -m "docs: update git origin and ensure project name consistency"
   git push origin 009-update-git-origin
   ```
   前往 GitHub Actions 頁面確認 Workflow 正常執行。
