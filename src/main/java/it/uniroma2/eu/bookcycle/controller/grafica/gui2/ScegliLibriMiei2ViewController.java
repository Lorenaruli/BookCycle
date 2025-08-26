package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
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

    GestoreUtente gestore = GestoreUtente.getInstance();

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



        List<LibroBean> libriUtente = null;
        try {
            libriUtente = gestore.caricaLibriUtente(username);
        } catch (ClienteNonTrovatoException _) {
            showAlert("Cliente non trovato. Riprovare");
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
       long libroRichiesto = propostaParzialeBean.getLibroOfferto();

        String destinatario=gestore.trovaProprietarioLibro(propostaParzialeBean.getLibroOfferto()).getUsername();

        PropostaBean propostaBean = new PropostaBean();
        propostaBean.setLibroRichiesto(libroRichiesto);
        propostaBean.setLibroOfferto(libroSelezionato.getIdLibro());
        propostaBean.setDestinatario(destinatario);

        try {
            InviaPropostaController controller = new InviaPropostaController();
            controller.inviaProposta(propostaBean);
            showAlert("Proposta inviata");
            tornaIndietro(event);
        } catch (Exception _) {
            showAlert("Errore durante l'invio della proposta.");
        }
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.LIBRI_SCAMBIO_VIEW);

    }
}