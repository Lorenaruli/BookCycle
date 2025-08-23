package it.uniroma2.eu.bookcycle.controller.grafica.guiComune;

import it.uniroma2.eu.bookcycle.controller.applicativo.LoginController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import javafx.event.ActionEvent;

public class LogoutGui extends GraphicController {
    public void logoutCliente(ActionEvent event, String path) throws PersistenzaException {
        LoginController loginController = new LoginController();
        loginController.logout();
        SceneManager.cambiaScena(event,path);

    }

}
