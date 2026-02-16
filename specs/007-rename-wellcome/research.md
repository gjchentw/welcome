# Research: Rename Project from Wellcome to Welcome

## 1. 資料夾遷移策略 (Data Folder Migration)
- **Decision**: 在 `onLoad()` 或 `onEnable()` 最早期偵測並更名。
- **Rationale**: 由於 `plugin.yml` 的 `name` 變更會導致 Spigot 自動尋找新的資料夾，若不手動遷移，舊設定將遺失。
- **Implementation Note**: 使用 `new File(getDataFolder().getParentFile(), "Wellcome")` 檢查是否存在，若存在且新資料夾不存在，則執行 `renameTo()`.

## 2. 大規模更名策略 (Mass Rename Strategy)
- **Decision**: 結合 IDE 重構工具與 `sed` 腳本。
- **Rationale**: IDE 能處理複雜的類別引用，而 `sed` 則確保註釋、文件與 `build.gradle` 等非程式碼部分無一遺漏。
- **Variant Mapping**:
  - `com.wellcome` -> `com.welcome`
  - `WellcomePlugin` -> `WelcomePlugin`
  - `WellcomeCommand` -> `WelcomeCommand`
  - `wellcome` -> `welcome` (Case-insensitive where appropriate)

## 3. CI/CD 與建置產物
- **Decision**: 同步更新 `settings.gradle` 的 `rootProject.name`。
- **Rationale**: 確保 GitHub Actions 生成的 JAR 檔案名稱為 `Welcome-1.1.0.jar` 而非舊名。
- **Risk**: 需要確認 CI 腳本（如 `.github/workflows/ci.yml`）是否硬編碼了舊名稱。
