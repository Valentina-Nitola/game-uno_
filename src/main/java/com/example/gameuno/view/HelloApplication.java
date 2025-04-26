package com.example.gameuno.view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación Sudoku.
 * @author Valentina Nitola
 * @version 1.0
 */
public class HelloApplication extends Application {

    /**
     * Método de inicio de la aplicación.
     * Carga y muestra la vista del menú principal.
     *
     * @param stage el escenario principal de la aplicación.
     * @throws IOException si ocurre un error al cargar la vista del menú.
     */
    @Override
    public void start(Stage stage) throws IOException {
        MenuView menuView = MenuView.getInstance();
        menuView.show();
    }

    /**
     * Método principal que lanza la aplicación.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch();
    }
}