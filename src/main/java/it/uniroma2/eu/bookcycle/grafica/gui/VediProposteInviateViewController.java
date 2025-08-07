package it.uniroma2.eu.bookcycle.grafica.gui;

import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class VediProposteInviateViewController {

    @FXML
    private TableColumn<PropostaDiScambio, String> statiColonna;

    @FXML
    private TableView<PropostaDiScambio> proposteTable;


    @FXML
    private TableColumn<PropostaDiScambio, String> titoliColonna;

    @FXML
    private Button tornaIndietroButton;
    private ObservableList<PropostaDiScambio> listaProposte;

    @FXML
    public void initialize() {
        titoliColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLibroOfferto().getTitolo()));

        statiColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStato().toString()));

        caricaLibriProposte();
    }

    void caricaLibriProposte(){
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        GestoreUtente gestore = new GestoreUtente();
        List<PropostaDiScambio> proposteUtente = gestore.caricaProposteUtenteMitente(username);
        listaProposte = FXCollections.observableArrayList(proposteUtente);
        proposteTable.setItems(listaProposte);
    }

    @FXML
    void tornaIndietro(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}

