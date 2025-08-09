package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.InviaPropostaController;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScegliLibriMieiViewController {

    @FXML
    private VBox contenitoreLibri;

    @FXML
    private Button tornaIndietroButton;

    private PropostaParzialeBean propostaParzialeBean;


    public void creaBeanProposta(PropostaParzialeBean propostaParzialeBean) {
        this.propostaParzialeBean = propostaParzialeBean;
        inizializzaConBean();
    }

    public void inizializzaConBean() {
        if (propostaParzialeBean != null) {
        } else {
            System.out.println("[DEBUG] propostaParzialeBean è NULL");
        }
        Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();
        String username = cliente.getUsername();
        GestoreUtente gestore = new GestoreUtente();

        if (cliente instanceof Utente) {
            List<Libro> libriUtente = gestore.caricaLibriUtente(username);
            List<Libro> libriDisponibili = libriUtente.stream()
                    .filter(libro -> libro.getStato() == StatoLibro.DISPONIBILE)
                    .collect(Collectors.toList());

            if (libriDisponibili.isEmpty()) {
                Label messaggio = new Label("Non puoi effettuare lo scambio perché non hai libri disponibili.");
                contenitoreLibri.getChildren().add(messaggio);
            } else {
                for (Libro libro : libriDisponibili) {
                    HBox riga = new HBox();
                    riga.setSpacing(10);

                    Label titoloLabel = new Label(libro.getTitolo());
                    Button scambiaButton = new Button("Scambia con questo");

                    scambiaButton.setOnAction(e -> {
                        System.out.println("[DEBUG] propostaParzialeBean: " + propostaParzialeBean);
                        System.out.println("[DEBUG] Destinatario in bean: " + (propostaParzialeBean != null ? propostaParzialeBean.getDestinatario() : "null"));
                        String destinatario = propostaParzialeBean.getDestinatario();
                        long libroRichiesto = propostaParzialeBean.getLibroRichiesto();

                        PropostaBean propostaBean = new PropostaBean();
                        propostaBean.setMittente(username);
                        propostaBean.setDestinatario(destinatario);
                        propostaBean.setLibroRichiesto(libroRichiesto);
                        propostaBean.setLibroOfferto(libro.getIdLibro());

                        InviaPropostaController controller = new InviaPropostaController();
                        controller.inviaProposta(propostaBean);

                        mostraConferma("Proposta inviata!");
                    });

                    riga.getChildren().addAll(titoloLabel, scambiaButton);
                    contenitoreLibri.getChildren().add(riga);
                }
            }
        }
    }



//    public void creaBeanProposta(PropostaParzialeBean propostaParzialeBean) {
//        this.propostaParzialeBean = propostaParzialeBean;
//        inizializzaConBean();
//    }

    @FXML
    private void tornaIndietro(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/it/uniroma2/eu/bookcycle/gui/VediLibriTuttiView.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostraConferma(String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scambio");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}

