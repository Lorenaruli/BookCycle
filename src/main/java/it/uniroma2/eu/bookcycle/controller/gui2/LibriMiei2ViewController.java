package it.uniroma2.eu.bookcycle.controller.gui2;
import it.uniroma2.eu.bookcycle.bean.CaricaLibroBean;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.controller.CaricaLibroController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class LibriMiei2ViewController extends GraphicController {

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
        GestoreUtente gestore = new GestoreUtente();
        List<LibroBean> libriUtente = gestore.caricaLibriUtente(username);

        listaLibri = FXCollections.observableArrayList(libriUtente);
        table.setItems(listaLibri);


        colonnaLibro.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getTitolo() + " di " + cellData.getValue().getAutore()
                )
        );
    }

    @FXML
    void caricaLibro(ActionEvent event) {
        CaricaLibroBean caricaLibroBean=new CaricaLibroBean();
        caricaLibroBean.setTitolo(titoloField.getText());
        caricaLibroBean.setAutore(autoreField.getText());
        caricaLibroBean.setGenere(genereField.getText());
        CaricaLibroController caricaLibroController = new CaricaLibroController();
        caricaLibroController.AggiungiLibro(caricaLibroBean);
        showAlert("Libro caricato con successo");



    }


    @FXML
    void tornaIndietro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui2/Profilo2View.fxml"));
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

