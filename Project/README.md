# 中华古建筑/非遗数字博物馆展示系统

> 一个基于 JavaFX 和 MySQL 的中华优秀传统文化展示平台

## 📖 项目简介

本系统旨在通过数字化手段保护和传承中华优秀传统文化，让用户能够便捷地浏览、学习中国古建筑和非物质文化遗产的相关知识。系统采用 JavaFX 图形界面技术，提供美观、直观的用户体验。

**主题**: 中华古建筑与非物质文化遗产数字传承

**设计理念**: 结合传统中国美学（帝王红、金色、玉绿色等），打造具有文化特色的现代化展示平台。

---

## ✨ 功能特性

### 用户功能
- ✅ 用户注册与登录
- ✅ 浏览非物质文化遗产项目
- ✅ 浏览中国古建筑信息
- ✅ 搜索与筛选功能
- ✅ 详细信息展示（图文并茂）
- ✅ 个人信息管理

### 管理员功能
- ✅ 后台管理面板
- ✅ 非遗项目增删改查
- ✅ 古建筑信息增删改查
- ✅ 数据验证与管理

### UI 特色
- 🎨 传统中国风配色方案（帝王红、金色、米白色）
- 🎯 直观的导航与交互
- 📱 响应式布局设计
- ✨ 流畅的动画效果

---

## 🛠️ 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| JavaFX | 17.0.14 | GUI 框架 |
| MySQL | 8.0+ | 数据库 |
| Maven | 3.6+ | 项目管理工具 |
| JDBC | 8.0.33 | 数据库驱动 |

---

## 📋 环境要求

### 必需软件
- ✅ JDK 17 或更高版本
- ✅ MySQL 8.0 或更高版本
- ✅ Maven 3.6 或更高版本
- ✅ IntelliJ IDEA（推荐）

### JavaFX 配置
如果在 IDEA 中直接运行，需要配置 JavaFX SDK：
- 下载 JavaFX SDK 17.0.14
- 在 IDEA 中配置 VM options（见下文）

---

## 🚀 安装与配置

### 1. 克隆/下载项目

```bash
git clone <repository-url>
cd MuseumDisplaySystem
```

### 2. 配置 MySQL 数据库

#### 启动 MySQL 服务
确保 MySQL 服务正在运行。

#### 创建数据库
在 MySQL 命令行或 Workbench 中执行：

```sql
CREATE DATABASE museum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 配置数据库连接
打开 `src/main/java/museum/dao/DatabaseManager.java`，修改以下配置：

```java
private static final String DB_HOST = "localhost";      // 数据库主机
private static final String DB_PORT = "3306";           // 端口
private static final String DB_NAME = "museum_db";      // 数据库名
private static final String DB_USER = "root";           // 用户名
private static final String DB_PASSWORD = "你的密码";    // 密码（必改）
```

### 3. 导入 Maven 依赖

在 IDEA 中打开项目后，Maven 会自动下载依赖。如果没有自动下载：

```bash
mvn clean install
```

---

## ▶️ 运行项目

### 方法 1：使用 Maven 运行（推荐）⭐

**最简单、最稳定的方式**

1. 在 IDEA 右侧打开 **Maven** 面板
2. 展开 **Plugins → javafx**
3. 双击 **javafx:run**

或在命令行中：
```bash
mvn javafx:run
```

### 方法 2：直接运行 MainApp（需配置）

#### 配置步骤：
1. **Run → Edit Configurations**
2. 点击 **+** → **Application**
3. 配置如下：
   - **Name**: Run Museum App
   - **Main class**: `museum.MainApp`
   - **VM options**: 
     ```
     --module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
     ```
     （将路径改为你的 JavaFX SDK 路径）
4. 点击 **OK**
5. 点击运行按钮 ▶️

---

## 📁 项目结构

```
MuseumDisplaySystem/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── museum/
│   │   │       ├── MainApp.java           # 主程序入口
│   │   │       ├── controller/            # 控制器层
│   │   │       │   ├── LoginController.java
│   │   │       │   ├── RegisterController.java
│   │   │       │   ├── MainDashboardController.java
│   │   │       │   ├── AdminDashboardController.java
│   │   │       │   ├── HeritageDetailController.java
│   │   │       │   └── ArchitectureDetailController.java
│   │   │       ├── dao/                   # 数据访问层
│   │   │       │   ├── DatabaseManager.java
│   │   │       │   ├── UserDAO.java
│   │   │       │   ├── HeritageDAO.java
│   │   │       │   └── ArchitectureDAO.java
│   │   │       ├── model/                 # 数据模型层
│   │   │       │   ├── User.java
│   │   │       │   ├── Heritage.java
│   │   │       │   └── Architecture.java
│   │   │       └── util/                  # 工具类
│   │   │           ├── SessionManager.java
│   │   │           └── AlertUtil.java
│   │   └── resources/
│   │       ├── com/                       # FXML 布局文件
│   │       │   ├── Login.fxml
│   │       │   ├── Register.fxml
│   │       │   ├── MainDashboard.fxml
│   │       │   ├── AdminDashboard.fxml
│   │       │   ├── HeritageDetail.fxml
│   │       │   └── ArchitectureDetail.fxml
│   │       └── css/
│   │           └── styles.css             # 样式表
├── pom.xml                                # Maven 配置
├── README.md                              # 项目说明
├── MYSQL_CONFIG.md                        # MySQL 配置详解
└── IDEA_RUN_GUIDE.md                      # IDEA 运行指南
```

---

## 💾 数据库表结构

系统首次运行时会自动创建以下表：

### users（用户表）
| 字段 | 类型 | 说明 |
|------|------|------|
| user_id | INT | 主键，自增 |
| username | VARCHAR(50) | 用户名，唯一 |
| password | VARCHAR(255) | 密码 |
| email | VARCHAR(100) | 邮箱 |
| role | VARCHAR(20) | 角色（user/admin）|
| created_at | TIMESTAMP | 创建时间 |

### heritage（非遗表）
| 字段 | 类型 | 说明 |
|------|------|------|
| heritage_id | INT | 主键，自增 |
| name | VARCHAR(200) | 项目名称 |
| category | VARCHAR(100) | 类别 |
| region | VARCHAR(100) | 地区 |
| description | TEXT | 描述 |
| image_path | VARCHAR(500) | 图片路径 |
| year_recognized | INT | 入选年份 |
| created_by | INT | 创建者ID |
| created_at | TIMESTAMP | 创建时间 |

### architecture（古建筑表）
| 字段 | 类型 | 说明 |
|------|------|------|
| architecture_id | INT | 主键，自增 |
| name | VARCHAR(200) | 建筑名称 |
| dynasty | VARCHAR(50) | 朝代 |
| location | VARCHAR(200) | 位置 |
| type | VARCHAR(50) | 类型 |
| description | TEXT | 描述 |
| image_path | VARCHAR(500) | 图片路径 |
| year_built | INT | 建造年份 |
| created_by | INT | 创建者ID |
| created_at | TIMESTAMP | 创建时间 |

---

## 📝 使用说明

### 首次使用

1. **注册账号**
   - 运行程序后进入登录界面
   - 点击"去注册"按钮
   - 填写用户名、邮箱、密码
   - 注册成功后返回登录

2. **登录系统**
   - 输入用户名和密码
   - 普通用户进入展示界面
   - 管理员进入后台管理

### 普通用户操作

- **浏览**: 在主页查看非遗和古建筑列表
- **搜索**: 在搜索框输入关键词
- **筛选**: 使用左侧菜单筛选分类
- **查看详情**: 双击列表项查看详细信息
- **退出**: 点击右上角"退出"按钮

### 管理员操作

- **添加项目**: 点击"添加项目"或"添加古建筑"
- **编辑数据**: 选中表格行后点击"编辑"
- **删除数据**: 选中表格行后点击"删除选中"
- **搜索管理**: 使用搜索框过滤数据

### 创建管理员账号

普通注册只能创建 user 角色。要创建管理员：

**方法 1**: 直接在数据库中修改
```sql
UPDATE users SET role = 'admin' WHERE username = '你的用户名';
```

**方法 2**: 修改 `UserDAO.java` 的注册方法，临时允许注册管理员

---

## ⚠️ 注意事项

### 运行前检查清单
- ✅ MySQL 服务已启动
- ✅ 数据库 `museum_db` 已创建
- ✅ `DatabaseManager.java` 中密码已修改
- ✅ Maven 依赖已下载完成

### 常见问题

#### 1. JavaFX runtime components are missing
**解决**: 使用 Maven 运行（`mvn javafx:run`）或配置 VM options

#### 2. Could not connect to database
**解决**: 
- 检查 MySQL 服务是否运行
- 确认数据库名、用户名、密码是否正确
- 确认端口号是否为 3306

#### 3. 找不到 FXML 文件
**解决**: 确保 FXML 文件在 `src/main/resources/com/` 目录下

#### 4. 图片无法显示
**解决**: 
- 确保图片路径正确
- 使用绝对路径或相对于项目的路径
- 检查文件是否存在

---

## 🎨 UI 主题配色

本系统采用传统中国风配色：

| 颜色名称 | 色值 | 用途 |
|---------|------|------|
| 帝王红 | #8B0000 | 主色调、标题 |
| 金色 | #FFD700 | 强调、按钮 |
| 米白色 | #F5F5DC | 背景 |
| 白玉色 | #F8F8FF | 卡片背景 |
| 墨黑色 | #2F4F4F | 文字 |
| 玉绿色 | #00A86B | 成功提示 |

---

## 📄 许可证

本项目为教学项目，仅供学习交流使用。

---

## 👨‍💻 开发者

- 项目类型: Java 程序设计期末作品
- 开发工具: IntelliJ IDEA
- 数据库: MySQL 8.0

---

## 📮 联系方式

如有问题，请查看项目中的其他文档：
- `MYSQL_CONFIG.md` - MySQL 详细配置说明
- `IDEA_RUN_GUIDE.md` - IDEA 运行详细指南

---

**祝你使用愉快！ 🎉**
