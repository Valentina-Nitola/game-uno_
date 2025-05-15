package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JugadorModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
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
	//@FXML private Label lblCartaMesa;
	@FXML private HBox contenedorManoJugador;
	//@FXML private VBox contenedorJuego;
	@FXML private ImageView descarte;
	@FXML private HBox contenedorManoCpu; //Dios, quien le puso asi a la mesita del cpu xd
	//@FXML private ImageView CpuMesa;
	@FXML private Button UnoButton;

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
			Image imagen = new Image(getClass().getResourceAsStream(cartaEnMesa.ruta));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(125); // Tamaño más grande para destacar la carta en mesa
			imagenView.setPreserveRatio(true);
			descarte.setImage(imagen);
			System.out.println(cartaEnMesa.ruta);
		}
	}


	public void actualizarManoJugador() {

		contenedorManoJugador.getChildren().clear(); //Jugador
		contenedorManoCpu.getChildren().clear(); //Cpu

		for (Carta carta : jugador.Mano) { //Recorre el arreglo de cartas del jugador
			Image imagen = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.ruta)));
			ImageView imagenView = new ImageView(imagen);
			imagenView.setFitWidth(80);
			imagenView.setPreserveRatio(true); // Carga la imagen de la carta jugador
			imagenView.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			contenedorManoJugador.getChildren().add(imagenView);
		}

		for (Carta carta : Cpu.Mano) { //(cambiar "reverso" por "ruta" si se quiere ver que cartas tiene Cpu
			Image imagen2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(carta.reverso)));
			ImageView imagenView = new ImageView(imagen2);
			imagenView.setFitWidth(80);
			imagenView.setPreserveRatio(true);
			//imagenView.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			contenedorManoCpu.getChildren().add(imagenView);
		}
		if (jugador.Mano.size() == 1 || Cpu.Mano.size() == 1) {
			UnoButton.setDisable(false);
			UnoButton.setVisible(true);
		} else {
			UnoButton.setDisable(true);
			UnoButton.setVisible(false);
		}
		if (jugador.Mano.size() > 1) jugador.setUno(false);
		if (Cpu.Mano.size() > 1) Cpu.setUno(false);
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
				jugador.setTurno(false);
				actualizarManoJugador();
				actualizarCartaEnMesa();
				turnoCPU();
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
	private void tomarCartaDelMazo() { //Cuando se presiona el boton, evalua si se puede tomar la carta
		if (!mazo.isEmpty() && jugador.isTurno()) { //Si hay cartas y es tu turno
			int contador = 0;
			for (int i=0; i<jugador.getMano().size(); i++) { //Tienes jugadas?
				if (esJugadaValida(jugador.getMano().get(i))) {contador++;}
			}
			if (contador == 0) { //solo si, no tienes una carta jugable
				Carta nuevaCarta = mazo.remove(0);
				nuevaCarta.setEstado(JuegoModel.Estado.MANO);
				jugador.Mano.add(nuevaCarta);
				actualizarManoJugador();
				lblJugadorActual.setText("Tomaste una carta. Turno de la CPU.");
				jugador.setTurno(false);
				turnoCPU(); // Turno automatico de la CPU
			}
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

			for (Carta carta : Cpu.Mano) { //Busca una carta jugable en su mano
				if (esJugadaValida(carta)) {
					cartaJugada = carta;
					break;
				}
			}

			if (cartaJugada != null) { //Si encontro una carta jugable, la juega
				Cpu.Mano.remove(cartaJugada);
				cartaEnMesa = cartaJugada;
				cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);

				// Carta SKIP
				if (cartaJugada.getTipo() == JuegoModel.Tipo.skip) {
					Platform.runLater(() -> {
						lblJugadorActual.setText("¡Tu turno fue saltado!");
						actualizarCartaEnMesa();
						actualizarManoJugador();
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
						actualizarCartaEnMesa();
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
						actualizarCartaEnMesa();
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
					Platform.runLater(() -> {
						actualizarManoJugador();
						actualizarCartaEnMesa();
					});
					System.out.println("CPU cambió el color a: " + colorAleatorio);
				}

				System.out.println("CPU juega: " + cartaJugada.getColor() + " " +
						(cartaJugada.getTipo() == JuegoModel.Tipo.numero ?
								cartaJugada.getNumero() : cartaJugada.getTipo()));

				//Canta Uno Cpu
				if (Cpu.Mano.size() == 1) {
					System.out.println("CPU dice: UNO");
					Cpu.setUno(true);
					Platform.runLater(() -> actualizarManoJugador());
				}
				if (jugador.Mano.size() == 1 && !jugador.getUno()) {
					System.out.println("Cpu te canto UNOOOO");
					robarCartas(jugador, 1);
					jugador.setUno(false);
					Platform.runLater(() -> actualizarManoJugador());
				}

				if (Cpu.Mano.isEmpty()) {
					Platform.runLater(() -> {lblJugadorActual.setText("La CPU ha ganado.");
					actualizarCartaEnMesa();
					actualizarManoJugador();});
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
		UnoButton.setVisible(false);
	}

}