package domainLogic;


import events.*;
import verwaltung.Hersteller;

import java.util.*;

public class Automat implements Subjekt {
    private final int kapazitaet;
    private Map<String, Hersteller> herstellerMap = new HashMap<>();
    private Map<Integer, ObstkuchenImpl> kuchenMap = new HashMap<>();
    private int naechsteFachnummer = 1;
    private final List<Beobachter> beobachterList = new ArrayList<>();

    public Automat(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }


    public synchronized boolean addHersteller(String name) {
        if (herstellerMap.containsKey(name)) {
            return false;
        }
        herstellerMap.put(name, new HerstellerImpl(name));
        return true;
    }


    public synchronized boolean removeHersteller(String name) {
        if (!herstellerMap.containsKey(name)) {
            return false;
        }
        herstellerMap.remove(name);
        return true;
    }

    public synchronized Collection<Hersteller> getHersteller() {
        return new ArrayList<>(herstellerMap.values());
    }

    // Create
    public synchronized boolean addKuchen(ObstkuchenImpl neuerKuchen) {
        if (kuchenMap.size() >= kapazitaet) {
            return false; // Automat voll
        }
        if (!herstellerMap.containsKey(neuerKuchen.getHersteller().getName())) {
            return false; // Hersteller unbekannt
        }
        neuerKuchen.setFachnummer(naechsteFachnummer++);
        //neuerKuchen.setInspektionsdatum(new Date()); // aktuelles Datum setzen
        kuchenMap.put(neuerKuchen.getFachnummer(), neuerKuchen);
        benachrichtige(new AddKuchenEvent(this, neuerKuchen));
        return true;
    }

    // Delete
    public synchronized boolean removeKuchen(int fachnummer) {
        ObstkuchenImpl entfernterKuchen = kuchenMap.remove(fachnummer);
        if (entfernterKuchen != null) {
            benachrichtige(new RemoveKuchenEvent(this, fachnummer));
            return true;
        }
        return false;
    }

    // List
    public synchronized List<ObstkuchenImpl> getAlleKuchen(String typ) {
        List<ObstkuchenImpl> kuchenList = new ArrayList<>();
        for (ObstkuchenImpl kuchen : kuchenMap.values()) {
            if (typ == null || kuchen.getClass().getSimpleName().equalsIgnoreCase(typ)) {
                kuchenList.add(kuchen);
            }
        }
        return kuchenList;
    }

    // Update Date
    public synchronized boolean updateDatum(int fachnummer, Date neuesDatum) {
        ObstkuchenImpl kuchen = kuchenMap.get(fachnummer);
        if (kuchen == null) {
            return false;
        }
        kuchen.setInspektionsdatum(neuesDatum);
        benachrichtige(new UpdateKuchenEvent(this, fachnummer));
        return true;
    }

    public synchronized int getKapazitaet() {
        return kapazitaet;
    }

    @Override
    public synchronized void addListener(Beobachter beobachter) {
        this.beobachterList.add(beobachter);
    }

    @Override
    public synchronized void removeListener(Beobachter listener) {
        this.beobachterList.remove(listener);
    }

    @Override
    public void benachrichtige(EventObject event) {
        List<Beobachter> currentBeobachter = new ArrayList<>(beobachterList); // Kopie erstellen
        for (Beobachter beobachter : currentBeobachter) {
            beobachter.aktualisiere(event);
        }
    }
}

