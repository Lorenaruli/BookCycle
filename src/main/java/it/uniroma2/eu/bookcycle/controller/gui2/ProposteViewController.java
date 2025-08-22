package it.uniroma2.eu.bookcycle.controller.gui2;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.bean.Proposta4Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.controller.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.controller.gui.GraphicController;
import it.uniroma2.eu.bookcycle.model.Eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class ProposteViewController extends GraphicController {


    @FXML private TableView<Proposta4Bean> proposteInviate;
    @FXML private TableColumn<Proposta4Bean, String> proposteInviateColonna;
    @FXML private TableColumn<Proposta4Bean, String> statoColonna;

    @FXML private TableView<Proposta2Bean> proposteRicevute;
    @FXML private TableColumn<Proposta2Bean, String> proposteRIcevuteColonna;
    @FXML private TableColumn<Proposta2Bean, Void> gestisciColonna;

    @FXML private Button tornaIndietroButton;

    private  GestisciPropostaController gestisciCtrl;
    private InviaPropostaController inviaCtrl;

    private static final String CONTATTI_LABEL = "Contatti";


    @FXML
    private void initialize() {
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();

        proposteInviateColonna.setCellValueFactory(new PropertyValueFactory<>("titoloOfferto"));
        statoColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStato().toString()));

        aggiungiColonnaContatti();
        caricaInviate();

        proposteRIcevuteColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(
                        cellData.getValue().getTitoloOfferto() + " per " + cellData.getValue().getTitoloRichiesto()
                )
        );
        gestisciColonna.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Gestisci");
            {
                btn.setOnAction(e -> {
                    Proposta2Bean row = getTableView().getItems().get(getIndex());
                    apriAccettaRifiuta(row, e);
                });
            }
            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : btn);
            }
        });
        caricaRicevute(username);
    }

    private void caricaInviate() {
        try {
            inviaCtrl= new InviaPropostaController();
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi");
        }
        var beans = inviaCtrl.creaListaBeanProposteInviate();
        proposteInviate.setItems(FXCollections.observableArrayList(beans));
    }

    private void caricaRicevute(String username) {
        try {
            gestisciCtrl= new GestisciPropostaController();
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. RIprovare più tardi.");
        }
        var beans = gestisciCtrl.creaListaBeanProposteRicevute(username);
        proposteRicevute.setItems(FXCollections.observableArrayList(beans));
    }

    private void apriAccettaRifiuta(Proposta2Bean propostaSelezionata, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/it/uniroma2/eu/bookcycle/gui2/AccettaRifiuta2View.fxml"));
            Parent root = loader.load();

            AccettaRifiuta2ViewController ctrl = loader.getController();
            ctrl.setDati(
                    propostaSelezionata.getIdProposta(),
                    propostaSelezionata.getTitoloOfferto(),
                    propostaSelezionata.getTitoloRichiesto()
            );


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            showAlert("Errore nel caricamento della schermata.");
        }
    }

    private void aggiungiColonnaContatti() {
        TableColumn<Proposta4Bean, Proposta4Bean> contattiCol = new TableColumn<>(CONTATTI_LABEL);
        contattiCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        contattiCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("CONTATTI_LABEL");
            {
                btn.setOnAction(e -> {
                    Proposta4Bean bean = getItem();
                    if (bean != null && "ACCETTATA".equalsIgnoreCase(bean.getStato().toString())) {
                        mostraContatti(bean);
                    }
                });
            }
            @Override
            protected void updateItem(Proposta4Bean bean, boolean empty) {
                super.updateItem(bean, empty);
                if (empty || bean == null) {
                    setGraphic(null);
                    return;
                }
                btn.setDisable(!"ACCETTATA".equalsIgnoreCase(bean.getStato().toString()));
                setGraphic(btn);
            }
        });
        proposteInviate.getColumns().add(contattiCol);
    }

    private void mostraContatti(Proposta4Bean bean) {
        String altroUsername = bean.getUsernameDestinatario();
        ContattiBean c = null;
        try {
            c = new GestoreUtente().trovaContattiDaUsername(altroUsername);
        } catch (ClienteNonTrovatoException e) {
            showAlert("Utente non trovato, riprovare");
            return;
        } catch (PersistenzaException e) {
            showAlert("Errore tecnico. Riprovare più tardi.");
            return;
        }
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        if (c != null) {
            a.setHeaderText("CONTATTI_LABEL");
            a.setContentText("Email: " + c.getEmail() + "\nTelefono: " + c.getTelefono());
        } else {
            a.setHeaderText("Contatti non disponibili");
            a.setContentText("Nessun contatto trovato per " + altroUsername);
        }
        a.showAndWait();
    }

    @FXML
    void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event,"/it/uniroma2/eu/bookcycle/gui2/Profilo2View.fxml");

    }
}