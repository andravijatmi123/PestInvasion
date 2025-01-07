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
 * FXML Controller class for PlantInfo.
 * Handles navigation back to HomePage and visual effects.
 */
public class PlantInfoController implements Initializable {

    @FXML
    private ImageView backabtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Efek glow untuk tombol backabtn
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);

        // Aktifkan efek hover untuk backabtn
        backabtn.setPickOnBounds(true); // Memastikan area klik mencakup seluruh gambar
        backabtn.setOnMouseEntered(event -> backabtn.setEffect(glow));
        backabtn.setOnMouseExited(event -> backabtn.setEffect(null));

        // Event klik untuk kembali ke halaman utama
        backabtn.setOnMouseClicked(event -> backToHomePage());
    }

    private void backToHomePage() {
        try {
            Stage stage = (Stage) backabtn.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/LiatInfo.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
