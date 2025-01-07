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


public class PestInfoController implements Initializable {

    @FXML
    private ImageView backBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Efek glow untuk tombol backabtn
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);

        // Aktifkan efek hover untuk backabtn
        backBtn.setPickOnBounds(true); // Memastikan area klik mencakup seluruh gambar
        backBtn.setOnMouseEntered(event -> backBtn.setEffect(glow));
        backBtn.setOnMouseExited(event -> backBtn.setEffect(null));

        // Event klik untuk kembali ke halaman utama
        backBtn.setOnMouseClicked(event -> backToHomePage());
    }

    private void backToHomePage() {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
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
