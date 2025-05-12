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
 * Maneja las interacciones del usuario en la pantalla de menú.
 */
public class MenuController {

	// Elementos de la vista inyectados desde FXML
	@FXML private Button btnSonido;
	@FXML private TextField txtNombre;


	/**
	 * Método que se ejecuta al cargar la vista del menú.
	 * Inicializa la música y actualiza el estado del botón de música.
	 */
	@FXML
	public void initialize() {
		MusicModel.getInstance();
		actualizarBotonMusica();
	}

	/**
	 * Método que se ejecuta al hacer clic en el botón de música.
	 * Alterna entre encender y apagar la música, y actualiza la imagen del botón.
	 *
	 * @param event Evento generado al hacer clic en el botón.
	 */
	@FXML
	private void musica(ActionEvent event) {
		MusicModel.getInstance().toggleMusic();
		actualizarBotonMusica();
	}

	/**
	 * Actualiza la imagen del botón de música dependiendo si la música está activada o no.
	 */
	private void actualizarBotonMusica() {
		boolean isMusicOn = MusicModel.getInstance().isMusicOn();

		String imgPath = isMusicOn
				? "/com/example/gameuno/img/on.png"
				: "/com/example/gameuno/img/off.png";

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
	 * Abre la vista del juego principal al hacer clic en el botón correspondiente.
	 *
	 * @param event Evento generado al hacer clic en el botón "Iniciar juego".
	 * @throws IOException Si ocurre un error al cargar la vista del juego.
	 */
	@FXML
	private void iniciarjuego(ActionEvent event) throws IOException {
		String nickname = txtNombre.getText();
		JugadorModel player = new JugadorModel();
		player.setNombre(nickname);

		// Validar si el nombre es válido
		if (!player.isValid()) {
			mostrarErrorNombre();
			return;
		}

		System.out.println("Iniciar juego");

		// Obtener la instancia de JuegoView
		JuegoView juegoView = JuegoView.getInstance();
		juegoView.getController().setPlayer(player);
		MenuView.getInstance().close(); // Cerrar el menú
		juegoView.show(); // Mostrar la vista del juego
	}

	/**
	 * Muestra un mensaje de error cuando no se ingresa nombre.
	 */
	private void mostrarErrorNombre() {
		txtNombre.setPromptText("¡Ingresa tu nombre!");
		txtNombre.setStyle("-fx-prompt-text-fill: red;");
	}

	/**
	 * boton ayuda
	 */
	@FXML
	private void ayuda(ActionEvent event) {
		try {
			TutorialView tutorialView = TutorialView.getInstance();
			tutorialView.show();
		} catch (IOException e) {
			System.err.println("Error al abrir el tutorial: " + e.getMessage());
		}
	}
}
