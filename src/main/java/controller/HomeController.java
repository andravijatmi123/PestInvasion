package com.javafx.wkwk;

import Dao.RecordDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Record;

public class HomeController implements Initializable {

    @FXML
    private ImageView backBtn;

    @FXML
    private ImageView StartBtn;

    @FXML
    private TableView<Record> leaderBoard;

    @FXML
    private TableColumn<Record, Integer> rank;

    @FXML
    private TableColumn<Record, String> user;

    @FXML
    private TableColumn<Record, Integer> score;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Konfigurasi kolom TableView
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        user.setCellValueFactory(new PropertyValueFactory<>("uname"));
        score.setCellValueFactory(new PropertyValueFactory<>("score"));

        // Memuat data leaderboard dari RecordDAO
        ObservableList<Record> data = RecordDAO.showLB();
        leaderBoard.setItems(data);

        // Menambahkan efek glow pada tombol
        addGlowEffect(backBtn);
        addGlowEffect(StartBtn);

        // Event untuk tombol backBtn
        backBtn.setPickOnBounds(true);
        backBtn.setOnMouseClicked(event -> Back());

        // Event untuk tombol StartBtn
        StartBtn.setPickOnBounds(true);
        StartBtn.setOnMouseClicked(event -> Start());
    }

    private void Back() {
        try {
            Stage stage = (Stage) backBtn.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/HomePage.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Start() {
        try {
            Stage stage = (Stage) StartBtn.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/GamePlay.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addGlowEffect(ImageView button) {
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);

        button.setOnMouseEntered(event -> button.setEffect(glow));
        button.setOnMouseExited(event -> button.setEffect(null));
    }
}
