# Research: Internationalization Strategy and File Structure

## 1. 語系檔格式與管理
- **格式**: 沿用 Spigot 慣用的 YAML 格式，易於閱讀且與 `FileConfiguration` 相容。
- **儲存位置**: `plugins/Welcome/lang/` 資料夾。
- **載入機制**: 
  - 插件啟動時，檢查 `config.yml` 中的 `language` 設定（預設 `en_US`）。
  - 自動從 JAR 中提取缺失的預設語系檔。
  - 使用 `Map<String, String>` 快取所有訊息鍵值對以確保效能。

## 2. 回退 (Fallback) 機制
- 當 `LanguageManager.getMessage(key)` 被呼叫時：
  1. 優先從當前選定語言的快取中查找。
  2. 若找不到，則從 `en_US` (主語系檔) 中查找。
  3. 若皆找不到，返回 `key` 字串本身以供除錯。

## 3. README.md 全球化
- **README.md**: 重新編寫為美式英文，內容應包含：
  - Project Purpose (English)
  - Features
  - Installation & Configuration
  - Support for multiple languages
  - Link to `README_zh_TW.md`
- **README_zh_TW.md**: 移動現有的正體中文內容至此檔案。

## 4. 翻譯字典 (參考)
| Key | en_US | zh_TW |
| :--- | :--- | :--- |
| `welcome-success` | `Voted for {target}. ({current}/{required})` | `已為 {target} 投下一張歡迎票。({current}/{required})` |
| `already-whitelisted` | `{target} is already whitelisted.` | `{target} 已經在白名單中了。` |
| `no-permission` | `You don't have permission.` | `你沒有執行此指令的權限。` |

## 決策 Rationale
- 選擇建立獨立的 `LanguageManager` 而非擴充 `ConfigManager`，是為了符合單一職責原則 (SRP)，方便日後新增語系或修改載入邏輯而不影響核心配置。
- README 拆分確保了專案對國際社群的友好性，同時保留了原有中文社群的技術文檔。

## 待確認事項 (Resolved)
- 已確認 README 採用獨立檔案策略。
- 已確認支援的語系清單。
