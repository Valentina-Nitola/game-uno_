package com.example.gameuno.view;

import com.example.gameuno.controller.WildController;
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
public class WildView extends Stage {

        private WildController controller;
        /**
         * Constructor que inicializa la vista del tutorial cargando el archivo FXML correspondiente.
         *
         * @throws IOException si ocurre un error al cargar el archivo FXML.
         */
        public WildView() throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    HelloApplication.class.getResource("/com/example/gameuno/view/WildView.fxml")
            );
            Scene scene = new Scene(fxmlLoader.load());
            this.controller = fxmlLoader.getController();
            this.setTitle("UNO MINECRAFT - COMODIN");
            this.setScene(scene);
        }
        /**
         * Obtiene el controlador de la vista del comodin.
         *
         * @return el controlador de la vista del comodin.
         */
        public WildController getController() {
            return controller;
        }
        /**
         *
         * @return instancia única de {@link WildView}.
         * @throws IOException si ocurre un error al crear la instancia.
         */
        public static WildView getInstance() throws IOException {
            if (WildViewHolder.INSTANCE == null) {
                WildViewHolder.INSTANCE = new WildView();
            }
            return WildViewHolder.INSTANCE;
        }
        /**
         * Clase interna estática que implementa el patrón Singleton para {@link WildView}.
         */
        private static class WildViewHolder {
            /**
             * Instancia única de {@link WildView}.
             */
            private static WildView INSTANCE;
        }
    }

