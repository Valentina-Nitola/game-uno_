package com.example.gameuno.model;

import com.example.gameuno.model.JuegoModel.Carta;
import com.example.gameuno.model.JuegoModel.Color;
import com.example.gameuno.model.JuegoModel.Tipo;

import java.util.ArrayList;
import java.util.List;

public class CPUPlayer {
	
	private List<Carta> mano;
	private String nombre;
	
	public CPUPlayer(String nombre) {
		this.nombre = nombre;
		this.mano = new ArrayList<>();
	}
	
	public List<Carta> getMano() {
		return mano;
	}
	
	public void agregarCarta(Carta carta) {
		mano.add(carta);
	}
	
	public void eliminarCarta(Carta carta) {
		mano.remove(carta);
	}
	
	public boolean tieneCartasJugables(Carta cartaMesa) {
		for (Carta c : mano) {
			if (cartaEsJugable(c, cartaMesa)) {
				return true;
			}
		}
		return false;
	}
	
	public Carta seleccionarCartaParaJugar(Carta cartaMesa) {
		for (Carta c : mano) {
			if (cartaEsJugable(c, cartaMesa)) {
				return c;
			}
		}
		return null;
	}
	
	private boolean cartaEsJugable(Carta carta, Carta cartaMesa) {
		return carta.getColor() == cartaMesa.getColor() ||
				(carta.getTipo() == Tipo.NUMERO && carta.getNumero() == cartaMesa.getNumero()) ||
				carta.getColor() == Color.NEGRO;
	}
	
	public boolean debeDecirUNO() {
		return mano.size() == 1;
	}
	
	public boolean haGanado() {
		return mano.isEmpty();
	}
	
	public String getNombre() {
		return nombre;
	}
}
