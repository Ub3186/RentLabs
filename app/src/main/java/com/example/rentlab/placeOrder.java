package com.example.rentlab;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class placeOrder extends AppCompatActivity {

    final String deposit = "10000";
    final String poInvoice = UUID.randomUUID().toString();
    private TextView poBrand, poGraphics, poModel, poOs, poProcessor, poRam, poScreensize;
    private EditText poDay, poContact, poShipAdrs, customername;
    private Button createInvoice;
    private FirebaseAuth poAuth;
    private String userID, currentDate, currentDate1, returnDate, balance;
    private int initialDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order);

        String oBrand, oGraphics, oModel, oOs, oProcessor, oRam, oScreensize, oPrUniqueID;
        oBrand = getIntent().getStringExtra("productBrand");
        oGraphics = getIntent().getStringExtra("productGraphics");
        oModel = getIntent().getStringExtra("productModel");
        oOs = getIntent().getStringExtra("productOs");
        oProcessor = getIntent().getStringExtra("productProcessor");
        oRam = getIntent().getStringExtra("productRam");
        oScreensize = getIntent().getStringExtra("productScreensize");
        oPrUniqueID = getIntent().getStringExtra("productUniqueID");
//        Log.v("PlaceOrder :  ","place order page data    :    "+oBrand);

        poBrand = findViewById(R.id.brandp);
        poGraphics = findViewById(R.id.graphicsp);
        poModel = findViewById(R.id.modelp);
        poOs = findViewById(R.id.osp);
        poProcessor = findViewById(R.id.processorp);
        poRam = findViewById(R.id.ramp);
        poScreensize = findViewById(R.id.screensizep);
        poDay = findViewById(R.id.rentaldays);
        poContact = findViewById(R.id.contactNumber);
        poShipAdrs = findViewById(R.id.shipAddress);

        poDay = findViewById(R.id.rentaldays);
        customername = findViewById(R.id.fullname);
        poContact = findViewById(R.id.contactNumber);
        poShipAdrs = findViewById(R.id.shipAddress);

        createInvoice = findViewById(R.id.createPO);

        poBrand.setText(oBrand);
        poGraphics.setText(oGraphics);
        poModel.setText(oModel);
        poOs.setText(oOs);
        poProcessor.setText(oProcessor);
        poRam.setText(oRam);
        poScreensize.setText(oScreensize);


        createInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String odays = poDay.getText().toString();
                String ocustomername = customername.getText().toString();
                String ocontact = poContact.getText().toString();
                String oshipAdress = poShipAdrs.getText().toString();


                if (ocustomername.isEmpty()) {
                    customername.setError("Customer name is required!");
                    customername.requestFocus();
                    return;
                }
                if (odays.isEmpty()) {
                    poDay.setError("Rental Time is required!");
                    poDay.requestFocus();
                    return;
                }

                if (ocontact.isEmpty()) {
                    poContact.setError("Contact Number is required!");
                    poContact.requestFocus();
                    return;
                }
                if (ocontact.length() < 10) {
                    poContact.setError("Contact Number cannot be less than 10 characters");
                    poContact.requestFocus();
                    return;
                }
                if (oshipAdress.isEmpty()) {
                    poShipAdrs.setError("shipping Address is required!");
                    poShipAdrs.requestFocus();
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                currentDate = DateFormat.getDateInstance(DateFormat.SHORT).format(calendar.getTime());
                currentDate1 = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

                initialDay = Integer.valueOf(odays);

                try {
                    initialDay = Integer.valueOf(currentDate1);
                } catch (NumberFormatException e) {
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(currentDate1));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                c.add(Calendar.DATE, initialDay);

                sdf = new SimpleDateFormat("dd/MM/yy");
                Date resultdate = new Date(c.getTimeInMillis());
                returnDate = sdf.format(resultdate);

                int bal = (initialDay * 250);
                balance = Integer.toString(bal);

//                Log.v("calculated amount", " balance :  " + returnDate);

                //save data to firebase
                poAuth = FirebaseAuth.getInstance();

                userID = Objects.requireNonNull(poAuth.getCurrentUser()).getUid();

                createInvoice.setText("Creating Invoice..");

                placeOrderHelperClass pohelper = new placeOrderHelperClass(poInvoice, oPrUniqueID, userID, currentDate, odays, deposit, balance, returnDate, ocontact, oshipAdress, ocustomername);

                FirebaseDatabase.getInstance().getReference("purchaseorders").child(poInvoice).setValue(pohelper).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        createInvoice.setText("CONFIRM ORDER");
                        if (task.isSuccessful()) {
                            Toast.makeText(placeOrder.this, "Order Placed successfully!", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(view.getContext(), invoice.class);
                            i.putExtra("poInvoice", poInvoice);
                            i.putExtra("currentDate", currentDate);
                            i.putExtra("customername", ocustomername);
                            i.putExtra("days", odays);
                            i.putExtra("deposit", deposit);
                            i.putExtra("balance", balance);
                            i.putExtra("returnDate", returnDate);
                            i.putExtra("contact", ocontact);
                            i.putExtra("shipAdress", oshipAdress);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(placeOrder.this, "Something went Wrong!! please try again after sometime", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.userhome_optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.v1:
                Intent intnt1 = new Intent(this, userOrderedProducts.class);
                startActivity(intnt1);
            case R.id.tc:
                Intent intnt = new Intent(this, termsandcnd.class);
                startActivity(intnt);
                return true;
            case R.id.p2:
                Intent intent = new Intent(this, privacyPolicy.class);
                startActivity(intent);
                return true;
            case R.id.l1:
                Intent intent1 = new Intent(this, Login.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}