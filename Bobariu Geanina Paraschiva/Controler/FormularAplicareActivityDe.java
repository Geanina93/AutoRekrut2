package com.example.autorekrut.Controler;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autorekrut.R;
import com.example.autorekrut.model.FormularDataDe;

public class FormularAplicareActivityDe extends MainActivity {
    public TrimitereFormularDEListener trimitereFormularListenerDE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formular_aplicare_de);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addActionListenersDe();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public FormularDataDe getFormularDataDe() {
        FormularDataDe formularDataDe = new FormularDataDe();

        EditText nameDeEditText = findViewById(R.id.nameEditText);
        String valStringName = nameDeEditText.getText().toString();
        if (valStringName.isEmpty()) {
            nameDeEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            nameDeEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.nameDE = nameDeEditText.getText().toString();
        }
        EditText vornameEditText = findViewById(R.id.vornameEditText);
        String valStringVorname = vornameEditText.getText().toString();
        if (valStringVorname.isEmpty()) {
            vornameEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            vornameEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.vorname = vornameEditText.getText().toString();
        }
        EditText firmennameEditText = findViewById(R.id.firmennameEditText);
        String valStringfirmenname = firmennameEditText.getText().toString();
        if (valStringfirmenname.isEmpty()) {
            firmennameEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            firmennameEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.firmenname = firmennameEditText.getText().toString();
        }
        EditText anschriftEditText = findViewById(R.id.anschriftEditText);
        String valStringAnschrift = anschriftEditText.getText().toString();
        if (valStringAnschrift.isEmpty()) {
            anschriftEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            anschriftEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.anschrift = anschriftEditText.getText().toString();
        }
        EditText telefonEditText = findViewById(R.id.telefonEditText);
        String valStringTelefon = telefonEditText.getText().toString();
        if (valStringTelefon.isEmpty()) {
            telefonEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            telefonEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.telefon =telefonEditText.getText().toString();
        }

        EditText emailDeEditText = findViewById(R.id.emailDeEditText);
        String valStringEmailDe = emailDeEditText.getText().toString();
        if (valStringEmailDe.isEmpty()) {
            emailDeEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            emailDeEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.emailDe = emailDeEditText.getText().toString();
        }

        EditText forderungenEditText = findViewById(R.id.forderungenEditText);
        String valStringForderungen = forderungenEditText.getText().toString();
        if (valStringForderungen.isEmpty()) {
            forderungenEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            forderungenEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.forderungen = forderungenEditText.getText().toString();
        }

        EditText vorteileEditText = findViewById(R.id.vorteileEditText);
        String valStringVorteile = vorteileEditText.getText().toString();
        if (valStringVorteile.isEmpty()) {
            vorteileEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            vorteileEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.vorteile = vorteileEditText.getText().toString();
        }

        EditText andereDetailsEditText = findViewById(R.id.andereDetailsEditText);
        String valStringAndreDetails = andereDetailsEditText.getText().toString();
        if (valStringAndreDetails.isEmpty()) {
            andereDetailsEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Required\n", Toast.LENGTH_LONG).show();
        } else {
            andereDetailsEditText.setBackgroundColor(Color.WHITE);
            formularDataDe.andreDetails = andereDetailsEditText.getText().toString();
        }
        CheckBox zeceMuncitoriCb = (CheckBox) findViewById(R.id.zeceMuncitoriCb);
        formularDataDe.zeceAngajati = zeceMuncitoriCb.isChecked();

        CheckBox cincizeciMuncitoriCb = (CheckBox) findViewById(R.id.cincizeciMuncitoriCb);
        formularDataDe.cicizeciAngajati = cincizeciMuncitoriCb.isChecked();

        CheckBox osutaMuncitoriCb = (CheckBox) findViewById(R.id.osutaMuncitoriCb);
        formularDataDe.osutaAngajati = osutaMuncitoriCb.isChecked();

        CheckBox cincisuteMuncitoriCb = (CheckBox) findViewById(R.id.cincisuteMuncitoriCb);
        formularDataDe.cincisuteAngajati = cincisuteMuncitoriCb.isChecked();

        return formularDataDe;
    }

    private void addActionListenersDe() {
        Button trimiteFormularBtnDe = (Button) findViewById(R.id.trimitereFormularButonDe);//TODO: de adaugat butonu in interfata
        trimitereFormularListenerDE = new TrimitereFormularDEListener();
        trimiteFormularBtnDe.setOnClickListener(trimitereFormularListenerDE);
        trimitereFormularListenerDE.setFormularAplicareActivityDe(this);
    }
}