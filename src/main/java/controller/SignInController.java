package com.javafx.wkwk;

import Dao.PlayerDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class SignInController implements Initializable {

    @FXML
    private TextField inputUser;
    @FXML
    private TextField inputPass;
    @FXML
    private ImageView btnLogin;
    @FXML
    private ImageView btnBack;
    @FXML
    private Text text;

    private DropShadow glowEffect;
    public static Player user;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Menambahkan efek glow
        glowEffect = new DropShadow();
        glowEffect.setColor(Color.YELLOW);
        glowEffect.setRadius(20);
        
        // Mengaktifkan klik di ImageView
        btnLogin.setPickOnBounds(true);
        btnBack.setPickOnBounds(true);
        
        // Menambahkan efek hover
        hoverEffect(btnLogin, glowEffect);
        hoverEffect(btnBack, glowEffect);
        
        // Event klik
        btnLogin.setOnMouseClicked(event -> Masuk());
        btnBack.setOnMouseClicked(event -> GoToMainMenu());
    }    

    private void hoverEffect(ImageView imageView, DropShadow glowEffect) {
        imageView.setOnMouseEntered(event -> imageView.setEffect(glowEffect));
        imageView.setOnMouseExited(event -> imageView.setEffect(null));
    }

    @FXML
    private void Masuk() {
        String name = inputUser.getText();
        String pass = inputPass.getText();

        // Cek apakah username atau password kosong
        if (name == null || name.isEmpty() || pass == null || pass.isEmpty()) {
            System.out.println("Field is empty");
            text.setText("Isi terlebih dahulu");
            return;
        } else {
            // Cek apakah username valid (ada di database)
            int id = PlayerDAO.validate(name);
            if (id == 0) {
                // Jika username tidak ditemukan
                text.setText("Username tidak ditemukan");
            } else {
                // Jika username ditemukan, cek passwordnya
                int validPassword = PlayerDAO.Valdi(name, pass);
                if (validPassword != -1) {
                    // Jika password benar, lanjut ke halaman berikutnya
                    Player p = new Player(name, pass);
                    try {
                        Stage stage = (Stage) btnLogin.getScene().getWindow();
                        URL url = new File("src/main/resources/com/javafx/wkwk/HomePage.fxml").toURI().toURL();
                        Parent root = FXMLLoader.load(url);
                        Scene scene = new Scene(root);
                        GamePlayController.setUser(p); // Menyimpan user yang login
                        stage.setScene(scene);
                        stage.centerOnScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Jika password salah
                    text.setText("Password salah, coba lagi");
                }
            }
        }
    }



    @FXML
    private void GoToMainMenu() {
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/MainMenu.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
