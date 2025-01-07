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
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameLoseController implements Initializable {

    @FXML
    private ImageView btnYes;
    @FXML
    private ImageView btnNo;
    
    private DropShadow glowEffect;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Menambahkan efek glow
        glowEffect = new DropShadow();
        glowEffect.setColor(Color.YELLOW);
        glowEffect.setRadius(20);
        
        // Mengaktifkan klik di ImageView
        btnYes.setPickOnBounds(true);
        btnNo.setPickOnBounds(true);
        
        // Menambahkan efek hover
        hoverEffect(btnYes, glowEffect);
        hoverEffect(btnNo, glowEffect);
        
        // Event klik
        btnYes.setOnMouseClicked(event -> yes());
        btnNo.setOnMouseClicked(event -> no());
    }

    // Method untuk menambahkan efek Glow saat hover
    private void addGlowEffect(ImageView button) {
        Glow glow = new Glow();
        glow.setLevel(0.7); // Atur tingkat intensitas efek glow

        button.setOnMouseEntered(event -> button.setEffect(glow)); // Menambahkan efek glow saat mouse masuk
        button.setOnMouseExited(event -> button.setEffect(null)); // Menghapus efek glow saat mouse keluar
    }
    
    private void hoverEffect(ImageView imageView, DropShadow glowEffect) {
        imageView.setOnMouseEntered(event -> imageView.setEffect(glowEffect));
        imageView.setOnMouseExited(event -> imageView.setEffect(null));
    }

    // Method untuk tombol Yes
    @FXML
    private void yes() {
        try {
            Stage stage = (Stage) btnYes.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/GamePlay.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            //GamePlayController.ResetData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk tombol No
    @FXML
    private void no() {
        try {
            Stage stage = (Stage) btnNo.getScene().getWindow();
            // Memuat HomePage.fxml
            URL url = getClass().getResource("/com/javafx/wkwk/HomePage.fxml");
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene); // Mengubah scene
            stage.centerOnScreen(); // Menyeimbangkan posisi layar
            stage.show(); // Menampilkan stage
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
