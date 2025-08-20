package it.uniroma2.eu.bookcycle.controller.gui;



import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
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
import java.util.List;
import java.util.stream.Collectors;

public class VediLibriTuttiViewController extends GraphicController{

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

    //private final GestoreLibri gestoreLibri = new GestoreLibri();

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
            GestoreUtente gestoreUtente= new GestoreUtente();

            List<LibroBean> libriDisponibili = gestoreUtente.caricaLibriTutti();
            listaLibriDisponibili = FXCollections.observableArrayList(libriDisponibili);
            libriTable.setItems(listaLibriDisponibili);
        } catch (Exception e) {
            showAlert("Errore durante il caricamento dei libri.");
            e.printStackTrace();
            LibroBean libroBean= new LibroBean();
            libroBean.getIdLibro();
        }
    }

    @FXML
    private void filtraLibri() {
        String titolo = titoloField.getText().toLowerCase().trim();

        if (titolo.isEmpty()) {
            libriTable.setItems(listaLibriDisponibili);
        } else {
            List<LibroBean> filtrati = listaLibriDisponibili.stream()
                    .filter(libro -> libro.getTitolo().toLowerCase().contains(titolo))
                    .collect(Collectors.toList());

            libriTable.setItems(FXCollections.observableArrayList(filtrati));
        }
    }

    @FXML
    public void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml");

    }

    private void aggiungiColonnaSeleziona() {
        azioneColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Scegli");

            {
                btn.setOnAction(event -> {
                    LibroBean libroSelezionato = getTableView().getItems().get(getIndex());
                    PropostaParzialeBean propostaParzialeBean= new PropostaParzialeBean();
                    propostaParzialeBean.setLibroOfferto(libroSelezionato.getIdLibro());
                    propostaParzialeBean.setMittente(libroSelezionato.getUsernameProprietario());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ScegliLibriMieiView.fxml"));
                        Parent root = loader.load();

                         ScegliLibriMieiViewController controller=loader.getController();
                        controller.creaBeanProposta(propostaParzialeBean);;

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        showAlert("Errore durante il caricamento della schermata di selezione.");
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }




    }

