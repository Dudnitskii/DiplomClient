package com.example.diplomclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class StartupMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    @FXML
    private Button btnAuthorization;
    @FXML
    private Button btnRegistration;


    @FXML
    void initialize() {
        try {
            this.clientSocket = new Socket("127.0.0.1", 8000);
            this.writerObj = new ObjectOutputStream(this.clientSocket.getOutputStream());
            this.readerObj = new ObjectInputStream(this.clientSocket.getInputStream());
            this.btnAuthorization.setOnAction((actionEvent)->{
                this.setWindow("Authorization");
            });
            this.btnRegistration.setOnAction((actionEvent) -> {
                this.setWindow("registration");
            });
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    void setWindow(String windowName) {
        Stage stage1 = (Stage)this.btnAuthorization.getScene().getWindow();
        stage1.hide();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            if (Objects.equals(windowName, "registration")) {
                RegistrationController controller = (RegistrationController)loader.getController();
                controller.getConnect(this.clientSocket, this.writerObj, this.readerObj, stage1);
            } else {
                AuthorizationController controller = (AuthorizationController)loader.getController();
                controller.getConnect(this.clientSocket, this.writerObj, this.readerObj, stage1);
            }
            stage.show();
        } catch (IOException var7) {
            var7.printStackTrace();
        }
    }
}
