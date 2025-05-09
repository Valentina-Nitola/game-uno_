package com.example.gameuno.view;

import com.example.gameuno.controller.MenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase que representa la vista del menú principal del juego uno
 * @author Valentina Nitola
 * @version 1.0.3
 */
public class MenuView extends Stage {
	
	private MenuController controller;
	/**
	 * Constructor que inicia la vista del menu
	 */
	public MenuView() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(
				HelloApplication.class.getResource("/com/example/gameuno/Interfaces/menuView.fxml")
		);
		Scene scene = new Scene(fxmlLoader.load());
		this.controller = fxmlLoader.getController();
		this.setTitle("UNO MINECRAFT --> MENU");
		this.setScene(scene);
	}
	/**
	 * Obtiene el controlador de la vista del menu
	 * @return el controlador de la vista del menu
	 */
	public MenuController getController() {
		return controller;
	}
	/**
	 *
	 */
	public static MenuView getInstance() throws IOException {
		if (MenuViewHolder.INSTANCE == null) {
			MenuViewHolder.INSTANCE = new MenuView();
		}
		return MenuViewHolder.INSTANCE;
	}
	
	/**
	 
	 */
	private static class MenuViewHolder {
		/**
		 
		 */
		private static MenuView INSTANCE;
	}
}