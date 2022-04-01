package com.example.rentlab;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class userHomepage extends AppCompatActivity {

    String searchtext;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private productAdapter prodAdapter;
    private ArrayList<productHelperClass> prodlist;
    private ProgressDialog progressDialog;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_home);

        search = findViewById(R.id.serachText);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.productList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference("products");
        prodlist = new ArrayList<>();
        prodAdapter = new productAdapter(this, prodlist);

        recyclerView.setAdapter(prodAdapter);


        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("datasnapshotondatachange","Product details: " + snapshot);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    productHelperClass prod = dataSnapshot.getValue(productHelperClass.class);
//                    Log.v("datasnapshot","Product details: " + prod);
                    prodlist.add(prod);

                }
                prodAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void search(View view) {
        searchtext = search.getText().toString();
        String srch = searchtext.toUpperCase(Locale.ROOT);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.productList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reference = FirebaseDatabase.getInstance().getReference("products");
        prodlist = new ArrayList<>();
        prodAdapter = new productAdapter(this, prodlist);

        recyclerView.setAdapter(prodAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("datasnapshotondatachange","Product details: " + snapshot);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String searchBrand = String.valueOf(dataSnapshot.child("brand").getValue());
                    productHelperClass prod = dataSnapshot.getValue(productHelperClass.class);
//                    Log.v("datasnapshot","Product details: " + prod);
                    if (searchBrand.equals(srch)) {
                        prodlist.add(prod);
                    }
                }
                if (prodlist.isEmpty()) {
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                productHelperClass prod = dataSnapshot.getValue(productHelperClass.class);
                                prodlist.add(prod);
                            }
                            prodAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                prodAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.userhome_optionmenu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.v1:
                Intent intent2 = new Intent(userHomepage.this, userOrderedProducts.class);
                startActivity(intent2);
                return true;
            case R.id.p2:
                Intent intent = new Intent(userHomepage.this, privacyPolicy.class);
                startActivity(intent);
                return true;
            case R.id.tc:
                Intent intnt = new Intent(userHomepage.this, termsandcnd.class);
                startActivity(intnt);
                return true;
            case R.id.l1:
                Intent intent1 = new Intent(userHomepage.this, Login.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(userHomepage.this);

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