package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JugadorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.gameuno.view.WildView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class JuegoController {
	
	@FXML private Label nicknameLabel;
	@FXML private Label lblJugadorActual;
	@FXML private Label lblCartaMesa;
	@FXML private HBox contenedorManoJugador;
	@FXML private VBox contenedorJuego;
	@FXML private ImageView descarte;
	
	private ArrayList<Carta> mazo;
	private Carta cartaEnMesa;

	JugadorModel jugador = new JugadorModel(); // Instancia de los jugadores
	JugadorModel Cpu = new JugadorModel();

	@FXML
	public void initialize() { //Wtf rios??
		iniciarPartida();
	}
	
	public void iniciarPartida() {
		JuegoModel modelo = new JuegoModel(); //Se instancia el modelo, se crean las cartas y se barajan
		mazo = modelo.CrearCartas();
		JuegoModel.Barajar(mazo);
		jugador.repartirCartasIniciales(mazo); //Instanciamos el jugador y una cpu
		Cpu.repartirCartasIniciales(mazo);
		jugador.setTurno(true);

		if (!mazo.isEmpty()) { //Ponemos la carta inicial del mazo en la mesa
			cartaEnMesa = mazo.remove(0);
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			System.out.println(cartaEnMesa.getTipo());
			System.out.println(cartaEnMesa.getColor());
			System.out.println(cartaEnMesa.getNumero());

		}
		actualizarManoJugador();
	}


	private void actualizarCartaEnMesa() {

		if (cartaEnMesa != null) {
			//String textoCarta = cartaEnMesa.getColor().toString() + " " + (cartaEnMesa.getTipo() == JuegoModel.Tipo.numero ? cartaEnMesa.getNumero() : cartaEnMesa.getTipo().toString());
			//lblCartaMesa.setText(textoCarta);
			Image imagen = new Image(getClass().getResourceAsStream(cartaEnMesa.ruta));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(115); // Tamaño más grande para destacar la carta en mesa
			imagenView.setPreserveRatio(true);
			descarte.setImage(imagen);
			System.out.println(cartaEnMesa.ruta);

		}
	}


	public void actualizarManoJugador() {
		contenedorManoJugador.getChildren().clear();
		for (Carta carta : jugador.Mano) { //Recorre el arreglo de cartas del jugador

			//Label lblCarta = new Label();
			//String textoCarta = carta.getColor().toString() + " " + (carta.getTipo() == JuegoModel.Tipo.numero ? carta.getNumero() : carta.getTipo().toString());
			//lblCarta.setText(textoCarta);

			// Carga la imagen de la carta desde resources/imagenes/
			Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.ruta)));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(80);
			imagenView.setPreserveRatio(true);


			//lblCarta.setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-margin: 5px;");
			imagenView.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			//contenedorManoJugador.getChildren().add(lblCarta);
			contenedorManoJugador.getChildren().add(imagenView);
		}
	}


	private void manejarSeleccionCarta(Carta cartaSeleccionada) {
		if (!jugador.isTurno()) return;
		if (esJugadaValida(cartaSeleccionada)) { //Es valida la jugada?
			jugador.Mano.remove(cartaSeleccionada);
			cartaEnMesa = cartaSeleccionada; //Ponga la carta en la mesa pues
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			actualizarManoJugador();

			// Carta SKIP
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.skip) {
				lblJugadorActual.setText("¡La CPU pierde su turno!");
				jugador.setTurno(true); // El jugador juega de nuevo
				return;
			}

			// Carta +2 (MASDOS)
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.masdos) {
				robarCartas(Cpu, 2);
				lblJugadorActual.setText("CPU toma 2 cartas");
				return;
			}
			// Carta +4 (MASCUATRO)
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.mascuatro) {
				robarCartas(Cpu, 4);
				lblJugadorActual.setText("CPU roba 4 cartas");
				mostrarSeleccionColor(true); // Selección de color del jugador
				return;
			}
			// Carta COMODIN
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.comodin) {
				mostrarSeleccionColor(true); // Selección de color del jugador
				return;
			}
			//Si el jugador ya no tiene cartas
			if (jugador.Mano.isEmpty()) {
				lblJugadorActual.setText("¡Ganaste!");
				return;
			}
			jugador.setTurno(false);
			turnoCPU();
		} else {
			lblJugadorActual.setText("Jugada no válida");
		}
	}


	private boolean esJugadaValida(Carta carta) { //pide si coinciden las cartas del 0-9 o el color
		return carta.getColor() == cartaEnMesa.getColor() ||
				(carta.getTipo() == JuegoModel.Tipo.numero && carta.getNumero() == cartaEnMesa.getNumero()) ||
				carta.getColor() == JuegoModel.Color.negro;
	}


	@FXML
	private void tomarCartaDelMazo() { //Dice que si, hay cartas y es turno del jugador tome carta del mazo
		if (!mazo.isEmpty() && jugador.isTurno()) {
			Carta nuevaCarta = mazo.remove(0);
			nuevaCarta.setEstado(JuegoModel.Estado.MANO);
			jugador.Mano.add(nuevaCarta);
			actualizarManoJugador();
			lblJugadorActual.setText("Tomaste una carta. Turno de la CPU.");
			jugador.setTurno(false);
			turnoCPU(); // Turno automatico de la CPU


		}
	}
	
	public void setPlayer(JugadorModel player) {
		nicknameLabel.setText(player.getNombre());
	}
	
	// HILO AQUIIIIIIIIIIIIIIIIIIIIIII --> Dios rios esto es todo lo de la cpu
	private void turnoCPU() {
		System.out.println("Turno de la CPU...");

		new Thread(() -> {
			try {
				Thread.sleep(new Random().nextInt(2000) + 2000); // Simula 2 a 4 segundos
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Carta cartaJugada = null;

			for (Carta carta : Cpu.Mano) {
				if (esJugadaValida(carta)) {
					cartaJugada = carta;
					break;
				}
			}

			if (cartaJugada != null) {
				Cpu.Mano.remove(cartaJugada);
				cartaEnMesa = cartaJugada;
				cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);

				// Carta SKIP
				if (cartaJugada.getTipo() == JuegoModel.Tipo.skip) {
					Platform.runLater(() -> {
						lblJugadorActual.setText("¡Tu turno fue saltado!");
						actualizarCartaEnMesa();
						jugador.setTurno(false);
						turnoCPU(); // La CPU vuelve a jugar
					});
					return;
				}

				// Efecto MASDOS (+2)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.masdos) {
					Platform.runLater(() -> {
						robarCartas(jugador, 2);
						actualizarManoJugador();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("¡Has tomado 2 cartas!");
						}
						jugador.setTurno(true);
						lblJugadorActual.setText("Tu turno");
					});
					return;
				}

				// Efecto MASCUATRO (+4)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.mascuatro) {
					Platform.runLater(() -> {
						robarCartas(jugador, 4);
						actualizarManoJugador();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("Has tomado 4 cartas");
						}

						// CPU elige color aleatorio
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

				// Efecto COMODIN (solo cambio de color)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.comodin) {
					JuegoModel.Color[] coloresDisponibles = {
							JuegoModel.Color._red,
							JuegoModel.Color._blue,
							JuegoModel.Color._yellow,
							JuegoModel.Color._green
					};
					JuegoModel.Color colorAleatorio = coloresDisponibles[new Random().nextInt(coloresDisponibles.length)];
					cartaJugada.setColor(colorAleatorio);
					System.out.println("CPU cambió el color a: " + colorAleatorio);
				}

				System.out.println("CPU juega: " + cartaJugada.getColor() + " " +
						(cartaJugada.getTipo() == JuegoModel.Tipo.numero ?
								cartaJugada.getNumero() : cartaJugada.getTipo()));

				if (Cpu.Mano.size() == 1) {
					System.out.println("CPU dice: UNO");
				}

				if (Cpu.Mano.isEmpty()) {
					Platform.runLater(() -> lblJugadorActual.setText("La CPU ha ganado."));
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
				actualizarCartaEnMesa();
				lblJugadorActual.setText("Tu turno");
			});
		}).start();
	}

	//COMODIN
	private void mostrarSeleccionColor(boolean esJugadorHumano) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/gameuno/interfaces/wildview.fxml"));
			Parent root = loader.load();
			
			WildView wildView = loader.getController();
			
			Stage stage = new Stage();
			stage.setTitle("Selecciona un color");
			stage.setScene(new Scene(root));
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			
			JuegoModel.Color nuevoColor = wildView.getColorSeleccionado();
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

	//Basicamente puede robar cartas cuando quiera
	private void robarCartas(JugadorModel jugadorObjetivo, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			if (!mazo.isEmpty()) {
				JuegoModel.Carta carta = mazo.remove(0);
				carta.setEstado(JuegoModel.Estado.MANO);
				jugadorObjetivo.Mano.add(carta);
			}
		}
	}
}

