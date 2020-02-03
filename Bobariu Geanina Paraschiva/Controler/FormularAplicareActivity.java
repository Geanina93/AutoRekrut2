package com.example.autorekrut.Controler;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autorekrut.R;
import com.example.autorekrut.model.Constants;
import com.example.autorekrut.model.ExperientaProfesionala;
import com.example.autorekrut.model.FormularData;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FormularAplicareActivity extends MainActivity {

    private static final int PICKFILE_FOR_BULETIN_REQUEST_CODE = 11;
    private static final int PICKFILE_FOR_PERMIS_REQUEST_CODE = 10;
    private static final int PICKFILE_FOR_CARTELA_TAHOGRAF_REQUEST_CODE = 9;
    private static final int PICKFILE_FOR_ATESTAT_REQUEST_CODE = 8;
    private static final int PICKFILE_FOR_ALTE_DOCUMENTE_REQUEST_CODE = 7;


    public TrimitereFormularROListener trimitereFormularListener;
    DatePickerDialog datePickerDialog;


    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {


        if (requestCode == PICKFILE_FOR_BULETIN_REQUEST_CODE) {
            // TODO salvam fisierul deschis la buletin
            String pathFisierSelectat = returnIntent.getData().getPath();
            TextView buletinTextView = findViewById(R.id.pickFileForBuletin);
            buletinTextView.setText(pathFisierSelectat);
        } else if (requestCode == PICKFILE_FOR_PERMIS_REQUEST_CODE) {
            String pathFisierSelectat = returnIntent.getData().getPath();
            TextView permisTextView = findViewById(R.id.pickFileForPermisDeConducere);
            permisTextView.setText(pathFisierSelectat);
        } else if (requestCode == PICKFILE_FOR_CARTELA_TAHOGRAF_REQUEST_CODE) {
            String pathFisierSelectat = returnIntent.getData().getPath();
            TextView cartelaTahografTextView = findViewById(R.id.pickFileCartelaTahograf);
            cartelaTahografTextView.setText(pathFisierSelectat);
        } else if (requestCode == PICKFILE_FOR_ATESTAT_REQUEST_CODE) {
            String pathFisierSelectat = returnIntent.getData().getPath();
            TextView atestatTextView = findViewById(R.id.pickFileForAtestat);
            atestatTextView.setText(pathFisierSelectat);
        } else if (requestCode == PICKFILE_FOR_ALTE_DOCUMENTE_REQUEST_CODE) {
            String pathFisierSelectat = returnIntent.getData().getPath();
            TextView alteDocumenteTextView = findViewById(R.id.pickFileForAlteDocumente);
            alteDocumenteTextView.setText(pathFisierSelectat);
        }
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formular_aplicare);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addActionListeners();
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void addActionListeners() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        Button alegeDataExpirarePermis = (Button) findViewById(R.id.datePickerExpirarePermis);
        final TextView dataExpirarePermis = findViewById(R.id.dataExpirarePermis);

        Button alegeDataExpirareAtestatMarfa = findViewById(R.id.datePickerExpirareAtestatMarfa);
        final TextView dataExpirareAtestatMarfa = findViewById(R.id.dataExpirareAtestatMarfa);

        final Button alegeDataExpirareCartelaTahograf = findViewById(R.id.datePickerExpirareCartelaTahograf);
        final TextView dataExpirareCartelaTahograf = findViewById(R.id.dataExpirareCartelaTahograf);

        alegeDataExpirarePermis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(FormularAplicareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dataExpirarePermis.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        alegeDataExpirareAtestatMarfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(FormularAplicareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dataExpirareAtestatMarfa.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        alegeDataExpirareCartelaTahograf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(FormularAplicareActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        dataExpirareCartelaTahograf.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });

        Button trimiteFormularBtn = (Button) findViewById(R.id.trimitereFormularButon);
        trimitereFormularListener = new TrimitereFormularROListener();
        trimiteFormularBtn.setOnClickListener(trimitereFormularListener);
        trimitereFormularListener.setFormularAplicareActivity(this);

        Button addExperiencebtn = findViewById(R.id.addExperience);
        AdaugaExperienteListener adaugaExperienteListener = new AdaugaExperienteListener();
        adaugaExperienteListener.setActivity(this);
        addExperiencebtn.setOnClickListener(adaugaExperienteListener);


        Button incarcaBuletinBtn = findViewById(R.id.incarcaBuletin);
        incarcaBuletinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_FOR_BULETIN_REQUEST_CODE);
            }
        });


        Button incarcaPermisBtn = findViewById(R.id.incarcaPermisDeConducere);
        incarcaPermisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_FOR_PERMIS_REQUEST_CODE);

            }
        });

        Button incarcaCartelaTahografBtn = findViewById(R.id.incarcaCartelaTahograf);
        incarcaCartelaTahografBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_FOR_CARTELA_TAHOGRAF_REQUEST_CODE);
            }
        });


        Button incarcaAtestatBtn = findViewById(R.id.incarcaAtestat);
        incarcaAtestatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_FOR_ATESTAT_REQUEST_CODE);
            }
        });


        Button incarcaAlteDocumenteBtn = findViewById(R.id.incarcaAlteDocumente);
        incarcaAlteDocumenteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("*/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_FOR_ALTE_DOCUMENTE_REQUEST_CODE);
            }
        });
    }

    public FormularData getFormularDataFromUI() throws ParseException {
        FormularData formularData = new FormularData();
        boolean isValid = true;

        TextView dataExpirarePermis = findViewById(R.id.dataExpirarePermis);
        String dataExpirareString = dataExpirarePermis.getText().toString();  // where startDate is your TextView
        // same date format as your TextView supports
        if (dataExpirareString.isEmpty()) {
            dataExpirarePermis.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza data!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            Date dateExpirarePermis = Constants.simpleDateFormat.parse(dataExpirareString); // parses the string date to get a date object
            dataExpirarePermis.setBackgroundColor(Color.WHITE);
            formularData.dataExpirarePermis = dateExpirarePermis;
        }


        TextView dataExpirareCartelaTahograf = findViewById(R.id.dataExpirareCartelaTahograf);
        String dataExpirareCartelaTahografString = dataExpirareCartelaTahograf.getText().toString();

        if (dataExpirareCartelaTahografString.isEmpty()) {
            dataExpirareCartelaTahograf.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza data!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            Date dateExpirareCartelaTahograf = Constants.simpleDateFormat.parse(dataExpirareCartelaTahografString);
            dataExpirareCartelaTahograf.setBackgroundColor(Color.WHITE);
            formularData.expirareCartelaTahograf = dateExpirareCartelaTahograf;

        }


        TextView dataExpirareAtestatMarfa = findViewById(R.id.dataExpirareAtestatMarfa);
        String dataExpirareAtestatMarfaString = dataExpirareAtestatMarfa.getText().toString();

        if (dataExpirareAtestatMarfaString.isEmpty()) {
            dataExpirareAtestatMarfa.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza data!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            Date dateExpirareAtestatMarfa = Constants.simpleDateFormat.parse(dataExpirareAtestatMarfaString);
            dataExpirareAtestatMarfa.setBackgroundColor(Color.WHITE);
            formularData.expirareAtestatMarfa = dateExpirareAtestatMarfa;

        }


        //aici citim datele din interfata

        EditText firstNameEditText = findViewById(R.id.firstNumeEditText);
        String valStringFirstName = firstNameEditText.getText().toString();
        if (valStringFirstName.isEmpty()) {
            firstNameEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza numele!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            firstNameEditText.setBackgroundColor(Color.WHITE);
            formularData.firstName = firstNameEditText.getText().toString();
        }


        EditText secondNameEditText = findViewById(R.id.secondNameEditText);
        String valStringSecondName = secondNameEditText.getText().toString();
        if (valStringSecondName.isEmpty()) {
            secondNameEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza prenumele!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            secondNameEditText.setBackgroundColor(Color.WHITE);
            formularData.secondName = secondNameEditText.getText().toString();
        }

        EditText contryEditText = findViewById(R.id.contryEditText);
        String valStringCountry = contryEditText.getText().toString();
        if (valStringCountry.isEmpty()) {
            contryEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza tara!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            contryEditText.setBackgroundColor(Color.WHITE);
            formularData.country = contryEditText.getText().toString();
        }

        EditText regionEditText = findViewById(R.id.regionEditText);
        String valStringRegion = regionEditText.getText().toString();
        if (valStringRegion.isEmpty()) {
            regionEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza orasul!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            regionEditText.setBackgroundColor(Color.WHITE);
            formularData.region = regionEditText.getText().toString();
        }


        EditText placeEditText = findViewById(R.id.placeEditText);
        String valStringPlace = placeEditText.getText().toString();
        if (valStringPlace.isEmpty()) {
            placeEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza localitatea!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            placeEditText.setBackgroundColor(Color.WHITE);
            formularData.place = placeEditText.getText().toString();
        }

        EditText zipCodeEditText = findViewById(R.id.zipCodeEditText);
        String valStringZipCode = zipCodeEditText.getText().toString();
        if (valStringZipCode.isEmpty()) {
            zipCodeEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza cod postal!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            zipCodeEditText.setBackgroundColor(Color.WHITE);
            formularData.zipCode = zipCodeEditText.getText().toString();
        }

        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        String valStringPhone = phoneEditText.getText().toString();
        if (valStringPhone.isEmpty()) {
            phoneEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza email!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            phoneEditText.setBackgroundColor(Color.WHITE);
            formularData.phone = Integer.parseInt(valStringPhone);
        }

        EditText emailEditText = findViewById(R.id.emailEditText);
        String valStringEmail = emailEditText.getText().toString();
        if (valStringEmail.isEmpty()) {
            emailEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza cod postal!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            emailEditText.setBackgroundColor(Color.WHITE);
            formularData.email = emailEditText.getText().toString();
        }

        //TODO: seteaza experiente profesionale

        EditText limbaMaternaEditText = (EditText) findViewById(R.id.limbaMaternaTextBox);
        String valStringLimbaMaterna = limbaMaternaEditText.getText().toString();
        if (valStringLimbaMaterna.isEmpty()) {
            limbaMaternaEditText.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza limba materna !", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            limbaMaternaEditText.setBackgroundColor(Color.WHITE);
            formularData.limbaMaterna = limbaMaternaEditText.getText().toString();
        }

        Spinner limbaEnglezaGrad = (Spinner) findViewById(R.id.level_limba_engleza);
        String valStringlimbaEngleza = limbaEnglezaGrad.getSelectedItem().toString();
        if (valStringlimbaEngleza.isEmpty()) {
            limbaEnglezaGrad.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza nivel limba!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            limbaEnglezaGrad.setBackgroundColor(Color.WHITE);
            formularData.limbaEnglezaGrad = limbaEnglezaGrad.getSelectedItem().toString();
        }

        Spinner limbaGermanaGrad = (Spinner) findViewById(R.id.level_limba_germana);
        String valStringlimbaGermana = limbaGermanaGrad.getSelectedItem().toString();
        if (valStringlimbaGermana.isEmpty()) {
            limbaGermanaGrad.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(), "Completeaza nivel limba!", Toast.LENGTH_LONG).show();
            isValid = false;
        } else {
            limbaGermanaGrad.setBackgroundColor(Color.WHITE);
            formularData.limbaGermanaGrad = limbaGermanaGrad.getSelectedItem().toString();
        }


        CheckBox areStudiuFacultateCb = (CheckBox) findViewById(R.id.areStudiuFacultateCb);
        formularData.areStudiuFacultate = areStudiuFacultateCb.isChecked();

        CheckBox areStudiuLiceuCb = (CheckBox) findViewById(R.id.areStudiuLiceuCb);
        formularData.areStudiuLiceu = areStudiuLiceuCb.isChecked();

        CheckBox areStudiuScoalaProfesionalaCb = (CheckBox) findViewById(R.id.areStudiuScoalaProfesionalaCb);
        formularData.areStudiuScoalaProfesionala = areStudiuScoalaProfesionalaCb.isChecked();

        CheckBox categoriaBCb = (CheckBox) findViewById(R.id.categoriaBCb);
        formularData.categoriaB = categoriaBCb.isChecked();

        CheckBox categoriaC1Cb = (CheckBox) findViewById(R.id.categoriaC1Cb);
        formularData.categoriaC1 = categoriaC1Cb.isChecked();

        CheckBox categoriaCCb = (CheckBox) findViewById(R.id.categoriaCCb);
        formularData.categoriaC = categoriaCCb.isChecked();

        CheckBox categoriaC1ECb = (CheckBox) findViewById(R.id.categoriaC1ECb);
        formularData.categoriaC1E = categoriaC1ECb.isChecked();

        CheckBox categoriaCECb = (CheckBox) findViewById(R.id.categoriaCECb);
        formularData.categoriaCE = categoriaCECb.isChecked();

        CheckBox categoriaDCb = (CheckBox) findViewById(R.id.categoriaDCb);
        formularData.categoriaD = categoriaDCb.isChecked();

        CheckBox categoriaDECb = (CheckBox) findViewById(R.id.categoriaDECb);
        formularData.categoriaDE = categoriaDECb.isChecked();

        CheckBox atestatColeteCb = (CheckBox) findViewById(R.id.atestatColeteCb);
        formularData.atestatColete = atestatColeteCb.isChecked();

        CheckBox atestatCisterneCb = (CheckBox) findViewById(R.id.atestatCisterneCb);
        formularData.atestatCisterne = atestatCisterneCb.isChecked();

        CheckBox faraAtestatCb = (CheckBox) findViewById(R.id.faraAtestatCb);
        formularData.faraAtestat = faraAtestatCb.isChecked();


        Spinner levelSp = findViewById(R.id.level_limba_engleza);
        String level = levelSp.getSelectedItem().toString();

        levelSp = findViewById(R.id.level_limba_germana);
        String level1 = levelSp.getSelectedItem().toString();

        getExperienteProfesionale(formularData);
        if (!isValid) {
            throw new RuntimeException("Date incomplete!");
        }

        return formularData;
    }

    private void getExperienteProfesionale(FormularData formularData) throws ParseException {
        int nrExperienteAdaugate = AdaugaExperienteListener.counterExperienta;

        //declari si sa intantiezi o lista de experiente, in care dupa aia sa adaugi
        List<ExperientaProfesionala> expProfList = new ArrayList<ExperientaProfesionala>();
        for (int idExperienta = 1; idExperienta <= nrExperienteAdaugate; idExperienta++) {
            int idBaza = 100 * idExperienta;
            //creezi un obiec Experienta Profesionala in care pui valorile
            ExperientaProfesionala experientaProfesionala = new ExperientaProfesionala();

            EditText dinDataEditText = (EditText) findViewById(idBaza + 1);
            String dinDataString = dinDataEditText.getText().toString();
            Date dinDataDate = Constants.simpleDateFormat.parse(dinDataString);
            experientaProfesionala.dataIncepere = dinDataDate;

            EditText panaInDataEditText = (EditText) findViewById(idBaza + 2);
            String panaInDataString = panaInDataEditText.getText().toString();
            Date panaInDataDate = Constants.simpleDateFormat.parse(panaInDataString);
            experientaProfesionala.incheiereExperientaProfesionala = panaInDataDate;

            EditText tipAnsamblu = (EditText) findViewById(idBaza + 3);
            String tipAnsambluString = tipAnsamblu.getText().toString();
            experientaProfesionala.tipAnsamblu = tipAnsambluString;

            EditText numeAngajatorEditText = (EditText) findViewById(idBaza + 4);
            String numeAngajatorString = numeAngajatorEditText.getText().toString();
            experientaProfesionala.numeAngajator = numeAngajatorString;

            EditText taraEditTex = (EditText) findViewById(idBaza + 5);
            String taraExperientaProfesionalaString = taraEditTex.getText().toString();
            experientaProfesionala.taraExperientaProfesionala = taraExperientaProfesionalaString;

            EditText orasEditText = (EditText) findViewById(idBaza + 6);
            String orasString = orasEditText.getText().toString();
            experientaProfesionala.orasExperientaProfesionala = orasString;

            //in formular data adaugi obiectul experienta
            expProfList.add(experientaProfesionala);

        }

        formularData.expProffList = expProfList;

    }

}