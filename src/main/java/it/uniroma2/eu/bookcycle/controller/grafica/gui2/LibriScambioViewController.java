package it.uniroma2.eu.bookcycle.controller.grafica.gui2;

import it.uniroma2.eu.bookcycle.bean.LibroBean;
import it.uniroma2.eu.bookcycle.bean.PropostaParzialeBean;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.SceneManager;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.GraphicController;
import it.uniroma2.eu.bookcycle.controller.grafica.guiComune.ViewPath;
import it.uniroma2.eu.bookcycle.model.dao.GestoreUtente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.List;
import java.util.stream.Collectors;

public class LibriScambioViewController extends GraphicController {

    @FXML
    private TextField titoloField;

    @FXML
    private TableColumn<LibroBean, String> autoreColonna;

    @FXML
    private Button cercaButton;

    @FXML
    private Button profiloButton;

    @FXML
    private Button scambiaButton;

    @FXML
    private TableView<LibroBean> tabella;

    @FXML
    private TableColumn<LibroBean, String> titoloColonna;

    private ObservableList<LibroBean> listaLibriDisponibili;

    @FXML
    public void initialize() {
        titoloColonna.setCellValueFactory(new PropertyValueFactory<>("titolo"));
        autoreColonna.setCellValueFactory(new PropertyValueFactory<>("autore"));
        caricaLibri();
        scambiaButton.setDisable(true);
        tabella.setRowFactory(tv -> {
            TableRow<LibroBean> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    LibroBean libro = row.getItem();
                    tabella.getSelectionModel().select(libro);
                    scambiaButton.setDisable(false);
                }
            });
            return row;
        });
    }

    private void caricaLibri() {
        try {
            GestoreUtente gestoreUtente= new GestoreUtente();

            List<LibroBean> libriDisponibili = gestoreUtente.caricaLibriTutti();
            listaLibriDisponibili = FXCollections.observableArrayList(libriDisponibili);
            tabella.setItems(listaLibriDisponibili);
        } catch (Exception e) {
            showAlert("Errore durante il caricamento dei libri.");

        }
    }



    @FXML
    void apriScambioSelezionato(ActionEvent event) {
        LibroBean libroSelezionato = tabella.getSelectionModel().getSelectedItem();
        if (libroSelezionato == null) {
            showAlert("Seleziona un libro prima di procedere.");
            return;
        }

        PropostaParzialeBean propostaParzialeBean = new PropostaParzialeBean();
        propostaParzialeBean.setLibroOfferto(libroSelezionato.getIdLibro());
        propostaParzialeBean.setMittente(libroSelezionato.getUsernameProprietario());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ViewPath.SCEGLI_LIBRI_MIEI2_VIEW));
            Parent root = loader.load();

            ScegliLibriMiei2ViewController controller = loader.getController();
            controller.creaBeanProposta(propostaParzialeBean);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Errore durante il caricamento della schermata di selezione.");
        }
    }




    @FXML
    void filtraLibri(ActionEvent event) {

    String titolo = titoloField.getText().toLowerCase().trim();

    if (titolo.isEmpty()) {
        tabella.setItems(listaLibriDisponibili);
    } else {
        List<LibroBean> filtrati = listaLibriDisponibili.stream()
                .filter(libro -> libro.getTitolo().toLowerCase().contains(titolo))
                .collect(Collectors.toList());

        tabella.setItems(FXCollections.observableArrayList(filtrati));
    }
}




    @FXML
    void tornaAlProfilo(ActionEvent event) {
        SceneManager.cambiaScena(event,ViewPath.PROFILO2_VIEW);

    }

}

