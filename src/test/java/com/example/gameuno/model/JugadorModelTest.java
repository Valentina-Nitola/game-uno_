package com.example.gameuno.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class JugadorModelTest {

    private JugadorModel jugador;
    private ArrayList<JuegoModel.Carta> mazoPrueba;

    @BeforeEach
    void setUp() {
        jugador = new JugadorModel();
        mazoPrueba = crearMazoPrueba();
    }

    private ArrayList<JuegoModel.Carta> crearMazoPrueba() {
        ArrayList<JuegoModel.Carta> mazo = new ArrayList<>();
        // Añadimos algunas cartas de prueba
        mazo.add(new JuegoModel.Carta("_red", "numero", "MAZO"));
        mazo.add(new JuegoModel.Carta("_yellow", "numero", "MAZO"));
        mazo.add(new JuegoModel.Carta("_blue", "numero", "MAZO"));
        mazo.add(new JuegoModel.Carta("_green", "numero", "MAZO"));
        mazo.add(new JuegoModel.Carta("negro", "comodin", "MAZO"));
        mazo.add(new JuegoModel.Carta("_red", "masdos", "MAZO"));
        mazo.add(new JuegoModel.Carta("_yellow", "skip", "MAZO"));
        mazo.add(new JuegoModel.Carta("negro", "mascuatro", "MAZO"));
        mazo.add(new JuegoModel.Carta("_blue", "numero", "MAZO"));
        mazo.add(new JuegoModel.Carta("_green", "masdos", "MAZO"));

        // Establecemos números para las cartas numéricas
        for(int i = 0; i < mazo.size(); i++) {
            JuegoModel.Carta carta = mazo.get(i);
            if(carta.getTipo() == JuegoModel.Tipo.numero) {
                carta.setNumero(i % 10); // Números del 0 al 9
            }
        }

        return mazo;
    }

    @Test
    void getNombre_DeberiaRetornarNullInicialmente() {
        assertNull(jugador.getNombre());
    }

    @Test
    void getNombre_DeberiaRetornarNombreEstablecido() {
        jugador.setNombre("Jugador1");
        assertEquals("Jugador1", jugador.getNombre());
    }

    @Test
    void isTurno_DeberiaRetornarFalseInicialmente() {
        assertFalse(jugador.isTurno());
    }

    @Test
    void isTurno_DeberiaRetornarTrueCuandoSeEstablece() {
        jugador.setTurno(true);
        assertTrue(jugador.isTurno());
    }

    @Test
    void getMano_DeberiaRetornarListaVaciaInicialmente() {
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void getUno_DeberiaRetornarFalseInicialmente() {
        assertFalse(jugador.getUno());
    }

    @Test
    void getUno_DeberiaRetornarTrueCuandoSeEstablece() {
        jugador.setUno(true);
        assertTrue(jugador.getUno());
    }

    @Test
    void setNombre_DeberiaCambiarElNombreCorrectamente() {
        jugador.setNombre("NuevoNombre");
        assertEquals("NuevoNombre", jugador.getNombre());
    }

    @Test
    void setTurno_DeberiaCambiarElEstadoDelTurno() {
        jugador.setTurno(true);
        assertTrue(jugador.isTurno());

        jugador.setTurno(false);
        assertFalse(jugador.isTurno());
    }

    @Test
    void setUno_DeberiaCambiarElEstadoUno() {
        jugador.setUno(true);
        assertTrue(jugador.getUno());

        jugador.setUno(false);
        assertFalse(jugador.getUno());
    }

    @Test
    void repartirCartasIniciales_DeberiaDar5CartasAlJugador() {
        jugador.repartirCartasIniciales(mazoPrueba);
        assertEquals(5, jugador.getMano().size());
    }

    @Test
    void repartirCartasIniciales_DeberiaReducirElMazoEn5Cartas() {
        int tamañoInicial = mazoPrueba.size();
        jugador.repartirCartasIniciales(mazoPrueba);
        assertEquals(tamañoInicial - 5, mazoPrueba.size());
    }

    @Test
    void repartirCartasIniciales_DeberiaCambiarEstadoDeCartasAMANO() {
        jugador.repartirCartasIniciales(mazoPrueba);
        for (JuegoModel.Carta carta : jugador.getMano()) {
            assertEquals(JuegoModel.Estado.MANO, carta.getEstado());
        }
    }

    @Test
    void repartirCartasIniciales_ConMazoVacio_NoDeberiaHacerNada() {
        ArrayList<JuegoModel.Carta> mazoVacio = new ArrayList<>();
        jugador.repartirCartasIniciales(mazoVacio);
        assertTrue(jugador.getMano().isEmpty());
    }

    @Test
    void isValid_DeberiaRetornarFalseCuandoNombreEsNull() {
        assertFalse(jugador.isValid());
    }

    @Test
    void isValid_DeberiaRetornarFalseCuandoNombreEstaVacio() {
        jugador.setNombre("");
        assertFalse(jugador.isValid());
    }

    @Test
    void isValid_DeberiaRetornarFalseCuandoNombreSoloTieneEspacios() {
        jugador.setNombre("   ");
        assertFalse(jugador.isValid());
    }

    @Test
    void isValid_DeberiaRetornarTrueCuandoNombreEsValido() {
        jugador.setNombre("Jugador Válido");
        assertTrue(jugador.isValid());
    }
}