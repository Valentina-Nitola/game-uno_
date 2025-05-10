package com.example.gameuno.model;

import java.util.ArrayList;
import java.util.Random;

public class JuegoModel {

	public enum Color {red, yellow, blue, green, Negro}
	public enum Tipo {NUMERO, MASDOS, MASCUATRO, SALTO, COMODIN}
	public enum Estado {MANO, JUGADA, MAZO}

	public static class Carta {
		private Color color;
		private Tipo tipo;
		private Estado estado;
		private boolean oculta;
		private int numero;

		public Carta(String color, String tipo, String estado) {
			this.color = Color.valueOf(color.toUpperCase());
			this.tipo = Tipo.valueOf(tipo.toUpperCase());
			this.estado = Estado.valueOf(estado.toUpperCase());
			this.oculta = true;
			this.numero = -1;
		}

		public Color getColor() { return color; }
		public Tipo getTipo() { return tipo; }
		public Estado getEstado() { return estado; }
		public boolean isOculta() { return oculta; }
		public int getNumero() { return numero; }

		public void setEstado(Estado estado) { this.estado = estado; }
		public void setOculta(boolean oculta) { this.oculta = oculta; }
		public void setNumero(int numero) { this.numero = numero; }

		public String getNombreImagen() {
			return switch (tipo) {
				case NUMERO -> numero + "_" + color.toString().toLowerCase();
				case MASDOS -> "2_wild_draw_" + color.toString().toLowerCase();
				case MASCUATRO -> "4_wild_draw";
				case SALTO -> "skip_" + color.toString().toLowerCase();
				case COMODIN -> "wild";
			} + ".png";
		}
	}

	public ArrayList<Carta> crearCartas() {
		ArrayList<Carta> cartas = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			for (Color color : new Color[]{Color.red, Color.green, Color.yellow, Color.blue}) {
				Carta carta = new Carta(color.toString(), "Numero", "Mazo");
				carta.setNumero(i);
				cartas.add(carta);
			}
		}

		for (int i = 0; i < 4; i++) {
			cartas.add(new Carta("Negro", "MasCuatro", "Mazo"));
			cartas.add(new Carta("Negro", "Comodin", "Mazo"));
		}

		for (Color color : new Color[]{Color.red, Color.green, Color.yellow, Color.blue}) {
			cartas.add(new Carta(color.toString(), "Salto", "Mazo"));
		}

		cartas.add(new Carta("Negro", "MasDos", "Mazo"));
		cartas.add(new Carta("Negro", "MasDos", "Mazo"));

		return cartas;
	}

	public static void barajar(ArrayList<Carta> cartas) {
		Random rand = new Random();
		for (int i = cartas.size() - 1; i > 0; i--) {
			int j = rand.nextInt(i + 1);
			Carta temp = cartas.get(i);
			cartas.set(i, cartas.get(j));
			cartas.set(j, temp);
		}
	}
}
