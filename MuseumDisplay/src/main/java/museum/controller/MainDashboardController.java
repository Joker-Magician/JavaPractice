package museum.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import museum.MainApp;
import museum.constants.AppConstants;
import museum.entity.Architecture;
import museum.entity.Heritage;
import museum.exception.DatabaseException;  
import museum.service.ArchitectureService;  
import museum.service.HeritageService;  
import museum.utils.AlertUtil;  
import museum.utils.SessionManager;
import museum.utils.UIHelper;

public class MainDashboardController {

    @FXML
    private ListView<Architecture> architectureListView;

    @FXML
    private VBox architectureSection;

    @FXML
    private ListView<Heritage> heritageListView;

    @FXML
    private VBox heritageSection;

    @FXML
    private TextField searchField;

    @FXML
    private Label welcomeLabel;

    private HeritageService heritageService;  // [MODIFIED] 使用 HeritageService
    private ArchitectureService architectureService;  // [MODIFIED] 使用 ArchitectureService

    public MainDashboardController() {
        this.heritageService = new HeritageService();  // [MODIFIED] 初始化 Service
        this.architectureService = new ArchitectureService();  // [MODIFIED] 初始化 Service
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("你好, " + SessionManager.getInstance().getCurrentUser().getUserName());

        setupHeritageList();
        setupArchitectureList();

        refreshData();
    }

    // [MODIFIED] 使用 UIHelper 简化列表设置，消除重复代码
    private void setupHeritageList() {
        UIHelper.setupCustomListView(
                heritageListView,
                Heritage::getName,  // 名称提取器
                h -> h.getCategory() + " | " + h.getRegion(),  // 详情提取器
                AppConstants.STYLE_NAME_LABEL_HERITAGE,  // 样式
                this::showHeritageDetail  // 双击回调
        );
    }

    // [MODIFIED] 使用 UIHelper 简化列表设置，消除重复代码
    private void setupArchitectureList() {
        UIHelper.setupCustomListView(
                architectureListView,
                Architecture::getName,  // 名称提取器
                a -> a.getDynasty() + " | " + a.getLocation(),  // 详情提取器
                AppConstants.STYLE_NAME_LABEL_ARCHITECTURE,  // 样式
                this::showArchitectureDetail  // 双击回调
        );
    }

    // [MODIFIED] 使用 Service 层获取数据，添加异常处理
    private void refreshData() {
        try {
            heritageListView.setItems(FXCollections.observableArrayList(
                heritageService.getAllHeritage()));
            architectureListView.setItems(FXCollections.observableArrayList(
                architectureService.getAllArchitecture()));
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "加载数据失败: " + e.getMessage());
        }
    }

    @FXML
    void handleLogout(ActionEvent event) {
        SessionManager.getInstance().logout();
        MainApp.showLoginScreen();
    }

    // [MODIFIED] 使用 Service 层搜索，添加异常处理
    @FXML
    void handleSearch(ActionEvent event) {
        String keyword = searchField.getText();
        try {
            heritageListView.setItems(FXCollections.observableArrayList(
                heritageService.searchHeritage(keyword)));
            architectureListView.setItems(FXCollections.observableArrayList(
                architectureService.searchArchitecture(keyword)));
        } catch (DatabaseException e) {
            AlertUtil.showError("错误", "搜索失败: " + e.getMessage());
        }
    }

    @FXML
    void showAll(ActionEvent event) {
        heritageSection.setVisible(true);
        heritageSection.setManaged(true);
        architectureSection.setVisible(true);
        architectureSection.setManaged(true);
    }

    @FXML
    void showArchitectureOnly(ActionEvent event) {
        heritageSection.setVisible(false);
        heritageSection.setManaged(false);
        architectureSection.setVisible(true);
        architectureSection.setManaged(true);
    }

    @FXML
    void showHeritageOnly(ActionEvent event) {
        heritageSection.setVisible(true);
        heritageSection.setManaged(true);
        architectureSection.setVisible(false);
        architectureSection.setManaged(false);
    }

    // [MODIFIED] 使用 UIHelper 简化详情窗口显示
    private void showHeritageDetail(Heritage heritage) {
        UIHelper.showDetailWindow(
                AppConstants.FXML_HERITAGE_DETAIL,
                heritage.getName(),
                heritage,
                HeritageDetailController::setHeritage
        );
    }

    // [MODIFIED] 使用 UIHelper 简化详情窗口显示
    private void showArchitectureDetail(Architecture architecture) {
        UIHelper.showDetailWindow(
                AppConstants.FXML_ARCHITECTURE_DETAIL,
                architecture.getName(),
                architecture,
                ArchitectureDetailController::setArchitecture
        );
    }
}