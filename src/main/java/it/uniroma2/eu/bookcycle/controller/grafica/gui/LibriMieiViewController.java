package it.uniroma2.eu.bookcycle.controller.grafica.gui;



import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
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
    public void initialize() {
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = null;
        try {
            gestore = new GestoreUtente();
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi.");
            return;
        }
        List<LibroBean> libriUtente = null;
        try {
            libriUtente = gestore.caricaLibriUtente(username);
        } catch (ClienteNonTrovatoException _) {
            showAlert("Utente non trovato. Riprovare");
        }

        ObservableList<LibroBean> lista = FXCollections.observableArrayList(libriUtente);
        libriList.setItems(lista);

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
}

