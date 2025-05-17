package com.example.gameuno.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Clase que representa el modelo del juego UNO.
 * Contiene la lógica para crear y manejar las cartas del juego,
 * incluyendo su creación, barajado y gestión de estados.
 *
 * @version 1.1.3
 */
public class JuegoModel {

	/**
	 * Enumeración de los posibles colores de las cartas UNO.
	 * Incluye los colores estándar y el color negro para comodines.
	 */
	public enum Color {_red, _yellow, _blue, _green, negro}

	/**
	 * Enumeración de los tipos de cartas UNO.
	 */
	public enum Tipo {numero, masdos, mascuatro, skip, comodin}

	/**
	 * Enumeración de los posibles estados de una carta.
	 */
	public enum Estado {MANO, JUGADA, MAZO}

	/**
	 * Clase interna que representa una carta del juego UNO.
	 * Contiene todos los atributos y comportamientos de una carta individual.
	 */
	public static class Carta {
		// Atributos
		private Color color;        // Color de la carta
		private Tipo tipo;          // Tipo de carta (número, +2, etc.)
		private Estado estado;      // Estado actual (en mano, jugada, etc.)
		private boolean oculta;     // Si la carta está boca abajo
		private int numero;         // Número de la carta (si aplica)
		public String ruta;        // Ruta de la imagen frontal
		public String reverso;      // Ruta de la imagen trasera

		/**
		 * Constructor de una carta.
		 *
		 * @param color Color de la carta como string
		 * @param tipo Tipo de carta como string
		 * @param estado Estado inicial de la carta como string
		 */
		public Carta(String color, String tipo, String estado) {
			this.color = Color.valueOf(color.toLowerCase());
			this.tipo = Tipo.valueOf(tipo.toLowerCase());
			this.estado = Estado.valueOf(estado.toUpperCase());
			this.oculta = true;
			this.numero = 10; // Valor inicial para compatibilidad
			this.ruta = "/com/example/gameuno/img/card_uno.png";
			this.reverso = "/com/example/gameuno/img/card_uno.png";
		}

		// Métodos de acceso (getters)
		public Color getColor() { return color; }
		public Tipo getTipo() { return tipo; }
		public Estado getEstado() { return estado; }
		public boolean isOculta() { return oculta; }
		public int getNumero() { return numero; }

		// Métodos de modificación (setters)
		public void setEstado(Estado estado) { this.estado = estado; }
		public void setOculta(boolean oculta) { this.oculta = oculta; }
		public void setNumero(int numero) { this.numero = numero; }
		public void setColor(Color nuevoColor) { this.color = nuevoColor; }
	}

	/**
	 * Crea y devuelve un mazo completo de cartas UNO.
	 * Incluye cartas numéricas, de acción y comodines.
	 *
	 * @return ArrayList<Carta> con todas las cartas del juego
	 */
	public ArrayList<Carta> CrearCartas() {
		ArrayList<Carta> NuevasCartas = new ArrayList<>();

		// Crear cartas numéricas (0-9) para cada color
		for (int i = 0; i < 10; i++) {
			for (Color color : new Color[]{Color._red, Color._green, Color._yellow, Color._blue}) {
				if (color != Color.negro) { // Los comodines no tienen números
					Carta carta = new Carta(color.name(), "Numero", "Mazo");
					carta.setNumero(i);
					NuevasCartas.add(carta);
				}
			}
		}

		// Crear cartas especiales
		// Comodines (+4 y comodín normal)
		for (int i = 0; i < 4; i++) {
			NuevasCartas.add(new Carta("Negro", "MasCuatro", "Mazo"));
			NuevasCartas.add(new Carta("Negro", "Comodin", "Mazo"));
		}

		// Cartas Skip y +2 para cada color
		for (Color color : new Color[]{Color._red, Color._green, Color._yellow, Color._blue}) {
			NuevasCartas.add(new Carta(color.name(), "skip", "Mazo"));
			NuevasCartas.add(new Carta(color.name(), "MasDos", "Mazo"));
		}

		// Asignar rutas de imágenes a cada carta
		for (Carta carta : NuevasCartas) {
			if (carta.getTipo().equals(Tipo.numero)) {
				carta.ruta = "/com/example/gameuno/img/" + carta.getNumero() + carta.getColor() + ".png";
			} else if (carta.getTipo().equals(Tipo.masdos) || carta.getTipo().equals(Tipo.skip)) {
				carta.ruta = "/com/example/gameuno/img/" + carta.getTipo() + carta.getColor() + ".png";
			} else if (carta.getTipo().equals(Tipo.comodin) || carta.getTipo().equals(Tipo.mascuatro)) {
				carta.ruta = "/com/example/gameuno/img/" + carta.getTipo() + ".png";
			}
		}

		return NuevasCartas;
	}

	/**
	 * Baraja un mazo de cartas usando el algoritmo Fisher-Yates.
	 *
	 * @param Cartas ArrayList<Carta> a barajar
	 */
	public static void Barajar(ArrayList<Carta> Cartas) {
		Random rand = new Random();
		for (int i = Cartas.size() - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1); // índice aleatorio entre 0 e i
			// Intercambiar cartas[i] y cartas[j]
			Carta temp = Cartas.get(i);
			Cartas.set(i, Cartas.get(j));
			Cartas.set(j, temp);
		}
	}

	/**
	 * Método de depuración que cuenta y muestra las cartas en el mazo.
	 *
	 * @param Cartas ArrayList<Carta> a contar
	 */
	public static void ContadorCartas(ArrayList<Carta> Cartas) {
		System.out.println("Hay " + Cartas.size() + " cartas");
		for (Carta carta : Cartas) {
			System.out.println(carta.ruta);
		}
	}
}