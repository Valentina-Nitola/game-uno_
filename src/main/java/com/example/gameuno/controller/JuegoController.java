package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class JuegoController {
	
	@FXML private Label lblJugadorActual;
	@FXML private Label lblCartaMesa;
	@FXML private HBox contenedorManoJugador;
	@FXML private VBox contenedorJuego;
	
	private ArrayList<Carta> mazo;
	private ArrayList<Carta> manoJugador;
	private Carta cartaEnMesa;
	
	@FXML
	public void initialize() {
		iniciarPartida();
	}
	
	private void iniciarPartida() {
		JuegoModel modelo = new JuegoModel();
		mazo = modelo.CrearCartas();
		JuegoModel.Barajar(mazo);
		
		manoJugador = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			if (!mazo.isEmpty()) {
				Carta carta = mazo.remove(0);
				carta.setEstado(JuegoModel.Estado.MANO);
				manoJugador.add(carta);
			}
		}
		
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
	
	private void actualizarManoJugador() {
		contenedorManoJugador.getChildren().clear();
		
		for (Carta carta : manoJugador) {
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
		if (esJugadaValida(cartaSeleccionada)) {
			manoJugador.remove(cartaSeleccionada);
			cartaEnMesa = cartaSeleccionada;
			cartaEnMesa.setEstado(JuegoModel.Estado.JUGADA);
			actualizarCartaEnMesa();
			actualizarManoJugador();
			
			if (manoJugador.isEmpty()) {
				lblJugadorActual.setText("¡Ganaste!");
			}
		} else {
			lblJugadorActual.setText("Jugada no válida");
		}
	}
	
	private boolean esJugadaValida(Carta carta) {
		return carta.getColor() == cartaEnMesa.getColor() ||
				(carta.getTipo() == JuegoModel.Tipo.NUMERO &&
						carta.getNumero() == cartaEnMesa.getNumero()) ||
				carta.getColor() == JuegoModel.Color.NEGRO;
	}
	
	@FXML
	private void tomarCartaDelMazo() {
		if (!mazo.isEmpty()) {
			Carta nuevaCarta = mazo.remove(0);
			nuevaCarta.setEstado(JuegoModel.Estado.MANO);
			manoJugador.add(nuevaCarta);
			actualizarManoJugador();
		}
	}
}