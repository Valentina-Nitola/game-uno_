package com.example.gameuno.model;

import java.util.ArrayList;

public class JugadorModel {
    public ArrayList<JuegoModel.Carta> Mano;

    //Clase
    public class Jugador {
        //Atributos - Variables
        private String nombre;
        private boolean turno;

        //Constructor - this.var = var
        public Jugador(String nombre, boolean turno) {
            this.nombre = nombre;
            this.turno = turno;
        }
        //Getters
        public String getNombre() {return nombre;}
        public boolean isTurno() {return turno;}
        //Setters
        public void setNombre(String nombre) {this.nombre = nombre;}
        public void setTurno(boolean turno) {this.turno = turno;}
    }
    //Metodos - Funciones









}
