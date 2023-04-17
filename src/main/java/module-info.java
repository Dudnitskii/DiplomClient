module com.example.diplomclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.diplomclient to javafx.fxml;
    exports com.example.diplomclient;
    exports com.example.diplomclient.Model;

}