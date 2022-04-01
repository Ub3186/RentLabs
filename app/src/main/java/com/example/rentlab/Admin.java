package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {

    TextView userCount, providerCount, productCount, invoiceCount;
    DatabaseReference userReference, productReference, invoiceReference;
    int user = 0, provider = 0, product = 0, invoice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        userCount = findViewById(R.id.uCount);
        providerCount = findViewById(R.id.pCount);
        productCount = findViewById(R.id.prCount);
        invoiceCount = findViewById(R.id.iCount);

        userReference = FirebaseDatabase.getInstance().getReference("users");
        productReference = FirebaseDatabase.getInstance().getReference("products");
        invoiceReference = FirebaseDatabase.getInstance().getReference("purchaseorders");

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String type = dataSnapshot.child("UserType").getValue(String.class);

                    if (type.equals("User")) {
                        user++;
                    } else if (type.equals("Provider")) {
                        provider++;
                    }

                    userCount.setText(Integer.toString(user));
                    providerCount.setText(Integer.toString(provider));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        productReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    product++;
                    productCount.setText(Integer.toString(product));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        invoiceReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    invoice++;
                    invoiceCount.setText(Integer.toString(invoice));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(Admin.this);

        builder.setMessage("Do you want to Exit?");
        builder.setTitle("Rent-Labs");
        builder.setCancelable(false);
        builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                System.exit(1);
            }
        });
        builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}