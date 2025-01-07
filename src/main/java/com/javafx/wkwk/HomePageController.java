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
 * @author Asus
 */
public class HomePageController implements Initializable {

    @FXML
    private ImageView btnStart;

    @FXML
    private ImageView btnLead;

    @FXML
    private ImageView btnAbout;

    @FXML
    private ImageView btnInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Efek glow untuk ImageView
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);

        // Mengaktifkan klik pada semua ImageView
        btnStart.setPickOnBounds(true);
        btnLead.setPickOnBounds(true);
        btnAbout.setPickOnBounds(true);
        btnInfo.setPickOnBounds(true);

        // Tambahkan efek hover untuk setiap ImageView
        applyHoverEffect(btnStart, glow);
        applyHoverEffect(btnLead, glow);
        applyHoverEffect(btnAbout, glow);
        applyHoverEffect(btnInfo, glow);

        // Event klik
        btnStart.setOnMouseClicked(event -> startGame());
        btnLead.setOnMouseClicked(event -> showLeaderboard());
        btnAbout.setOnMouseClicked(event -> about());
        btnInfo.setOnMouseClicked(event -> info());
    }

    private void applyHoverEffect(ImageView button, DropShadow glow) {
        button.setOnMouseEntered(event -> button.setEffect(glow));
        button.setOnMouseExited(event -> button.setEffect(null));
    }

    private void startGame() {
        try {
            Stage stage = (Stage) btnStart.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/GamePlay.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showLeaderboard() {
        try {
            Stage stage = (Stage) btnLead.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/Home.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void about() {
        try {
            Stage stage = (Stage) btnAbout.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/Informasi.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void info() {
        try {
            Stage stage = (Stage) btnInfo.getScene().getWindow();
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
