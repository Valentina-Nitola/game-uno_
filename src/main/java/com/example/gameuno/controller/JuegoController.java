package com.example.gameuno.controller;

import com.example.gameuno.model.CartaNoValidaException;
import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JugadorModel;
import com.example.gameuno.model.MusicModel;
import com.example.gameuno.util.AlertBox;
import com.example.gameuno.view.TutorialView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import com.example.gameuno.view.WildView;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * Controlador principal del juego UNO.
 * Gestiona la lógica del juego, incluyendo el manejo de turnos, cartas especiales,
 * interacción con la CPU y actualización de la interfaz gráfica.
 * <p>
 * Este controlador se encarga de las interacciones entre el modelo, la vista y la lógica del juego.
 * Los métodos principales incluyen la inicialización del juego, el manejo de los turnos de los jugadores
 * (humano y CPU), la actualización de las cartas en la interfaz gráfica, y la interacción con la música del juego.
 * </p>
 *
 * @author Daniel, Moreno y Valentina
 * @version 2.3.6
 */
public class JuegoController {

	/**
	 * Etiqueta que muestra el nickname del jugador.
	 */
	@FXML private Label nicknameLabel;

	/**
	 * Etiqueta que indica el jugador actual.
	 */
	@FXML private Label lblJugadorActual;

	/**
	 * Contenedor para las cartas en mano del jugador humano.
	 */
	@FXML private HBox contenedorManoJugador;

	/**
	 * Imagen que muestra la carta actual en el descarte.
	 */
	@FXML private ImageView descarte;

	/**
	 * Contenedor para las cartas en mano de la CPU.
	 */
	@FXML private HBox contenedorManoCpu;

	/**
	 * Botón para gritar "UNO" cuando el jugador tiene una sola carta.
	 */
	@FXML private Button UnoButton;

	/**
	 * Botón para controlar la música del juego.
	 */
	@FXML private Button btnSonido;

	private ArrayList<Carta> mazo;
	private Carta cartaEnMesa;

	/**
	 * Instancia del jugador humano.
	 */
	JugadorModel jugador = new JugadorModel();

	/**
	 * Instancia del jugador CPU.
	 */
	JugadorModel Cpu = new JugadorModel();

	/**
	 * Método de inicialización que se ejecuta al cargar la vista.
	 * Inicia una nueva partida y configura la música.
	 */
	@FXML
	public void initialize() {
		iniciarPartida();
		MusicModel.getInstance();
		actualizarBotonMusica();
	}

	/**
	 * Inicia una nueva partida de UNO.
	 * Crea el mazo, reparte cartas a los jugadores y establece la primera carta en mesa.
	 */
	public void iniciarPartida() {
		JuegoModel modelo = new JuegoModel();
		mazo = modelo.CrearCartas();
		JuegoModel.Barajar(mazo);
		jugador.repartirCartasIniciales(mazo);
		Cpu.repartirCartasIniciales(mazo);
		jugador.setTurno(true);

		if (!mazo.isEmpty()) {
			cartaEnMesa = mazo.remove(0);
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			System.out.println(cartaEnMesa.getTipo());
			System.out.println(cartaEnMesa.getColor());
			System.out.println(cartaEnMesa.getNumero());
		}
		actualizarManoJugador();
	}

	/**
	 * Actualiza la visualización de la carta en mesa.
	 */
	private void actualizarCartaEnMesa() {
		if (cartaEnMesa != null) {
			Image imagen = new Image(getClass().getResourceAsStream(cartaEnMesa.ruta));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(125);
			imagenView.setPreserveRatio(true);
			descarte.setImage(imagen);
			System.out.println(cartaEnMesa.ruta);
		}
	}

	/**
	 * Actualiza la visualización de las cartas en mano de ambos jugadores.
	 * También controla la visibilidad del botón UNO según las cartas restantes.
	 */
	public void actualizarManoJugador() {
		contenedorManoJugador.getChildren().clear();
		contenedorManoCpu.getChildren().clear();

		// Actualiza cartas del jugador humano
		for (Carta carta : jugador.Mano) {
			Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.ruta)));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(80);
			imagenView.setPreserveRatio(true);
			imagenView.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			contenedorManoJugador.getChildren().add(imagenView);
		}

		// Actualiza cartas de la CPU (mostrando solo el reverso)
		for (Carta carta : Cpu.Mano) {
			Image imagen2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.reverso)));
			ImageView imagenView = new ImageView(imagen2);
			imagenView.setFitWidth(80);
			imagenView.setPreserveRatio(true);
			contenedorManoCpu.getChildren().add(imagenView);
		}

		// Controla la visibilidad del botón UNO
		if (jugador.Mano.size() == 1 || Cpu.Mano.size() == 1) {
			UnoButton.setDisable(false);
			UnoButton.setOpacity(1.0);
			GuerraUno();
		} else {
			UnoButton.setDisable(true);
			UnoButton.setOpacity(0.4);
		}

		if (jugador.Mano.size() > 1) jugador.setUno(false);
		if (Cpu.Mano.size() > 1) Cpu.setUno(false);
	}

	/**
	 * Maneja la selección de una carta por parte del jugador humano.
	 * Lanza y captura una excepción personalizada si la jugada no es válida.
	 *
	 * @param cartaSeleccionada La carta que el jugador intenta jugar
	 */
	private void manejarSeleccionCarta(Carta cartaSeleccionada) {
		if (!jugador.isTurno()) {
			return;
		}

		try {
			// 1) Comprueba si la jugada es válida; si no, lanza excepción
			if (!esJugadaValida(cartaSeleccionada)) {
				throw new CartaNoValidaException(
						"No puedes jugar " + cartaSeleccionada +
								" porque no coincide con la carta en mesa."
				);
			}

			// 2) Lógica normal para jugar la carta
			jugador.Mano.remove(cartaSeleccionada);
			cartaEnMesa = cartaSeleccionada;
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			actualizarManoJugador();

			// 3) Efectos de cartas especiales
			switch (cartaSeleccionada.getTipo()) {
				case skip:
					lblJugadorActual.setText("¡La CPU pierde su turno!");
					jugador.setTurno(true);
					return;
				case masdos:
					robarCartas(Cpu, 2);
					lblJugadorActual.setText("CPU toma 2 cartas");
					jugador.setTurno(false);
					actualizarManoJugador();
					actualizarCartaEnMesa();
					turnoCPU();
					return;
				case mascuatro:
					robarCartas(Cpu, 4);
					lblJugadorActual.setText("CPU roba 4 cartas");
					mostrarSeleccionColor(true);
					return;
				case comodin:
					mostrarSeleccionColor(true);
					return;
				default:
					break;
			}

			// 4) Verifica victoria
			if (jugador.Mano.isEmpty()) {
				lblJugadorActual.setText("¡Ganaste!");
				return;
			}

			// 5) Pasa el turno a la CPU
			jugador.setTurno(false);
			turnoCPU();

		} catch (CartaNoValidaException ex) {
			// 6) Captura la excepción y muestra una alerta al jugador
			AlertBox.showError("Jugada inválida", ex.getMessage());
		}
	}

	/**
	 * Verifica si una carta puede ser jugada sobre la carta actual en mesa.
	 * @param carta La carta a validar
	 * @return true si la jugada es válida, false en caso contrario
	 */
	private boolean esJugadaValida(Carta carta) {
		return carta.getColor() == cartaEnMesa.getColor() ||
				(carta.getTipo() == JuegoModel.Tipo.numero && carta.getNumero() == cartaEnMesa.getNumero()) ||
				carta.getColor() == JuegoModel.Color.negro;
	}

	/**
	 * Maneja la acción de robar una carta del mazo.
	 */
	@FXML
	private void tomarCartaDelMazo() {
		if (!mazo.isEmpty() && jugador.isTurno()) {
			int contador = 0;
			for (int i=0; i<jugador.getMano().size(); i++) {
				if (esJugadaValida(jugador.getMano().get(i))) {contador++;}
			}
			if (contador == 0) {
				Carta nuevaCarta = mazo.remove(0);
				nuevaCarta.setEstado(JuegoModel.Estado.MANO);
				jugador.Mano.add(nuevaCarta);
				actualizarManoJugador();
				lblJugadorActual.setText("Tomaste una carta. Turno de la CPU.");
				jugador.setTurno(false);
				turnoCPU();
			}
		}
	}

	/**
	 * Establece el nombre del jugador en la interfaz.
	 * @param player El modelo del jugador humano
	 */
	public void setPlayer(JugadorModel player) {
		nicknameLabel.setText(player.getNombre());
	}

	/**
	 * Maneja el turno de la CPU en un hilo separado.
	 * Incluye lógica para jugar cartas válidas, robar cartas y efectos especiales.
	 */
	private void turnoCPU() {
		System.out.println("Turno de la CPU...");

		new Thread(() -> {
			try {
				Thread.sleep(new Random().nextInt(2000) + 2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Carta cartaJugada = null;

			// Busca una carta jugable
			for (Carta carta : Cpu.Mano) {
				if (esJugadaValida(carta)) {
					cartaJugada = carta;
					break;
				}
			}

			if (cartaJugada != null) {
				// Procesa la carta jugada por la CPU
				Cpu.Mano.remove(cartaJugada);
				cartaEnMesa = cartaJugada;
				cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);

				// Efectos de cartas especiales
				if (cartaJugada.getTipo() == JuegoModel.Tipo.skip) {
					Platform.runLater(() -> {
						lblJugadorActual.setText("¡Tu turno fue saltado!");
						actualizarCartaEnMesa();
						actualizarManoJugador();
						jugador.setTurno(false);
						turnoCPU();
					});
					return;
				}

				if (cartaJugada.getTipo() == JuegoModel.Tipo.masdos) {
					Platform.runLater(() -> {
						robarCartas(jugador, 2);
						actualizarManoJugador();
						actualizarCartaEnMesa();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("¡Has tomado 2 cartas!");
						}
						jugador.setTurno(true);
						lblJugadorActual.setText("Tu turno");
					});
					return;
				}

				if (cartaJugada.getTipo() == JuegoModel.Tipo.mascuatro) {
					Platform.runLater(() -> {
						robarCartas(jugador, 4);
						actualizarManoJugador();
						actualizarCartaEnMesa();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("Has tomado 4 cartas");
						}

						// Selección aleatoria de color por la CPU
						JuegoModel.Color[] coloresDisponibles = {
								JuegoModel.Color._red,
								JuegoModel.Color._blue,
								JuegoModel.Color._green,
								JuegoModel.Color._yellow
						};
						JuegoModel.Color colorAleatorio = coloresDisponibles[new Random().nextInt(coloresDisponibles.length)];
						cartaEnMesa.setColor(colorAleatorio);
						actualizarCartaEnMesa();
						System.out.println("CPU cambio el color a: " + colorAleatorio);
						jugador.setTurno(true);
						lblJugadorActual.setText("Tu turno");
					});
					return;
				}

				if (cartaJugada.getTipo() == JuegoModel.Tipo.comodin) {
					JuegoModel.Color[] coloresDisponibles = {
							JuegoModel.Color._red,
							JuegoModel.Color._blue,
							JuegoModel.Color._yellow,
							JuegoModel.Color._green
					};
					JuegoModel.Color colorAleatorio = coloresDisponibles[new Random().nextInt(coloresDisponibles.length)];
					cartaJugada.setColor(colorAleatorio);
					Platform.runLater(() -> {
						actualizarManoJugador();
						actualizarCartaEnMesa();
					});
					System.out.println("CPU cambió el color a: " + colorAleatorio);
				}

				System.out.println("CPU juega: " + cartaJugada.getColor() + " " +
						(cartaJugada.getTipo() == JuegoModel.Tipo.numero ?
								cartaJugada.getNumero() : cartaJugada.getTipo()));

				if (Cpu.Mano.isEmpty()) {
					Platform.runLater(() -> {
						lblJugadorActual.setText("La CPU ha ganado.");
						actualizarCartaEnMesa();
						actualizarManoJugador();
					});
					return;
				}
			} else if (!mazo.isEmpty()) {
				Carta nueva = mazo.remove(0);
				nueva.setEstado(JuegoModel.Estado.MANO);
				Cpu.Mano.add(nueva);
				System.out.println("CPU no pudo jugar. Roba una carta del mazo");
			}

			jugador.setTurno(true);

			Platform.runLater(() -> {
				lblJugadorActual.setText("Tu turno");
				actualizarManoJugador();
				actualizarCartaEnMesa();
			});
		}).start(); //Inicia el Hilo
	} //Fin Turno CPU

	/**
	 * Maneja el evento de presionar el botón UNO.
	 * @param event El evento de acción
	 */
	@FXML
	private void On_Push_Uno(ActionEvent event) {
		if (jugador.Mano.size() == 1) {
			System.out.println(" yo cante uno");
			jugador.setUno(true);
		}
		if (Cpu.Mano.size() == 1 && !Cpu.getUno()) {
			System.out.println(" le cante unoooo");
			robarCartas(Cpu, 1);
			Cpu.setUno(false);
		}
		actualizarManoJugador();
		UnoButton.setDisable(true);
		UnoButton.setOpacity(0.4); //Modificar la opacidad en lugar de visibilidad
	}

	/**
	 * Realiza una lógica relacionada con el "UNO" en el juego.
	 */
	void GuerraUno() {
		//Hilo 2 -> Canto del UNO
		new Thread(() -> {
			Random random = new Random(); // Para generar tiempo aleatorio

			while (true) { //Logica de Cantar el Uno para player y cpu
				try {
					Thread.sleep(2000); // Revisa cada 2 segundos

					if (Cpu.Mano.size() == 1 && !Cpu.getUno()) {
						// Simula que la CPU "piensa" entre 2 y 4 segundos antes de decir UNO
						int delay = 2000 + random.nextInt(4000); // 2000 a 4000 ms
						Thread.sleep(delay);

						// Vuelve a verificar que aún tenga solo 1 carta (puede haber cambiado mientras dormía)
						if (Cpu.Mano.size() == 1 && !Cpu.getUno()) {
							System.out.println("CPU dice: UNO (después de " + delay + " ms)");
							Cpu.setUno(true);
							Platform.runLater(() -> actualizarManoJugador());
						}
					}
					if (jugador.Mano.size() == 1 && !jugador.getUno()) {
						System.out.println("¡CPU te gritó UNO antes que tú!");
						robarCartas(jugador, 1);
						jugador.setUno(false); // opcional: ya fue penalizado
						Platform.runLater(() -> actualizarManoJugador());
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
					break; // por si queremos detener el hilo más adelante
				}
			}
		}).start();//Inicia el hilo
	}

	/**
	 * Muestra la ventana para seleccionar color cuando se juega una carta comodín.
	 * @param esJugadorHumano Indica si es el jugador humano quien está seleccionando el color
	 */
	private void mostrarSeleccionColor(boolean esJugadorHumano) {
		try {

			WildView wildView = WildView.getInstance();
			wildView.showAndWait();
			WildController wildController = wildView.getController();
			JuegoModel.Color nuevoColor = wildController.getColorSeleccionado();

			if (nuevoColor != null) {
				cartaEnMesa.setColor(nuevoColor);
				actualizarCartaEnMesa();
				actualizarManoJugador();

				if (esJugadorHumano) {
					jugador.setTurno(false);
					turnoCPU();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Hace que un jugador robe cartas del mazo.
	 * @param jugadorObjetivo El jugador que robará las cartas
	 * @param cantidad La cantidad de cartas a robar
	 */
	private void robarCartas(JugadorModel jugadorObjetivo, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			if (!mazo.isEmpty()) {
				JuegoModel.Carta carta = mazo.remove(0);
				carta.setEstado(JuegoModel.Estado.MANO);
				jugadorObjetivo.Mano.add(carta);
			}
		}
	}

	/**
	 * Actualiza la imagen del botón de música según su estado.
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
			imageView.setFitWidth(90);
			imageView.setFitHeight(90);
			btnSonido.setGraphic(imageView);
		}
	}

	/**
	 * Alterna el estado de la música y actualiza el botón.
	 * @param event El evento de acción
	 */
	@FXML
	private void musica(ActionEvent event) {
		MusicModel.getInstance().toggleMusic();
		actualizarBotonMusica();
	}

	/**
	 * Abre la vista del tutorial.
	 * @param event El evento de acción
	 * @throws IOException Si ocurre un error al cargar la vista
	 */
	@FXML
	private void help(ActionEvent event) throws IOException {
		try {
			TutorialView tutorialView = TutorialView.getInstance();
			tutorialView.show(); // Muestra la vista del tutorial
		} catch (IOException e) {
			System.err.println("Error al abrir el tutorial: " + e.getMessage());
		}
	}
}
