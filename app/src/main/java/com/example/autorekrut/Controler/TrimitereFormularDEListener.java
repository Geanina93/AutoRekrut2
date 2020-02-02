package com.example.autorekrut.Controler;

import android.view.View;

import com.example.autorekrut.database.DbService;
import com.example.autorekrut.model.FormularDataDe;

public class TrimitereFormularDEListener implements View.OnClickListener {

    private FormularAplicareActivityDe formularAplicareActivityDe;


    @Override
    public void onClick(View v) {
        //TODO: vrem sa luam datele de pe formular si sa le bagam intr-un obiect
        FormularDataDe formularDataDe = formularAplicareActivityDe.getFormularDataDe();
        //se trimit in baza de date

        //  DbService.connectionTest();
        DbService.saveFormularDe(formularDataDe);
    }


    public void setFormularAplicareActivityDe(FormularAplicareActivityDe formularAplicareActivityDe) {
        this.formularAplicareActivityDe = formularAplicareActivityDe;
    }


}
