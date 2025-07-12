package com.example.gameuno.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

/**
 * Pruebas unitarias para la clase {@link MusicModel}.
 *
 * Esta clase verifica:
 *
 *   El patrón singleton de {@code MusicModel}.
 *   El comportamiento de {@code toggleMusic()} tanto al pausar como al reproducir.
 *   La correcta inicialización del toolkit de JavaFX.
 *   Los mensajes impresos por el stub de {@link MediaPlayerWrapper}.
 *
 */
class MusicModelTest {

    /**
     * Inicializa el toolkit de JavaFX antes de ejecutar las pruebas.
     * Evita la excepción "Toolkit not initialized".
     */
    @BeforeAll
    static void iniciarJavaFX() {
        try {
            javafx.application.Platform.startup(() -> {});
        } catch (IllegalStateException ignored) {
            // Toolkit ya inicializado
        }
    }

    /**
     * Stub que implementa {@link MediaPlayerWrapper} para simular
     * llamadas a play() y pause() sin reproducir audio real.
     */
    private static class StubReproductor implements MediaPlayerWrapper {
        /** Indica si se invocó el método play(). */
        boolean llamadaPlay = false;
        /** Indica si se invocó el método pause(). */
        boolean llamadaPause = false;

        /**
         * Simula la reproducción y registra la llamada.
         */
        @Override
        public void play() {
            llamadaPlay = true;
            System.out.println("play() invocado");
        }

        /**
         * Simula la pausa y registra la llamada.
         */
        @Override
        public void pause() {
            llamadaPause = true;
            System.out.println("pause() invocado");
        }
    }

    private StubReproductor stub;
    private MusicModel modelo;

    /**
     * Configura el entorno antes de cada prueba:
     *   Resetea la instancia singleton de {@link MusicModel}.
     *   Inyecta el stub de {@link MediaPlayerWrapper}.
     *   Forza el estado inicial de la música en encendida.
     *
     * @throws Exception si ocurre un error de acceso por reflexión.
     */
    @BeforeEach
    void configurar() throws Exception {
        // Resetear singleton de MusicModel
        Field campoInstancia = MusicModel.class.getDeclaredField("instance");
        campoInstancia.setAccessible(true);
        campoInstancia.set(null, null);

        // Crear e inyectar stub en el modelo
        modelo = MusicModel.getInstance();
        stub = new StubReproductor();
        Field campoWrapper = MusicModel.class.getDeclaredField("mediaPlayerWrapper");
        campoWrapper.setAccessible(true);
        campoWrapper.set(modelo, stub);

        // Forzar estado inicial de música encendida
        Field campoOn = MusicModel.class.getDeclaredField("isMusicOn");
        campoOn.setAccessible(true);
        campoOn.set(modelo, true);
    }

    /**
     * Verifica que {@link MusicModel#getInstance()} siempre devuelva
     * la misma instancia (singleton).
     */
    @Test
    void pruebaInstanciaSingleton() {
        MusicModel primero = MusicModel.getInstance();
        MusicModel segundo = MusicModel.getInstance();
        if (primero == segundo) {
            System.out.println("PASÓ");
        } else {
            System.out.println("FALLÓ");
        }
    }

    /**
     * Verifica que al llamar a {@code toggleMusic()} con la música encendida,
     * se invoque {@code pause()} en el wrapper y se actualice el estado interno.
     */
    @Test
    void pruebaTogglePausa() {
        modelo.toggleMusic();
        if (stub.llamadaPause && !modelo.isMusicOn()) {
            System.out.println("PASÓ");
        } else {
            System.out.println("FALLÓ");
        }
    }

    /**
     * Verifica que al llamar a {@code toggleMusic()} con la música apagada,
     * se invoque {@code play()} en el wrapper y se actualice el estado interno.
     *
     * @throws Exception si ocurre un error de acceso por reflexión.
     */
    @Test
    void pruebaTogglePlay() throws Exception {
        // Simular que la música está apagada
        Field campoOn = MusicModel.class.getDeclaredField("isMusicOn");
        campoOn.setAccessible(true);
        campoOn.set(modelo, false);

        modelo.toggleMusic();
        if (stub.llamadaPlay && modelo.isMusicOn()) {
            System.out.println("PASÓ");
        } else {
            System.out.println("FALLÓ");
        }
    }

    /**
     * Verifica que los mensajes del stub se impriman correctamente
     * al invocar {@code play()} y {@code pause()}.
     */
    @Test
    void pruebaMensajesStub() {
        // Capturar salida de consola del stub
        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        PrintStream original = System.out;
        System.setOut(new PrintStream(salida));

        stub.play();
        stub.pause();

        // Restaurar salida
        System.setOut(original);
        String texto = salida.toString();
        if (texto.contains("play() invocado") && texto.contains("pause() invocado")) {
            System.out.println("PASÓ");
        } else {
            System.out.println("FALLÓ");
        }
    }
}
