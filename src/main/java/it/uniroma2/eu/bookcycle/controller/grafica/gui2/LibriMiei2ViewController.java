package it.uniroma2.eu.bookcycle.controller.grafica.gui2;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.CaricaLibroGui;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LibriMiei2ViewController extends CaricaLibroGui {

    @FXML
    private TextField autoreField;

    @FXML
    private Button caricaButton;

    @FXML
    private TextField genereField;

    @FXML
    private TableView<LibroBean> table;

    @FXML
    private TextField titoloField;

    @FXML
    private Button tornaIndietroButton;

    @FXML
    private TableColumn<LibroBean, String> colonnaLibro;

    private ObservableList<LibroBean> listaLibri;


    public void initialize() {
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = null;
        try {
            gestore = new GestoreUtente();
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi.");
            return;
        }
        List<LibroBean> libriUtente = null;
        try {
            libriUtente = gestore.caricaLibriUtente(username);
        } catch (ClienteNonTrovatoException e) {
            showAlert("Utente non trovato. RIporvare.");
            return;
        }

        listaLibri = FXCollections.observableArrayList(libriUtente);
        table.setItems(listaLibri);


        colonnaLibro.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getTitolo() + " di " + cellData.getValue().getAutore()
                )
        );
    }

    @Override
    protected void goToLogin() {
        try {
            String path = ViewPath.LOGIN2_VIEW;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage stage = (Stage) tornaIndietroButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            showAlert("Errore nel caricamento della schermata di login.");
        }

    }

    @FXML
    void caricaLibro(ActionEvent event) {
            carica( titoloField, autoreField, genereField);


    }


    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.PROFILO2_VIEW);


    }

}

