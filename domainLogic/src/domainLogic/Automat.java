package domainLogic;


import events.*;
import verwaltung.Hersteller;

import java.io.*;
import java.util.*;

public class Automat implements Subjekt, Serializable {
    private static final long serialVersionUID = 1L;
    private int kapazitaet;
    private Map<String, Hersteller> herstellerMap = new HashMap<>();
    private Map<Integer, ObstkuchenImpl> kuchenMap = new HashMap<>();
    private int naechsteFachnummer = 1;
    private transient List<Beobachter> beobachterList = new ArrayList<>();

    public Automat(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    // Konstruktor für JBP
    public Automat() {
        this(10);
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


    // Subjekt Methoden
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

    // Methode zur Wiederherstellung der Beobachterliste
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        beobachterList = new ArrayList<>();
    }

    // Getter und Setter for JBP
    public synchronized int getKapazitaet() {
        return kapazitaet;
    }

    public synchronized void setKapazitaet(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }

    public synchronized Map<String, Hersteller> getHerstellerMap() {
        return herstellerMap;
    }

    public synchronized void  setHerstellerMap(Map<String, Hersteller> herstellerMap) {
        this.herstellerMap = herstellerMap != null ? herstellerMap : new HashMap<>();
    }

    public synchronized Map<Integer, ObstkuchenImpl> getKuchenMap() {
        return kuchenMap;
    }

    public synchronized void setKuchenMap(Map<Integer, ObstkuchenImpl> kuchenMap) {
        this.kuchenMap = kuchenMap != null ? kuchenMap : new HashMap<>();
    }

    public synchronized int getNaechsteFachnummer() {
        return naechsteFachnummer;
    }

    public synchronized void setNaechsteFachnummer(int naechsteFachnummer) {
        this.naechsteFachnummer = naechsteFachnummer;
    }
}

