package com.example.gameuno.model;

import java.util.ArrayList;
import java.util.Random;


public class JuegoModel {

	//Constantes
	public enum Color {_red, _yellow, _blue, _green, negro}
	public enum Tipo {numero, masdos, mascuatro, skip, comodin}
	public enum Estado {MANO, JUGADA, MAZO}

	//Clase
	public static class Carta {
		//Atributos - variables
		private Color color;
		private Tipo tipo;
		private Estado estado;
		private boolean oculta = false;
		private int numero;
		public String ruta = "/com/example/gameuno/img/card_uno.png";
		public String reverso = "/com/example/gameuno/img/card_uno.png";

		//Constructor - this.var = var
		public Carta(String color, String tipo, String estado) {
			this.color = Color.valueOf(color.toLowerCase());
			this.tipo = Tipo.valueOf(tipo.toLowerCase());
			this.estado = Estado.valueOf(estado.toUpperCase());
			this.oculta = true;
			this.numero = 10; //Simboliza la compatibilidad de los comodines, y del 0-9 en cartas con numero
		}
		//Getters
		public Color getColor() {return color;}
		public Tipo getTipo() {return tipo;}
		public Estado getEstado() {return estado;}
		public boolean isOculta() {return oculta;}
		public int getNumero() {return numero;}
		//Setters
		public void setEstado(Estado estado) {this.estado = estado;}
		public void setOculta(boolean oculta) {this.oculta = oculta;}
		public void setNumero(int numero) {this.numero = numero;}
		public void setColor(Color nuevoColor) {this.color = nuevoColor;}
	}
	//Metodos - Funciones

	//Creamos todas las cartas
	public ArrayList<Carta> CrearCartas() {
		ArrayList<Carta> NuevasCartas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			NuevasCartas.add(new Carta("_red", "Numero", "Mazo"));
			NuevasCartas.getLast().setNumero(i);
			NuevasCartas.add(new Carta("_green", "Numero", "Mazo"));
			NuevasCartas.getLast().setNumero(i);
			NuevasCartas.add(new Carta("_yellow", "Numero", "Mazo"));
			NuevasCartas.getLast().setNumero(i);
			NuevasCartas.add(new Carta("_blue", "Numero", "Mazo"));
			NuevasCartas.getLast().setNumero(i);
		}
		for (int i = 0; i < 4; i++) {
			NuevasCartas.add(new Carta("Negro", "MasCuatro", "Mazo"));
			NuevasCartas.add(new Carta("Negro", "Comodin", "Mazo"));
		}
		NuevasCartas.add(new Carta("_red", "skip", "Mazo"));
		NuevasCartas.add(new Carta("_green", "skip", "Mazo"));
		NuevasCartas.add(new Carta("_yellow", "skip", "Mazo"));
		NuevasCartas.add(new Carta("_blue", "skip", "Mazo"));
		NuevasCartas.add(new Carta("_red", "MasDos", "Mazo"));
		NuevasCartas.add(new Carta("_green", "MasDos", "Mazo"));
		NuevasCartas.add(new Carta("_yellow", "MasDos", "Mazo"));
		NuevasCartas.add(new Carta("_blue", "MasDos", "Mazo"));

		//Ruta de la imagen de las cartas
		for (int i = 0; i < NuevasCartas.size(); i++) {
			if (NuevasCartas.get(i).getTipo().equals(Tipo.numero))
				NuevasCartas.get(i).ruta = "/com/example/gameuno/img/"
						+ NuevasCartas.get(i).getNumero() + NuevasCartas.get(i).getColor() + ".png";

			if (NuevasCartas.get(i).getTipo().equals(Tipo.masdos) || NuevasCartas.get(i).getTipo().equals(Tipo.skip))
				NuevasCartas.get(i).ruta = "/com/example/gameuno/img/" +
						NuevasCartas.get(i).getTipo() + NuevasCartas.get(i).getColor() + ".png";

			if (NuevasCartas.get(i).getTipo().equals(Tipo.comodin) || NuevasCartas.get(i).getTipo().equals(Tipo.mascuatro))
				NuevasCartas.get(i).ruta = "/com/example/gameuno/img/" + NuevasCartas.get(i).getTipo() + ".png";

		}
		return NuevasCartas;
	}


	//Crear algoritmo para barajar
	//Algoritmo de Fisher-Yates
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

	public static void ContadorCartas(ArrayList<Carta> Cartas) {
		int contador = 0;
		for (int i = 0; i < Cartas.size(); i++) {
			contador++;
		}
		System.out.println("Hay " + contador + " cartas");
		for (int i = 0; i < Cartas.size(); i++) {
			System.out.println(Cartas.get(i).ruta);
		}
	}
}