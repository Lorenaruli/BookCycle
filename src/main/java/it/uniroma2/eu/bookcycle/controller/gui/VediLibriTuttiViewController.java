package it.uniroma2.eu.bookcycle.controller.gui;



import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Libro;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
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

public class VediLibriTuttiViewController {

    @FXML
    private TextField titoloField;

    @FXML
    private Button tornaIndietroButton;

    @FXML
    private TableView<Libro> libriTable;

    @FXML
    private TableColumn<Libro, String> titoloColumn;

    @FXML
    private TableColumn<Libro, String> autoreColumn;

    @FXML
    private TableColumn<Libro, String> genereColumn;

    @FXML
    private TableColumn<Libro, Void> azioneColumn;

    private ObservableList<Libro> listaLibriDisponibili;

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
            String usernameCorrente = Sessione.ottieniIstanza().getClienteLoggato().getUsername();

            List<Libro> libriDisponibili = gestoreUtente.caricaLibriTutti();
            List<Libro> soloAltriLibri = libriDisponibili.stream()
                    .filter(libro -> !libro.getUsernameProprietario().equals(usernameCorrente))
                    .collect(Collectors.toList());
            listaLibriDisponibili = FXCollections.observableArrayList(soloAltriLibri);
            libriTable.setItems(listaLibriDisponibili);
        } catch (Exception e) {
            mostraErrore("Errore durante il caricamento dei libri.");
            e.printStackTrace();
        }
    }

    @FXML
    private void filtraLibri() {
        String titolo = titoloField.getText().toLowerCase().trim();

        if (titolo.isEmpty()) {
            libriTable.setItems(listaLibriDisponibili);
        } else {
            List<Libro> filtrati = listaLibriDisponibili.stream()
                    .filter(libro -> libro.getTitolo().toLowerCase().contains(titolo))
                    .collect(Collectors.toList());

            libriTable.setItems(FXCollections.observableArrayList(filtrati));
        }
    }

    @FXML
    public void tornaIndietro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostraErrore("Impossibile caricare la schermata del profilo.");
            e.printStackTrace();
        }
    }

    private void aggiungiColonnaSeleziona() {
        azioneColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btn = new Button("Scegli");

            {
                btn.setOnAction(event -> {
                    Libro libroSelezionato = getTableView().getItems().get(getIndex());
                    PropostaParzialeBean propostaParzialeBean= new PropostaParzialeBean();
                    propostaParzialeBean.setLibroRichiesto(libroSelezionato.getIdLibro());
                    propostaParzialeBean.setDestinatario(libroSelezionato.getUsernameProprietario());

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ScegliLibriMieiView.fxml"));
                        Parent root = loader.load();

                         ScegliLibriMieiViewController controller=loader.getController();
                        controller.creaBeanProposta(propostaParzialeBean);
                        //controller.inizializzaConBean();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        mostraErrore("Errore durante il caricamento della schermata di selezione.");
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


    private void mostraErrore(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Errore");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void mostraInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informazione");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    }

