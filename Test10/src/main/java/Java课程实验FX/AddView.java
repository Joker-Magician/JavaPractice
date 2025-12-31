package Java课程实验FX;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddView extends Application {
    public void start(Stage stage) throws Exception {
        GridPane gp = new GridPane();
        Label l1 = new Label("飞船名称");
        Label l2 = new Label("发射时间");
        Label l3 = new Label("发射地点");
        Label l4 = new Label("任务概述");
        Button b1 = new Button("增加");
        Button b2 = new Button("清空");
        TextField v1 = new TextField();
        TextField v2 = new TextField();
        TextField v3 = new TextField();
        TextField v4 = new TextField();
        HBox h1 = new HBox(20);
        SpacecraftDao dao = new SpacecraftDaoImpl();
        gp.setHgap(10);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);
        stage.setTitle("飞船增加");
        gp.add(l1, 0, 0);
        gp.add(v1, 1, 0);
        gp.add(l2, 0, 1);
        gp.add(v2, 1, 1);
        gp.add(l3, 0, 2);
        gp.add(v3, 1, 2);
        gp.add(l4, 0, 3);
        gp.add(v4, 1, 3);
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(b1, b2);
        gp.add(h1, 0, 4, 2, 1);
        b1.setOnAction(e -> {
            try {
                String name = v1.getText().trim();
                String text = v2.getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.
                        ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime time = LocalDateTime.parse(text, formatter);
                Timestamp launchTime = java.sql.Timestamp.valueOf(time);
                String launchSite = v3.getText().trim();
                String summary = v4.getText().trim();
                Spacecraft p = new Spacecraft(name, launchTime, launchSite, summary);
                if (dao.addSpacecraft(p)) {
                    new Test().start(new Stage());
                    stage.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        b2.setOnAction(e -> {
            v1.setText("");
            v2.setText("");
            v3.setText("");
            v4.setText("");
        });
        stage.setScene(new Scene(gp, 350, 300));
        stage.show();
    }

    public static class UpdateView extends Application {
        public Spacecraft spacecraft = null;

        public UpdateView(Spacecraft p) {
            spacecraft = p;
        }

        public UpdateView() {
        }

        @Override
        public void start(Stage stage) throws Exception {
            SpacecraftDao dao = new SpacecraftDaoImpl();
            GridPane gp = new GridPane();
            Label l1 = new Label("飞船名称");
            Label l2 = new Label("发射时间");
            Label l3 = new Label("发射地点");
            Label l4 = new Label("任务概述");
            Button b1 = new Button("修改");
            Button b2 = new Button("清空");
            TextField v1 = new TextField(spacecraft.getName());
            TextField v2 = new TextField(spacecraft.getLaunchTime() + "");
            TextField v3 = new TextField(spacecraft.getLaunchSite());
            TextField v4 = new TextField(spacecraft.getSummary());
            HBox h1 = new HBox(20);
            gp.setHgap(10);
            h1.getChildren().addAll(b1, b2);
            gp.setVgap(10);
            gp.setAlignment(Pos.CENTER);
            stage.setTitle("飞船修改");
            gp.add(l1, 0, 0);
            gp.add(v1, 1, 0);
            gp.add(l2, 0, 1);
            gp.add(v2, 1, 1);
            gp.add(l3, 0, 2);
            gp.add(v3, 1, 2);
            gp.add(l4, 0, 3);
            gp.add(v4, 1, 3);
            h1.setAlignment(Pos.CENTER);
            gp.add(h1, 0, 4, 2, 1);
            b1.setOnAction(e -> {
                try {
                    String name = v1.getText().trim();
                    String text = v2.getText().trim();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime time = LocalDateTime.parse(text, formatter);
                    Timestamp launchTime = java.sql.Timestamp.valueOf(time);
                    String launchSite = v3.getText().trim();
                    String summary = v4.getText().trim();
                    spacecraft.setLaunchTime(launchTime);
                    spacecraft.setLaunchSite(launchSite);
                    spacecraft.setName(name);
                    spacecraft.setSummary(summary);
                    if (dao.updateSpacecraft(spacecraft)) {
                        new Test().start(new Stage());
                        stage.close();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            b2.setOnAction(e -> {
                v1.setText("");
                v2.setText("");
                v3.setText("");
                v4.setText("");
            });
            stage.setScene(new Scene(gp, 350, 300));
            stage.show();
        }
    }
}