package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class userOrderedProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference reference, orderreference;
    private userProductsAdapter userProductsAdapter;
    private ArrayList<productHelperClass> orderslist;
    private ArrayList<placeOrderHelperClass> polist;
    private ProgressDialog progressDialog;
    private FirebaseAuth prAuth;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_ordered_products);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.orderlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prAuth = FirebaseAuth.getInstance();
        userid = Objects.requireNonNull(prAuth.getCurrentUser()).getUid();


        reference = FirebaseDatabase.getInstance().getReference().child("products");
        orderreference = FirebaseDatabase.getInstance().getReference().child("purchaseorders");
        orderslist = new ArrayList<>();
        polist = new ArrayList<>();
        userProductsAdapter = new userProductsAdapter(this, orderslist, polist);

        recyclerView.setAdapter(userProductsAdapter);

        // Purchase order data
        orderreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snap) {
                for (DataSnapshot db : snap.getChildren()) {
                    String purprodid = String.valueOf(db.child("productid").getValue());
                    String pruserid = String.valueOf(db.child("userid").getValue());
                    String purPO = String.valueOf(db.child("poinvoice").getValue());

                    placeOrderHelperClass po = db.getValue(placeOrderHelperClass.class);
                    // Products data
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String prprodid = String.valueOf(dataSnapshot.child("uniqueid").getValue());

                                productHelperClass order = dataSnapshot.getValue(productHelperClass.class);

                                if (prprodid.equals(purprodid) && pruserid.equals(userid)) {
                                    orderslist.add(order);
                                    polist.add(po);
                                }
//                                Log.v("orderlist", "order" + order);
//                                Log.v("Polist", "Invoice" + po);

                            }
                            userProductsAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewproduct_optionmenu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.Home:
                Intent intent1 = new Intent(userOrderedProducts.this, userHomepage.class);
                startActivity(intent1);
                return true;
            case R.id.p2:
                Intent intent = new Intent(userOrderedProducts.this, privacyPolicy.class);
                startActivity(intent);
                return true;
            case R.id.tc:
                Intent intnt = new Intent(userOrderedProducts.this, termsandcnd.class);
                startActivity(intnt);
                return true;
            case R.id.l1:
                Intent intent2 = new Intent(userOrderedProducts.this, Login.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}