package gui;


import domainLogic.ObstkuchenImpl;
import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Date;
import java.util.stream.Collectors;


public class ObstkuchenFX {
    private final StringProperty herstellerName;
    private final StringProperty haltbarkeit; // Formatiert als String
    private final ObjectProperty<Date> inspektionsdatum;
    private final IntegerProperty fachnummer;
    private final ObstkuchenImpl originalKuchen;


    public ObstkuchenFX(ObstkuchenImpl kuchen) {
        this.herstellerName = new SimpleStringProperty(kuchen.getHersteller().getName());
        this.haltbarkeit = new SimpleStringProperty(haltbarkeitFormatieren(kuchen.getHaltbarkeit()));
        this.inspektionsdatum = new SimpleObjectProperty<>(kuchen.getInspektionsdatum());
        this.fachnummer = new SimpleIntegerProperty(kuchen.getFachnummer());
        this.originalKuchen = kuchen;
    }

    public ObstkuchenImpl getOriginalKuchen() {
        return originalKuchen; }

    private String haltbarkeitFormatieren(Duration haltbarkeit) {
        long days = haltbarkeit.toDays();
        if (days > 0) {
            return days + " Tage";
        }
        return "Weniger als 1 Tag";
    }

    // Hersteller

    public String getHersteller() {
        return herstellerName.get();
    }

    public void setHersteller(String value) {
        herstellerName.set(value);
    }

    public StringProperty herstellerNameProperty() {
        return herstellerName;
    }

    // Haltbarkeit

    public String getHaltbarkeit() {
        return haltbarkeit.get();
    }

    public void setHaltbarkeit(String value) {
        haltbarkeit.set(value);
    }

    public StringProperty haltbarkeitProperty() {
        return haltbarkeit;
    }

    // Inspektionsdatum

    public Date getInspektionsdatum() {
        return inspektionsdatum.get();
    }

    public void setInspektionsdatum(Date value) {
        inspektionsdatum.set(value);
    }

    public ObjectProperty<Date> inspektionsdatumProperty() {
        return inspektionsdatum;
    }

    // Fachnummer
    public int getFachnummer() {
        return fachnummer.get();
    }

    public void setFachnummer(int value) {
        fachnummer.set(value);
    }

    public IntegerProperty fachnummerProperty() {
        return fachnummer;
    }

}