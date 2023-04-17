package com.example.diplomclient;

import com.example.diplomclient.Model.Doctors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SheduleMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    @FXML
    private TableView<Object> tableShedule;
    @FXML
    private TableColumn<Doctors,String> colName = new TableColumn<Doctors, String>();
    @FXML
    private TableColumn<Doctors,String> colSpec;
    @FXML
    private TableColumn<Doctors,String> colRoom;
    @FXML
    private TableColumn<Doctors,String> colMonday;
    @FXML
    private TableColumn<Doctors,String> colTuesday;
    @FXML
    private TableColumn<Doctors,String> colWed;
    @FXML
    private TableColumn<Doctors,String> colTh;
    @FXML
    private TableColumn<Doctors,String> colFr;
    @FXML
    private TableColumn<Doctors,String> colSat;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnProfile;
    @FXML
    private Button btnRefresh;

    private static final String SHOW_QUERY = "show";

    @FXML
    void initialize(){
        colName.setCellValueFactory(new PropertyValueFactory<Doctors, String>("fullname"));
        colSpec.setCellValueFactory(new PropertyValueFactory<Doctors, String>("proffession"));
        colRoom.setCellValueFactory(new PropertyValueFactory<Doctors, String>("room"));
        colMonday.setCellValueFactory(new PropertyValueFactory<Doctors, String>("monday"));
        colTuesday.setCellValueFactory(new PropertyValueFactory<Doctors, String>("tuesday"));
        colWed.setCellValueFactory(new PropertyValueFactory<Doctors, String>("wednesday"));
        colTh.setCellValueFactory(new PropertyValueFactory<Doctors, String>("thursday"));
        colFr.setCellValueFactory(new PropertyValueFactory<Doctors, String>("friday"));
        colSat.setCellValueFactory(new PropertyValueFactory<Doctors, String>("saturday"));

        btnRefresh.setOnAction(event -> tableShedule.setItems(getNotes()));
        btnBack.setOnAction(event -> setWindowBack());
        btnProfile.setOnAction(event -> setWindow());
    }

    public ObservableList getNotes(){
        ArrayList shedule = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject("doctors");
            shedule = (ArrayList) readerObj.readObject();
            System.out.println(shedule);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(shedule);
    }

    public void setWindow(){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            ProfileMenuController controller = loader.getController();
            controller.getConnection(clientSocket, writerObj, readerObj, stage1);
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

    public void getConnection(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
