package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.controller.applicativo.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.PersistenzaException;
import it.uniroma2.eu.bookcycle.model.domain.Sessione;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

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


    String username = Sessione.ottieniIstanza().getClienteLoggato().getUsername();

    @FXML
    private void initialize() {
        libroOffertoCol.setCellValueFactory(new PropertyValueFactory<>("titoloOfferto"));
        libroRichiestoCol.setCellValueFactory(new PropertyValueFactory<>("titoloRichiesto"));

        caricaDati(username);

        gestisciCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Gestisci");

            {
                btn.setOnAction(e -> {
                    Proposta2Bean row = getTableView().getItems().get(getIndex());
                    long id = row.getIdProposta();
                    apriAccettaRifiuta(id, e);
                });
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
        } catch (PersistenzaException e) {
           showAlert("Errore tecnico. Riprovare più tardi.");
        }
        var beans = app.creaListaBeanProposteRicevute(usernameDestinatario);
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
        } catch (IOException ex) {
            showAlert("Impossibile caricare la schermata.");
        }
    }






    @FXML
    void tornaIndietro (ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.PROFILO_UTENTE_VIEW);

    }

}


