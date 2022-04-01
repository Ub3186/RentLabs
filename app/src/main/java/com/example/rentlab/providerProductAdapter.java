package com.example.rentlab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class providerProductAdapter extends RecyclerView.Adapter<providerProductAdapter.providerProductViewHolder> {

    Context context;
    ArrayList<productHelperClass> list;

    public providerProductAdapter(Context context, ArrayList<productHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public providerProductAdapter.providerProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.products, parent, false);
        return new providerProductAdapter.providerProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull providerProductAdapter.providerProductViewHolder holder, int position) {

        productHelperClass prod = list.get(position);


        holder.prbrand.setText(prod.getBrand());
        holder.prgraphics.setText(prod.getGraphics());
        holder.prmodel.setText(prod.getModel());
        holder.properatingSystem.setText(prod.getOs());
        holder.prprocessor.setText(prod.getProcessor());
        holder.prram.setText(prod.getRam());
        holder.prscreensize.setText(prod.getScreensize());
        holder.pruniqid.setText(prod.getUniqueid());
        holder.deleteProduct.setText("Delete Product");

        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(view.getContext());

                builder.setMessage("Are you sure you want to Delete order?");
                builder.setTitle("Rent-Labs");
                builder.setCancelable(false);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String prid;
                        prid = holder.pruniqid.getText().toString();
                        Log.v("Product id", "unique ID  :   " + prid);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products").child(prid);
                        DatabaseReference orderreference = FirebaseDatabase.getInstance().getReference("purchaseorders");

                        orderreference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int count = 0;
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    String purprodid = String.valueOf(dataSnapshot.child("productid").getValue());
                                    Log.v("Product id", "purchase product id  :   " + purprodid);
                                    if (purprodid.equals(prid)) {
                                        count++;
                                    }
                                }
                                if (count == 0) {
                                    reference.removeValue();
                                    Toast.makeText(context, "Product Deleted successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, providerProducts.class);
                                    context.startActivity(intent);

                                } else {
                                    Toast.makeText(context, "Product Cannot be deleted as user has placed order", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, providerProducts.class);
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


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
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class providerProductViewHolder extends RecyclerView.ViewHolder {

        TextView prbrand, prgraphics, prmodel, properatingSystem, prprocessor, prram, prscreensize, pruniqid;
        Button deleteProduct;

        public providerProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prbrand = itemView.findViewById(R.id.brandpr);
            //            Log.v("Brand Name","findviewbyid"+brand);
            prgraphics = itemView.findViewById(R.id.graphicspr);
            prmodel = itemView.findViewById(R.id.modelpr);
            properatingSystem = itemView.findViewById(R.id.operatingsystempr);
            prprocessor = itemView.findViewById(R.id.processorpr);
            prram = itemView.findViewById(R.id.rampr);
            prscreensize = itemView.findViewById(R.id.screenizepr);
            pruniqid = itemView.findViewById(R.id.uniqueidpr);

            deleteProduct = itemView.findViewById(R.id.placeOrder);

        }
    }
}
