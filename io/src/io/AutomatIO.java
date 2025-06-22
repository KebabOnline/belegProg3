package io;

import domainLogic.Automat;

import java.io.*;

public class AutomatIO {

    // Methoden für Persistenz
    public static void speichern(Automat automat, String dateiname) throws IOException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dateiname))) {
            oos.writeObject(automat);
        }
    }

    public static Automat laden(String dateiname) throws IOException, ClassNotFoundException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dateiname))) {
            return (Automat) ois.readObject();
        }
    }
}
