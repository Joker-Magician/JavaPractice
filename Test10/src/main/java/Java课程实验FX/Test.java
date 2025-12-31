package Java课程实验FX;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Test extends Application {
    SpacecraftDao dao = new SpacecraftDaoImpl();
    TableView<Spacecraft> tableView = new TableView<Spacecraft>();
    @SuppressWarnings("unchecked")
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
        // 顶部
        Label lb_id = new Label("飞船名称");
        TextField t_id = new TextField();
        Button btn_search = new Button("查询");
        HBox hb_top = new HBox(10);
        hb_top.getChildren().addAll(lb_id, t_id, btn_search);
        root.setTop(hb_top);
        // 底部
        Button btn_insert = new Button("添加"), btn_delete = new Button("删除"),
                btn_update = new Button("修改"),	btn_exit = new Button("退出");
        HBox hb_bottom = new HBox(10);
        hb_bottom.getChildren().addAll(btn_insert, btn_delete, btn_update, btn_exit);
        root.setBottom(hb_bottom);
        List<Spacecraft> list = dao.getAll();
        ObservableList<Spacecraft> obList = FXCollections.observableArrayList(list);
        tableView.setItems(obList);
        root.setCenter(tableView);
        TableColumn<Spacecraft, Integer> tc_id = new TableColumn<Spacecraft, 					Integer>("id");
        TableColumn<Spacecraft, String> tc_name = new TableColumn<Spacecraft, 					String>("飞船名称");
        TableColumn<Spacecraft, String> tc_launchTime = new TableColumn<Spacecraft, 				String>("发射时间");
        TableColumn<Spacecraft, String> tc_launchSite = new TableColumn<Spacecraft, 				String>("发射地点");
        TableColumn<Spacecraft, String> tc_summary = new TableColumn<Spacecraft, 				String>("任务概述");
        tableView.getColumns().addAll(tc_id, tc_name, tc_launchTime, tc_launchSite, 					tc_summary);
        tc_id.setMinWidth(50);
        tc_name.setMinWidth(100);
        tc_launchTime.setMinWidth(150);
        tc_launchSite.setMinWidth(150);
        tc_summary.setMinWidth(350);
        // 给按钮添加事件
        btn_search.setOnAction(e -> {
            // 从文本框中取值，将文本框中多余的空格
            String f_id = t_id.getText().trim();
            try {
                refreshTable(f_id);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        // 添加
        btn_insert.setOnAction(e -> {
            try {
                new AddView().start(new Stage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        // 修改
        btn_update.setOnAction(e -> {
            try {
                Spacecraft p = tableView.getSelectionModel().getSelectedItem();
                if (p != null) {
                    new AddView.UpdateView(p).start(new Stage());
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "请选择要修改的数据！！");
                    alert.show();
                    return;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // 删除
        btn_delete.setOnAction(e -> {
            // 获得表格中选中行的信息
            try {
                Spacecraft p = tableView.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要删除吗？");
                Optional<ButtonType> btn = alert.showAndWait();
                if (btn.get() == ButtonType.OK) {
                    if (dao.deleteSpacecraft(p.getId())) {
                        refreshTable("");
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        });
        // 退出
        btn_exit.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "确定要退出吗？");
            Optional<ButtonType> btn = alert.showAndWait();
            if (btn.get() == ButtonType.OK) {
                Platform.exit();
            }
        });
        tc_id.setCellValueFactory(new PropertyValueFactory<Spacecraft, Integer>("id"));
        tc_name.setCellValueFactory(new PropertyValueFactory<Spacecraft,String>("name"));
        tc_launchTime.setCellValueFactory(new PropertyValueFactory<Spacecraft,String>("launchTime"));
        tc_launchSite.setCellValueFactory(new PropertyValueFactory<Spacecraft,String>("launchSite"));
        tc_summary.setCellValueFactory(new PropertyValueFactory<Spacecraft,String>("summary"));
        Scene scene = new Scene(root, 850, 500);
        stage.setScene(scene);
        stage.setTitle("神舟飞船管理系统");
        stage.show();
    }
    // 更新表格
    public void refreshTable(String param) throws Exception {
        List<Spacecraft> list = new ArrayList<Spacecraft>();
        if (param != null && param.length() > 0) {
            list = dao.getSpacecraftByName(param);
        } else {
            list = dao.getAll();
        }
        TableView<Spacecraft> tv = new TableView<Spacecraft>();
        ObservableList<Spacecraft> oblist = FXCollections.observableArrayList(list);
        tableView.setItems(oblist);
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}

