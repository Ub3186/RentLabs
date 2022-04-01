package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class providerProducts extends AppCompatActivity {

    FirebaseAuth prAuth;
    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private providerProductAdapter prodAdapter;
    private ArrayList<productHelperClass> prodlist;
    private ProgressDialog progressDialog;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_products);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.providerprodList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prAuth = FirebaseAuth.getInstance();
        userid = Objects.requireNonNull(prAuth.getCurrentUser()).getUid();

        reference = FirebaseDatabase.getInstance().getReference("products");
        prodlist = new ArrayList<>();
        prodAdapter = new providerProductAdapter(this, prodlist);

        recyclerView.setAdapter(prodAdapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.v("datasnapshotondatachange","Product details: " + snapshot);

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String pruserid = String.valueOf(dataSnapshot.child("userid").getValue());

                    productHelperClass prod = dataSnapshot.getValue(productHelperClass.class);
//                    Log.v("datasnapshot","Product details: " + prod);
                    if (pruserid.equals(userid)) {
                        prodlist.add(prod);
                    }
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
        inflater.inflate(R.menu.myproduct_optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.H:
                Intent intent1 = new Intent(this, providerHome.class);
                startActivity(intent1);
                return true;
            case R.id.p:
                Intent intent = new Intent(this, provider_privacypolicy.class);
                startActivity(intent);
                return true;
            case R.id.t:
                Intent intnt = new Intent(this, provider_trmscnd.class);
                startActivity(intnt);
                return true;
            case R.id.logout:
                Intent intent2 = new Intent(this, Login.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}