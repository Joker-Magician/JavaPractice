package Java课程实验FX;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SpacecraftController implements Initializable {
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private TableView<Spacecraft> spacecraftTable;
    
    @FXML
    private TableColumn<Spacecraft, Integer> idColumn;
    
    @FXML
    private TableColumn<Spacecraft, String> nameColumn;
    
    @FXML
    private TableColumn<Spacecraft, String> launchTimeColumn;
    
    @FXML
    private TableColumn<Spacecraft, String> launchSiteColumn;
    
    @FXML
    private TableColumn<Spacecraft, String> summaryColumn;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private Button exitButton;
    
    private SpacecraftDao dao = new SpacecraftDaoImpl();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        launchTimeColumn.setCellValueFactory(new PropertyValueFactory<>("launchTime"));
        launchSiteColumn.setCellValueFactory(new PropertyValueFactory<>("launchSite"));
        summaryColumn.setCellValueFactory(new PropertyValueFactory<>("summary"));
        
        // Load initial data
        try {
            refreshTable("");
        } catch (Exception e) {
            e.printStackTrace();
            showError("加载数据失败", "无法从数据库加载飞船数据");
        }
    }
    
    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        try {
            refreshTable(searchText);
        } catch (Exception e) {
            e.printStackTrace();
            showError("查询失败", "查询飞船数据时发生错误");
        }
    }
    
    @FXML
    private void handleAdd() {
        try {
            new AddView().start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("添加失败", "打开添加窗口时发生错误");
        }
    }
    
    @FXML
    private void handleDelete() {
        try {
            Spacecraft selectedSpacecraft = spacecraftTable.getSelectionModel().getSelectedItem();
            
            if (selectedSpacecraft == null) {
                showError("删除失败", "请选择要删除的数据！");
                return;
            }
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要删除吗？");
            Optional<ButtonType> result = alert.showAndWait();
            
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (dao.deleteSpacecraft(selectedSpacecraft.getId())) {
                    refreshTable("");
                    showInfo("删除成功", "飞船数据已成功删除");
                } else {
                    showError("删除失败", "无法删除选中的飞船数据");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            showError("删除失败", "删除飞船数据时发生错误");
        }
    }
    
    @FXML
    private void handleUpdate() {
        try {
            Spacecraft selectedSpacecraft = spacecraftTable.getSelectionModel().getSelectedItem();
            
            if (selectedSpacecraft == null) {
                showError("修改失败", "请选择要修改的数据！");
                return;
            }
            
            new AddView.UpdateView(selectedSpacecraft).start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
            showError("修改失败", "打开修改窗口时发生错误");
        }
    }
    
    @FXML
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要退出吗？");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Platform.exit();
        }
    }
    
    /**
     * Refresh the table with data from database
     * @param searchParam search parameter for filtering by name
     */
    public void refreshTable(String searchParam) throws Exception {
        List<Spacecraft> list = new ArrayList<>();
        
        if (searchParam != null && searchParam.length() > 0) {
            list = dao.getSpacecraftByName(searchParam);
        } else {
            list = dao.getAll();
        }
        
        ObservableList<Spacecraft> obList = FXCollections.observableArrayList(list);
        spacecraftTable.setItems(obList);
    }
    
    /**
     * Show error alert dialog
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Show information alert dialog
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
