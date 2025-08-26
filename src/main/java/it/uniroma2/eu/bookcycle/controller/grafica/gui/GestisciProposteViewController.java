package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.controller.applicativo.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.dao.GestoreLibroScambio;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.eccezioni.LibroNonTrovatoException;
import it.uniroma2.eu.bookcycle.model.eccezioni.PersistenzaException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GestisciProposteViewController extends GraphicController {

    @FXML
    private TableColumn<Proposta2Bean, Void> gestisciCol;

    @FXML
    private TableColumn<Proposta2Bean, String> libroOffertoCol;

    @FXML
    private TableColumn<Proposta2Bean, String> libroRichiestoCol;

    @FXML
    private TableView<Proposta2Bean> proposteTable;

    @FXML
    private Button tornaIndietroButton;

    private  GestisciPropostaController app;

    private GestoreLibroScambio gestore=GestoreLibroScambio.getInstance();

    private GestoreUtente gestoreUtente=GestoreUtente.getInstance();

    String usernameLog = gestoreUtente.getClienteLoggato().getUsername();



    @FXML
    private void initialize() {
        libroOffertoCol.setCellValueFactory(cellData -> {
            Proposta2Bean proposta = cellData.getValue();
            String titolo;
            try {
                titolo = gestore.restituisciLibro(proposta.getLibroOfferto())
                        .getTitolo();
            } catch (LibroNonTrovatoException _) {
                titolo = "Libro non trovato";
            }
            return new SimpleStringProperty(titolo);
        });

        libroRichiestoCol.setCellValueFactory(cellData -> {
            Proposta2Bean proposta = cellData.getValue();
            String titolo;
            try {
                titolo = gestore.restituisciLibro(proposta.getLibroRichiesto())
                        .getTitolo();
            } catch (LibroNonTrovatoException _) {
                titolo = "Libro non trovato";
            }
            return new SimpleStringProperty(titolo);
        });

        caricaDati(usernameLog);


        gestisciCol.setCellFactory(col -> new TableCell<Proposta2Bean, Void>() {
            private final Button btn = creaBottone();

            private Button creaBottone() {
                Button b = new Button("Gestisci");
                b.setOnAction(e -> {
                    Proposta2Bean row = getTableView().getItems().get(getIndex());
                    long id = row.getIdProposta();
                    apriAccettaRifiuta(id, e);
                });
                return b;
            }
            @Override
            protected void updateItem(Void v, boolean empty) {
                super.updateItem(v, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    public void caricaDati(String usernameDestinatario) {
        try {
            app=new GestisciPropostaController();
        } catch (PersistenzaException _) {
           showAlert("Errore tecnico. Riprovare più tardi.");
        }
        List<Proposta2Bean> beans = app.creaListaBeanProposteRicevute(usernameDestinatario);
        proposteTable.setItems(FXCollections.observableArrayList(beans));
    }

    private void apriAccettaRifiuta(long idProposta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    ViewPath.ACCETTA_RIFIUTA_VIEW));
            Parent root = loader.load();

            AccettaRifiutaViewController ctrl = loader.getController();
            ctrl.setIdProposta(idProposta);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException _) {
            showAlert("Impossibile caricare la schermata.");
        }
    }

    @FXML
    void tornaIndietro (ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);

    }

}


