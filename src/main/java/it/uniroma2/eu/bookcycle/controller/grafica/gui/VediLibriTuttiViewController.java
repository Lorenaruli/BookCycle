package it.uniroma2.eu.bookcycle.controller.grafica.gui;
import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VediLibriTuttiViewController extends GraphicController {

    @FXML
    private TextField titoloField;

    @FXML
    private Button tornaIndietroButton;

    @FXML
    private TableView<LibroBean> libriTable;

    @FXML
    private TableColumn<LibroBean, String> titoloColumn;

    @FXML
    private TableColumn<LibroBean, String> autoreColumn;

    @FXML
    private TableColumn<LibroBean, String> genereColumn;

    @FXML
    private TableColumn<LibroBean, Void> azioneColumn;

    private ObservableList<LibroBean> listaLibriDisponibili;


    @FXML
    public void initialize() {
        titoloColumn.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreColumn.setCellValueFactory(new PropertyValueFactory<>("autore"));
        genereColumn.setCellValueFactory(new PropertyValueFactory<>("genere"));

        aggiungiColonnaSeleziona();

        caricaLibri();
    }

    private void caricaLibri() {
        try {
            GestoreUtente gestoreUtente= GestoreUtente.getInstance();

            List<LibroBean> libriDisponibili = gestoreUtente.caricaLibriTuttiAltri();
            listaLibriDisponibili = FXCollections.observableArrayList(libriDisponibili);
            libriTable.setItems(listaLibriDisponibili);
        } catch (Exception _) {
            showAlert("Errore durante il caricamento dei libri.");
        }
    }

    @FXML
    private void filtraLibri() {
        String titolo = titoloField.getText().toLowerCase().trim();

        if (titolo.isEmpty()) {
            libriTable.setItems(listaLibriDisponibili);
        } else {
            List<LibroBean> filtrati = new ArrayList<>();
            for (LibroBean libro : listaLibriDisponibili) {
                if (libro.getTitolo().toLowerCase().contains(titolo)) {
                    filtrati.add(libro);
                }
            }
            libriTable.setItems(FXCollections.observableArrayList(filtrati));
        }
    }


    @FXML
    public void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);

    }

    private void aggiungiColonnaSeleziona() {
        azioneColumn.setCellFactory(col -> new SelezionaCell());
    }

    private class SelezionaCell extends TableCell<LibroBean, Void> {
        private final Button btn = new Button("Scegli");


        public SelezionaCell() {
            btn.setOnAction(event -> {
                LibroBean libroSelezionato = getTableView().getItems().get(getIndex());
                PropostaParzialeBean propostaParzialeBean = new PropostaParzialeBean();
                propostaParzialeBean.setLibroOfferto(libroSelezionato.getIdLibro());

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath.SCEGLI_LIBRI_MIEI_VIEW));
                    Parent root = loader.load();
                    ScegliLibriMieiViewController controller = loader.getController();
                    controller.creaBeanProposta(propostaParzialeBean);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException _) {
                    showAlert("Errore durante il caricamento della schermata di selezione.");
                }
            });
        }

        @Override
        protected void updateItem(Void v, boolean empty) {
            super.updateItem(v, empty);
            setGraphic(empty ? null : btn);
        }
    }






}

