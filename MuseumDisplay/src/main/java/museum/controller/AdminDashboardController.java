package museum.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import museum.MainApp;
import museum.entity.Architecture;
import museum.entity.Heritage;
import museum.exception.DatabaseException;  
import museum.exception.ValidationException;  
import museum.service.ArchitectureService;  
import museum.service.HeritageService;  
import museum.utils.AlertUtil;
import museum.utils.SessionManager;

import java.util.Optional;
import javafx.stage.FileChooser; 
import java.io.File; 
import museum.utils.ImageManager; 
import java.io.IOException; 
import javafx.scene.layout.HBox; 

public class AdminDashboardController {

    //非遗
    @FXML private TableView<Heritage> heritageTable;
    @FXML private TableColumn<Heritage, Number> hIdCol;
    @FXML private TableColumn<Heritage, String> hNameCol;
    @FXML private TableColumn<Heritage, String> hCategoryCol;
    @FXML private TableColumn<Heritage, String> hRegionCol;
    @FXML private TableColumn<Heritage, Number> hYearCol;
    @FXML private TextField hSearchField;

    //古建筑
    @FXML private TableView<Architecture> architectureTable;
    @FXML private TableColumn<Architecture, Number> aIdCol;
    @FXML private TableColumn<Architecture, String> aNameCol;
    @FXML private TableColumn<Architecture, String> aDynastyCol;
    @FXML private TableColumn<Architecture, String> aTypeCol;
    @FXML private TableColumn<Architecture, Number> aYearCol;
    @FXML private TextField aSearchField;

    @FXML private Label welcomeLabel;

    private HeritageService heritageService;  // [MODIFIED] 使用 HeritageService
    private ArchitectureService architectureService;  // [MODIFIED] 使用 ArchitectureService
    private ObservableList<Heritage> heritageList;
    private ObservableList<Architecture> architectureList;

    public AdminDashboardController() {
        this.heritageService = new HeritageService();  // [MODIFIED] 初始化 Service
        this.architectureService = new ArchitectureService();  // [MODIFIED] 初始化 Service
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("欢迎管理员: " + SessionManager.getInstance().getCurrentUser().getUserName());

        setupHeritageTable();
        setupArchitectureTable();

        loadHeritageData();
        loadArchitectureData();
    }

    public void setupHeritageTable() {
        hIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHeritageId()));
        hNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        hCategoryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        hRegionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRegion()));
        hYearCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearRecognized()));
        
        // [ADDED] 添加双击事件监听器，双击表格行进入编辑模式
        heritageTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Heritage selected = heritageTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    handleEditHeritage(selected);
                }
            }
        });
    }
    
    public void setupArchitectureTable() {
        aIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getArchitectureID()));
        aNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        aDynastyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDynasty()));
        aTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        aYearCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearBuilt()));
        
        // [ADDED] 添加双击事件监听器，双击表格行进入编辑模式
        architectureTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Architecture selected = architectureTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    handleEditArchitecture(selected);
                }
            }
        });
    }

    // [MODIFIED] 使用 Service 层加载数据，添加异常处理
    private void loadHeritageData() {
        try {
            heritageList = FXCollections.observableArrayList(heritageService.getAllHeritage());
            heritageTable.setItems(heritageList);
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "加载非遗数据失败: " + e.getMessage());
        }
    }

    // [MODIFIED] 使用 Service 层加载数据，添加异常处理
    private void loadArchitectureData() {
        try {
            architectureList = FXCollections.observableArrayList(architectureService.getAllArchitecture());
            architectureTable.setItems(architectureList);
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "加载古建筑数据失败: " + e.getMessage());
        }
    }
    
    // [MODIFIED] 使用 Service 层删除，添加异常处理
    @FXML
    void handleDeleteHeritage(ActionEvent event) {
        Heritage selected = heritageTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(AlertUtil.showConfirmation("确认", "确定要删除 " + selected.getName() + " 吗?")) {
                try {
                    heritageService.deleteHeritage(selected.getHeritageId());
                    loadHeritageData();
                } catch (ValidationException | DatabaseException e) {
                    AlertUtil.showError("错误", "删除失败: " + e.getMessage());
                }
            }
        }else{
            AlertUtil.showWarning("提示", "请先选择一项");
        }
    }

    // [MODIFIED] 使用 Service 层删除，添加异常处理
    @FXML
    void handleDeleteArchitecture(ActionEvent event) {
        Architecture selected = architectureTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (AlertUtil.showConfirmation("确认", "确定要删除 " + selected.getName() + " 吗?")) {
                try {
                    architectureService.deleteArchitecture(selected.getArchitectureID());
                    loadArchitectureData();
                } catch (ValidationException | DatabaseException e) {
                    AlertUtil.showError("错误", "删除失败: " + e.getMessage());
                }
            }
        } else {
            AlertUtil.showWarning("提示", "请先选择一项");
        }
    }

    @FXML
    void handleLogout() {
        SessionManager.getInstance().logout();
        MainApp.showLoginScreen();
    }

    // [MODIFIED] 使用 Service 层搜索，添加异常处理
    @FXML
    void handleHeritageSearch() {
        String keyword = hSearchField.getText();
        try {
            heritageList = FXCollections.observableArrayList(
                heritageService.searchHeritage(keyword));
            heritageTable.setItems(heritageList);
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "搜索失败: " + e.getMessage());
        }
    }

    // [MODIFIED] 使用 Service 层搜索，添加异常处理
    @FXML
    void handleArchitectureSearch() {
        String keyword = aSearchField.getText();
        try {
            architectureList = FXCollections.observableArrayList(
                architectureService.searchArchitecture(keyword));
            architectureTable.setItems(architectureList);
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "搜索失败: " + e.getMessage());
        }
    }

    @FXML
    void handleAddHeritage() {
        Dialog<Heritage> dialog = new Dialog<>();
        dialog.setTitle("添加非遗项目");

        ButtonType loginButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name          = new TextField();          name.setPromptText("名称");
        TextField category      = new TextField();          category.setPromptText("类别");
        TextField region        = new TextField();          region.setPromptText("地区");
        TextArea description    = new TextArea();           description.setPromptText("描述");
        TextField imagePath     = new TextField();          imagePath.setPromptText("图片路径");
        TextField year          = new TextField();          year.setPromptText("入选年份");

        grid.add(new Label("名称"), 0, 0);    grid.add(name, 1, 0);
        grid.add(new Label("类别"), 0, 1);    grid.add(category, 1, 1);
        grid.add(new Label("地区:"), 0, 2);   grid.add(region, 1, 2);
        grid.add(new Label("描述:"), 0, 3);   grid.add(description, 1, 3);
        grid.add(new Label("图片:"), 0, 4);   grid.add(createImageUploadRow(imagePath), 1, 4); // [MODIFIED] 使用上传组件
        grid.add(new Label("年份:"), 0, 5);   grid.add(year, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    Heritage
                    h = new Heritage(name.getText(), category.getText(), region.getText(), description.getText(), imagePath.getText(), Integer.parseInt(year.getText()));
                    h.setCreatedBy(SessionManager.getInstance().getCurrentUser().getUserId());
                    return h;
                }catch (NumberFormatException e){
                    AlertUtil.showError("错误", "年份必须是数字");
                    return null;
                }
            }
            return null;
        });

        Optional<Heritage> result = dialog.showAndWait();
        // [MODIFIED] 使用 Service 层创建，添加异常处理
        result.ifPresent(heritage -> {
            try {
                if(heritageService.createHeritage(heritage)) {
                    loadHeritageData();
                } else {
                    AlertUtil.showError("错误", "添加失败");
                }
            } catch (ValidationException | DatabaseException e) {
                AlertUtil.showError("错误", "添加失败: " + e.getMessage());
            }
        });
    }

    @FXML
    void handleAddArchitecture() {
        Dialog<Architecture> dialog = new Dialog<>();
        dialog.setTitle("添加古建筑");

        ButtonType loginButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField name       = new TextField();             name.setPromptText("名称");
        TextField dynasty    = new TextField();             dynasty.setPromptText("朝代");
        TextField location   = new TextField();             location.setPromptText("位置");
        TextField type       = new TextField();             type.setPromptText("类型");
        TextArea description = new TextArea();              description.setPromptText("描述");
        TextField imagePath  = new TextField();             imagePath.setPromptText("图片路径");
        TextField year       = new TextField();             year.setPromptText("建造年份");

        grid.add(new Label("名称:"), 0, 0);   grid.add(name, 1, 0);
        grid.add(new Label("朝代:"), 0, 1);   grid.add(dynasty, 1, 1);
        grid.add(new Label("位置:"), 0, 2);   grid.add(location, 1, 2);
        grid.add(new Label("类型:"), 0, 3);   grid.add(type, 1, 3);
        grid.add(new Label("描述:"), 0, 4);   grid.add(description, 1, 4);
        grid.add(new Label("图片:"), 0, 5);   grid.add(createImageUploadRow(imagePath), 1, 5); // [MODIFIED] 使用上传组件
        grid.add(new Label("年份:"), 0, 6);   grid.add(year, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    Architecture a = new Architecture(name.getText(), dynasty.getText(), location.getText(), type.getText(), description.getText(), imagePath.getText(), Integer.parseInt(year.getText()));
                    a.setCreatedBy(SessionManager.getInstance().getCurrentUser().getUserId());
                    return a;
                } catch (NumberFormatException e) {
                    AlertUtil.showError("错误", "年份必须是数字");
                    return null;
                }
            }
            return null;
        });

        Optional<Architecture> result = dialog.showAndWait();
        // [MODIFIED] 使用 Service 层创建，添加异常处理
        result.ifPresent(arch -> {
            try {
                if (architectureService.createArchitecture(arch)) {
                    loadArchitectureData();
                } else {
                    AlertUtil.showError("错误", "添加失败");
                }
            } catch (ValidationException | DatabaseException e) {
                AlertUtil.showError("错误", "添加失败: " + e.getMessage());
            }
        });
    }

    // [ADDED] 编辑非遗项目方法
    void handleEditHeritage(Heritage heritage) {
        Dialog<Heritage> dialog = new Dialog<>();
        dialog.setTitle("编辑非遗项目");

        ButtonType saveButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // [ADDED] 预填充现有数据
        TextField name = new TextField(heritage.getName());
        name.setPromptText("名称");
        TextField category = new TextField(heritage.getCategory());
        category.setPromptText("类别");
        TextField region = new TextField(heritage.getRegion());
        region.setPromptText("地区");
        TextArea description = new TextArea(heritage.getDescription());
        description.setPromptText("描述");
        TextField imagePath = new TextField(heritage.getImagePath());
        imagePath.setPromptText("图片路径");
        TextField year = new TextField(String.valueOf(heritage.getYearRecognized()));
        year.setPromptText("入选年份");

        grid.add(new Label("名称:"), 0, 0);    grid.add(name, 1, 0);
        grid.add(new Label("类别:"), 0, 1);    grid.add(category, 1, 1);
        grid.add(new Label("地区:"), 0, 2);    grid.add(region, 1, 2);
        grid.add(new Label("描述:"), 0, 3);    grid.add(description, 1, 3);
        grid.add(new Label("图片:"), 0, 4);    grid.add(createImageUploadRow(imagePath), 1, 4); // [MODIFIED] 使用上传组件
        grid.add(new Label("年份:"), 0, 5);    grid.add(year, 1, 5);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    // 更新现有对象的属性
                    heritage.setName(name.getText());
                    heritage.setCategory(category.getText());
                    heritage.setRegion(region.getText());
                    heritage.setDescription(description.getText());
                    heritage.setImagePath(imagePath.getText());
                    heritage.setYearRecognized(Integer.parseInt(year.getText()));
                    return heritage;
                } catch (NumberFormatException e) {
                    AlertUtil.showError("错误", "年份必须是数字");
                    return null;
                }
            }
            return null;
        });

        Optional<Heritage> result = dialog.showAndWait();
        // [ADDED] 调用 Service 层的 update 方法
        result.ifPresent(h -> {
            try {
                if (heritageService.updateHeritage(h)) {
                    AlertUtil.showInfo("成功", "更新成功！");
                    loadHeritageData();
                } else {
                    AlertUtil.showError("错误", "更新失败");
                }
            } catch (ValidationException | DatabaseException e) {
                AlertUtil.showError("错误", "更新失败: " + e.getMessage());
            }
        });
    }

    // [ADDED] 编辑古建筑项目方法
    void handleEditArchitecture(Architecture architecture) {
        Dialog<Architecture> dialog = new Dialog<>();
        dialog.setTitle("编辑古建筑");

        ButtonType saveButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // [ADDED] 预填充现有数据
        TextField name = new TextField(architecture.getName());
        name.setPromptText("名称");
        TextField dynasty = new TextField(architecture.getDynasty());
        dynasty.setPromptText("朝代");
        TextField location = new TextField(architecture.getLocation());
        location.setPromptText("位置");
        TextField type = new TextField(architecture.getType());
        type.setPromptText("类型");
        TextArea description = new TextArea(architecture.getDescription());
        description.setPromptText("描述");
        TextField imagePath = new TextField(architecture.getImagePath());
        imagePath.setPromptText("图片路径");
        TextField year = new TextField(String.valueOf(architecture.getYearBuilt()));
        year.setPromptText("建造年份");

        grid.add(new Label("名称:"), 0, 0);    grid.add(name, 1, 0);
        grid.add(new Label("朝代:"), 0, 1);    grid.add(dynasty, 1, 1);
        grid.add(new Label("位置:"), 0, 2);    grid.add(location, 1, 2);
        grid.add(new Label("类型:"), 0, 3);    grid.add(type, 1, 3);
        grid.add(new Label("描述:"), 0, 4);    grid.add(description, 1, 4);
        grid.add(new Label("图片:"), 0, 5);    grid.add(createImageUploadRow(imagePath), 1, 5); // [MODIFIED] 使用上传组件
        grid.add(new Label("年份:"), 0, 6);    grid.add(year, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    // 更新现有对象的属性
                    architecture.setName(name.getText());
                    architecture.setDynasty(dynasty.getText());
                    architecture.setLocation(location.getText());
                    architecture.setType(type.getText());
                    architecture.setDescription(description.getText());
                    architecture.setImagePath(imagePath.getText());
                    architecture.setYearBuilt(Integer.parseInt(year.getText()));
                    return architecture;
                } catch (NumberFormatException e) {
                    AlertUtil.showError("错误", "年份必须是数字");
                    return null;
                }
            }
            return null;
        });

        Optional<Architecture> result = dialog.showAndWait();
        // [ADDED] 调用 Service 层的 update 方法
        result.ifPresent(arch -> {
            try {
                if (architectureService.updateArchitecture(arch)) {
                    AlertUtil.showInfo("成功", "更新成功！");
                    loadArchitectureData();
                } else {
                    AlertUtil.showError("错误", "更新失败");
                }
            } catch (ValidationException | DatabaseException e) {
                AlertUtil.showError("错误", "更新失败: " + e.getMessage());
            }
        });
    }

    // [ADDED] 创建图片上传行组件
    private HBox createImageUploadRow(TextField imagePathField) {
        imagePathField.setEditable(false); // 设为只读，强制使用上传
        
        Button uploadBtn = new Button("上传...");
        uploadBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择图片");
            fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("图片文件", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            
            // 获取当前窗口作为父窗口
            javafx.stage.Window window = uploadBtn.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(window);
            
            if (selectedFile != null) {
                try {
                    String savedPath = ImageManager.saveImage(selectedFile);
                    imagePathField.setText(savedPath);
                } catch (IOException ex) {
                    AlertUtil.showError("错误", "图片保存失败: " + ex.getMessage());
                }
            }
        });
        
        HBox box = new HBox(10);
        box.getChildren().addAll(imagePathField, uploadBtn);
        return box;
    }
}
