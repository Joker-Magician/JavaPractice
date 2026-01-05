package museum.controller;

import museum.entity.Heritage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class HeritageDetailController {

    @FXML private Label nameLabel;
    @FXML private Label categoryLabel;
    @FXML private Label regionLabel;
    @FXML private Label yearLabel;
    @FXML private Text descriptionText;
    @FXML private ImageView imageView;

    public void setHeritage(Heritage heritage) {
        nameLabel.setText(heritage.getName());
        categoryLabel.setText("类别: " + heritage.getCategory());
        regionLabel.setText("地区: " + heritage.getRegion());
        yearLabel.setText("入选年份: " + heritage.getYearRecognized());
        descriptionText.setText(heritage.getDescription());

        // Try to load image if path exists, otherwise load default
        if (heritage.getImagePath() != null && !heritage.getImagePath().isEmpty()) {
            try {
                File file = new File(heritage.getImagePath());
                if(file.exists()) {
                    imageView.setImage(new Image(file.toURI().toString()));
                }
            } catch (Exception e) {
                // Ignore image error
            }
        }
    }
}
