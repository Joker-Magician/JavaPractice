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
import museum.dao.ArchitectureDAO;
import museum.dao.HeritageDAO;
import museum.entity.Architecture;
import museum.entity.Heritage;
import museum.utils.AlertUtil;
import museum.utils.SessionManager;

import java.util.Optional;

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

    private HeritageDAO heritageDAO;
    private ArchitectureDAO architectureDAO;
    private ObservableList<Heritage> heritageList;
    private ObservableList<Architecture> architectureList;

    public AdminDashboardController() {
        this.heritageDAO = new HeritageDAO();
        this.architectureDAO = new ArchitectureDAO();
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
    }
    
    public void setupArchitectureTable() {
        aIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getArchitectureID()));
        aNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        aDynastyCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDynasty()));
        aTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        aYearCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getYearBuilt()));
    }

    private void loadHeritageData() {
        heritageList = FXCollections.observableArrayList(heritageDAO.getAll());
        heritageTable.setItems(heritageList);
    }

    private void loadArchitectureData() {
        architectureList = FXCollections.observableArrayList(architectureDAO.getAll());
        architectureTable.setItems(architectureList);
    }
    
    @FXML
    void handleDeleteHeritage(ActionEvent event) {
        Heritage selected = heritageTable.getSelectionModel().getSelectedItem();
        if(selected != null) {
            if(AlertUtil.showConfirmation("确认", "确定要删除 " + selected.getName() + " 吗?")) {
                heritageDAO.delete(selected.getHeritageId());
                loadHeritageData();
            }
        }else{
            AlertUtil.showWarning("提示", "请先选择一项");
        }
    }

    @FXML
    void handleDeleteArchitecture(ActionEvent event) {
        Architecture selected = architectureTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (AlertUtil.showConfirmation("确认", "确定要删除 " + selected.getName() + " 吗?")) {
                architectureDAO.delete(selected.getArchitectureID());
                loadArchitectureData();
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

    @FXML
    void handleHeritageSearch() {
        String keyword = hSearchField.getText();
        if (keyword == null || keyword.isEmpty()) {
            loadHeritageData();
        } else  {
            heritageList = FXCollections.observableArrayList(heritageDAO.searchByName(keyword));
            heritageTable.setItems(heritageList);
        }
    }

    @FXML
    void handleArchitectureSearch() {
        String keyword = aSearchField.getText();
        if (keyword == null || keyword.isEmpty()) {
            loadHeritageData();
        } else {
            architectureList = FXCollections.observableArrayList(architectureDAO.searchByName(keyword));
            architectureTable.setItems(architectureList);
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
        grid.add(new Label("图片:"), 0, 4);   grid.add(imagePath, 1, 4);
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
        result.ifPresent(heritage -> {
            if(heritageDAO.create(heritage)) {
                loadHeritageData();
            }else{
                AlertUtil.showError("错误", "添加失败");
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
        grid.add(new Label("图片:"), 0, 5);   grid.add(imagePath, 1, 5);
        grid.add(new Label("年份:"), 0, 6);   grid.add(year, 1, 6);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                try {
                    Architecture a = new Architecture(
                            name.getText(),
                            dynasty.getText(),
                            location.getText(),
                            type.getText(),
                            description.getText(),
                            imagePath.getText(),
                            Integer.parseInt(year.getText())
                    );
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
        result.ifPresent(arch -> {
            if (architectureDAO.create(arch)) {
                loadArchitectureData();
            } else {
                AlertUtil.showError("错误", "添加失败");
            }
        });
    }
}

