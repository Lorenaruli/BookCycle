package it.uniroma2.eu.bookcycle.controller.guiComune;

import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import javafx.event.ActionEvent;

public class LogoutGui extends GraphicController {
    public void logoutCliente(ActionEvent event, String path) {
        LoginController loginController = new LoginController();
        loginController.logout();
        SceneManager.cambiaScena(event,path);

    }

}
