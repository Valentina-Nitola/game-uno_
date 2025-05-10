module com.example.gameuno {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;

	opens com.example.gameuno to javafx.fxml;
	opens com.example.gameuno.view to javafx.fxml;
	opens com.example.gameuno.controller to javafx.fxml;

	exports com.example.gameuno;
	exports com.example.gameuno.view;
}
