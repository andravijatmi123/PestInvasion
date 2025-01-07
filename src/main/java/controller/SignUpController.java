/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField inputUser;
    @FXML
    private TextField inputPass;
    @FXML
    private Button signUp;
    @FXML
    private Button back;
    @FXML
    private Text text;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void RegUser(ActionEvent event) {
        String name = inputUser.getText();
        String pass = inputPass.getText();
        if (name == null || name.isEmpty() && pass == null || pass.isEmpty()) {
            System.out.println("Field is empty");
            text.setText("isi terlebih dahulu");
            return;
        }else{
            int i = PlayerDAO.validate(name);
            if(i == 0){
                Player p = new Player(name, pass);
                text.setText(name + " berhasil didaftarkan, silahkan kembali dan kemudian login");
                PlayerDAO.registerPlayer(p);
                
            }else{
                text.setText(name + " sudah dipakai");
            }
            
        }
    }

    @FXML
    private void GoToMainMenu(ActionEvent event) {
        try {
            Stage stage = (Stage) back.getScene().getWindow();
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
