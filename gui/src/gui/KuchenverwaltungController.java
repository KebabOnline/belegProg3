package gui;

import domainLogic.*;
import events.Beobachter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import kuchen.Allergen;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class KuchenverwaltungController implements Beobachter {

    @FXML private Button removeButton;
    @FXML private Button updateButton;
    @FXML private TableView<String> herstellerTable;
    @FXML private TableView<ObstkuchenFX> kuchenTable;

    private Automat automat;
    private final ObservableList<String> herstellerList = FXCollections.observableArrayList();
    private final ObservableList<ObstkuchenFX> kuchenList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        setupHerstellerTable();
        setupKuchenTable();
        setupButtons();
    }

    public void setAutomat(Automat automat) {
        this.automat = automat;
        this.automat.addListener(this);
        updateHerstellerList();
        updateKuchenList();
    }

    private void setupHerstellerTable() {
        // Hersteller Tabelle
        TableColumn<String, String> herstellerCol = new TableColumn<>("Hersteller");
        herstellerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
        herstellerTable.getColumns().add(herstellerCol);
        herstellerTable.setItems(herstellerList);
    }

    private void setupKuchenTable() {
        // Kuchen Tabelle
        TableColumn<ObstkuchenFX, Number> fachnummerCol = new TableColumn<>("Fachnummer");
        fachnummerCol.setCellValueFactory(data -> data.getValue().fachnummerProperty());

        TableColumn<ObstkuchenFX, String> herstellerCol = new TableColumn<>("Hersteller");
        herstellerCol.setCellValueFactory(data -> data.getValue().herstellerNameProperty());

        TableColumn<ObstkuchenFX, String> inspektionCol = new TableColumn<>("Inspektionsdatum");
        inspektionCol.setCellValueFactory(data -> data.getValue().inspektionsdatumProperty().asString());

        TableColumn<ObstkuchenFX, String> haltbarkeitCol = new TableColumn<>("Haltbarkeit");
        haltbarkeitCol.setCellValueFactory(data -> data.getValue().haltbarkeitProperty());

        kuchenTable.getColumns().addAll(fachnummerCol, herstellerCol, inspektionCol, haltbarkeitCol);
        kuchenTable.setItems(kuchenList);

    }

    private void setupButtons() {
        removeButton.disableProperty().bind(kuchenTable.getSelectionModel().selectedItemProperty().isNull());
        updateButton.disableProperty().bind(kuchenTable.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void handleAddKuchen() {
        HerstellerImpl hersteller = new HerstellerImpl("Anton");
        Set<Allergen> allergene = EnumSet.of(Allergen.Gluten, Allergen.Haselnuss);
        ObstkuchenImpl kuchen = new ObstkuchenImpl(hersteller, allergene, 20, Duration.ofDays(5), new BigDecimal("1.99"), "Apfel");
        automat.addKuchen(kuchen);
    }

    @FXML
    private void handleRemoveKuchen() {
        ObstkuchenFX selected = kuchenTable.getSelectionModel().getSelectedItem();
        automat.removeKuchen(selected.getFachnummer());
    }

    @FXML
    private void handleUpdateDatum() {
        ObstkuchenFX selected = kuchenTable.getSelectionModel().getSelectedItem();
        automat.updateDatum(selected.getFachnummer(), new Date());
    }

    private void updateHerstellerList() {
        herstellerList.clear();
        herstellerList.addAll(Arrays.asList("Anton", "Alina", "Luca"));
    }

    private void updateKuchenList() {
        kuchenList.clear();
        List<ObstkuchenImpl> alleKuchen = automat.getAlleKuchen(null);
        for (ObstkuchenImpl kuchen : alleKuchen) {
            kuchenList.add(new ObstkuchenFX(kuchen));
        }
    }

    @Override
    public void aktualisiere(EventObject event) {
        Platform.runLater(() -> {
            updateKuchenList();
            updateHerstellerList();
        });
    }
}