# Data Model: Configuration Structures

**Feature**: 初始化 Spigot 插件核心架構
**Date**: 2026-02-15

## 1. config.yml (主要設定)

### 結構定義

| 鍵 (Key) | 類型 (Type) | 預設值 (Default) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `debug-mode` | Boolean | `false` | 是否開啟詳細的除錯日誌 (Debug Logs)。 |
| `prefix` | String | `&8[&bWellcome&8] ` | 插件發送所有訊息的前綴 (支援 Hex Color)。 |

### 範例 (YAML)

```yaml
# Wellcome Plugin Configuration
# Version: 1.0

# 是否啟用除錯模式，用於開發與問題排查
debug-mode: false

# 插件訊息前綴
# 支援 Hex Color (#RRGGBB) 與舊版顏色代碼 (&a, &b...)
prefix: "&8[&bWellcome&8] "
```

## 2. messages.yml (訊息設定)

### 結構定義

| 鍵 (Key) | 類型 (Type) | 預設值 (Default) | 說明 (Description) |
| :--- | :--- | :--- | :--- |
| `reload-success` | String | `&a設定已成功重載！` | 當執行 `/wellcome reload` 成功時顯示。 |
| `no-permission` | String | `&c你沒有權限執行此指令！` | 當玩家權限不足時顯示。 |
| `help-message` | List<String> | (見範例) | `/wellcome help` 的幫助訊息列表。 |

### 範例 (YAML)

```yaml
# Wellcome Plugin Messages
# Version: 1.0

reload-success: "&a設定已成功重載！"
no-permission: "&c你沒有權限執行此指令！"

help-message:
  - "&e----- &bWellcome Help &e-----"
  - "&6/wellcome reload &7- 重載設定檔"
  - "&6/wellcome help &7- 顯示此幫助訊息"
```

## 3. plugin.yml (插件描述檔)

### 結構定義

| 鍵 (Key) | 類型 (Type) | 值 (Value) |
| :--- | :--- | :--- |
| `name` | String | `Wellcome` |
| `version` | String | `${project.version}` (自動替換) |
| `main` | String | `com.wellcome.WellcomePlugin` |
| `api-version` | String | `1.21` |
| `commands` | Map | 定義 `/wellcome` 指令 |
| `permissions` | Map | 定義 `wellcome.admin` |

### 範例 (YAML)

```yaml
name: Wellcome
version: '${version}'
main: com.wellcome.WellcomePlugin
api-version: 1.21
authors: [YourName]
description: A welcome plugin for your server.

commands:
  wellcome:
    description: 主要管理指令
    usage: /wellcome <reload|help>
    permission: wellcome.use
    aliases: [wc]

permissions:
  wellcome.admin:
    description: 允許使用所有管理指令
    default: op
```
