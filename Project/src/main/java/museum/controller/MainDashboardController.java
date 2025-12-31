package museum.controller;

import museum.MainApp;
import museum.dao.ArchitectureDAO;
import museum.dao.HeritageDAO;
import museum.model.Architecture;
import museum.model.Heritage;
import museum.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainDashboardController {

    @FXML private Label welcomeLabel;
    @FXML private ListView<Heritage> heritageListView;
    @FXML private ListView<Architecture> architectureListView;
    @FXML private TextField searchField;
    @FXML private VBox heritageSection;
    @FXML private VBox architectureSection;

    private HeritageDAO heritageDAO;
    private ArchitectureDAO architectureDAO;

    public MainDashboardController() {
        this.heritageDAO = new HeritageDAO();
        this.architectureDAO = new ArchitectureDAO();
    }

    @FXML
    public void initialize() {
        welcomeLabel.setText("你好, " + SessionManager.getInstance().getCurrentUser().getUsername());
        
        setupHeritageList();
        setupArchitectureList();
        
        refreshData();
    }

    private void setupHeritageList() {
        heritageListView.setCellFactory(lv -> new ListCell<Heritage>() {
            @Override
            protected void updateItem(Heritage item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Simple custom cell layout
                    VBox vbox = new VBox(5);
                    Label nameLabel = new Label(item.getName());
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #8B0000;");
                    Label detailLabel = new Label(item.getCategory() + " | " + item.getRegion());
                    vbox.getChildren().addAll(nameLabel, detailLabel);
                    setGraphic(vbox);
                }
            }
        });

        heritageListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Heritage selected = heritageListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    showHeritageDetail(selected);
                }
            }
        });
    }

    private void setupArchitectureList() {
        architectureListView.setCellFactory(lv -> new ListCell<Architecture>() {
            @Override
            protected void updateItem(Architecture item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Simple custom cell layout
                    VBox vbox = new VBox(5);
                    Label nameLabel = new Label(item.getName());
                    nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2F4F4F;");
                    Label detailLabel = new Label(item.getDynasty() + " | " + item.getLocation());
                    vbox.getChildren().addAll(nameLabel, detailLabel);
                    setGraphic(vbox);
                }
            }
        });

        architectureListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Architecture selected = architectureListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    showArchitectureDetail(selected);
                }
            }
        });
    }

    private void refreshData() {
        heritageListView.setItems(FXCollections.observableArrayList(heritageDAO.readAll()));
        architectureListView.setItems(FXCollections.observableArrayList(architectureDAO.readAll()));
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword == null || keyword.isEmpty()) {
            refreshData();
        } else {
            heritageListView.setItems(FXCollections.observableArrayList(heritageDAO.search(keyword)));
            architectureListView.setItems(FXCollections.observableArrayList(architectureDAO.search(keyword)));
        }
    }

    @FXML
    private void showHeritageOnly() {
        heritageSection.setVisible(true);
        heritageSection.setManaged(true);
        architectureSection.setVisible(false);
        architectureSection.setManaged(false);
    }

    @FXML
    private void showArchitectureOnly() {
        heritageSection.setVisible(false);
        heritageSection.setManaged(false);
        architectureSection.setVisible(true);
        architectureSection.setManaged(true);
    }

    @FXML
    private void showAll() {
        heritageSection.setVisible(true);
        heritageSection.setManaged(true);
        architectureSection.setVisible(true);
        architectureSection.setManaged(true);
    }

    @FXML
    private void handleLogout() {
        SessionManager.getInstance().logout();
        MainApp.showLoginScreen();
    }

    private void showHeritageDetail(Heritage heritage) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/HeritageDetail.fxml"));
            Parent root = loader.load();
            
            HeritageDetailController controller = loader.getController();
            controller.setHeritage(heritage);
            
            Stage stage = new Stage();
            stage.setTitle(heritage.getName());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showArchitectureDetail(Architecture architecture) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/ArchitectureDetail.fxml"));
            Parent root = loader.load();
            
            ArchitectureDetailController controller = loader.getController();
            controller.setArchitecture(architecture);
            
            Stage stage = new Stage();
            stage.setTitle(architecture.getName());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
