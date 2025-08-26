package it.uniroma2.eu.bookcycle.controller.grafica.gui;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.applicativo.InviaPropostaController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guicomune.ViewPath;
import it.uniroma2.eu.bookcycle.model.eccezioni.*;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Collections;
import java.util.List;

public class ScegliLibriMieiViewController extends GraphicController {

    @FXML
    private VBox contenitoreLibri;

    @FXML
    private Button tornaIndietroButton;

    private PropostaParzialeBean propostaParzialeBean;

    private GestoreUtente gestore = GestoreUtente.getInstance();


    public void creaBeanProposta(PropostaParzialeBean propostaParzialeBean) {
        this.propostaParzialeBean = propostaParzialeBean;
        inizializzaConBean();
    }

    public void inizializzaConBean() {
        String username = gestore.getClienteLoggato().getUsername();

        List<LibroBean> libri = caricaLibriUtente(username);

        if (libri.isEmpty()) {
            mostraMessaggioNessunLibro();
        } else {
            mostraLibriConBottone( libri);
        }
    }

    private List<LibroBean> caricaLibriUtente(String username) {


        try {
            return gestore.caricaLibriUtente(username);
        } catch (ClienteNonTrovatoException _) {
           showAlert("Cliente non trovato. Riprovare");
        }
        return Collections.emptyList();

    }

    private void mostraMessaggioNessunLibro() {
        Label messaggio = new Label("Non puoi effettuare lo scambio perché non hai libri disponibili.");
        contenitoreLibri.getChildren().add(messaggio);
    }

    private void mostraLibriConBottone( List<LibroBean> libri) {
        for (LibroBean libro : libri) {
            HBox riga = new HBox();
            riga.setSpacing(10);

            Label titoloLabel = new Label(libro.getTitolo());
            Button scambiaButton = creaBottoneScambio( libro);

            riga.getChildren().addAll(titoloLabel, scambiaButton);
            contenitoreLibri.getChildren().add(riga);
        }
    }

    private Button creaBottoneScambio( LibroBean libro) {
        Button scambiaButton = new Button("Scambia con questo");
        scambiaButton.setOnAction(e -> inviaProposta( libro));
        return scambiaButton;
    }

    private void inviaProposta(LibroBean libro) {
        long libroRichiesto = propostaParzialeBean.getLibroOfferto();

        PropostaBean propostaBean = new PropostaBean();
        propostaBean.setLibroRichiesto(libroRichiesto);
        propostaBean.setLibroOfferto(libro.getIdLibro());
        propostaBean.setDestinatario(gestore.trovaProprietarioLibro(libroRichiesto).getUsername());

        try {
            InviaPropostaController controller = new InviaPropostaController();
            controller.inviaProposta(propostaBean);
            showAlert("Proposta inviata");
        } catch (BeanInvalidoException _) {
            showAlert("Non sono state fornite abbastanza informazioni");
        } catch (OggettoInvalidoException _) {
            showAlert("Dati non validi: verifica i campi inseriti.");
        } catch (PersistenzaException _) {
            showAlert("Errore tecnico, riprovare più tardi.");
        }
    }

    @FXML
    private void tornaIndietro(ActionEvent event) {
        SceneManager.cambiaScena(event, ViewPath.VEDI_LIBRI_TUTTI_VIEW);

    }

}

