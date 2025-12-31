package museum.controller;

import museum.model.Architecture;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;

public class ArchitectureDetailController {

    @FXML private Label nameLabel;
    @FXML private Label dynastyLabel;
    @FXML private Label locationLabel;
    @FXML private Label typeLabel;
    @FXML private Label yearLabel;
    @FXML private Text descriptionText;
    @FXML private ImageView imageView;

    public void setArchitecture(Architecture architecture) {
        nameLabel.setText(architecture.getName());
        dynastyLabel.setText("朝代: " + architecture.getDynasty());
        locationLabel.setText("位置: " + architecture.getLocation());
        typeLabel.setText("类型: " + architecture.getType());
        yearLabel.setText("修建年份: " + architecture.getYearBuilt());
        descriptionText.setText(architecture.getDescription());

        // Try to load image
        if (architecture.getImagePath() != null && !architecture.getImagePath().isEmpty()) {
             try {
                File file = new File(architecture.getImagePath());
                if(file.exists()) {
                     imageView.setImage(new Image(file.toURI().toString()));
                }
            } catch (Exception e) {
                // Ignore
            }
        }
    }
}
