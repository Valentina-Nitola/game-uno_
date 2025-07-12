package com.example.gameuno.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * La clase {@code MusicModel} es responsable de gestionar la reproducción de música de fondo en el juego.
 * Utiliza el patrón Singleton para garantizar que solo haya una instancia de la clase.
 * La música se reproduce de manera indefinida en bucle.
 * Esta clase implementa los controles para reproducir y pausar la música utilizando un adaptador a {@code MediaPlayer}.
 *
 * @author Valentina Nitola
 * @version 1.2.0
 */
public class MusicModel {

    /**
     * Instancia única de {@code MusicModel} (Patrón Singleton).
     * Garantiza que solo haya una instancia de la clase en toda la aplicación.
     */
    private static MusicModel instance;

    /**
     * La instancia de {@code MediaPlayerWrapper} que maneja la reproducción de música.
     * Esta interfaz es implementada por {@code MediaPlayerWrapperImpl}.
     */
    private MediaPlayerWrapper mediaPlayerWrapper;

    /**
     * Bandera que indica si la música está actualmente activa o pausada.
     * {@code true} significa que la música está en reproducción y {@code false} significa que está pausada.
     */
    private boolean isMusicOn = true;

    /**
     * Constructor privado para evitar la creación de instancias externas.
     * Inicializa la música cuando se crea la instancia de la clase.
     */
    private MusicModel() {
        iniciarMusica();
    }

    /**
     * Método que garantiza que solo haya una instancia de {@code MusicModel} en toda la aplicación.
     * Si la instancia no ha sido creada, la crea; si ya existe, devuelve la instancia existente.
     *
     * @return La única instancia de {@code MusicModel}.
     */
    public static MusicModel getInstance() {
        if (instance == null) {
            instance = new MusicModel();
        }
        return instance;
    }

    /**
     * Método privado que inicia la reproducción de la música cargando un archivo de audio desde los recursos del proyecto.
     * La música se reproduce en bucle indefinido.
     */
    private void iniciarMusica() {
        // Carga el archivo de música desde los recursos del proyecto
        URL musicURL = getClass().getResource("/com/example/gameuno/sound/C418  - Sweden - Minecraft Volume Alpha.mp3");

        if (musicURL != null) {
            // Crea un objeto {@code Media} para cargar el archivo de audio
            Media media = new Media(musicURL.toExternalForm());
            // Utiliza el adaptador {@code MediaPlayerWrapperImpl} para controlar la reproducción
            mediaPlayerWrapper = new MediaPlayerWrapperImpl(media);
            // Inicia la reproducción de la música
            mediaPlayerWrapper.play();
        } else {
            // Si no se encuentra el archivo de música, imprime un mensaje de error
            System.out.println("No se encontró el archivo de música.");
        }
    }

    /**
     * Método que alterna el estado de la música entre reproducción y pausa.
     * Si la música está en reproducción, se pausa; si está pausada, se reproduce.
     */
    public void toggleMusic() {
        // Si el reproductor de música no está inicializado, no hacer nada
        if (mediaPlayerWrapper == null) return;

        // Si la música está activa, pausarla; si está pausada, reproducirla
        if (isMusicOn) {
            mediaPlayerWrapper.pause();  // Pausar música
        } else {
            mediaPlayerWrapper.play();   // Reproducir música
        }
        // Cambiar el estado de la música
        isMusicOn = !isMusicOn;
    }

    /**
     * Método que devuelve el estado actual de la música.
     *
     * @return {@code true} si la música está en reproducción, {@code false} si está pausada.
     */
    public boolean isMusicOn() {
        return isMusicOn;
    }

    /**
     * Implementación de la interfaz {@code MediaPlayerWrapper} dentro de {@code MusicModel}.
     * Esta clase interna gestiona la reproducción de la música utilizando {@code MediaPlayer} de JavaFX.
     */
    private class MediaPlayerWrapperImpl implements MediaPlayerWrapper {

        /**
         * La instancia de {@code MediaPlayer} que maneja la reproducción de la música.
         */
        private final MediaPlayer mediaPlayer;

        /**
         * Constructor de {@code MediaPlayerWrapperImpl} que inicializa un objeto {@code MediaPlayer}
         * con el archivo de música proporcionado.
         *
         * @param media El objeto {@code Media} que contiene la URL del archivo de audio.
         */
        public MediaPlayerWrapperImpl(Media media) {
            this.mediaPlayer = new MediaPlayer(media);
            // Configura el {@code MediaPlayer} para que la música se repita indefinidamente
            this.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }

        /**
         * Método que inicia la reproducción de la música.
         */
        @Override
        public void play() {
            mediaPlayer.play();
        }

        /**
         * Método que pausa la reproducción de la música.
         */
        @Override
        public void pause() {
            mediaPlayer.pause();
        }
    }
}
