package io;

import domainLogic.Automat;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class AutomatIO {

    //JOS
    public static void speichernJOS(Automat automat, String dateiname) throws IOException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dateiname))) {
            oos.writeObject(automat);
        }
    }

    public static Automat ladenJOS(String dateiname) throws IOException, ClassNotFoundException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dateiname))) {
            return (Automat) ois.readObject();
        }
    }

    // JBP
    public static void speichernJBP(Automat automat, String dateiname) throws IOException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (XMLEncoder encoder = new XMLEncoder(new FileOutputStream(dateiname))) {
            encoder.writeObject(automat);
        }
    }

    public static Automat ladenJBP(String dateiname) throws IOException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (XMLDecoder decoder = new XMLDecoder(new FileInputStream(dateiname))) {
            return (Automat) decoder.readObject();
        }
    }
}
