package org.example;

import java.util.*;

public class Student implements Comparable<Student> {
    String nume;
    double medie;
    ArrayList<String> preferinte = new ArrayList<String>();

    public double getMedie() {
        return medie;
    }

    public void setMedie(double medie) {
        this.medie = medie;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int compareTo(Student student) {
        if (getMedie() < ((Student)student).getMedie()) {
            return 1;
        } else if (getMedie() > ((Student)student).getMedie()){
            return -1;
        } else {
            return getNume().compareTo(((Student)student).getNume());
        }
    }
}
