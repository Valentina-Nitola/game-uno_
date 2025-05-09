package com.example.gameuno.controller;

import com.example.gameuno.view.JuegoView;
import com.example.gameuno.view.TutorialView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controlador para la vista del menú principal del juego UNO.
 * Maneja las interacciones del usuario en la pantalla de menú.
 */
public class MenuController {
	
	// Elementos de la vista inyectados desde FXML
	@FXML private Button btnComenzar;
	@FXML private Button btnAyuda;
	@FXML private Button btnSonido;
	@FXML private TextField txtNombre;
	
	// Estado del sonido
	private boolean sonidoActivado = true;
	
	/**
	 * Método de inicio, llamado automáticamente después de cargar el FXML.
	 */
	@FXML
	public void initialize() {
		configurarEventos();
	}
	
	/**
	 * Configura los eventos para los botones de la interfaz.
	 */
	private void configurarEventos() {
		// Botón para comenzar el juego
		btnComenzar.setOnAction(event -> manejarBotonComenzar());
		
		// Botón para mostrar el tutorial
		btnAyuda.setOnAction(event -> manejarBotonAyuda());
		
		// Botón para alternar el sonido
		btnSonido.setOnAction(event -> alternarSonido());
	}
	
	/**
	 * Maneja la acción del botón "Comenzar".
	 */
	private void manejarBotonComenzar() {
		if (validarNombreJugador()) {
			abrirVistaJuego();
		}
	}
	
	/**
	 * Valida que se haya ingresado un nombre de jugador.
	 * @return true si el nombre es válido, false en caso contrario
	 */
	private boolean validarNombreJugador() {
		if (txtNombre.getText().trim().isEmpty()) {
			mostrarErrorNombre();
			return false;
		}
		return true;
	}
	
	/**
	 * Muestra un mensaje de error cuando no se ingresa nombre.
	 */
	private void mostrarErrorNombre() {
		txtNombre.setPromptText("¡Ingresa tu nombre!");
		txtNombre.setStyle("-fx-prompt-text-fill: red;");
	}
	
	/**
	 * Abre la vista del juego principal.
	 */
	private void abrirVistaJuego() {
		try {
			JuegoView juegoView = JuegoView.getInstance();
			juegoView.show();
			cerrarVentanaActual();
		} catch (IOException e) {
			System.err.println("Error al abrir el juego: " + e.getMessage());
		}
	}
	
	/**
	 * boton ayuda
	 */
	private void manejarBotonAyuda() {
		try {
			TutorialView tutorialView = TutorialView.getInstance();
			tutorialView.show();
		} catch (IOException e) {
			System.err.println("Error al abrir el tutorial: " + e.getMessage());
		}
	}
	
	/**
	 sonido activado, desactivado
	 */
	private void alternarSonido() {
		sonidoActivado = !sonidoActivado;
		// falta aqui lo de activar/desactivar
		System.out.println("Sonido " + (sonidoActivado ? "activado" : "desactivado"));
	}
	
	/**
	 cambio de ventana
	 */
	private void cerrarVentanaActual() {
		Stage stage = (Stage) btnComenzar.getScene().getWindow();
		stage.close();
	}
	
	/**
	 nombre del jugador ingresado
	 */
	public String getNombreJugador() {
		return txtNombre.getText().trim();
	}
}