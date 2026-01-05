package museum.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import museum.MainApp;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * UI 通用工具类
 * 提供通用的 ListView 配置和窗口管理方法，减少重复代码
 */
public class UIHelper {

    /**
     * 设置自定义 ListView，包含双列布局（名称 + 详情）和双击事件
     *
     * @param <T>              列表项类型
     * @param listView         要配置的 ListView
     * @param nameExtractor    从对象提取名称的函数
     * @param detailExtractor  从对象提取详情的函数
     * @param nameStyle        名称标签的样式字符串
     * @param onDoubleClick    双击时的回调函数
     */
    public static <T> void setupCustomListView(
            ListView<T> listView,
            Function<T, String> nameExtractor,
            Function<T, String> detailExtractor,
            String nameStyle,
            Consumer<T> onDoubleClick
    ) {
        // 设置自定义单元格工厂
        listView.setCellFactory(lv -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // 创建自定义单元格布局
                    VBox vbox = new VBox(5);
                    Label nameLabel = new Label(nameExtractor.apply(item));
                    nameLabel.setStyle(nameStyle);
                    Label detailLabel = new Label(detailExtractor.apply(item));
                    vbox.getChildren().addAll(nameLabel, detailLabel);
                    setGraphic(vbox);
                }
            }
        });

        // 设置双击事件监听器
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                T selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null && onDoubleClick != null) {
                    onDoubleClick.accept(selected);
                }
            }
        });
    }

    /**
     * 显示详情窗口
     *
     * @param <T>               实体类型
     * @param <C>               Controller 类型
     * @param fxmlPath          FXML 文件路径
     * @param title             窗口标题
     * @param entity            要显示的实体对象
     * @param controllerSetter  设置 controller 数据的回调函数
     */
    public static <T, C> void showDetailWindow(
            String fxmlPath,
            String title,
            T entity,
            ControllerSetter<C, T> controllerSetter
    ) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Parent root = loader.load();

            C controller = loader.getController();
            controllerSetter.setData(controller, entity);

            Stage stage = new Stage();
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showError("错误", "无法打开详情窗口: " + e.getMessage());
        }
    }

    /**
     * 函数式接口：用于设置 Controller 的数据
     *
     * @param <C> Controller 类型
     * @param <T> 实体类型
     */
    @FunctionalInterface
    public interface ControllerSetter<C, T> {
        void setData(C controller, T entity);
    }

    // 私有构造函数，防止实例化
    private UIHelper() {
        throw new AssertionError("工具类不应该被实例化");
    }
}
