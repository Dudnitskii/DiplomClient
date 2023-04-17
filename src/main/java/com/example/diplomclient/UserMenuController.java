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

public class UserMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    @FXML
    private Button btnProfile;
    @FXML
    private Button btnCard;
    @FXML
    private Button btnRequest;
    @FXML
    private Button btnShedule;

    @FXML
    void initialize(){
        btnProfile.setOnAction(actionEvent ->{
            setWindow("ProfileMenu");
        });
        btnShedule.setOnAction(actionEvent ->{
            setWindow("SheduleMenu");
        });
        btnCard.setOnAction(actionEvent ->{
            setWindow("CardMenu");
        });
        btnRequest.setOnAction(actionEvent ->{
            setWindow("RequestMenu");
        });
    }

    public void setWindow(String windowName){
        Stage stage1 = (Stage) btnProfile.getScene().getWindow();
        stage1.hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName+".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            switch (windowName)
            {
                case "ProfileMenu"->{
                    ProfileMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "SheduleMenu"->{
                    SheduleMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "RequestMenu"->{
                    RequestMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "CardMenu"->{
                    CardMenuController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getConnection(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
