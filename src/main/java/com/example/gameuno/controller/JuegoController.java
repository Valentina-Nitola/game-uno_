package com.example.gameuno.controller;

import com.example.gameuno.model.JuegoModel;
import com.example.gameuno.model.JuegoModel.Carta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	private ArrayList<Button> botonesCartas;

	@FXML
	public void initialize() {
		botonesCartas = new ArrayList<>();
		// Obtener los botones ya definidos en el FXML
		contenedorManoJugador.getChildren().forEach(nodo -> {
			if (nodo instanceof Button btn) {
				botonesCartas.add(btn);
			}
		});

		iniciarPartida();
	}

	private void iniciarPartida() {
		JuegoModel modelo = new JuegoModel();
		mazo = modelo.crearCartas();
		JuegoModel.barajar(mazo);

		manoJugador = new ArrayList<>();
		for (int i = 0; i < 7 && !mazo.isEmpty(); i++) {
			Carta carta = mazo.remove(0);
			carta.setEstado(JuegoModel.Estado.MANO);
			manoJugador.add(carta);
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
		for (int i = 0; i < botonesCartas.size(); i++) {
			Button boton = botonesCartas.get(i);

			if (i < manoJugador.size()) {
				Carta carta = manoJugador.get(i);
				String ruta = "/images/cartas/" + carta.getNombreImagen();  // Por ejemplo: "rojo_5.png"
				Image img = new Image(getClass().getResourceAsStream(ruta));

				ImageView vista = new ImageView(img);
				vista.setFitWidth(130);
				vista.setFitHeight(184);
				vista.setPreserveRatio(true);

				boton.setGraphic(vista);
				boton.setDisable(false);
				boton.setOnAction(e -> manejarSeleccionCarta(carta));
			} else {
				boton.setGraphic(null);
				boton.setDisable(true);
				boton.setOnAction(null);
			}
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
				carta.getColor() == JuegoModel.Color.Negro;
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
