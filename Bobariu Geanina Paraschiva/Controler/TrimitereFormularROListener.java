package com.example.autorekrut.Controler;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.autorekrut.database.DbService;
import com.example.autorekrut.model.FormularData;

import java.text.ParseException;

public class TrimitereFormularROListener implements View.OnClickListener {

    private FormularAplicareActivity formularAplicareActivity;
    private Contact contact;

    @Override
    public void onClick(View v) {
        try {
            final FormularData formularData = formularAplicareActivity.getFormularDataFromUI();
            if (formularData.expProffList == null) {
                throw new RuntimeException("Nu ati completat experiente profesionale");
            }
            Thread thread = new Thread(){
                public void run(){
                    try {
                        //doar de test
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("new Thread started");

                    DbService.saveFormular(formularData);
                    System.out.println("Formularul a fost salvat in baza de date");
                    //TODO: afiseaza mesaj de suces in main actvity
                    String msg = "Trimitere formular in baza de date efectuata cu succes!";
                }
            };

            //pornire thread separat salvare date in db
            thread.start();

            System.out.println("Datele din formular au fost trimise spre salvare!!!!!!!!!!!!!!!!!!");
            Toast toast = Toast.makeText(formularAplicareActivity, "Formularul a fost trimis spre salvare in baza de date!", Toast.LENGTH_LONG);
            toast.show();

            Thread thread2 = new Thread(){
                public void run(){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // redirectionare pagina principala
                    Intent i = new Intent(formularAplicareActivity,MainActivity.class);
                    formularAplicareActivity.startActivity(i);
                }
            };
            //porneste fir executie care asteapta 3 sec si redirectioneaza in pagina principala
            thread2.start();

        } catch (Exception e) {
            //exceptii la preluare date din UI
            e.printStackTrace();

            Toast toast = Toast.makeText(formularAplicareActivity, "A aparut o exceptie: " + e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
    }


    public void setFormularAplicareActivity(FormularAplicareActivity formularAplicare) {
        this.formularAplicareActivity = formularAplicare;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
