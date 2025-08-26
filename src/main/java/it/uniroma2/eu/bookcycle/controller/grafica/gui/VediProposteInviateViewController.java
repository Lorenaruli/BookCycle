package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.controller.applicativo.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.ClienteNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.util.List;

public class VediProposteInviateViewController extends GraphicController {

    @FXML
    private TableColumn<Proposta2Bean, String> statiColonna;

    @FXML
    private TableView<Proposta2Bean> proposteTable;


    @FXML
    private TableColumn<Proposta2Bean, String> titoliColonna;

    @FXML
    private Button tornaIndietroButton;
    private ObservableList<Proposta2Bean> listaProposte;

    private static final String CONTATTI_LABEL = "Contatti";

    GestoreUtente gestoreUtente=GestoreUtente.getInstance();


    @FXML
    public void initialize() {
        titoliColonna.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTitoloRichiesto())
        );
        statiColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStato().toString()));
        aggiungiColonnaContatti();
        caricaLibriProposte();
    }

    void caricaLibriProposte(){
        InviaPropostaController app = null;
        try {
            app = new InviaPropostaController();
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi.");
        }
        List<Proposta2Bean> beans = app.creaListaProposteInviate();
        ObservableList<Proposta2Bean> obs = FXCollections.observableArrayList(beans);
        proposteTable.setItems(obs);
    }



    @FXML
    void tornaIndietro(ActionEvent event) {

        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);




    }

    private void aggiungiColonnaContatti() {
        TableColumn<Proposta2Bean, Proposta2Bean> contattiCol = new TableColumn<>(CONTATTI_LABEL);
        contattiCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        contattiCol.setCellFactory(col -> new TableCell<Proposta2Bean, Proposta2Bean>() {
            private final Button btn = creaBottone();

            private Button creaBottone() {
                Button b = new Button("CONTATTI_LABEL");
                b.setOnAction(e -> {
                    Proposta2Bean bean = getItem();
                    if (bean != null) {
                        mostraContatti(bean);
                    }
                });
                return b;
            }

            @Override
            protected void updateItem(Proposta2Bean bean, boolean empty) {
                super.updateItem(bean, empty);
                if (empty || bean == null) {
                    setGraphic(null);
                    return;
                }
                boolean abilitato = "ACCETTATA".equalsIgnoreCase(bean.getStato().toString());
                btn.setDisable(!abilitato);
                setGraphic(btn);
            }
        });

        contattiCol.setPrefWidth(100);
        contattiCol.setSortable(false);
        proposteTable.getColumns().add(contattiCol);
    }

    private void mostraContatti(Proposta2Bean bean) {
        if (bean == null) return;
        if (!"ACCETTATA".equalsIgnoreCase(bean.getStato().toString())) return;


        ContattiBean c = null;
        try {
            c=gestoreUtente.trovaContattiDaUsername(bean.getDestinatario());
        } catch (ClienteNonTrovatoException _) {
            showAlert("Cliente non trovato. Riprovare.");
            return;
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico. Riprovare più tardi.");
            return;
        }

        if (c != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(CONTATTI_LABEL);
            a.setContentText("Email: " + c.getEmail() + "\nTelefono: " + c.getTelefono());
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Contatti non disponibili");
            a.setContentText("Nessun contatto trovato per " + bean.getDestinatario());
            a.showAndWait();
        }
    }

}

