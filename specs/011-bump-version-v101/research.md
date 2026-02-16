# Research: Version Synchronization at v1.1.0

## 1. 版本號分佈情況
經過全專案掃描，確認 `1.1.0` 版本號出現於以下關鍵位置：
- **`build.gradle`**: `version = '1.1.0'` (核心定義)
- **`src/main/resources/config.yml`**: `# Version: 1.1.0` (註解)
- **`README.md`**: 多處提及 `v1.1.0+` (版本遷移說明)
- **`src/test/resources/plugin.yml`**: `version: '1.0.1'` (待修正，應與主版本同步)

## 2. 同步策略
- **build.gradle**: 維持 `1.1.0`。
- **config.yml**: 確保註解與核心版本一致。
- **README.md**: 確保描述與當前發布版本一致。
- **測試資源**: 更新 `src/test/resources/plugin.yml` 避免測試時出現版本歧義。

## 決策 Rationale
- **一致性**: 為了確保開發、測試與使用者看到的是相同的版本資訊，必須執行全面同步。
- **自動化**: 雖然 `plugin.yml` 透過 Gradle 自動注入，但 `config.yml` 註解與 `README` 文字仍需手動或透過腳本維護。本功能將手動完成此次同步。

## 待確認事項 (Resolved)
- 目標版本確認為 `1.1.0`。
- `README.md` 內容需同步修正。
