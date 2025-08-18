package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import it.uniroma2.eu.bookcycle.model.domain.StatoLibro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScegliLibriMiei2ViewController extends GraphicController {

    @FXML
    private Button annullaBottone;

    @FXML
    private Button selezionaButton;

    @FXML
    private TableView<LibroBean> tabella;

    @FXML
    private TableColumn<LibroBean, String> titoloColonna;

    private ObservableList<LibroBean> listaLibriDisponibili;

    private PropostaParzialeBean propostaParzialeBean;

    private LibroBean libroSelezionato;

    @FXML
    public void initialize() {
        titoloColonna.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        selezionaButton.setDisable(true);
        caricaLibri();
        tabella.setRowFactory(tv -> {
            TableRow<LibroBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    libroSelezionato = row.getItem();
                    tabella.getSelectionModel().select(libroSelezionato);
                    selezionaButton.setDisable(false);
                }
            });
            return row;
        });
    }

    public void caricaLibri() {
        Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();
        String username = cliente.getUsername();
        GestoreUtente gestore = new GestoreUtente();

        List<LibroBean> libriUtente = gestore.caricaLibriUtente(username);
        List<LibroBean> libriDisponibili = libriUtente.stream()
                //.filter(libro -> libro.getStato() == StatoLibro.DISPONIBILE)
                .collect(Collectors.toList());

        listaLibriDisponibili = FXCollections.observableArrayList(libriDisponibili);
        tabella.setItems(listaLibriDisponibili);
    }

    public void creaBeanProposta(PropostaParzialeBean propostaParzialeBean) {
        this.propostaParzialeBean = propostaParzialeBean;
    }

    @FXML
    void seleziona(ActionEvent event) {
        if (libroSelezionato == null) {
            showAlert("Seleziona un libro con doppio click prima di procedere.");
            return;
        }
        Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();
        String username = cliente.getUsername();
        String destinatario = propostaParzialeBean.getDestinatario();
        long libroRichiesto = propostaParzialeBean.getLibroRichiesto();

        PropostaBean propostaBean = new PropostaBean();
        propostaBean.setMittente(username);
        propostaBean.setDestinatario(destinatario);
        propostaBean.setLibroRichiesto(libroRichiesto);
        propostaBean.setLibroOfferto(libroSelezionato.getIdLibro());

        try {
            InviaPropostaController controller = new InviaPropostaController();
            controller.inviaProposta(propostaBean);
            showAlert("Proposta inviata");
            tornaIndietro(event);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Errore durante l'invio della proposta.");
        }
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui2/LibriScambioView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Errore nel caricamento della schermata precedente.");
        }
    }
}