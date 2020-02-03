package com.example.autorekrut.model;

import java.util.Date;

public class ExperientaProfesionala {
    public Date dataIncepere;
    public Date incheiereExperientaProfesionala;
    public String numeAngajator;
    public String tipAnsamblu;
    public String taraExperientaProfesionala;
    public String orasExperientaProfesionala;
    public int idInformatiiSofer;

    @Override
    public String toString() {
        return "ExperientaProfesionala{" +
                "dataIncepere=" + dataIncepere +
                ", incheiereExperientaProfesionala=" + incheiereExperientaProfesionala +
                ", numeAngajator='" + numeAngajator + '\'' +
                ", tipAnsamblu='" + tipAnsamblu + '\'' +
                ", taraExperientaProfesionala='" + taraExperientaProfesionala + '\'' +
                ", orasExperientaProfesionala='" + orasExperientaProfesionala + '\'' +
                ", idInformatiiSofer=" + idInformatiiSofer +
                '}';
    }
}
