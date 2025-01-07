package com.javafx.wkwk;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * 
 * Handles navigation and visual effects for Plant, Pest, and Back buttons.
 */
public class LiatInfoController implements Initializable {

    @FXML
    private ImageView PlantBtn;

    @FXML
    private ImageView PestBtn;

    @FXML
    private ImageView backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Efek glow untuk semua tombol
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);

        // Tambahkan efek hover untuk tombol
        applyHoverEffect(PlantBtn, glow);
        applyHoverEffect(PestBtn, glow);
        applyHoverEffect(backBtn, glow);

        // Event klik untuk tombol
        PlantBtn.setOnMouseClicked(event -> toPlant());
        PestBtn.setOnMouseClicked(event -> toPest());
        backBtn.setOnMouseClicked(event -> back());
    }
    private void applyHoverEffect(ImageView button, DropShadow glow) {
        button.setPickOnBounds(true); // Memastikan area klik mencakup seluruh gambar
        button.setOnMouseEntered(event -> button.setEffect(glow));
        button.setOnMouseExited(event -> button.setEffect(null));
    }
    private void back() {
        navigateTo("src/main/resources/com/javafx/wkwk/HomePage.fxml");
    }
    private void toPlant() {
        navigateTo("src/main/resources/com/javafx/wkwk/PestInfo.fxml");
    }
    private void toPest() {
        navigateTo("src/main/resources/com/javafx/wkwk/PlantInfo.fxml");
    }
    private void navigateTo(String fxmlPath) {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            URL url = new File(fxmlPath).toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
