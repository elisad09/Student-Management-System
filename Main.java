package org.example;

import java.io.*;
import java.util.*;

public class Main {
    // metoda care citeste toate liniile din fisierele .in
    public static ArrayList<String> citesteComenzi(String file) {
        ArrayList<String> comenzi = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                comenzi.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return comenzi;
    }


    public static void main(String[] args) throws IOException {
        String inFile = null;
        String outFile = null;

        assert args != null;
        inFile = "./src/main/resources/";
        inFile = inFile.concat(args[0] + "/" + args[0] + ".in");
        outFile = "./src/main/resources/";
        outFile = outFile.concat(args[0] + "/" + args[0] + ".out");

        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));


        ArrayList<String> comenzi = citesteComenzi(inFile);
        for (String comanda : comenzi) {
            if (comanda.contains("adauga_student")) {
                String[] data = comanda.split(" ");
                String tipStudent = data[2];
                String numeStudent = data[4];
                try {
                    Secretariat.adaugaStudent(numeStudent, tipStudent);
                } catch (StudentDejaAdaugat e) {
                    writer.write("***");
                    writer.newLine();
                    writer.write("Student duplicat: " + numeStudent);
                    writer.newLine();
                }
            } else if (comanda.equals("citeste_mediile")) {
                String directoryPath = "./src/main/resources/";
                directoryPath = directoryPath.concat(args[0]);
                Secretariat.citesteMediile(directoryPath, "note_");
            } else if (comanda.equals("posteaza_mediile")) {
                Secretariat.posteazaMedii(writer);
            } else if (comanda.contains("contestatie")) {
                String[] data = comanda.split(" ");
                String nume = data[2];
                double medieNoua = Double.parseDouble(data[4]);
                Secretariat.contestatie(nume, medieNoua);
            } else if (comanda.contains("adauga_curs")) {
                String[] data = comanda.split(" ");
                String denumire = data[4];
                String programDeStudii = data[2];
                int capacitateMaxima = Integer.parseInt(data[6]);
                Secretariat.adaugaCurs(denumire, programDeStudii, capacitateMaxima);
            } else if (comanda.contains("adauga_preferinte")) {
                String[] data = comanda.split(" ");
                String nume = data[2];
                Secretariat.adaugaPreferinte(nume, comanda);
            } else if (comanda.equals("repartizeaza")) {
                Secretariat.repartizeaza();
            } else if (comanda.contains("posteaza_curs")) {
                String[] data = comanda.split(" ");
                String numeCurs = data[2];
                Secretariat.posteazaCurs(numeCurs, writer);
            } else if (comanda.contains("posteaza_student")) {
                String[] data = comanda.split(" ");
                String numeStudent = data[2];
                Secretariat.posteazaStudent(numeStudent, writer);
            }
        }
        writer.close();
        Secretariat.studenti.clear();
        for (Curs curs : Secretariat.cursuri) {
            curs.studenti.clear();
        }
        Secretariat.cursuri.clear();
    }
}
