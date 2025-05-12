package domainLogic;


import verwaltung.Hersteller;

import java.util.*;

public class Automat {
    private final int kapazitaet;
    private Map<String, Hersteller> herstellerMap = new HashMap<>();
    private Map<Integer, ObstkuchenImpl> kuchenMap = new HashMap<>();
    private int naechsteFachnummer = 1;

    public Automat(int kapazitaet) {
        this.kapazitaet = kapazitaet;
    }


    public boolean addHersteller(String name) {
        if (herstellerMap.containsKey(name)) {
            return false;
        }
        herstellerMap.put(name, new HerstellerImpl(name));
        return true;
    }


    public boolean removeHersteller(String name) {
        if (!herstellerMap.containsKey(name)) {
            return false;
        }
        herstellerMap.remove(name);
        return true;
    }

    // Create
    public boolean addKuchen(ObstkuchenImpl neuerKuchen) {
        if (kuchenMap.size() >= kapazitaet) {
            return false; // Automat voll
        }
        if (!herstellerMap.containsKey(neuerKuchen.getHersteller().getName())) {
            return false; // Hersteller unbekannt
        }
        neuerKuchen.setFachnummer(naechsteFachnummer++);
        neuerKuchen.setInspektionsdatum(new Date()); // aktuelles Datum setzen
        kuchenMap.put(neuerKuchen.getFachnummer(), neuerKuchen);
        return true;
    }

    // Delete
    public boolean removeKuchen(int fachnummer) {
        return kuchenMap.remove(fachnummer) != null;
    }

    // List
    public List<ObstkuchenImpl> getAlleKuchen(String typ) {
        List<ObstkuchenImpl> kuchenList = new ArrayList<>();
        for (ObstkuchenImpl kuchen : kuchenMap.values()) {
            if (typ == null || kuchen.getClass().getSimpleName().equalsIgnoreCase(typ)) {
                kuchenList.add(kuchen);
            }
        }
        return kuchenList;
    }

    // Update Date
    public boolean updateDatum(int fachnummer, Date neuesDatum) {
        ObstkuchenImpl kuchen = kuchenMap.get(fachnummer);
        if (kuchen == null) {
            return false;
        }
        kuchen.setInspektionsdatum(neuesDatum);
        return true;
    }

    public int getKapazitaet() {
        return kapazitaet;
    }
}

