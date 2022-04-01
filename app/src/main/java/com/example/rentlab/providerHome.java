package com.example.rentlab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Objects;
import java.util.UUID;


public class providerHome extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final String uniqueid = UUID.randomUUID().toString();
    Spinner pBrand, pProcessor, pRAM, pGraphics, pOS, pScreenSize;
    productHelperClass productHelperClass;
    private EditText Model;
    //    private int ProductID = 0;
    private Button addProduct;
    private FirebaseAuth prAuth;
    private String userid;
    private String[] Brandlist = {"--SELECT--", "DELL", "HP", "LENOVO", "ASUS"};
    private String[] ProcessorList = {"--SELECT--", "Intel i7", "Intel i5", "Intel i3", "RYZEN 7", "RYZEN 5", "RYZEN 3"};
    private String[] RAMList = {"--SELECT--", "16 GB", "8 GB", "6 GB", "4 GB"};
    private String[] GraphicsList = {"--SELECT--", "NVIDIA GeForce RTX 3090", "AMD Radeon 6900 XT", "NVIDIA GeForce RTX 3080", "AMD Radeon RX 6800", "NVIDIA GeForce RTX 2070", "AMD Radeon RX 6800M", "Apple M1 Max 32-Core", "AMD Radeon RX 6600", "NVIDIA GeForce RTX 2060", "NVIDIA GeForce GTX 1650 Ti"};
    private String[] OSList = {"--SELECT--", "Windows 11", "Windows 10", "Windows 8", "Windows 7"};
    private String[] ScreenSizeList = {"--SELECT--", "12.5 inch", "13.3 inch", "14.0 inch", "15.6 inch"};

    private String Brand, Processor, RAM, Graphics, OS, ScreenSize, prModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_home);

//        String UserName = getIntent().getStringExtra("UserName");
////        Log.v("UserName","UserName from Login page "+UserName);

        pBrand = findViewById(R.id.pBrand);
        pBrand.setOnItemSelectedListener(this);
        pProcessor = findViewById(R.id.pProcessor);
        pProcessor.setOnItemSelectedListener(this);
        pRAM = findViewById(R.id.pRAM);
        pRAM.setOnItemSelectedListener(this);
        pGraphics = findViewById(R.id.pGraphics);
        pGraphics.setOnItemSelectedListener(this);
        pOS = findViewById(R.id.pOS);
        pOS.setOnItemSelectedListener(this);
        pScreenSize = findViewById(R.id.pScreenSize);
        pScreenSize.setOnItemSelectedListener(this);

        Model = findViewById(R.id.pModel);

        addProduct = findViewById(R.id.addProduct);

        productHelperClass = new productHelperClass();
        ArrayAdapter brand = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, Brandlist);
        brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pBrand.setAdapter(brand);

        ArrayAdapter processor = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ProcessorList);
        processor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pProcessor.setAdapter(processor);

        ArrayAdapter ram = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, RAMList);
        ram.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pRAM.setAdapter(ram);

        ArrayAdapter graphics = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, GraphicsList);
        graphics.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pGraphics.setAdapter(graphics);

        ArrayAdapter os = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, OSList);
        os.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pOS.setAdapter(os);

        ArrayAdapter screenSize = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ScreenSizeList);
        screenSize.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pScreenSize.setAdapter(screenSize);


        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prBrand = Brand;
                String prProcessor = Processor;
                String prRAM = RAM;
                String prGraphics = Graphics;
                String prOS = OS;
                String prScreenSize = ScreenSize;

                prModel = Model.getText().toString();

                if (prBrand.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select the Brand of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prProcessor.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select Processor of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prRAM.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select RAM of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prGraphics.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select Graphics of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prOS.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select OS of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prScreenSize.equals("--SELECT--")) {
                    Toast.makeText(getApplicationContext(), "Please Select ScreenSize of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (prModel.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter Model of the product", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Save data to firebase
                prAuth = FirebaseAuth.getInstance();


                userid = Objects.requireNonNull(prAuth.getCurrentUser()).getUid();
                addProduct.setText("Adding Product...");

                productHelperClass productHelperClass = new productHelperClass(uniqueid, userid, prBrand, prProcessor, prRAM, prGraphics, prOS, prScreenSize, prModel);

//                db.collection("products").document(uniqueid).set(productHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        addProduct.setText("Add Product");
//                        if(task.isSuccessful()){
//                            Toast.makeText(providerHome.this,"Product Added Successfully",Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(providerHome.this,providerHome.class);
//                        }else{
//                            Toast.makeText(providerHome.this,"Failed!! Please try again later",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

                FirebaseDatabase.getInstance().getReference("products").child(uniqueid).setValue(productHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        addProduct.setText("Add Product");
                        if (task.isSuccessful()) {
                            Toast.makeText(providerHome.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(view.getContext(), providerHome.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(providerHome.this, "Failed!! Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("Notify", "Notify", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager = getSystemService(NotificationManager.class);
//            manager.createNotificationChannel(channel);
//        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.providerhome_optionmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.product:
                Intent intent2 = new Intent(this, providerProducts.class);
                startActivity(intent2);
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
                Intent intent1 = new Intent(this, Login.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Brand = pBrand.getSelectedItem().toString();
        Processor = pProcessor.getSelectedItem().toString();
        RAM = pRAM.getSelectedItem().toString();
        Graphics = pGraphics.getSelectedItem().toString();
        OS = pOS.getSelectedItem().toString();
        ScreenSize = pScreenSize.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onBackPressed() {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(providerHome.this);

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