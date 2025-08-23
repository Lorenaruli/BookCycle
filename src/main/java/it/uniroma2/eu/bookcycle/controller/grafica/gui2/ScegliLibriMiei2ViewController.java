package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
        GestoreUtente gestore = null;
        try {
            gestore = new GestoreUtente();
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi");
        }

        List<LibroBean> libriUtente = null;
        try {
            libriUtente = gestore.caricaLibriUtente(username);
        } catch (ClienteNonTrovatoException e) {
            showAlert("Cliente non trovato, riprovare.");
            return;
        }
        List<LibroBean> libriDisponibili = libriUtente.stream().toList();

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
        String destinatario = propostaParzialeBean.getMittente();
        long libroRichiesto = propostaParzialeBean.getLibroOfferto();

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
            showAlert("Errore durante l'invio della proposta.");
        }
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.LIBRI_SCAMBIO_VIEW);

    }
}