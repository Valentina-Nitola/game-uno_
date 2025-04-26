package com.example.gameuno.view;

import com.example.gameuno.controller.JuegoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que representa la vista del juego Sudoku.
 *
 * @author Valentina Nitola
 * @version 1.0
 */
public class JuegoView extends Stage {
    /**
     * Controlador asociado a la vista del juego.
     */
    private JuegoController controller;
    /**
     * Constructor que inicializa la vista del juego cargando el archivo FXML correspondiente.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public JuegoView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/gameuno/view/juegoView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("UNO MINECRAFT - JUEGO");
        this.setScene(scene);
    }
    /**
     * Obtiene el controlador de la vista del juego.
     *
     * @return el controlador de la vista del juego.
     */
    public JuegoController getController() {
        return controller;
    }
    /**
     *
     * @return instancia única de {@link JuegoView}.
     * @throws IOException si ocurre un error al crear la instancia.
     */
    public static JuegoView getInstance() throws IOException {
        if (JuegoViewHolder.INSTANCE == null) {
            JuegoViewHolder.INSTANCE = new JuegoView();
        }
        return JuegoViewHolder.INSTANCE;
    }

    /**
     * Clase interna estática que implementa el patrón Singleton para {@link JuegoView}.
     */
    private static class JuegoViewHolder {
        /**
         * Instancia única de {@link JuegoView}.
         */
        private static JuegoView INSTANCE;
    }
}
