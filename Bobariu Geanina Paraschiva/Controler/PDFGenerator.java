package com.example.autorekrut.Controler;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.autorekrut.R;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.autorekrut.model.Constants;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PDFGenerator extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    // private EditText mContentEditText;
    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfgenerator);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCreateButton = (Button) findViewById(R.id.button_create);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    createPdfWrapper();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public PdfPCell createCell(String content, float borderWidth, int colspan, int vAlignment) {
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Paragraph(content));
        cell.setVerticalAlignment(vAlignment);
        cell.setBorder(PdfPCell.BOX);
        cell.setBorderWidth(borderWidth);

        return cell;
    }

    private void createPdf() throws FileNotFoundException, DocumentException {
        try {
            //incarcare driver
            Class.forName("org.postgresql.Driver");
            //creare conexiune
            Connection conn = DriverManager.getConnection(Constants.URL);
            Statement stmt = conn.createStatement();

            /* Define the SQL query */
            ResultSet query_set = stmt.executeQuery("SELECT * FROM informatii_sofer");
            /* Step-2: Initialize PDF documents - logical objects */
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i(TAG, "Created a new directory for PDF");
            }

            pdfFile = new File(docsFolder.getAbsolutePath(), "PdfGenerator.pdf");
            OutputStream output = new FileOutputStream(pdfFile);
            Document my_pdf_report = new Document(PageSize.A1.rotate(), 0f, 0f, 100f, 0f);


            PdfWriter.getInstance(my_pdf_report, output);
            my_pdf_report.open();
            //we have four columns in our table
            PdfPTable table = new PdfPTable(27);
            //create a table object
            PdfPCell table_cell;
            // set up table


            // add header
            table.addCell(createCell("Nume", 2, 1, 3));
            table.addCell(createCell("Prenume", 2, 1, 3));
            table.addCell(createCell("Tara", 2, 1, 3));
            table.addCell(createCell("Judet", 2, 1, 3));
            table.addCell(createCell("Localitate", 2, 1, 3));
            table.addCell(createCell("Cod Postal", 2, 1, 3));
            table.addCell(createCell("Telefon", 2, 1, 3));
            table.addCell(createCell("Email", 2, 1, 3));
            table.addCell(createCell("Studii facultate", 2, 1, 3));
            table.addCell(createCell("Studii liceu", 2, 1, 3));
            table.addCell(createCell("Studii scoala profesionala", 2, 1, 3));
            table.addCell(createCell("Limba Materna", 2, 1, 3));
            table.addCell(createCell("Nivel Germana", 2, 1, 3));
            table.addCell(createCell("Nivel Engleza", 2, 1, 3));
            table.addCell(createCell("Permis catB", 2, 1, 3));
            table.addCell(createCell("Permis catC1", 2, 1, 3));
            table.addCell(createCell("Permis catC1E", 2, 1, 3));
            table.addCell(createCell("Permis catC", 2, 1, 3));
            table.addCell(createCell("Permis catCE", 2, 1, 3));
            table.addCell(createCell("Permis catD", 2, 1, 3));
            table.addCell(createCell("Permis catDE", 2, 1, 3));
            table.addCell(createCell("Expirare permis", 2, 1, 3));
            table.addCell(createCell("Expirare cartela Tahograf", 2, 1, 3));
            table.addCell(createCell("Expirare atestat", 2, 1, 3));
            table.addCell(createCell("Atestat Colete", 2, 1, 3));
            table.addCell(createCell("Atestat Cisterne", 2, 1, 3));
            table.addCell(createCell("Fara Atestat", 2, 1, 3));

          //  table.setWidths(new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7});

            while (query_set.next()) {

                String first_name = query_set.getString("first_name");
                table_cell = new PdfPCell(new Phrase(first_name));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String second_name = query_set.getString("second_name");
                table_cell = new PdfPCell(new Phrase(second_name));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String country = query_set.getString("country");
                table_cell = new PdfPCell(new Phrase(country));
                table.addCell(table_cell);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

                String region = query_set.getString("region");
                table_cell = new PdfPCell(new Phrase(region));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String place = query_set.getString("place");
                table_cell = new PdfPCell(new Phrase(place));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String zip_code = query_set.getString("zip_code");
                table_cell = new PdfPCell(new Phrase(zip_code));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String phone = query_set.getString("phone");
                table_cell = new PdfPCell(new Phrase(phone));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String email = query_set.getString("email");
                table_cell = new PdfPCell(new Phrase(email));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String are_studiu_facultate = query_set.getString("are_studiu_facultate");
                table_cell = new PdfPCell(new Phrase(are_studiu_facultate));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String are_studiu_liceu = query_set.getString("are_studiu_liceu");
                table_cell = new PdfPCell(new Phrase(are_studiu_liceu));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String are_studiu_scoala_profesionala = query_set.getString("are_studiu_scoala_profesionala");
                table_cell = new PdfPCell(new Phrase(are_studiu_scoala_profesionala));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String limba_materna = query_set.getString("limba_materna");
                table_cell = new PdfPCell(new Phrase(limba_materna));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String limba_engleza_grad = query_set.getString("limba_engleza_grad");
                table_cell = new PdfPCell(new Phrase(limba_engleza_grad));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String limba_germana_grad = query_set.getString("limba_germana_grad");
                table_cell = new PdfPCell(new Phrase(limba_germana_grad));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_b = query_set.getString("categoria_b");
                table_cell = new PdfPCell(new Phrase(categoria_b));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_c1 = query_set.getString("categoria_c1");
                table_cell = new PdfPCell(new Phrase(categoria_c1));
                table.addCell(table_cell);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

                String categoria_c1e = query_set.getString("categoria_c1e");
                table_cell = new PdfPCell(new Phrase(categoria_c1e));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_c = query_set.getString("categoria_c");
                table_cell = new PdfPCell(new Phrase(categoria_c));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_ce = query_set.getString("categoria_ce");
                table_cell = new PdfPCell(new Phrase(categoria_ce));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_d = query_set.getString("categoria_d");
                table_cell = new PdfPCell(new Phrase(categoria_d));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String categoria_de = query_set.getString("categoria_de");
                table_cell = new PdfPCell(new Phrase(categoria_de));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String expirare_permis = query_set.getString("expirare_permis");
                table_cell = new PdfPCell(new Phrase(expirare_permis));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String expirare_cartela_tahograf = query_set.getString("expirare_cartela_tahograf");
                table_cell = new PdfPCell(new Phrase(expirare_cartela_tahograf));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String expirare_atestat_marfa = query_set.getString("expirare_atestat_marfa");
                table_cell = new PdfPCell(new Phrase(expirare_atestat_marfa));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String atestat_colete = query_set.getString("atestat_colete");
                table_cell = new PdfPCell(new Phrase(atestat_colete));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String atestat_cisterne = query_set.getString("atestat_cisterne");
                table_cell = new PdfPCell(new Phrase(atestat_cisterne));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String fara_atestat = query_set.getString("fara_atestat");
                table_cell = new PdfPCell(new Phrase(fara_atestat));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


            }
            /* Attach report table to PDF */
            my_pdf_report.add(table);
            my_pdf_report.close();

            /* Close all DB related objects */
            query_set.close();
            stmt.close();
            conn.close();

            Toast toast = Toast.makeText(PDFGenerator.this, "Raportul a fost generat cu succes!", Toast.LENGTH_SHORT);
            toast.show();

            Thread thread = new Thread(){
                public void run(){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Intent i = new Intent(PDFGenerator.this,MainActivity.class);
                    PDFGenerator.this.startActivity(i);
                }
            };
            //porneste fir executie care astepa 3 sec si redirectioneaza in pagina principala
            thread.start();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(PDFGenerator.this, "Raportul nu a fost generat. Exceptie: " + e.getMessage(), Toast.LENGTH_SHORT);
            toast.show();
        }
//        previewPdf();

    }


    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");

            startActivity(intent);
        } else {
            Toast.makeText(this, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }
}