package com.javafx.wkwk;

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
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class MainMenuController implements Initializable {

    @FXML
    private ImageView btnSignin;
    @FXML
    private ImageView btnSignup;
    @FXML
    private AnchorPane mainRoot;

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        DropShadow glow = new DropShadow();
        glow.setColor(Color.YELLOW);
        glow.setRadius(20);
        
        // mengaktifkan klik di imgview
        btnSignin.setPickOnBounds(true);
        btnSignup.setPickOnBounds(true);
        
        //tambahkan efek hover
        hoverEffect(btnSignin,glow);
        hoverEffect(btnSignup,glow);
        
        //event click
        btnSignin.setOnMouseClicked(event -> SignIn());
        btnSignup.setOnMouseClicked(event -> SignUp());
        
    }    
    
    private void hoverEffect(ImageView button, DropShadow glow) {
        button.setOnMouseEntered(event -> button.setEffect(glow));
        button.setOnMouseExited(event -> button.setEffect(null));
    }

    private void SignIn() {
        try {
            Stage stage = (Stage) btnSignin.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/SignIn.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SignUp() {
        try {
            Stage stage = (Stage) btnSignup.getScene().getWindow();
            URL url = new File("src/main/resources/com/javafx/wkwk/SignUp.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
