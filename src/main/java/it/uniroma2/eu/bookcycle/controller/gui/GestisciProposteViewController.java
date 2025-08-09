package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.Proposta2Bean;
import it.uniroma2.eu.bookcycle.controller.GestisciPropostaController;
import it.uniroma2.eu.bookcycle.model.dao.FactoryDao;
import it.uniroma2.eu.bookcycle.model.domain.Cliente;
import it.uniroma2.eu.bookcycle.model.domain.PropostaDiScambio;
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

public class GestisciProposteViewController extends GraphicController{

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

    private final GestisciPropostaController app = new GestisciPropostaController();
    Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();

    @FXML
    private void initialize() {
        libroOffertoCol.setCellValueFactory(new PropertyValueFactory<>("titoloRichiesto"));
        libroRichiestoCol.setCellValueFactory(new PropertyValueFactory<>("titoloOfferto"));

        // Carico subito i dati dell'utente loggato
        caricaDati(cliente.getUsername());

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
        var beans = app.creaListaBeanProposteRicevute(usernameDestinatario);
        proposteTable.setItems(FXCollections.observableArrayList(beans));
    }

    private void apriAccettaRifiuta(long idProposta, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "/it/uniroma2/eu/bookcycle/gui/AccettaRifiutaView.fxml"));
            Parent root = loader.load();

            AccettaRifiutaViewController ctrl = loader.getController();
            ctrl.setIdProposta(idProposta);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }






    @FXML
    void tornaIndietro (ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/ProfiloView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Impossibile caricare la schermata del profilo.");
            e.printStackTrace();
        }

    }

}


