# 中华古建筑/非遗数字博物馆 - 代码重构方案

## 零、当前项目架构 (Current Architecture)

基于当前代码库生成的架构图如下：

```mermaid
graph TB
    subgraph Presentation_Layer [表现层 (Presentation Layer)]
        MainApp[MainApp.java]
        
        subgraph Controllers [Controllers]
            LoginC[LoginController]
            RegisterC[RegisterController]
            MainDB_C[MainDashboardController]
            AdminDB_C[AdminDashboardController]
            HeritageDetailC[HeritageDetailController]
            ArchDetailC[ArchitectureDetailController]
        end
        
        subgraph FXML_Views [FXML Views]
            LoginView[Login.fxml]
            RegisterView[Register.fxml]
            MainDBView[MainDashboard.fxml]
            AdminDBView[AdminDashboard.fxml]
        end
    end

    subgraph Data_Layer [数据访问层 (DAO Layer)]
        UserDAO[UserDAO]
        HeritageDAO[HeritageDAO]
        ArchitectureDAO[ArchitectureDAO]
    end

    subgraph Model_Layer [模型层 (Entity Layer)]
        User[User]
        Heritage[Heritage]
        Architecture[Architecture]
    end

    subgraph Utils [工具类 (Utils)]
        DBUtil[DBUtil]
        SessionManager[SessionManager]
        AlertUtil[AlertUtil]
        ImageManager[ImageManager]
    end

    %% Relationships
    MainApp --> LoginC
    MainApp --> RegisterC
    MainApp --> MainDB_C
    MainApp --> AdminDB_C
    
    LoginC --> UserDAO
    LoginC --> SessionManager
    RegisterC --> UserDAO
    
    MainDB_C --> HeritageDAO
    MainDB_C --> ArchitectureDAO
    MainDB_C --> HeritageDetailC
    MainDB_C --> ArchDetailC
    
    AdminDB_C --> HeritageDAO
    AdminDB_C --> ArchitectureDAO
    AdminDB_C --> ImageManager
    
    UserDAO --> DBUtil
    HeritageDAO --> DBUtil
    ArchitectureDAO --> DBUtil
    
    Controllers --> AlertUtil
```

## 一、重构目标

本次重构旨在提升系统的**可维护性**、**安全性**、**性能**和**用户体验**，为项目的长期发展打下坚实基础。

## 二、当前问题分析

### 2.1 架构问题
- ❌ **缺少服务层**：Controllers 直接调用 DAOs，违反了单一职责原则
- ❌ **紧耦合**：业务逻辑分散在 Controller 和 DAO 中，难以复用和测试
- ❌ **缺乏异常统一处理**：错误处理分散，用户体验不一致

### 2.2 安全问题
- 🔴 **密码明文存储**：严重安全隐患，必须立即修复
- 🔴 **SQL 注入风险**：虽然使用了 PreparedStatement，但仍需审查

### 2.3 代码质量问题
- ⚠️ **代码重复**：[MainDashboardController](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/controller/MainDashboardController.java#21-157) 中列表设置逻辑重复
- ⚠️ **硬编码**：颜色、尺寸等常量散落在代码中
- ⚠️ **资源管理**：部分异常处理只是打印堆栈，没有向用户反馈

### 2.4 用户体验问题
- 💡 **错误提示不友好**：数据库错误时用户看不到任何提示
- 💡 **加载状态缺失**：加载数据时没有进度提示
- 💡 **UI 交互单一**：缺少动画和过渡效果

---

## 三、重构方案详解

### 阶段一：架构优化（高优先级）

#### 1.1 引入服务层 (Service Layer)

**目标**：将业务逻辑从 Controller 中抽离，提高代码复用性和可测试性。

##### 新建目录结构
```
src/main/java/museum/
  └─ service/
      ├─ UserService.java
      ├─ HeritageService.java
      └─ ArchitectureService.java
```

##### 实现示例：UserService
```java
public class UserService {
    private UserDAO userDAO;
    
    // 负责用户注册业务逻辑
    public boolean registerUser(User user) throws ServiceException {
        // 1. 验证用户名是否已存在
        // 2. 加密密码
        // 3. 调用 DAO 保存
        // 4. 统一异常处理
    }
    
    public User authenticateUser(String username, String password) {
        // 业务逻辑：验证、加密对比等
    }
}
```

##### 修改文件
- **[新增]** `UserService.java` - 用户业务逻辑服务
- **[新增]** `HeritageService.java` - 非遗业务逻辑服务
- **[新增]** `ArchitectureService.java` - 古建筑业务逻辑服务
- **[修改]** 所有 Controller 文件 - 改为调用 Service 而非 DAO

---

#### 1.2 统一异常处理

**目标**：统一管理异常，提供友好的用户提示。

##### 新建异常类
```
src/main/java/museum/exception/
  ├─ ServiceException.java        (业务异常基类)
  ├─ DatabaseException.java       (数据库异常)
  ├─ AuthenticationException.java (认证异常)
  └─ ValidationException.java     (验证异常)
```

##### 修改文件
- **[新增]** `museum.exception` 包及相关异常类
- **[修改]** 所有 DAO/Service - 抛出具体异常而非打印堆栈
- **[修改]** 所有 Controller - 捕获异常并用 `AlertUtil` 显示

---

### 阶段二：安全性增强（最高优先级）

#### 2.1 密码加密存储

**目标**：使用安全的哈希算法存储密码。

##### 添加依赖（pom.xml）
```xml
<dependency>
    <groupId>org.mindrot</groupId>
    <artifactId>jbcrypt</artifactId>
    <version>0.4</version>
</dependency>
```

##### 新建工具类
```java
// src/main/java/museum/utils/PasswordUtil.java
public class PasswordUtil {
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }
    
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
```

##### 修改文件
- **[新增]** `PasswordUtil.java` - 密码加密工具类
- **[修改]** `UserService.java` - 注册时加密，登录时验证
- **[修改]** [UserDAO.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/dao/UserDAO.java) - 登录查询改为只查用户名，密码验证移到 Service

> [!CAUTION]
> **数据库迁移注意**：现有明文密码需要迁移。建议在首次登录时要求用户重置密码。

---

### 阶段三：代码质量改进（中优先级）

#### 3.1 消除代码重复

**目标**：提取通用逻辑，减少重复代码。

##### 示例：通用列表单元格工厂
```java
// src/main/java/museum/utils/UIHelper.java
public class UIHelper {
    public static <T> void setupCustomListView(
        ListView<T> listView,
        Function<T, String> nameExtractor,
        Function<T, String> detailExtractor,
        String nameColor
    ) {
        listView.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                // 通用逻辑...
            }
        });
    }
}
```

##### 修改文件
- **[新增]** `UIHelper.java` - UI 通用工具类
- **[修改]** [MainDashboardController.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/controller/MainDashboardController.java) - 使用通用方法替代重复代码

---

#### 3.2 常量提取

**目标**：将硬编码的常量提取到配置类。

##### 新建常量类
```java
// src/main/java/museum/constants/AppConstants.java
public class AppConstants {
    // 窗口尺寸
    public static final int LOGIN_WIDTH = 800;
    public static final int LOGIN_HEIGHT = 600;
    public static final int DASHBOARD_WIDTH = 1024;
    public static final int DASHBOARD_HEIGHT = 768;
    
    // 颜色
    public static final String IMPERIAL_RED = "#8B0000";
    public static final String GOLD = "#FFD700";
    
    // 路径
    public static final String FXML_LOGIN = "/com/Login.fxml";
    public static final String CSS_MAIN = "/css/styles.css";
}
```

##### 修改文件
- **[新增]** `AppConstants.java` - 应用常量类
- **[修改]** [MainApp.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/MainApp.java) 及所有 Controllers - 使用常量替代硬编码

---

### 阶段四：用户体验提升（低优先级）

#### 4.1 错误提示优化

**目标**：所有错误都要向用户展示友好提示。

##### 修改文件
- **[修改]** 所有 Controller - 在 catch 块中使用 `AlertUtil.showError()` 替代 `e.printStackTrace()`
- **[修改]** [AlertUtil.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/utils/AlertUtil.java) - 增加更多提示类型（Success, Info, Warning, Error）

---

#### 4.2 加载状态提示

**目标**：数据加载时显示进度指示器。

##### 实现示例
```java
// 在 Controller 中加载数据前显示加载提示
ProgressIndicator indicator = new ProgressIndicator();
// 异步加载数据...
Task<ObservableList<Heritage>> loadTask = new Task<>() {
    @Override
    protected ObservableList<Heritage> call() {
        return FXCollections.observableArrayList(heritageService.getAll());
    }
};
```

##### 修改文件
- **[修改]** [MainDashboardController.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/controller/MainDashboardController.java) - 添加加载指示器
- **[修改]** [AdminDashboardController.java](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/java/museum/controller/AdminDashboardController.java) - 添加加载指示器

---

#### 4.3 UI 动画增强

**目标**：添加平滑过渡动画，提升视觉体验。

##### CSS 增强
```css
/* 按钮悬停动画 */
.button {
    -fx-transition: all 0.3s ease;
}

.button:hover {
    -fx-scale-x: 1.05;
    -fx-scale-y: 1.05;
}

/* 列表项淡入效果 */
.list-cell {
    -fx-opacity: 0;
    -fx-transition: opacity 0.3s ease-in;
}

.list-cell:filled {
    -fx-opacity: 1;
}
```

##### 修改文件
- **[修改]** [styles.css](file:///e:/github_repos/JavaPractice/MuseumDisplay/src/main/resources/css/styles.css) - 添加 CSS 过渡动画

---

### 阶段五：图片资源管理（功能增强）

#### 5.1 自动化图片管理

**目标**：简化管理员上传图片流程，并实现图片的统一存储与便携性。

##### 工作原理
1.  管理员点击"选择图片"按钮。
2.  系统接收文件，自动重命名（UUID防止重名）并复制到项目下的 `data/images/` 目录。
3.  数据库仅存储相对路径（如 `data/images/abc-123.jpg`）。

##### 新建工具类
```java
// src/main/java/museum/utils/ImageManager.java
public class ImageManager {
    private static final String STORAGE_DIR = "data/images/";

    public static String saveImage(File sourceFile) throws IOException {
        // 1. 确保存储目录存在
        File dir = new File(STORAGE_DIR);
        if (!dir.exists()) dir.mkdirs();

        // 2. 生成唯一文件名 (防止覆盖)
        String ext = getFileExtension(sourceFile.getName());
        String newName = UUID.randomUUID().toString() + "." + ext;
        File destFile = new File(dir, newName);

        // 3. 复制文件
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // 4. 返回相对路径
        return STORAGE_DIR + newName;
    }
}
```

##### UI 改造
- **[修改]** `AdminDashboardController.java`
    - 将"图片路径"文本框设为只读。
    -在其旁新增一个"上传..."按钮。
    - 按钮点击事件：
      ```java
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
      File selectedFile = fileChooser.showOpenDialog(stage);
      if (selectedFile != null) {
          String savedPath = ImageManager.saveImage(selectedFile);
          imagePathField.setText(savedPath); // 显示相对路径
      }
      ```

---

## 四、实施计划

### 推荐顺序

| 阶段 | 内容 | 预计工作量 | 风险等级 |
|------|------|------------|----------|
| **Phase 1** | 密码加密 + 异常处理 | 2-3小时 | 🔴 高（需数据迁移）|
| **Phase 2** | 引入服务层 | 3-4小时 | 🟡 中（架构调整）|
| **Phase 3** | 代码重复消除 + 常量提取 | 2小时 | 🟢 低 |
| **Phase 4** | UX 优化（错误提示/加载/动画）| 2-3小时 | 🟢 低 |
| **Phase 5** | 图片自动化管理 | 1-2小时 | 🟢 低 |

### 建议步骤

1. **先从安全性入手**：实现密码加密（Phase 1）
2. **再优化架构**：引入服务层（Phase 2）
3. **然后清理代码**：消除重复和硬编码（Phase 3）
4. **最后打磨体验**：UI/UX 优化（Phase 4）

---

## 五、验证计划

### 每个阶段完成后需要验证

1. ✅ **功能测试**：确保所有原有功能正常工作
2. ✅ **边界测试**：测试异常情况（空输入、重复注册、数据库断开等）
3. ✅ **性能测试**：确保重构后性能没有下降
4. ✅ **代码审查**：检查代码规范和注释

---

## 六、风险提示

> [!WARNING]
> **密码迁移风险**：重构后现有用户将无法登录（明文密码变为加密）。
> 
> **解决方案**：
> - 方案A：要求所有用户重置密码
> - 方案B：保留一个临时迁移接口，首次登录时自动转换密码

> [!IMPORTANT]
> **测试环境**：建议在测试数据库上先完成重构，验证无误后再迁移到生产环境。

---

## 七、后续优化方向

重构完成后，可以考虑以下增强功能：

- 📊 **日志系统**：引入 Log4j/SLF4J 记录系统行为
- 🔍 **搜索优化**：支持模糊搜索、高级筛选
- 📤 **数据导入导出**：支持 Excel/PDF 导出
- 🌐 **国际化**：支持多语言切换
- 🎨 **主题切换**：支持暗色模式

---

**准备好开始重构了吗？建议从最高优先级的「密码加密」开始！**
