package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JugadorModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import com.example.gameuno.view.WildView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Random;

public class JuegoController {
	
	@FXML private Label nicknameLabel;
	@FXML private Label lblJugadorActual;
	@FXML private Label lblCartaMesa;
	@FXML private HBox contenedorManoJugador;
	@FXML private VBox contenedorJuego;
	
	private ArrayList<Carta> mazo;
	private Carta cartaEnMesa;
	
	// Instancia de los jugadores
	JugadorModel jugador = new JugadorModel();
	JugadorModel Cpu = new JugadorModel();
	
	private boolean esTurnoJugador = true;
	
	@FXML
	public void initialize() {
		iniciarPartida();
	}
	
	public void iniciarPartida() {
		JuegoModel modelo = new JuegoModel();
		mazo = modelo.CrearCartas();
		JuegoModel.Barajar(mazo);
		
		jugador.repartirCartasIniciales(mazo);
		Cpu.repartirCartasIniciales(mazo);
		
		if (!mazo.isEmpty()) {
			cartaEnMesa = mazo.remove(0);
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
		}
		
		actualizarManoJugador();
	}
	
	private void actualizarCartaEnMesa() {
		if (cartaEnMesa != null) {
			String textoCarta = cartaEnMesa.getColor().toString() + " " +
					(cartaEnMesa.getTipo() == JuegoModel.Tipo.NUMERO ?
							cartaEnMesa.getNumero() : cartaEnMesa.getTipo().toString());
			
			lblCartaMesa.setText(textoCarta);
		}
	}
	
	public void actualizarManoJugador() {
		contenedorManoJugador.getChildren().clear();
		
		for (Carta carta : jugador.Mano) {
			Label lblCarta = new Label();
			
			String textoCarta = carta.getColor().toString() + " " +
					(carta.getTipo() == JuegoModel.Tipo.NUMERO ?
							carta.getNumero() : carta.getTipo().toString());
			
			lblCarta.setText(textoCarta);
			lblCarta.setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-margin: 5px;");
			lblCarta.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			contenedorManoJugador.getChildren().add(lblCarta);
		}
	}
	
	private void manejarSeleccionCarta(Carta cartaSeleccionada) {
		if (!esTurnoJugador) return;
		
		if (esJugadaValida(cartaSeleccionada)) {
			jugador.Mano.remove(cartaSeleccionada);
			cartaEnMesa = cartaSeleccionada;
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			
			
			actualizarCartaEnMesa();
			actualizarManoJugador();
			
			// Carta SKIP
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.SKIP) {
				lblJugadorActual.setText("¡La CPU pierde su turno!");
				actualizarCartaEnMesa();
				actualizarManoJugador();
				esTurnoJugador = true; // El jugador juega de nuevo
				return;
			}
			
			// Carta +2 (MASDOS)
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.MASDOS) {
				robarCartas(Cpu, 2);
				lblJugadorActual.setText("CPU roba 2 cartas");
				actualizarCartaEnMesa();
				actualizarManoJugador();
				esTurnoJugador = true; // jugador conserva turno
				return;
			}
			// Carta +4 (MASCUATRO)
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.MASCUATRO) {
				robarCartas(Cpu, 4);
				lblJugadorActual.setText("CPU roba 4 cartas");
				mostrarSeleccionColor(true); // Selección de color del jugador
				return;
			}
			// Carta COMODIN
			if (cartaSeleccionada.getTipo() == JuegoModel.Tipo.COMODIN) {
				mostrarSeleccionColor(true); // Selección de color del jugador
				return;
			}
			
			if (jugador.Mano.isEmpty()) {
				lblJugadorActual.setText("¡Ganaste!");
				return;
			}
			
			esTurnoJugador = false;
			turnoCPU();
		} else {
			lblJugadorActual.setText("Jugada no válida");
		}
	}
	
	private boolean esJugadaValida(Carta carta) {
		return carta.getColor() == cartaEnMesa.getColor() ||
				(carta.getTipo() == JuegoModel.Tipo.NUMERO && carta.getNumero() == cartaEnMesa.getNumero()) ||
				carta.getColor() == JuegoModel.Color.NEGRO;
	}
	
	@FXML
	private void tomarCartaDelMazo() {
		if (!mazo.isEmpty() && esTurnoJugador) {
			Carta nuevaCarta = mazo.remove(0);
			nuevaCarta.setEstado(JuegoModel.Estado.MANO);
			jugador.Mano.add(nuevaCarta);
			actualizarManoJugador();
			
			lblJugadorActual.setText("Tomaste una carta. Turno de la CPU.");
			esTurnoJugador = false;
			turnoCPU(); // Turno automatico de la CPU
		}
	}
	
	public void setPlayer(JugadorModel player) {
		nicknameLabel.setText(player.getNombre());
	}
	
	// HILO AQUIIIIIIIIIIIIIIIIIIIIIII -->
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
				if (cartaJugada.getTipo() == JuegoModel.Tipo.SKIP) {
					Platform.runLater(() -> {
						lblJugadorActual.setText("¡Tu turno fue saltado!");
						actualizarCartaEnMesa();
						esTurnoJugador = false;
						turnoCPU(); // La CPU vuelve a jugar
					});
					return;
				}
				
				// Efecto MASDOS (+2)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.MASDOS) {
					Platform.runLater(() -> {
						robarCartas(jugador, 2);
						actualizarManoJugador();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("¡Has robado 2 cartas!");
						}
						esTurnoJugador = true;
						lblJugadorActual.setText("Tu turno");
					});
					return;
				}
				
				// Efecto MASCUATRO (+4)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.MASCUATRO) {
					Platform.runLater(() -> {
						robarCartas(jugador, 4);
						actualizarManoJugador();
						if (lblJugadorActual != null) {
							lblJugadorActual.setText("Has robado 4 cartas");
						}
						
						// CPU elige color aleatorio
						JuegoModel.Color[] coloresDisponibles = {
								JuegoModel.Color._RED,
								JuegoModel.Color._BLUE,
								JuegoModel.Color._GREEN,
								JuegoModel.Color._YELLOW
						};
						JuegoModel.Color colorAleatorio = coloresDisponibles[new Random().nextInt(coloresDisponibles.length)];
						cartaEnMesa.setColor(colorAleatorio);
						actualizarCartaEnMesa();
						System.out.println("CPU cambio el color a: " + colorAleatorio);
						esTurnoJugador = true;
						lblJugadorActual.setText("Tu turno");
					});
					return;
				}
				
				// Efecto COMODIN (solo cambio de color)
				if (cartaJugada.getTipo() == JuegoModel.Tipo.COMODIN) {
					JuegoModel.Color[] coloresDisponibles = {
							JuegoModel.Color._RED,
							JuegoModel.Color._BLUE,
							JuegoModel.Color._YELLOW,
							JuegoModel.Color._GREEN
					};
					JuegoModel.Color colorAleatorio = coloresDisponibles[new Random().nextInt(coloresDisponibles.length)];
					cartaJugada.setColor(colorAleatorio);
					System.out.println("CPU cambió el color a: " + colorAleatorio);
				}
				
				System.out.println("CPU juega: " + cartaJugada.getColor() + " " +
						(cartaJugada.getTipo() == JuegoModel.Tipo.NUMERO ?
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
			
			esTurnoJugador = true;
			
			Platform.runLater(() -> {
				actualizarCartaEnMesa();
				lblJugadorActual.setText("Tu turno");
			});
		}).start();
	}
	
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
					esTurnoJugador = false;
					turnoCPU();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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

