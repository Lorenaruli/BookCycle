package it.uniroma2.eu.bookcycle.controller.gui;



import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LibriMieiViewController {

    @FXML
    private Button tornaIndietroButton;

    @FXML
    private ListView<Libro> libriList;
    public void initialize() {
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = new GestoreUtente();
        List<Libro> libriUtente = gestore.caricaLibriUtente(username);

        ObservableList<Libro> lista = FXCollections.observableArrayList(libriUtente);
        libriList.setItems(lista);

        libriList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Libro libro, boolean empty) {
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}

