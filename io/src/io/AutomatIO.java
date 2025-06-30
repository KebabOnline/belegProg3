package io;

import domainLogic.Automat;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

public class AutomatIO {

    public static void speichernJOS(Automat automat, OutputStream os) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(automat);
        }
    }

    //JOS
    public static void speichernJOS(Automat automat, String dateiname) throws IOException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (FileOutputStream fos = new FileOutputStream(dateiname)) {
            speichernJOS(automat, fos);
        }
    }

    public static Automat ladenJOS(InputStream is) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Automat) ois.readObject();
        }
    }

    public static Automat ladenJOS(String dateiname) throws IOException, ClassNotFoundException {
        if (dateiname.contains("/") || dateiname.contains("\\")) {
            throw new IllegalArgumentException("Dateiname darf keine Pfade enthalten");
        }
        try (FileInputStream fis = new FileInputStream(dateiname)) {
            return ladenJOS(fis);
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
