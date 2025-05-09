package com.example.gameuno.view;

import com.example.gameuno.controller.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class JuegoView extends Stage {
	private JuegoController controller;
	
	private JuegoView() throws IOException {
		// Cargar el FXML
		FXMLLoader fxmlLoader = new FXMLLoader(
				getClass().getResource("/com/example/gameuno/interfaces/juegoView.fxml")
		);
		
		// Configurar la escena
		Scene scene = new Scene(fxmlLoader.load(), 800, 600);
		
		// Configurar el controlador y la ventana
		this.controller = fxmlLoader.getController();
		this.setTitle("UNO MINECRAFT - JUEGO");
		this.setScene(scene);
		this.setResizable(false);
	}
	
	public JuegoController getController() {
		return controller;
	}
	
	public static JuegoView getInstance() throws IOException {
		return JuegoViewHolder.INSTANCE == null ?
				(JuegoViewHolder.INSTANCE = new JuegoView()) :
				JuegoViewHolder.INSTANCE;
	}
	
	private static class JuegoViewHolder {
		private static JuegoView INSTANCE;
	}
}