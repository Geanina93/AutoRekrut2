package com.example.autorekrut.database;

import com.example.autorekrut.model.Constants;
import com.example.autorekrut.model.ExperientaProfesionala;
import com.example.autorekrut.model.FormularData;
import com.example.autorekrut.model.FormularDataDe;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbService {

    public static void saveFormular(FormularData fd) {

        Long idInfoSofer = null;
        try {
            //incarcare driver
            Class.forName("org.postgresql.Driver");
            //creare conexiune

            Connection con = DriverManager.getConnection(Constants.URL);
            Statement stmt = con.createStatement();


            PreparedStatement pstmt = con.prepareStatement("INSERT INTO informatii_sofer(first_name, second_name, country, region, " +
                    "place, zip_code, phone, email, are_studiu_facultate, are_studiu_liceu, are_studiu_scoala_profesionala, " +
                    "limba_materna, limba_engleza_grad, limba_germana_grad, categoria_b, categoria_c1,categoria_c1e,categoria_c, " +
                    "categoria_ce,categoria_d, categoria_de, expirare_permis, expirare_cartela_tahograf, expirare_atestat_marfa, " +
                    "atestat_colete, atestat_cisterne, fara_atestat ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fd.firstName);
            pstmt.setString(2, fd.secondName);
            pstmt.setString(3, fd.country);
            pstmt.setString(4, fd.region);
            pstmt.setString(5, fd.place);
            pstmt.setString(6, fd.zipCode);
            pstmt.setInt(7, fd.phone);
            pstmt.setString(8, fd.email);
            pstmt.setBoolean(9, fd.areStudiuFacultate);
            pstmt.setBoolean(10, fd.areStudiuLiceu);
            pstmt.setBoolean(11, fd.areStudiuScoalaProfesionala);
            pstmt.setString(12, fd.limbaMaterna);
            pstmt.setString(13, fd.limbaEnglezaGrad);
            pstmt.setString(14, fd.limbaGermanaGrad);
            pstmt.setBoolean(15, fd.categoriaB);
            pstmt.setBoolean(16, fd.categoriaC1);
            pstmt.setBoolean(17, fd.categoriaC1E);
            pstmt.setBoolean(18, fd.categoriaC);
            pstmt.setBoolean(19, fd.categoriaCE);
            pstmt.setBoolean(20, fd.categoriaD);
            pstmt.setBoolean(21, fd.categoriaDE);
            if (fd.dataExpirarePermis != null) {

                pstmt.setDate(22, new Date(fd.dataExpirarePermis.getTime()));
            } else {
                pstmt.setDate(22, null);
            }
            if (fd.expirareCartelaTahograf != null) {
                pstmt.setDate(23, new Date(fd.expirareCartelaTahograf.getTime()));

            } else {
                pstmt.setDate(23, null);
            }
            if (fd.expirareAtestatMarfa != null) {
                pstmt.setDate(24, new Date(fd.expirareAtestatMarfa.getTime()));
            } else {
                pstmt.setDate(24, null);
            }
            pstmt.setBoolean(25, fd.atestatCisterne);
            pstmt.setBoolean(26, fd.atestatColete);
            pstmt.setBoolean(27, fd.faraAtestat);

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating informatii sofer failed, no rows affected.");
            }
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idInfoSofer = generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating informatii sofer  failed, no ID obtained.");
                }
            }

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        try {
            //incarcare driver
            Class.forName("org.postgresql.Driver");
            //creare conexiune

            Connection con = DriverManager.getConnection(Constants.URL);

            for (ExperientaProfesionala expProfesionala : fd.expProffList) {
                Statement stmt = con.createStatement();

                PreparedStatement pstmt = con.prepareStatement("INSERT INTO experienta_profesionala(data_incepere, incheiere_experienta_profesionala," +
                        "tip_ansamblu, nume_angajator, tara_experienta_profesionala, oras_experienta_profesionala, id_informatii_sofer) " +
                        "VALUES (?, ?, ?, ?, ?, ?,?);");
                pstmt.setDate(1, new Date(expProfesionala.dataIncepere.getTime()));
                pstmt.setDate(2, new Date(expProfesionala.incheiereExperientaProfesionala.getTime()));
                pstmt.setString(3, expProfesionala.tipAnsamblu);
                pstmt.setString(4, expProfesionala.numeAngajator);
                pstmt.setString(5, expProfesionala.taraExperientaProfesionala);
                pstmt.setString(6, expProfesionala.orasExperientaProfesionala);
                pstmt.setInt(7, idInfoSofer.intValue());

                pstmt.executeUpdate();
            }

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void saveFormularDe(FormularDataDe fdDe) {

        try {
            //incarcare driver
            Class.forName("org.postgresql.Driver");
            //creare conexiune
            Connection con = DriverManager.getConnection(Constants.URL);
            Statement stmt = con.createStatement();

            PreparedStatement pstmt = con.prepareStatement("INSERT INTO informatii_sofer_de(name_de, vorname, " +
                    "firmenname, anschrift, telefon, email_de, zeceangajati, cicizeciangajati, osutaangajati, cincisuteAngajati," +
                    " forderungen, vorteile, andredetails   ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, fdDe.nameDE);
            pstmt.setString(2, fdDe.vorname);
            pstmt.setString(3, fdDe.firmenname);
            pstmt.setString(4, fdDe.anschrift);
            pstmt.setString(5, fdDe.telefon);
            pstmt.setString(6, fdDe.emailDe);
            pstmt.setBoolean(7, fdDe.zeceAngajati);
            pstmt.setBoolean(8, fdDe.cicizeciAngajati);
            pstmt.setBoolean(9, fdDe.osutaAngajati);
            pstmt.setBoolean(10, fdDe.cincisuteAngajati);
            pstmt.setString(11, fdDe.forderungen);
            pstmt.setString(12, fdDe.vorteile);
            pstmt.setString(13, fdDe.andreDetails);


            pstmt.executeUpdate();

            con.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}