package it.uniroma2.eu.bookcycle.controller.gui;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ScegliLibriMieiViewController extends GraphicController {

    @FXML
    private VBox contenitoreLibri;

    @FXML
    private Button tornaIndietroButton;

    private PropostaParzialeBean propostaParzialeBean;

    private GestoreUtente gestore = new GestoreUtente();


    public void creaBeanProposta(PropostaParzialeBean propostaParzialeBean) {
        this.propostaParzialeBean = propostaParzialeBean;
        inizializzaConBean();
    }

    public void inizializzaConBean() {
        if (propostaParzialeBean != null) {
        } else {
        }
        Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();
        String username = cliente.getUsername();

        if (cliente instanceof Utente) {
            List<LibroBean> libriUtente = gestore.caricaLibriUtente(username);
            List<LibroBean> libriDisponibili = libriUtente.stream()
                    .collect(Collectors.toList());

            if (libriDisponibili.isEmpty()) {
                Label messaggio = new Label("Non puoi effettuare lo scambio perché non hai libri disponibili.");
                contenitoreLibri.getChildren().add(messaggio);
            } else {
                for (LibroBean libro : libriDisponibili) {
                    HBox riga = new HBox();
                    riga.setSpacing(10);

                    Label titoloLabel = new Label(libro.getTitolo());
                    Button scambiaButton = new Button("Scambia con questo");

                    scambiaButton.setOnAction(e -> {
                        String destinatario = propostaParzialeBean.getMittente();
                        long libroRichiesto = propostaParzialeBean.getLibroOfferto();

                        PropostaBean propostaBean = new PropostaBean();
                        propostaBean.setMittente(username);
                        propostaBean.setDestinatario(destinatario);
                        propostaBean.setLibroRichiesto(libroRichiesto);
                        propostaBean.setLibroOfferto(libro.getIdLibro());

                        InviaPropostaController controller = new InviaPropostaController();
                        controller.inviaProposta(propostaBean);

                        showAlert("Proposta inviata");
                    });

                    riga.getChildren().addAll(titoloLabel, scambiaButton);
                    contenitoreLibri.getChildren().add(riga);
                }
            }
        }
    }


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

}

