package com.example.gameuno.util;

/**
 * Interfaz que define los métodos para mostrar ventanas de alerta al usuario.
 * Se utiliza para abstraer el comportamiento de las alertas informativas y de confirmación en la GUI del juego.
 *
 * Esta interfaz es implementada por la clase {@link AlertBox}.
 *
 * @author Valentina Nitola
 * @version 1.0
 */
public interface AlertBoxInterface {
    /**
     * Muestra una ventana de alerta informativa con título, encabezado y contenido personalizado.
     *
     * @param title   Título de la alerta.
     * @param header  Encabezado de la alerta.
     * @param content Mensaje de contenido de la alerta.
     */
    void showAlertBox(String title, String header, String content);

    /**
     * Muestra una ventana de confirmación con título, encabezado y contenido personalizado.
     *
     * @param title   Título de la alerta de confirmación.
     * @param header  Encabezado de la alerta.
     * @param content Mensaje de contenido.
     * @return {@code true} si el usuario acepta, {@code false} si cancela.
     */
    boolean showConfirmAlertBox(String title, String header, String content);
}