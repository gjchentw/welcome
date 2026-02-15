# Quickstart: 測試白名單歡迎功能

## 1. 環境設定
1. 啟動支援 Spigot/Paper 1.21.4 的伺服器。
2. 開啟白名單: `/whitelist on`。
3. 設定投票門檻: `/gamerule playersSleepingPercentage 50`。

## 2. 測試流程 (範例: 2位玩家在線)
1. 確保玩家 A 與 玩家 B 在線上。
2. 準備一個不在白名單的玩家名稱 `Stranger`。
3. 玩家 A 輸入: `/wellcome Stranger`。
   - **預期**: 顯示 `1/1` 或 `1/2` (視比例與進位而定)。若門檻為 50%，1 票即達標 (1/2 = 50%)。
4. 驗證 `Stranger` 是否已在白名單: `/whitelist list`。
5. 驗證全服廣播。

## 3. 重複性測試
1. 玩家 A 再次輸入: `/wellcome Stranger`。
   - **預期**: 顯示「你已經歡迎過該玩家」。
