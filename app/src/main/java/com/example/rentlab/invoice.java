package com.example.rentlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class invoice extends AppCompatActivity {

    Button print;
    Bitmap bmp, scaledBmp;
    int pagewidth = 1200;
    private TextView poDate, returnDate, shipAddress, customerName, contact, balance, TotalAmount;
    private String poNumber, icurrentDate, iday, ideposit, ibalance, ireturndate, icontact, ishipAddress, icustomername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice);

        poNumber = getIntent().getStringExtra("poInvoice");

        icurrentDate = getIntent().getStringExtra("currentDate");
        iday = getIntent().getStringExtra("days");
        ideposit = getIntent().getStringExtra("deposit");
        ibalance = getIntent().getStringExtra("balance");
        ireturndate = getIntent().getStringExtra("returnDate");
        icontact = getIntent().getStringExtra("contact");
        ishipAddress = getIntent().getStringExtra("shipAdress");
        icustomername = getIntent().getStringExtra("customername");

        poDate = findViewById(R.id.poDate);
        returnDate = findViewById(R.id.return_date);
        shipAddress = findViewById(R.id.address);
        contact = findViewById(R.id.contact);
        balance = findViewById(R.id.balance);
        TotalAmount = findViewById(R.id.total);
        print = findViewById(R.id.print);
        customerName = findViewById(R.id.fullname);
//        scaledBmp = Bitmap.createScaledBitmap(bmp,1200,518,false);

        poDate.setText(icurrentDate);
        returnDate.setText(ireturndate);
        shipAddress.setText(ishipAddress);
        contact.setText(icontact);
        balance.setText(ibalance);
        TotalAmount.setText(ibalance);
        customerName.setText(icustomername);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        createPdf();
    }

    private void createPdf() {
        print.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                PdfDocument myinvoice = new PdfDocument();
                Paint mypaint = new Paint();
                Paint titlepaint = new Paint();
                Paint issuedate = new Paint();
                Paint retdate = new Paint();
                Paint customer = new Paint();
                Paint contact = new Paint();
                Paint shipadd = new Paint();
                Paint depositamt = new Paint();
                Paint balanceamt = new Paint();
                Paint totalamt = new Paint();
                Paint endline = new Paint();

                Toast.makeText(invoice.this, "Invoice Created!", Toast.LENGTH_SHORT).show();

                PdfDocument.PageInfo Mypageinfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
                PdfDocument.Page myPage1 = myinvoice.startPage(Mypageinfo1);
                Canvas canvas = myPage1.getCanvas();

//                    canvas.drawBitmap(scaledBmp,0,0,mypaint);
                titlepaint.setTextAlign(Paint.Align.CENTER);
                titlepaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlepaint.setTextSize(70);
                canvas.drawText("Rentlabs", pagewidth / 2, 270, titlepaint);

//                    canvas.drawText("PO invoice :",200,400,titlepaint);
//                        canvas.drawText(poNumber,800,400,titlepaint);
                issuedate.setTextAlign(Paint.Align.LEFT);
                issuedate.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                issuedate.setTextSize(50);
                canvas.drawText("Issue Date :", 100, 500, issuedate);
                canvas.drawText(icurrentDate, 600, 500, issuedate);

                retdate.setTextAlign(Paint.Align.LEFT);
                retdate.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                retdate.setTextSize(50);
                canvas.drawText("Return Date :", 100, 600, retdate);
                canvas.drawText(ireturndate, 600, 600, retdate);

                customer.setTextAlign(Paint.Align.LEFT);
                customer.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                customer.setTextSize(50);
                canvas.drawText("Customer Name :", 100, 900, customer);
                canvas.drawText(icustomername, 600, 900, customer);

                contact.setTextAlign(Paint.Align.LEFT);
                contact.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                contact.setTextSize(50);
                canvas.drawText("Contact :", 100, 1000, contact);
                canvas.drawText(icontact, 600, 1000, contact);

                shipadd.setTextAlign(Paint.Align.LEFT);
                shipadd.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                shipadd.setTextSize(50);
                canvas.drawText("Shipping Address :", 100, 1100, shipadd);
                canvas.drawText(ishipAddress, 600, 1100, shipadd);

                depositamt.setTextAlign(Paint.Align.LEFT);
                depositamt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                depositamt.setTextSize(50);
                canvas.drawText("Deposit amount :", 100, 1300, depositamt);
                canvas.drawText(ideposit, 600, 1300, depositamt);

                balanceamt.setTextAlign(Paint.Align.LEFT);
                balanceamt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                balanceamt.setTextSize(50);
                canvas.drawText("Balance amount :", 100, 1400, balanceamt);
                canvas.drawText(ibalance, 600, 1400, balanceamt);

                endline.setTextAlign(Paint.Align.LEFT);
                endline.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                endline.setTextSize(30);
                canvas.drawText("------------------------------------------------------------------------------------", 100, 1450, endline);
                canvas.drawText("-", 600, 1450, endline);

                totalamt.setTextAlign(Paint.Align.LEFT);
                totalamt.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                totalamt.setTextSize(50);
                canvas.drawText("Total amount :", 100, 1500, totalamt);
                canvas.drawText(ibalance, 600, 1500, totalamt);

                myinvoice.finishPage(myPage1);

                File file = new File(Environment.getExternalStorageDirectory().getPath(), "Documents/myInvoice.pdf");
                try {
                    myinvoice.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myinvoice.close();

            }
        });
    }


    public void onBackPressed() {
        startActivity(new Intent(invoice.this, userHomepage.class));
        finish();
    }
}