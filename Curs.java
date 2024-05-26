package org.example;

import java.util.*;

public class Curs {
    String denumire;
    int capacitateMaxima;
    String programDeStudii;
    ArrayList<Student> studenti = new ArrayList<Student>();

    public Curs(String denumire, String programDeStudii, int capacitateMaxima) {
        this.denumire = denumire;
        this.programDeStudii = programDeStudii;
        this.capacitateMaxima = capacitateMaxima;
    }
}
