package com.example.diplomclient;

import com.example.diplomclient.Model.User;
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

public class RegistrationController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    private static final String ADD_USER = "add user";
    private static final String CHECK_USER = "check user";

    @FXML
    private Button btnCreate;
    @FXML
    private TextField inputLogin;
    @FXML
    private PasswordField inputPass;
    @FXML
    private PasswordField repeatPass;
    @FXML
    private TextField tfRole;
    @FXML
    private Label infoArea;

    @FXML
    void initialize() {
        btnCreate.setOnAction(actionEvent -> {
            String newWindow = "UserMenu";
            String role = "user";

            String login = inputLogin.getText();
            String password = inputPass.getText();
            String repeatPassword = repeatPass.getText();
            if(Objects.equals(tfRole.getText(), "Doctor")) {
                role = "doctor";
                newWindow = "DoctorMenu";
            }

            if(Objects.equals(login, "") || Objects.equals(password, "")
                    || Objects.equals(repeatPassword, "")){
                infoArea.setText("Все поля должны быть заполнены!");
            } else {
                try {
                    writerObj.writeObject(CHECK_USER);
                    writerObj.writeObject(login);
                    Boolean unique = (Boolean) readerObj.readObject();
                    if(unique){
                        if(Objects.equals(password, repeatPassword)){
                            String request = login + "=" + password + "=" + role;
                            writerObj.writeObject(ADD_USER);
                            writerObj.writeObject(request);

                            String res = (String) readerObj.readObject();
                            System.out.println(res);

                            Stage stage1 = (Stage) btnCreate.getScene().getWindow();
                            stage1.close();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource( newWindow + ".fxml"));
                            Parent root = loader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            if(Objects.equals(role, "user")){
                                UserMenuController controller = loader.getController();
                                controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                            }else{
                                DoctorMenuController controller = loader.getController();
                                controller.getConnection(clientSocket, writerObj, readerObj, stage1);
                            }

                            stage.show();
                        } else{
                            System.out.println("Пароли должны совпадать!");
                            infoArea.setText("Пароли должны совпадать!");
                        }
                    } else infoArea.setText("Такой логин уже существует!");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
