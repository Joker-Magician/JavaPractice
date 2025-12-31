# 在 IntelliJ IDEA 中直接运行 JavaFX 应用

## 说明

当前的 `MainApp` 类已经是标准的 JavaFX 启动类，可以直接在 IDEA 中运行。

## 运行步骤

### 方法 1：直接运行（如果 IDEA 已配置好 JavaFX）

1. 右键点击 `MainApp.java` 文件
2. 选择 **Run 'MainApp.main()'**

如果出现 "JavaFX runtime components are missing" 错误，请使用方法 2。

---

### 方法 2：配置 VM Options（推荐）

#### 步骤 1：添加 JavaFX 库到项目

1. **File → Project Structure → Libraries**
2. 点击 **+** → **Java**
3. 找到你下载的 JavaFX SDK 的 `lib` 目录
4. 选择所有 `.jar` 文件，点击 **OK**

#### 步骤 2：配置运行配置

1. 点击 **Run → Edit Configurations**
2. 点击 `MainApp` 配置（或点击 **+** 创建新的 Application）
3. 在 **Main class** 填写：`museum.MainApp`
4. 在 **VM options** 添加（根据你的 JavaFX 路径修改）：

```
--module-path "C:\path\to\javafx-sdk\lib" --add-modules javafx.controls,javafx.fxml
```

**示例**（请修改为你的实际路径）：
```
--module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml
```

5. 点击 **OK**
6. 点击运行按钮

---

### 方法 3：使用 Maven 运行（最简单）

如果上面的方法配置麻烦，直接使用 Maven：

1. 打开右侧 **Maven** 面板
2. 展开 **Plugins → javafx**
3. 双击 **javafx:run**

**优点**：
- Maven 会自动处理 JavaFX 依赖
- 不需要手动配置 VM options
- 不需要下载单独的 JavaFX SDK

---

## 当前代码说明

`MainApp.java` 必须继承 `Application` 类，这是 JavaFX 的标准要求：

```java
public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // 启动逻辑
    }
    
    public static void main(String[] args) {
        launch(args);  // 启动 JavaFX 应用
    }
}
```

这是**正确且标准**的 JavaFX 启动方式。`launch()` 方法会：
1. 初始化 JavaFX 运行时
2. 创建 Application 实例
3. 调用 `start()` 方法

---

## MySQL 数据库配置

运行前别忘了：

1. 启动 MySQL 服务
2. 创建数据库：
   ```sql
   CREATE DATABASE museum_db;
   ```
3. 在 `DatabaseManager.java` 中修改密码：
   ```java
   private static final String DB_PASSWORD = "你的MySQL密码";
   ```

---

## 推荐方案

**如果你已经在 IDEA 中配置了 JavaFX 项目**，使用**方法 3（Maven）**最简单：
- 右侧 Maven 面板 → Plugins → javafx → javafx:run

如果你想直接运行 MainApp，使用**方法 2** 配置 VM options。
