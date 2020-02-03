package com.example.autorekrut.model;

import java.util.Date;
import java.util.List;

public class FormularData {

    public String firstName;
    public String secondName;
    public String country;
    public String region;
    public String place;
    public String zipCode;
    public int phone;
    public String email;
    public List<ExperientaProfesionala> expProffList;
    public String educatie;
    public boolean areStudiuLiceu;
    public boolean areStudiuFacultate;
    public boolean areStudiuScoalaProfesionala;
    public String limbaMaterna;
    public String limbaEnglezaGrad;
    public String limbaGermanaGrad;
    public boolean categoriaB;
    public boolean categoriaC1;
    public boolean categoriaC1E;
    public boolean categoriaC;
    public boolean categoriaCE;
    public boolean categoriaD;
    public boolean categoriaDE;
    public Date dataExpirarePermis;
    public Date expirareCartelaTahograf;
    public Date expirareAtestatMarfa;
    public boolean atestatCisterne;
    public boolean atestatColete;
    public boolean faraAtestat;
    public String level;

    @Override
    public String toString() {
        return "FormularData{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", place='" + place + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", experienteProfesionale=" + expProffList +
                ", educatie='" + educatie + '\'' +
                ", areStudiuLiceu=" + areStudiuLiceu +
                ", areStudiuFacultate=" + areStudiuFacultate +
                ", areStudiuScoalaProfesionala=" + areStudiuScoalaProfesionala +
                ", limbaMaterna='" + limbaMaterna + '\'' +
                ", limbaEnglezaGrad='" + limbaEnglezaGrad + '\'' +
                ", limbaGermanaGrad='" + limbaGermanaGrad + '\'' +
                ", categoriaB=" + categoriaB +
                ", categoriaC1=" + categoriaC1 +
                ", categoriaC1E=" + categoriaC1E +
                ", categoriaC=" + categoriaC +
                ", categoriaCE=" + categoriaCE +
                ", categoriaD=" + categoriaD +
                ", categoriaDE=" + categoriaDE +
                ", expirarePermis=" + dataExpirarePermis +
                ", expirareCartelaTahograf=" + expirareCartelaTahograf +
                ", expirareAtestatMarfa=" + expirareAtestatMarfa +
                ", atestatCisterne=" + atestatCisterne +
                ", atestatColete=" + atestatColete +
                ", faraAtestat=" + faraAtestat +
                ", level='" + level + '\'' +
                '}';
    }
}
