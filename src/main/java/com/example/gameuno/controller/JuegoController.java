package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JugadorModel;
import com.example.gameuno.model.NamePlayerModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class JuegoController {


	@FXML private Label nicknameLabel;
	@FXML private Label lblJugadorActual;
	@FXML private Label lblCartaMesa;
	@FXML private HBox contenedorManoJugador;
	@FXML private VBox contenedorJuego;
	
	private ArrayList<Carta> mazo;
	private Carta cartaEnMesa;

	//Instancia de los jugadores
	private NamePlayerModel player;
	JugadorModel jugador = new JugadorModel();
	JugadorModel Cpu = new JugadorModel();

	public void setPlayer(NamePlayerModel player) {
		this.player = player;
	}

	public void showNicknameLabel() {
		nicknameLabel.setText(player.getNickname());
	}

	@FXML
	public void initialize() { //Wtf rios?
		iniciarPartida();
	}
	
	public void iniciarPartida() {
		JuegoModel modelo = new JuegoModel(); //instancia la clase modelo y crea las cartas y las baraja
		mazo = modelo.CrearCartas();
		JuegoModel.Barajar(mazo);


		jugador.Mano = new ArrayList<>(); //Cartas en la mano del jugador
		for (int i = 0; i < 5; i++) { //Agrega las 5 cartas bajo la condicion
			if (!mazo.isEmpty()) { //Si en el mazo se crearon las cartas y no esta vacio
				Carta carta = mazo.remove(0); //la quita del mazo
				carta.setEstado(JuegoModel.Estado.MANO); //se modifica el estado a mano del jugador
				jugador.Mano.add(carta); //Se agrega al arraylist de la mano del jugador
			}
		}
		if (!mazo.isEmpty()) { //Esto pone la carta en la mesa si el mazo no esta vacio
			cartaEnMesa = mazo.remove(0);
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
		}
		actualizarManoJugador();
	}


	private void actualizarCartaEnMesa() {
		if (cartaEnMesa != null) { //Si hay una carta en la mesa

			//Agarra el color y el tipo si es numero de la carta que esta en la mesa
			String textoCarta = cartaEnMesa.getColor().toString() + " " +
					(cartaEnMesa.getTipo() == JuegoModel.Tipo.NUMERO ?
							cartaEnMesa.getNumero() : cartaEnMesa.getTipo().toString());

			lblCartaMesa.setText(textoCarta); //Este muestra el texto en la pantalla
		}
	}

//Basicamente es lo que permite la interaccion de tocar las cartas que vamos a jugar
	public void actualizarManoJugador() {
		contenedorManoJugador.getChildren().clear();

		for (Carta carta : jugador.Mano) {
			Label lblCarta = new Label();

			String textoCarta = carta.getColor().toString() + " " +
					(carta.getTipo() == JuegoModel.Tipo.NUMERO ?
							carta.getNumero() : carta.getTipo().toString());

			lblCarta.setText(textoCarta);
			lblCarta.setStyle("-fx-border-color: black; -fx-padding: 10px; -fx-margin: 5px;");
			//Evento de interaccion del mouse con las cartas
			lblCarta.setOnMouseClicked(event -> manejarSeleccionCarta(carta));
			contenedorManoJugador.getChildren().add(lblCarta);
		}
	}

	//si la carta que se selecciono es valida la pone, sino no
	private void manejarSeleccionCarta(Carta cartaSeleccionada) {
		if (esJugadaValida(cartaSeleccionada)) {
			jugador.Mano.remove(cartaSeleccionada);
			cartaEnMesa = cartaSeleccionada;
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			actualizarManoJugador();
			
			if (jugador.Mano.isEmpty()) { //Si luego de eso se queda sin cartas - gana
				lblJugadorActual.setText("¡Ganaste!");
			}

		} else { //si no es valida la carta seleccionada
			lblJugadorActual.setText("Jugada no válida");

		}
	}


	private boolean esJugadaValida(Carta carta) {
		//devuelve que la carta tenga el mismo color o numero
		return carta.getColor() == cartaEnMesa.getColor() ||
				(carta.getTipo() == JuegoModel.Tipo.NUMERO && carta.getNumero() == cartaEnMesa.getNumero()) ||
				carta.getColor() == JuegoModel.Color.NEGRO;
	} //wtf rios por que?


	@FXML
	private void tomarCartaDelMazo() {
		if (!mazo.isEmpty()) {
			Carta nuevaCarta = mazo.remove(0);
			nuevaCarta.setEstado(JuegoModel.Estado.MANO);
			jugador.Mano.add(nuevaCarta);
			actualizarManoJugador();
		}
	}
}