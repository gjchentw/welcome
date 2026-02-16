# Research: Rename Build Artifact to Welcome.jar

## 1. Gradle Artifact Naming
- **Decision**: 在 `build.gradle` 中設定 `base.archivesName = 'Welcome'`。
- **Rationale**: 這是 Gradle 指導的現代做法（取代舊版的 `archivesBaseName`），能直接影響所有 JAR 產出物的名稱前綴。
- **Alternatives considered**: 
    - 使用 `jar { archiveFileName = 'Welcome.jar' }`: 會完全固定檔名，導致無法包含版本號，且無法套用於其他任務（如 sourcesJar）。

## 2. GitHub Actions Workflow Updates
- **Decision**: 全域掃描 `.github/workflows/` 並將所有 `welcome-*.jar` 的引用更新為 `Welcome-*.jar`。
- **Rationale**: GitHub Actions 通常會使用萬用字元或變數來定位產出物（例如上傳 Release），必須確保大小寫完全匹配 Linux 檔案系統。
- **Risk**: 若遺漏更新，發布流程會因找不到檔案而中斷。

## 3. Java 25 & Gradle Compatibility
- **Decision**: 維持目前的 Gradle 8.x 版本。
- **Rationale**: 目前專案已能成功編譯 Java 25 且無顯著相容性問題。
