package com.example.gameuno.controller;

import com.example.gameuno.model.JugadorModel;
import com.example.gameuno.model.MusicModel;
import com.example.gameuno.view.JuegoView;
import com.example.gameuno.view.MenuView;
import com.example.gameuno.view.TutorialView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.IOException;
import java.net.URL;

/**
 * Controlador para la vista del menú principal del juego UNO.
 * Maneja las interacciones del usuario en la pantalla de menú,
 * incluyendo la configuración inicial, control de música y navegación
 * entre vistas.
 *
 * @author Valentina Nitola
 * @version 1.1.3
 */
public class MenuController {

	// Elementos de la interfaz gráfica

	/**
	 * Botón para controlar el estado de la música del juego
	 */
	@FXML private Button btnSonido;

	/**
	 * Campo de texto para ingresar el nombre del jugador
	 */
	@FXML private TextField txtNombre;

	/**
	 * Método de inicialización que se ejecuta al cargar la vista.
	 * Configura la música del juego y actualiza el estado visual del botón de música.
	 */
	@FXML
	public void initialize() {
		MusicModel.getInstance(); // Inicializa el reproductor de música
		actualizarBotonMusica(); // Configura la imagen inicial del botón
	}

	/**
	 * Maneja el evento de clic en el botón de música.
	 * Alterna el estado de la música entre encendido/apagado y actualiza
	 * la imagen del botón.
	 *
	 * @param event Evento de acción generado por el clic
	 */
	@FXML
	private void musica(ActionEvent event) {
		MusicModel.getInstance().toggleMusic(); // Cambia el estado de la música
		actualizarBotonMusica(); // Actualiza la imagen del botón
	}

	/**
	 * Actualiza la imagen del botón de música según su estado actual.
	 * Muestra un ícono diferente para música encendida/apagada.
	 */
	private void actualizarBotonMusica() {
		boolean isMusicOn = MusicModel.getInstance().isMusicOn();

		// Selecciona la ruta de la imagen según el estado
		String imgPath = isMusicOn
				? "/com/example/gameuno/img/on.png"
				: "/com/example/gameuno/img/off.png";

		// Carga y configura la imagen
		URL imgURL = getClass().getResource(imgPath);
		if (imgURL != null) {
			Image img = new Image(imgURL.toString());
			ImageView imageView = new ImageView(img);
			imageView.setFitWidth(87);
			imageView.setFitHeight(75);
			btnSonido.setGraphic(imageView);
		} else {
			System.out.println("No se encontró la imagen: " + imgPath);
		}
	}

	/**
	 * Maneja el inicio de una nueva partida.
	 * Valida el nombre del jugador y abre la vista del juego principal.
	 *
	 * @param event Evento de acción generado por el clic
	 * @throws IOException Si ocurre un error al cargar la vista del juego
	 */
	@FXML
	private void iniciarjuego(ActionEvent event) throws IOException {
		String nickname = txtNombre.getText();
		JugadorModel player = new JugadorModel();
		player.setNombre(nickname);

		// Validación del nombre
		if (!player.isValid()) {
			mostrarErrorNombre();
			return;
		}

		System.out.println("Iniciar juego");

		// Configuración y apertura de la vista del juego
		JuegoView juegoView = JuegoView.getInstance();
		juegoView.getController().setPlayer(player); // Pasa el jugador al controlador del juego
		MenuView.getInstance().close(); // Cierra el menú actual
		juegoView.show(); // Muestra la vista del juego
	}

	/**
	 * Muestra un mensaje de error cuando no se ingresa un nombre válido.
	 * Cambia el estilo del campo de texto para indicar el error.
	 */
	private void mostrarErrorNombre() {
		txtNombre.setPromptText("¡Ingresa tu nombre!");
		txtNombre.setStyle("-fx-prompt-text-fill: red;");
	}

	/**
	 * Maneja la apertura de la vista del tutorial.
	 *
	 * @param event Evento de acción generado por el clic
	 */
	@FXML
	private void ayuda(ActionEvent event) throws IOException {
		String nickname = txtNombre.getText();
		JugadorModel player = new JugadorModel();
		player.setNombre(nickname);

		// Validación del nombre
		if (!player.isValid()) {
			mostrarErrorNombre();
			return;
		}
		try {
			TutorialView tutorialView = TutorialView.getInstance();
			tutorialView.show(); // Muestra la vista del tutorial
		} catch (IOException e) {
			System.err.println("Error al abrir el tutorial: " + e.getMessage());
		}
	}
}