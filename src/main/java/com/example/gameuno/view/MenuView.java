package com.example.gameuno.view;

import com.example.gameuno.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que representa la vista del menú principal del juego Sudoku.
 *
 * @author Valentina Nitola
 * @version 1.0.3
 */
public class MenuView extends Stage {
    /**
     * Controlador asociado a la vista del menú.
     */
    private MenuController controller;
    /**
     * Constructor que inicializa la vista del menú cargando el archivo FXML correspondiente.
     *
     * @throws IOException si ocurre un error al cargar el archivo FXML.
     */
    public MenuView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("/com/example/gameuno/Interfaces/menuView.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load());
        this.controller = fxmlLoader.getController();
        this.setTitle("UNO MINECRAFT - MENU");
        this.setScene(scene);
    }
    /**
     * Obtiene el controlador de la vista del menú.
     *
     * @return el controlador de la vista del menú.
     */
    public MenuController getController() {
        return controller;
    }
    /**
     *
     * @return instancia única de {@link MenuView}.
     * @throws IOException si ocurre un error al crear la instancia.
     */
    public static MenuView getInstance() throws IOException {
        if (MenuViewHolder.INSTANCE == null) {
            MenuViewHolder.INSTANCE = new MenuView();
        }
        return MenuViewHolder.INSTANCE;
    }

    /**
     * Clase interna estática que implementa el patrón Singleton para {@link MenuView}.
     */
    private static class MenuViewHolder {
        /**
         * Instancia única de {@link MenuView}.
         */
        private static MenuView INSTANCE;
    }
}
