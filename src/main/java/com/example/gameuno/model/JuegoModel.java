package com.example.gameuno.model;

import java.util.ArrayList;
import java.util.Random;

public class JuegoModel {
	
	//Constantes
	public enum Color {_RED, _YELLOW, _BLUE, _GREEN, NEGRO}
	public enum Tipo {NUMERO, MASDOS, MASCUATRO, SKIP, COMODIN}
	public enum Estado {MANO, JUGADA, MAZO}
	
	//Clase
	public static class Carta {
		//Atributos - variables
		private Color color;
		private Tipo tipo;
		private Estado estado;
		private boolean oculta;
		private int numero;
		
		//Constructor - this.var = var
		public Carta(String color, String tipo, String estado) {
			this.color = Color.valueOf(color.toUpperCase());
			this.tipo = Tipo.valueOf(tipo.toUpperCase());
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
		public void setEstado(Estado estado) { this.estado = estado; }
		public void setOculta(boolean oculta) {this.oculta = oculta;}
		public void setNumero(int numero) {this.numero = numero;}
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
		for (int i=0; i < Cartas.size(); i++) {
			contador++;
		}
		System.out.println("Hay " + contador + " cartas");
	}


public static void CambioTurno(){

	}







}