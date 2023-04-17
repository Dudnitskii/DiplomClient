package com.example.diplomclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = (Parent)FXMLLoader.load((URL) Objects.requireNonNull(this.getClass().getResource("startupMenu.fxml")));
        primaryStage.setTitle("Main project");
        primaryStage.setScene(new Scene(root, 800.0, 600.0));
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch();
    }
}