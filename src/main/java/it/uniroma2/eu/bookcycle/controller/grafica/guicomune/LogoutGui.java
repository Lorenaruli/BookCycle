package it.uniroma2.eu.bookcycle.controller.grafica.guicomune;

import it.uniroma2.eu.bookcycle.controller.applicativo.LoginController;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import javafx.event.ActionEvent;

public class LogoutGui extends GraphicController {
    public void logoutCliente(ActionEvent event, String path) throws PersistenzaException {
        LoginController loginController = new LoginController();
        loginController.logout();
        SceneManager.cambiaScena(event,path);

    }

}
