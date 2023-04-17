package com.example.diplomclient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;

public class AuthorizationController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private String windowName;
    private String[] resArr;
    private Stage stage = null;

    @FXML
    private Button btnLogin;

    @FXML
    private Label infoArea;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPass;

    public AuthorizationController() {
    }

    @FXML
    void initialize(){
        btnLogin.setOnAction(actionEvent -> {
            try {
                if(Objects.equals(inputLogin.getText(), "") || Objects.equals(inputPass.getText(), "")){
                    infoArea.setText("Поля должны быть заполнены!");
                } else {
                    String request = inputLogin.getText() + "=" + inputPass.getText();

                    writerObj.writeObject("logIn");
                    writerObj.writeObject(request);

                    String res = (String) readerObj.readObject();
                    System.out.println(res);
                    resArr = res.split(";");
                    if(Objects.equals(resArr[0], "Admin") || Objects.equals(resArr[0], "User") || Objects.equals(resArr[0], "Doctor")) {
                        windowName = resArr[0] + "Menu.fxml";
                        setWindow(windowName);
                    }else infoArea.setText(resArr[0]);
//
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void setWindow(String windowName){
        Stage stage1 = (Stage) btnLogin.getScene().getWindow();
        stage1.hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            switch (resArr[0])
            {
                case "Admin"-> {
                    AdminMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "User"->{
                    UserMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "Doctor"->{
                    DoctorMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
