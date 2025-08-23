package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.Eccezioni.*;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import it.uniroma2.eu.bookcycle.model.domain.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ScegliLibriMieiViewController extends GraphicController {

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
        Cliente cliente = Sessione.ottieniIstanza().getClienteLoggato();
        String username = cliente.getUsername();

        if (cliente instanceof Utente) {
            List<LibroBean> libriUtente = null;
            try {
                GestoreUtente gestore = null;
                try {
                    gestore = new GestoreUtente();
                } catch (PersistenzaException e) {
                    showAlert("Errore tecnico. Riprovare più tardi");
                }
                libriUtente = gestore.caricaLibriUtente(username);
            } catch (ClienteNonTrovatoException e) {
                showAlert("Cliente non trovato.");
                libriUtente = Collections.emptyList();
            }
            List<LibroBean> libri = libriUtente.stream()
                    .collect(Collectors.toList());

            if (libri.isEmpty()) {
                Label messaggio = new Label("Non puoi effettuare lo scambio perché non hai libri disponibili.");
                contenitoreLibri.getChildren().add(messaggio);
            } else {
                for (LibroBean libro : libri) {
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

                        try {
                            InviaPropostaController controller = new InviaPropostaController();
                            controller.inviaProposta(propostaBean);
                        } catch (BeanInvalidoException ex) {
                            showAlert("Non sono state fornite abbastanza informazioni");
                            return;
                        } catch (RuoloClienteException ex) {
                            showAlert("Ruolo cliente sbagliato, rieffettuare il login");
                            return;
                        } catch (OggettoInvalidoException ex) {
                            showAlert("Dati non validi: verifica i campi inseriti.");
                            return;
                        } catch (PersistenzaException ex) {
                            showAlert("Errore tecnico, riprovare più tardi.");
                            return;
                        }

                        showAlert("Proposta inviata");
                    });

                    riga.getChildren().addAll(titoloLabel, scambiaButton);
                    contenitoreLibri.getChildren().add(riga);
                }
            }
        }else{
            showAlert("Il ruolo è sbagliato. Rieffettuare il login");
        }
    }


    @FXML
    private void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.VEDI_LIBRI_TUTTI_VIEW);

    }

}

