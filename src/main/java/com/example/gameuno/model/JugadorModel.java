package com.example.gameuno.model;

import java.util.ArrayList;

/**
 * Clase que representa a un jugador en el juego UNO.
 * Gestiona las cartas en mano, el estado del turno y
 * la condición de haber gritado "UNO".
 *
 * @version 1.1.3
 */
public class JugadorModel {

    // Atributos

    /**
     * Lista de cartas que el jugador tiene en su mano
     */
    public ArrayList<JuegoModel.Carta> Mano;

    /**
     * Nombre del jugador
     */
    private String nombre;

    /**
     * Indica si es el turno del jugador
     */
    private boolean turno;

    /**
     * Indica si el jugador ha gritado "UNO" (cuando tiene 1 carta)
     */
    private boolean Uno = false;

    /**
     * Constructor que inicializa la mano del jugador como lista vacía.
     */
    public JugadorModel() {
        this.Mano = new ArrayList<>();
    }

    // Métodos de acceso (getters)

    /**
     * Obtiene el nombre del jugador
     * @return Nombre del jugador
     */
    public String getNombre() { return nombre; }

    /**
     * Verifica si es el turno del jugador
     * @return true si es su turno, false si no
     */
    public boolean isTurno() { return turno; }

    /**
     * Obtiene las cartas en mano del jugador
     * @return ArrayList de cartas en mano
     */
    public ArrayList<JuegoModel.Carta> getMano() { return Mano; }

    /**
     * Verifica si el jugador ha gritado UNO
     * @return true si gritó UNO, false si no
     */
    public boolean getUno() { return Uno; }

    // Métodos de modificación (setters)

    /**
     * Establece el nombre del jugador
     * @param nombre Nuevo nombre del jugador
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Establece el estado del turno del jugador
     * @param turno true para asignar turno, false para quitarlo
     */
    public void setTurno(boolean turno) { this.turno = turno; }

    /**
     * Establece si el jugador ha gritado UNO
     * @param Uno true si gritó UNO, false si no
     */
    public void setUno(boolean Uno) { this.Uno = Uno; }

    // Métodos de funcionalidad

    /**
     * Reparte las cartas iniciales al jugador (5 cartas).
     * Toma cartas del mazo principal y las añade a la mano del jugador.
     *
     * @param mazo Mazo de cartas del que se reparten
     */
    public void repartirCartasIniciales(ArrayList<JuegoModel.Carta> mazo) {
        for (int i = 0; i < 5; i++) {
            if (!mazo.isEmpty()) {
                JuegoModel.Carta carta = mazo.remove(0); // Toma la primera carta
                carta.setEstado(JuegoModel.Estado.MANO); // Cambia estado a "en mano"
                this.Mano.add(carta); // Añade a la mano del jugador
            }
        }
    }

    /**
     * Valida si el nombre del jugador es válido.
     * Un nombre válido no es nulo y no está vacío o solo contiene espacios.
     *
     * @return true si el nombre es válido, false en caso contrario
     */
    public boolean isValid() {
        return nombre != null && !nombre.trim().isEmpty();
    }
}