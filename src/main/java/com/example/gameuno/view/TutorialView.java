package com.example.gameuno.view;

import com.example.gameuno.controller.TutorialController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que representa la vista del tutorial del juego Sudoku.
 *
 * @author Valentina Nitola
 * @version 1.0.
 */
public class TutorialView extends Stage {
    /**
     * Controlador asociado a la vista del tutorial.
     */
    private TutorialController controller;
    /**
     * Constructor que inicializa la vista del tutorial cargando el archivo FXML correspondiente.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public TutorialView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/gameuno/view/tutorialView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("UNO MINECRAFT - TUTORIAL");
        this.setScene(scene);
    }
    /**
     * Obtiene el controlador de la vista del tutorial.
     *
     * @return el controlador de la vista del tutorial.
     */
    public TutorialController getController() {
        return controller;
    }
    /**
     *
     * @return instancia única de {@link TutorialView}.
     * @throws IOException si ocurre un error al crear la instancia.
     */
    public static TutorialView getInstance() throws IOException {
        if (TutorialViewHolder.INSTANCE == null) {
            TutorialViewHolder.INSTANCE = new TutorialView();
        }
        return TutorialViewHolder.INSTANCE;
    }
    /**
     * Clase interna estática que implementa el patrón Singleton para {@link TutorialView}.
     */
    private static class TutorialViewHolder {
        /**
         * Instancia única de {@link TutorialView}.
         */
        private static TutorialView INSTANCE;
    }
}

