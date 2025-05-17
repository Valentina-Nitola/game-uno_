package com.example.gameuno.controller;

import com.example.gameuno.model.JugadorModel;
import com.example.gameuno.view.JuegoView;
import com.example.gameuno.view.MenuView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class TutorialController {
    /**
     * Maneja el inicio de una nueva partida.
     * Valida el nombre del jugador y abre la vista del juego principal.
     *
     * @param event Evento de acción generado por el clic
     * @throws IOException Si ocurre un error al cargar la vista del juego
     */
    @FXML
    private void iniciarjuego(ActionEvent event) throws IOException {
        JugadorModel player = new JugadorModel();

        System.out.println("Iniciar juego");

        // Configuración y apertura de la vista del juego
        JuegoView juegoView = JuegoView.getInstance();
        juegoView.getController().setPlayer(player); // Pasa el jugador al controlador del juego
        MenuView.getInstance().close(); // Cierra el menú actual
        juegoView.show(); // Muestra la vista del juego
    }
}
