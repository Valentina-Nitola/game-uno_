package com.example.gameuno.view;

import com.example.gameuno.model.JuegoModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WildView {
	
	private JuegoModel.Color colorSeleccionado;
	
	@FXML private Button btnRed;
	@FXML private Button btnBlue;
	@FXML private Button btnGreen;
	@FXML private Button btnYellow;
	
	public JuegoModel.Color getColorSeleccionado() {
		return colorSeleccionado;
	}
	
	@FXML
	private void seleccionarRojo() {
		colorSeleccionado = JuegoModel.Color._RED;
		cerrarVentana();
	}
	
	@FXML
	private void seleccionarAzul() {
		colorSeleccionado = JuegoModel.Color._BLUE;
		cerrarVentana();
	}
	
	@FXML
	private void seleccionarVerde() {
		colorSeleccionado = JuegoModel.Color._GREEN;
		cerrarVentana();
	}
	
	@FXML
	private void seleccionarAmarillo() {
		colorSeleccionado = JuegoModel.Color._YELLOW;
		cerrarVentana();
	}
	
	private void cerrarVentana() {
		Stage stage = (Stage) btnRed.getScene().getWindow(); // Puedes usar cualquier botón aquí
		stage.close();
	}
}