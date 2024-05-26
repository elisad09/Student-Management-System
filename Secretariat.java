package org.example;

import java.lang.reflect.Array;
import java.util.*;
import java.io.*;


public class Secretariat {
    static ArrayList<Student> studenti = new ArrayList<Student>();
    static ArrayList<Curs> cursuri = new ArrayList<Curs>();

    public static void adaugaStudent(String nume, String tipStudent) throws StudentDejaAdaugat {
        for (Student st : studenti) {
            if (st.nume.equals(nume)) {
                throw new StudentDejaAdaugat("Student duplicat: " + st.nume);
            }
        }

        if (tipStudent.equals("licenta")) {
            StudentLicenta student = new StudentLicenta(nume);
            studenti.add(student);
        } else {
            StudentMaster student = new StudentMaster(nume);
            studenti.add(student);
        }
    }

    public static void citesteMediile(String directoryPath, String fileNamePrefix) {
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().startsWith(fileNamePrefix)) {
                        adaugaMedii(file);
                    }
                }
            }
        }
    }

    public static void adaugaMedii(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                String numeStudent = data[0];
                double medieStudent = Double.parseDouble(data[2]);
                for (Student st : studenti) {
                    if (st.getNume().equals(numeStudent)) {
                        st.setMedie(medieStudent);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void posteazaMedii(BufferedWriter writer) {
        Collections.sort(studenti);
        try {
            writer.write("***");
            writer.newLine();
            for (Student st : studenti) {
                writer.write(st.nume + " - " + st.medie);
                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void contestatie(String nume, double medieNoua) {
        for (Student st : studenti) {
            if (st.nume.equals(nume)) {
                st.setMedie(medieNoua);
            }
        }
    }

    public static void adaugaCurs(String denumire, String programDeStudii, int capacitateMaxima) {
        Curs curs = new Curs(denumire, programDeStudii, capacitateMaxima);
        cursuri.add(curs);
    }

    public static ArrayList<String> listaPreferinte(String comanda) {
        ArrayList<String> preferinte = new ArrayList<String>();
        String[] data = comanda.split(" ");
        for (int i = 4; i < data.length; i += 2) {
            preferinte.add(data[i]);
        }
        return preferinte;
    }

    public static void adaugaPreferinte(String nume, String comanda) {
        ArrayList<String> preferinteNew = listaPreferinte(comanda);
        for(Student st : studenti) {
            if (st.nume.equals(nume)) {
                st.preferinte.addAll(preferinteNew);
                return;
            }
        }
    }

    public static void repartizeazaStudent(Student st) {
        String tipStudent = null;
        if (st instanceof StudentMaster) {
            tipStudent = "master";
        } else if (st instanceof StudentLicenta) {
            tipStudent = "licenta";
        }
        for (String pref : st.preferinte) {
            for (Curs curs : cursuri) {
                if (pref.equals(curs.denumire)) {
                    if (tipStudent.equals(curs.programDeStudii)) {
                        if (curs.studenti.size() < curs.capacitateMaxima) {
                            curs.studenti.add(st);
                            return;
                        } else if (Double.compare(st.medie, curs.studenti.get(curs.studenti.size() - 1).medie) == 0) {
                            curs.studenti.add(st);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static void repartizeaza() {
        Collections.sort(studenti);

        for (Student st : studenti) {
            repartizeazaStudent(st);
        }
    }

    public static void posteazaCurs(String denumire, BufferedWriter writer) {
        try {
            writer.write("***");
            writer.newLine();

            for (Curs curs : cursuri) {
                if (curs.denumire.equals(denumire)) {
                    writer.write(denumire + " (" + curs.capacitateMaxima + ")");
                    writer.newLine();
                    curs.studenti.sort(Comparator.comparing(Student::getNume));
                    for (Student st : curs.studenti) {
                        writer.write(st.nume + " - " + st.medie);
                        writer.newLine();
                    }
                    return;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String gasesteCurs(String nume) {
        for (Curs curs : cursuri) {
            for (Student st : curs.studenti) {
                if (st.nume.equals(nume)) {
                    return curs.denumire;
                }
            }
        }
        return null;
    }

    public static void posteazaStudent(String nume, BufferedWriter writer) {
        try {
            writer.write("***");
            writer.newLine();

            for (Student st : studenti) {
                if (st.nume.equals(nume)) {
                    writer.write("Student ");

                    if (st instanceof StudentLicenta) writer.write("Licenta: ");
                    else if (st instanceof StudentMaster) writer.write("Master: ");

                    writer.write(st.nume + " - " + st.medie + " - " + gasesteCurs(nume));
                    writer.newLine();
                    break;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
