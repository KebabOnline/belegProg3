package gui;

import domainLogic.*;
import events.Beobachter;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;

import java.util.*;

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
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Kuchen hinzufügen");
        inputDialog.setHeaderText("Eigenschaften des Kuchens eingeben");
        inputDialog.setContentText("Format: [Hersteller] [Preis] \n[Nährwert] [Haltbarkeit in Stunden] \n[Allergene] [Obstsorte]");

        Optional<String> result = inputDialog.showAndWait();
        if (result.isEmpty()) return;

        String input = result.get().trim();
        if (input.isEmpty()) return;

        try {
            ObstkuchenImpl kuchen = parseKuchenString(input);
            if (!automat.addKuchen(kuchen)) {
                if (automat.getAlleKuchen(null).size() >= automat.getKapazitaet()) {
                    showError("Automat voll", "Der Automat ist bereits voll.");
                } else {
                    showError("Hersteller unbekannt", "Unbekannter Hersteller: " + kuchen.getHersteller().getName());
                }
            }
        } catch (IllegalArgumentException e) {
            showError("Eingabefehler", e.getMessage());
        } catch (Exception e) {
            showError("Fehler", "Unerwarteter Fehler: " + e.getMessage());
        }
    }

    public static ObstkuchenImpl parseKuchenString(String input) throws IllegalArgumentException { // Könnte in Util Modul bewegt werden?
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Eingabe darf nicht leer sein");
        }

        String[] parts = input.trim().split(" ");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Eingabe muss aus 6 Parametern bestehen.");
        }

        try {
            // Hersteller
            String herstellerName = parts[0];
            HerstellerImpl hersteller = new HerstellerImpl(herstellerName);

            // Preis
            BigDecimal preis = new BigDecimal(parts[1].replace(',', '.'));

            // Nährwert
            int naehrwert = Integer.parseInt(parts[2]);

            // Haltbarkeit (in Stunden)
            Duration haltbarkeit = Duration.ofHours(Long.parseLong(parts[3]));

            // Allergene
            Set<Allergen> allergene = new HashSet<>();
            String allergeneStr = parts[4];
            if (!allergeneStr.equals(",")) { // Einzelnes Komma = keine Allergene
                String[] allergeneArray = allergeneStr.split(",");
                for (String allergen : allergeneArray) {
                    try {
                        allergene.add(Allergen.valueOf(allergen.trim()));
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Unbekanntes Allergen: " + allergen);
                    }
                }
            }

            // Obstsorte
            String obstsorte = parts[5];

            // Kuchen erstellen
            return new ObstkuchenImpl(hersteller, allergene, naehrwert, haltbarkeit, preis, obstsorte);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ungültige Eingabe: " + e.getMessage());
        }
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
        Collection<Hersteller> hersteller = automat.getHersteller();
        for (Hersteller h : hersteller) {
            herstellerList.add(h.getName());
        }
    }

    private void updateKuchenList() {
        kuchenList.clear();
        List<ObstkuchenImpl> alleKuchen = automat.getAlleKuchen(null);
        for (ObstkuchenImpl k : alleKuchen) {
            kuchenList.add(new ObstkuchenFX(k));
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void aktualisiere(EventObject event) {
        Platform.runLater(() -> {
            updateKuchenList();
            updateHerstellerList();
        });
    }
}