package com.example.autorekrut.Controler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.autorekrut.R;

public class MainActivity extends AppCompatActivity {
    private final static String deLanguageCode = "de";
    private final static String roLanguageCode = "ro";

    private static String selectedLanguage = roLanguageCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.activity_main:
                Intent intentMenu = new Intent(this, MainActivity.class);
                this.startActivity(intentMenu);
                break;
            case R.id.about:
                Intent intentAbout = new Intent(this, About.class);
                this.startActivity(intentAbout);
                break;
            case R.id.apply:
                Intent intentApply = new Intent(this, FormularAplicareActivity.class);
                this.startActivity(intentApply);
                break;
            case R.id.applyDe:
                Intent intentApplyDe = new Intent(this, FormularAplicareActivityDe.class);
                this.startActivity(intentApplyDe);
                break;
            case R.id.contact:
                Intent intentContact = new Intent(this, Contact.class);
                this.startActivity(intentContact);
                break;
            case R.id.genereazaPdf:
                Intent intentGenereazaPdf = new Intent(this, PDFGenerator.class);
                this.startActivity(intentGenereazaPdf);
                break;
            case R.id.erzeugenPdf:
                Intent intentErzeugenPdf = new Intent(this, PDFErzeugen.class);
                this.startActivity(intentErzeugenPdf);
                break;
            case R.id.chooseDe:
                LocaleHelper.setLocale(MainActivity.this, deLanguageCode);
                selectedLanguage = deLanguageCode;
                recreate();
                break;
            case R.id.chooseRo:
                LocaleHelper.setLocale(MainActivity.this, roLanguageCode);
                selectedLanguage = roLanguageCode;
                recreate();

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        System.out.println("onPrepareOptionsMenu!!!!!!!!!!!");
        if (Build.VERSION.SDK_INT > 11) {

            invalidateOptionsMenu();


        }
        //TODO: set current language depending on system locale
        if (selectedLanguage.equals(roLanguageCode)) {
            menu.findItem(R.id.chooseRo).setVisible(false);
            menu.findItem(R.id.chooseDe).setVisible(true);

            menu.findItem(R.id.apply).setVisible(true);
            menu.findItem(R.id.applyDe).setVisible(false);

            menu.findItem(R.id.genereazaPdf).setVisible(true);
            menu.findItem(R.id.erzeugenPdf).setVisible(false);

        } else {
            menu.findItem(R.id.chooseRo).setVisible(true);
            menu.findItem(R.id.chooseDe).setVisible(false);

            menu.findItem(R.id.apply).setVisible(false);
            menu.findItem(R.id.applyDe).setVisible(true);

            menu.findItem(R.id.genereazaPdf).setVisible(false);
            menu.findItem(R.id.erzeugenPdf).setVisible(true);

        }

        return super.onPrepareOptionsMenu(menu);
    }

}


