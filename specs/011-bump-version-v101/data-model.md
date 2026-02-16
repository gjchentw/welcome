# Data Model: Versioning Representation

本文件定義版本號在專案不同組件中的表現形式及其同步規則。

## 1. 版本實體 (Version Entity)

| 檔案 | 表示形式 (Pattern) | 角色 |
| :--- | :--- | :--- |
| `build.gradle` | `version = '1.1.0'` | 事實來源 (Source of Truth) |
| `config.yml` | `# Version: 1.1.0` | 靜態註解 (Static Annotation) |
| `README.md` | `v1.1.0+` | 文件說明 (Documentation) |
| `plugin.yml` | `${version}` | 動態注入 (Dynamic Injection) |

## 2. 狀態轉換 (State Transitions)

- **更新路徑**: `build.gradle` -> `config.yml` -> `README.md` -> `tests`
- **一致性檢查**: 所有檔案中的主版本號 (`X.Y.Z`) 必須完全匹配。
