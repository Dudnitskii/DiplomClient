package com.example.diplomclient;

import com.example.diplomclient.Model.Patient;
import com.example.diplomclient.Model.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    @FXML
    private TableView<Object> tableRequest;
    @FXML
    private TableColumn<Request, String> colDoctor;
    @FXML
    private TableColumn<Request, String> colStatus;
    @FXML
    private TableColumn<Request, String> colDescription;
    @FXML
    private TableColumn<Request, String> colMedicine;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnDelete;

    @FXML
    void initialize(){
        colDoctor.setCellValueFactory(new PropertyValueFactory<Request, String>("doctor"));
        colMedicine.setCellValueFactory(new PropertyValueFactory<Request, String>("medicine"));
        colDescription.setCellValueFactory(new PropertyValueFactory<Request, String>("description"));
        colStatus.setCellValueFactory(new PropertyValueFactory<Request, String>("status"));
        btnBack.setOnAction(event -> setWindowBack());
        btnCreate.setOnAction(event -> create());
        btnDelete.setOnAction(event -> deleteItem());
    }

    public void create(){

    }
    public void deleteItem(){

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

    public void getConnection(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }}
