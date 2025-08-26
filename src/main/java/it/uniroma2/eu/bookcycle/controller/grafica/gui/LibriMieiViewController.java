package it.uniroma2.eu.bookcycle.controller.grafica.gui;



import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.util.List;

public class LibriMieiViewController extends GraphicController {

    @FXML
    private Button tornaIndietroButton;


    @FXML
    private ListView<LibroBean> libriList;
    private final ObservableList<LibroBean> listaLibri = FXCollections.observableArrayList();
    @FXML

    public void initialize() {
        String usernameLog = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = GestoreUtente.getInstance();

        try {
            List<LibroBean> libriUtente = gestore.caricaLibriUtente(usernameLog);
            listaLibri.setAll(libriUtente);
        } catch (ClienteNonTrovatoException _) {
            showAlert("Cliente non trovato. Riprovare");
            return;
        }

        libriList.setItems(listaLibri);

        libriList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(LibroBean libro, boolean empty) {
                super.updateItem(libro, empty);
                if (empty || libro == null) {
                    setText(null);
                } else {
                    setText(libro.getTitolo() + " di " + libro.getAutore());
                }
            }
        });
    }

    @FXML
    void tornaIndietro(ActionEvent event){
        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);

    }

    public void aggiornaLista() {
        String usernameLog = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = GestoreUtente.getInstance();

        try {
            List<LibroBean> libriUtente = gestore.caricaLibriUtente(usernameLog);
            listaLibri.setAll(libriUtente);
        } catch (ClienteNonTrovatoException _) {
            showAlert("Cliente non trovato. Riprovare");
        }
    }

}

