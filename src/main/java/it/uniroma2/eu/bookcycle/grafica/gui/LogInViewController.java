package it.uniroma2.eu.bookcycle.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.LoginBean;
import it.uniroma2.eu.bookcycle.controller.LoginController;
import it.uniroma2.eu.bookcycle.model.domain.RuoloCliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInViewController extends GraphicController{

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameLabel;

    @FXML
    void login(ActionEvent event) {
        LoginBean loginBean = new LoginBean();
        loginBean.setUsername(usernameLabel.getText());
        loginBean.setPassword(passwordField.getText());
        //loginBean.setRuolo(libraioCheck.isSelected() ? RuoloCliente.LIBRAIO : RuoloCliente.UTENTE);
        LoginController loginController = new LoginController();
        RuoloCliente ruoloCliente;
        try {
            ruoloCliente = loginController.login(loginBean);
        } catch (RuntimeException e) {
            showAlert("credenziali errate");
            return;
        }
        showAlert("login avvenuto");
//        switch (ruoloCliente){
//            case LIBRAIO -> //apri menu libraio
//            case UTENTE -> //apri menu utente
//            default -> // errore
//        }


    }

}