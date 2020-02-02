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
            Thread thread = new Thread(){
                public void run(){
                    try {
                        //doar de test
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("new Thread started");
                    if (formularData.expProffList == null) {
                        throw new RuntimeException("Nu ati completat experiente profesionale");
                    }

                    DbService.saveFormular(formularData);
                    System.out.println("Formularul a fost salvat in baza de date");
                    //TODO: afiseaza mesaj de suces in main actvity
                    String msg = "Trimitere formular in baza de date efectuata cu succes!";
                }
            };

            //pornire thread separat salvare date in db
            thread.start();

            // redirectionare pagina principala
            Intent i = new Intent(formularAplicareActivity,MainActivity.class);
            formularAplicareActivity.startActivity(i);


        } catch (Exception e) {
            //exceptii la preluare date din UI
            e.printStackTrace();
            Context context = formularAplicareActivity;
            CharSequence text = "A aparut o exceptie: " + e.getMessage();
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        //se trimit in baza de date

        //  DbService.connectionTest();
    }


    public void setFormularAplicareActivity(FormularAplicareActivity formularAplicare) {
        this.formularAplicareActivity = formularAplicare;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
}
