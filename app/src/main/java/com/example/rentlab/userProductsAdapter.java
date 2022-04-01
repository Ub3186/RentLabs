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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class userProductsAdapter extends RecyclerView.Adapter<userProductsAdapter.productViewHolder> {

    Context context;
    ArrayList<productHelperClass> list;
    ArrayList<placeOrderHelperClass> order;
    private FirebaseAuth prAuth;
    private String userid;

    public userProductsAdapter(Context context, ArrayList<productHelperClass> list, ArrayList<placeOrderHelperClass> order) {
        this.context = context;
        this.list = list;
        this.order = order;
    }

    @NonNull
    @Override
    public userProductsAdapter.productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_ordercart, parent, false);
        return new userProductsAdapter.productViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull userProductsAdapter.productViewHolder holder, int position) {

        Log.v("position", "position" + position);

        productHelperClass prod = list.get(position);
        placeOrderHelperClass porder = order.get(position);

        holder.prbrand.setText(prod.getBrand());
        holder.prgraphics.setText(prod.getGraphics());
        holder.prmodel.setText(prod.getModel());
        holder.properatingSystem.setText(prod.getOs());
        holder.prprocessor.setText(prod.getProcessor());
        holder.prram.setText(prod.getRam());
        holder.prscreensize.setText(prod.getScreensize());
        holder.pruniqid.setText(prod.getUniqueid());

        holder.poinvoiceid.setText(porder.getPoinvoice());

//            Log.v("orderlist", "order" + holder.pruniqid);
//        Log.v("Polist", "Invoice" + prod);

        holder.cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(view.getContext());

                builder.setMessage("Are you sure you want to Cancel order?");
                builder.setTitle("Rent-Labs");
                builder.setCancelable(false);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String poinvoice;
                        poinvoice = holder.poinvoiceid.getText().toString();
//                    Log.v("orderlist", "invoice" + poinvoice);
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("purchaseorders").child(poinvoice);
                        reference.removeValue();
                        Toast.makeText(context, "Order cancelled successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, userOrderedProducts.class);
                        context.startActivity(intent);
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

        holder.returnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String poinvoice;
                poinvoice = holder.poinvoiceid.getText().toString();
//                    Log.v("orderlist", "invoice" + poinvoice);
                // Set up the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose reason for returning product");

//                  Add a checkbox list
                String[] animals = {"Placed order by mistake", "Want to order different product", "Received damaged product", "Delivered different product", "Not necessary now"};
                boolean[] checkedItems = {true, false, false, true, false};
                builder.setMultiChoiceItems(animals, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // The user checked or unchecked a box
                    }
                });

//                  Add OK and Cancel buttons
                builder.setPositiveButton("Return", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The user clicked OK
//                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("purchaseorders").child(poinvoice);
//                            reference.removeValue();
                        Toast.makeText(context, "Return request has been placed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, userOrderedProducts.class);
                        context.startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", null);

// Create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class productViewHolder extends RecyclerView.ViewHolder {

        TextView prbrand, prgraphics, prmodel, properatingSystem, prprocessor, prram, prscreensize, pruniqid, poinvoiceid;
        Button cancelOrder, returnOrder;

        public productViewHolder(@NonNull View itemView) {
            super(itemView);

            prbrand = itemView.findViewById(R.id.brandv);
            //            Log.v("Brand Name","findviewbyid"+brand);
            prgraphics = itemView.findViewById(R.id.graphicsv);
            prmodel = itemView.findViewById(R.id.modelv);
            properatingSystem = itemView.findViewById(R.id.osv);
            prprocessor = itemView.findViewById(R.id.processorv);
            prram = itemView.findViewById(R.id.ramv);
            prscreensize = itemView.findViewById(R.id.screenizev);
            pruniqid = itemView.findViewById(R.id.uniqueidv);
            poinvoiceid = itemView.findViewById(R.id.poidv);

            cancelOrder = itemView.findViewById(R.id.cnlOrdr);
            returnOrder = itemView.findViewById(R.id.rtnProd);

        }
    }
}
