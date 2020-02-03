package com.example.autorekrut.Controler;

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

public class PDFErzeugen extends AppCompatActivity {
    private static final String TAG = "PdfCreatorActivity";
    // private EditText mContentEditText;
    private Button mCreateButton;
    private File pdfFile;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdferzeugen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mCreateButton = (Button) findViewById(R.id.button_schafft);
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
        cell.setHorizontalAlignment(vAlignment);
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
            ResultSet query_set = stmt.executeQuery("SELECT name_de, vorname, firmenname, anschrift, telefon, email_de, zeceangajati, cicizeciangajati," +
                    " osutaangajati, cincisuteAngajati, forderungen, vorteile, andredetails  FROM informatii_sofer_de");
            /* Step-2: Initialize PDF documents - logical objects */
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.i(TAG, "Created a new directory for PDF");
            }

            pdfFile = new File(docsFolder.getAbsolutePath(), "PdfErzeungen.pdf");
            OutputStream output = new FileOutputStream(pdfFile);
            Document my_pdf_report = new Document(PageSize.A2.rotate(), 0f, 0f, 100f, 0f);


            PdfWriter.getInstance(my_pdf_report, output);
            my_pdf_report.open();
            //we have four columns in our table
            PdfPTable table = new PdfPTable(13);
            //create a table object
            PdfPCell table_cell;
            // set up table


            // add header
            table.addCell(createCell("Name", 2, 1, 3));
            table.addCell(createCell("Vorname", 2, 1, 3));
            table.addCell(createCell("Firmenname", 2, 1, 3));
            table.addCell(createCell("Anschrift", 2, 1, 3));
            table.addCell(createCell("Telefon", 2, 1, 3));
            table.addCell(createCell("Email", 2, 1, 3));
            table.addCell(createCell("Mitarbeiter(zehn)", 2, 1, 3));
            table.addCell(createCell("Mitarbeiter (fünfzig)", 2, 1, 3));
            table.addCell(createCell("Mitarbeiter (einhundert)", 2, 1, 3));
            table.addCell(createCell("Mitarbeiter (fünfhundert)", 2, 1, 3));
            table.addCell(createCell("Forderungen", 2, 1, 3));
            table.addCell(createCell("Vorteile", 2, 1, 3));
            table.addCell(createCell("Andredetails", 2, 1, 3));

            table.setWidths(new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7});

            while (query_set.next()) {

                String name_de = query_set.getString("name_de");
                table_cell = new PdfPCell(new Phrase(name_de));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String dept_vorname = query_set.getString("vorname");
                table_cell = new PdfPCell(new Phrase(dept_vorname));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String firmenname = query_set.getString("firmenname");
                table_cell = new PdfPCell(new Phrase(firmenname));
                table.addCell(table_cell);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

                String anschrift = query_set.getString("anschrift");
                table_cell = new PdfPCell(new Phrase(anschrift));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String telefon = query_set.getString("telefon");
                table_cell = new PdfPCell(new Phrase(telefon));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String email_de = query_set.getString("email_de");
                table_cell = new PdfPCell(new Phrase(email_de));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String zeceangajati = query_set.getString("zeceangajati");
                table_cell = new PdfPCell(new Phrase(zeceangajati));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String cicizeciangajati = query_set.getString("cicizeciangajati");
                table_cell = new PdfPCell(new Phrase(cicizeciangajati));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String osutaangajati = query_set.getString("osutaangajati");
                table_cell = new PdfPCell(new Phrase(osutaangajati));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String cincisuteAngajati = query_set.getString("cincisuteAngajati");
                table_cell = new PdfPCell(new Phrase(cincisuteAngajati));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);

                String forderungen = query_set.getString("forderungen");
                table_cell = new PdfPCell(new Phrase(forderungen));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String vorteile = query_set.getString("vorteile");
                table_cell = new PdfPCell(new Phrase(vorteile));
                table.addCell(table_cell);
                table_cell.setVerticalAlignment(Element.ALIGN_LEFT);


                String andredetails = query_set.getString("andredetails");
                table_cell = new PdfPCell(new Phrase(andredetails));
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


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        previewPdf();

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