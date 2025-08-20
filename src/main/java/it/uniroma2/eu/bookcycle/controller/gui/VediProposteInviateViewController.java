package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.ContattiBean;
import it.uniroma2.eu.bookcycle.bean.Proposta4Bean;
import it.uniroma2.eu.bookcycle.controller.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.SceneManager;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class VediProposteInviateViewController {

    @FXML
    private TableColumn<Proposta4Bean, String> statiColonna;

    @FXML
    private TableView<Proposta4Bean> proposteTable;


    @FXML
    private TableColumn<Proposta4Bean, String> titoliColonna;

    @FXML
    private Button tornaIndietroButton;
    private ObservableList<Proposta4Bean> listaProposte;

    private static final String CONTATTI_LABEL = "Contatti";


    @FXML
    public void initialize() {
        titoliColonna.setCellValueFactory(new PropertyValueFactory<>("titoloOfferto"));

        statiColonna.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStato().toString()));
        aggiungiColonnaContatti();
        caricaLibriProposte();
    }

    void caricaLibriProposte(){
        String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();
        InviaPropostaController app = new InviaPropostaController();
        List<Proposta4Bean> beans = app.creaListaBeanProposteInviate(username);
        ObservableList<Proposta4Bean> obs = FXCollections.observableArrayList(beans);
        proposteTable.setItems(obs);
    }

    @FXML
    void tornaIndietro(ActionEvent event) {

        SceneManager.cambiaScena(event, "/it/uniroma2/eu/bookcycle/gui/ProfiloUtenteView.fxml");




    }

    private void aggiungiColonnaContatti() {
        TableColumn<Proposta4Bean, Proposta4Bean> contattiCol = new TableColumn<>(CONTATTI_LABEL);
        contattiCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        contattiCol.setCellFactory(col -> new TableCell<Proposta4Bean, Proposta4Bean>() {
            private final Button btn = new Button("CONTATTI_LABEL");

            {
                btn.setOnAction(e -> {
                    Proposta4Bean bean = getItem();
                    if (bean != null) {
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
                boolean abilitato = "ACCETTATA".equalsIgnoreCase(bean.getStato().toString());
                btn.setDisable(!abilitato);
                setGraphic(btn);
            }
        });

        contattiCol.setPrefWidth(100);
        contattiCol.setSortable(false);
        proposteTable.getColumns().add(contattiCol);
    }

    private void mostraContatti(Proposta4Bean bean) {
        if (bean == null) return;
        if (!"ACCETTATA".equalsIgnoreCase(bean.getStato().toString())) return;

        String altroUsername = bean.getUsernameDestinatario();
        ContattiBean c = new GestoreUtente().trovaContattiDaUsername(altroUsername);

        if (c != null) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText(CONTATTI_LABEL);
            a.setContentText("Email: " + c.getEmail() + "\nTelefono: " + c.getTelefono());
            a.showAndWait();
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setHeaderText("Contatti non disponibili");
            a.setContentText("Nessun contatto trovato per " + altroUsername);
            a.showAndWait();
        }
    }

}

