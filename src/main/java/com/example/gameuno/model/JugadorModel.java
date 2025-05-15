package com.example.gameuno.model;
import java.util.ArrayList;

public class JugadorModel {

    //Atributos - Variables
    public ArrayList<JuegoModel.Carta> Mano;
    private String nombre;
    private boolean turno;
    private boolean Uno = false;

    //Constructor - this.var = var
    public JugadorModel() {
        this.Mano = new ArrayList<>();
    }

    //Getters
    public String getNombre() {return nombre;}
    public boolean isTurno() {return turno;}
    public ArrayList<JuegoModel.Carta> getMano() {return Mano;}
    public boolean getUno() {return Uno;}

    //Setters
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setTurno(boolean turno) {this.turno = turno;}
    public void setUno(boolean Uno) {this.Uno = Uno;}

//Metodos - Funciones

    //Metodo para dar cartas iniciales
    public void repartirCartasIniciales(ArrayList<JuegoModel.Carta> mazo) {
        for (int i = 0; i < 2; i++) { //Agrega las 5 cartas bajo la condicion
            if (!mazo.isEmpty()) { //Si en el mazo se crearon las cartas y no esta vacio
                JuegoModel.Carta carta = mazo.remove(0); //la quita del mazo
                carta.setEstado(JuegoModel.Estado.MANO); //se modifica el estado a mano del jugador
                this.Mano.add(carta); //Se agrega al arraylist de la mano del jugador
            }
        }
    }

    /**
     * Valida si el nickname es válido (no nulo ni vacío).
     *
     * @return true si el nickname es válido, false si está vacío o solo espacios.
     */

// Validar nickname - Implementado de la clase que creo valentina, pero resumida en el constructor propio
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty();
    }
}