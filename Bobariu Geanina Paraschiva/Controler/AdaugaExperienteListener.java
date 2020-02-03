package com.example.autorekrut.Controler;

import android.app.DatePickerDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.autorekrut.R;

import java.util.Calendar;

public class AdaugaExperienteListener implements View.OnClickListener {

    public static int counterExperienta = 0;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
    DatePickerDialog datePickerDialog;
    private FormularAplicareActivity activity;

    @Override
    public void onClick(View v) {
        counterExperienta++;
        int idComponentaView = 100 * counterExperienta;
        LinearLayout experienteProfesionalaView = this.activity.findViewById(R.id.layoutExperientaProfesionala);

        final View dinDataEditText = new EditText(this.activity);
        ((EditText) dinDataEditText).setHint("Din data:");
        idComponentaView++;
        dinDataEditText.setId(idComponentaView);
        dinDataEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(dinDataEditText);
        dinDataEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == 0) {

                    datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            ((EditText) dinDataEditText).setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }, year, month, dayOfMonth);
                    datePickerDialog.show();
                }

                return true;
            }
        });


        final View panaInDataEditText = new EditText(this.activity);
        ((EditText) panaInDataEditText).setHint("Pana in data data:");
        idComponentaView++;
        panaInDataEditText.setId(idComponentaView);
        panaInDataEditText.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(panaInDataEditText);
        panaInDataEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == 0) {

                    datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                            ((EditText) panaInDataEditText).setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        }
                    }, year, month, dayOfMonth);
                    datePickerDialog.show();
                }

                return true;
            }
        });


        View tipAnsamblu = new EditText(this.activity);
        ((EditText) tipAnsamblu).setHint("Tipul de ansamblu:");
        idComponentaView++;
        tipAnsamblu.setId(idComponentaView);
        tipAnsamblu.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(tipAnsamblu);

        View numeAngajator = new EditText(this.activity);
        ((EditText) numeAngajator).setHint("Nume Angajator :");
        idComponentaView++;
        numeAngajator.setId(idComponentaView);
        numeAngajator.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(numeAngajator);

        View tara = new EditText(this.activity);
        ((EditText) tara).setHint("Tara:");
        idComponentaView++;
        tara.setId(idComponentaView);
        tara.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(tara);

        View oras = new EditText(this.activity);
        ((EditText) oras).setHint("Oras:");
        idComponentaView++;
        oras.setId(idComponentaView);
        oras.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        experienteProfesionalaView.addView(oras);

    }

    public void setActivity(FormularAplicareActivity activity) {
        this.activity = activity;

    }


}

