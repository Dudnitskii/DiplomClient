package com.example.diplomclient;

import com.example.diplomclient.Model.Basic;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CardMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private static final String UPDATE_QUERY = "update";

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfDate;
    @FXML
    private TextField tfAdress;
    @FXML
    private TextField tfSex;
    @FXML
    private TextField tfJob;
    @FXML
    private TextField tfBlood;
    @FXML
    private TextField tfAllergy;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnAnalyzes;
    @FXML
    private Button btnAnamnesis;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnVaccination;

    @FXML
    void initialize(){
        btnBack.setOnAction(event -> setWindowBack());
        btnEdit.setOnAction(event -> update());
        btnAnalyzes.setOnAction(event -> setWindow("Analyzes"));
        btnVaccination.setOnAction(event -> setWindow("Vaccination"));
        btnAnamnesis.setOnAction(event -> setWindow("Anamnesis"));
    }

    public void setWindow(String windowName){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName+".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            switch (windowName)
            {
                case "Analyzes"->{
                    AnalyzesController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "Vaccination"->{
                    VaccinationController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
                case "Anamnesis"->{
                    AnamnesisController controller = loader.getController();
                    controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                }
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWindowBack(){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            UserMenuController controller =loader.getController();
            controller.getConnection(clientSocket, writerObj, readerObj, stage1);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        try {
            Basic basic = new Basic(tfName.getText(), tfDate.getText(), tfSex.getText(),
                    tfAdress.getText(), tfJob.getText(), tfBlood.getText(), tfAllergy.getText());
            writerObj.writeObject(UPDATE_QUERY);
            writerObj.writeObject(basic);
            writerObj.writeObject("patient");
        }catch (IOException e) {e.printStackTrace();}
    }

    public void getConnection(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
