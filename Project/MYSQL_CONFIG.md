# MySQL 数据库配置说明

## 数据库连接配置

在运行项目前，请确保：

### 1. 安装 MySQL
确保本地已安装 MySQL 数据库服务器。

### 2. 创建数据库
在 MySQL 中创建数据库：

```sql
CREATE DATABASE museum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 修改连接配置（可选）

在 `DatabaseManager.java` 文件中，默认配置为：

```java
private static final String DB_HOST = "localhost";
private static final String DB_PORT = "3306";
private static final String DB_NAME = "museum_db";
private static final String DB_USER = "root";
private static final String DB_PASSWORD = "root";
```

**请根据你的 MySQL 实际配置修改：**

- `DB_HOST`: MySQL 服务器地址（默认 localhost）
- `DB_PORT`: MySQL 端口（默认 3306）
- `DB_NAME`: 数据库名称（默认 museum_db）
- `DB_USER`: MySQL 用户名（默认 root）
- `DB_PASSWORD`: MySQL 密码（默认 root，**请改为你的实际密码**）

### 4. 在 IDEA 中运行

由于 JavaFX 需要特殊的运行方式，请在 IDEA 中：

**方法 1：使用 Maven 运行（推荐）**

1. 打开右侧的 Maven 面板
2. 展开 **Plugins → javafx**
3. 双击 **javafx:run**

**方法 2：配置 Run Configuration**

1. 点击菜单 **Run → Edit Configurations**
2. 点击 **+** → **Maven**
3. Name: `Run JavaFX App`
4. Command line: `javafx:run`
5. 点击 **OK**
6. 点击运行按钮

---

## 表结构

系统会自动创建以下表：

### users 表
```sql
user_id INT PRIMARY KEY AUTO_INCREMENT
username VARCHAR(50) NOT NULL UNIQUE
password VARCHAR(255) NOT NULL
email VARCHAR(100)
role VARCHAR(20) DEFAULT 'user'
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

### heritage 表（非遗）
```sql
heritage_id INT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(200) NOT NULL
category VARCHAR(100)
region VARCHAR(100)
description TEXT
image_path VARCHAR(500)
year_recognized INT
created_by INT
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

### architecture 表（古建筑）
```sql
architecture_id INT PRIMARY KEY AUTO_INCREMENT
name VARCHAR(200) NOT NULL
dynasty VARCHAR(50)
location VARCHAR(200)
type VARCHAR(50)
description TEXT
image_path VARCHAR(500)
year_built INT
created_by INT
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
```

---

## 注意事项

⚠️ **重要**：
- 确保 MySQL 服务正在运行
- 确保已创建数据库 `museum_db`
- 确保数据库用户名和密码正确
- 首次运行时，表会自动创建
