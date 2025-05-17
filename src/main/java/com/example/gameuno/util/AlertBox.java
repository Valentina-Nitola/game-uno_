package com.example.gameuno.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBox {

    /**
     * Muestra una alerta informativa con título, encabezado y contenido.
     *
     * @param title   Título de la ventana de alerta.
     * @param header  Encabezado del mensaje.
     * @param content Cuerpo del mensaje.
     */
    public static void showAlertBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de confirmación con botones personalizados (Sí / Cancelar).
     *
     * @param title   Título de la alerta.
     * @param header  Encabezado del mensaje.
     * @param content Contenido de la alerta.
     * @return true si el usuario hace clic en "Sí", false si cancela.
     */
    public static boolean showConfirmAlertBox(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType botonAceptar = new ButtonType("Sí");
        ButtonType botonCancelar = new ButtonType("Cancelar");

        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

        Optional<ButtonType> resultado = alert.showAndWait();

        return resultado.isPresent() && resultado.get() == botonAceptar;
    }

    /**
     * Muestra una alerta informativa con un solo botón "OK".
     *
     * @param title   Título de la alerta.
     * @param message Mensaje a mostrar.
     */
    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta de error con un solo botón "OK".
     *
     * @param title   Título de la alerta.
     * @param message Mensaje a mostrar.
     */
    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
