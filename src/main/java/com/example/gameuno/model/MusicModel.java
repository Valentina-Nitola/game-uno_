package com.example.gameuno.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Clase que gestiona la reproducción de música de fondo en el juego Sudoku.
 * Utiliza el patrón Singleton para asegurar una única instancia y permite controlar la reproducción.
 * La música se reproduce en bucle indefinido.
 *
 * @author Valentina Nitola
 * @version 1.0.
 */
public class MusicModel {
    /**
     * Instancia única de la clase {@link MusicModel} (patrón Singleton).
     */
    private static MusicModel instance;

    /**
     * Reproductor de medios de JavaFX utilizado para reproducir la música.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Bandera que indica si la música está actualmente activa o pausada.
     */
    private boolean isMusicOn = true;

    /**
     * Constructor privado para evitar instanciación externa.
     * Llama al método {@code iniciarMusica()} para reproducir la música al crear la instancia.
     */
    private MusicModel() {
        iniciarMusica();
    }

    /**
     * Obtiene la instancia única de {@link MusicModel}.
     *
     * @return instancia única de la clase.
     */
    public static MusicModel getInstance() {
        if (instance == null) {
            instance = new MusicModel();
        }
        return instance;
    }

    /**
     * Inicia la reproducción de música cargando el archivo de sonido desde los recursos del proyecto.
     * La música se reproduce en bucle infinito.
     */
    private void iniciarMusica() {
        URL musicURL = getClass().getResource("/com/example/gameuno/sound/C418  - Sweden - Minecraft Volume Alpha.mp3");
        if (musicURL != null) {
            Media media = new Media(musicURL.toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else {
            System.out.println("No se encontró el archivo de música.");
        }
    }

    public void toggleMusic() {
        if (mediaPlayer == null) return;
        if (isMusicOn) {
            mediaPlayer.pause();
        } else {
            mediaPlayer.play();
        }
        isMusicOn = !isMusicOn;
    }

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isMusicOn = false;
        }
    }

    public boolean isMusicOn() {
        return isMusicOn;
    }
}
