package com.javafx.wkwk;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;


public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL url = new File("src/main/resources/com/javafx/wkwk/MainMenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Pest Invasion");
        stage.show();
    }

    
    public static void main(String[] args) {
        launch(args);
    }

}