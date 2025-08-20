package it.uniroma2.eu.bookcycle.controller.gui;



import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.util.List;

public class LibriMieiViewController {

    @FXML
    private Button tornaIndietroButton;


    @FXML
    private ListView<LibroBean> libriList;
    public void initialize() {
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = new GestoreUtente();
        List<LibroBean> libriUtente = gestore.caricaLibriUtente(username);

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
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml");

    }
}

