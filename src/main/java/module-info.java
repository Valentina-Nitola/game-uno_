module com.example.gameuno {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gameuno to javafx.fxml;
    exports com.example.gameuno;
    exports com.example.gameuno.view;
    opens com.example.gameuno.view to javafx.fxml;
}