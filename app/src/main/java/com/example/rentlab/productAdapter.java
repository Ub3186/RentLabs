package com.example.rentlab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class productAdapter extends RecyclerView.Adapter<productAdapter.productViewHolder> {


    Context context;
    ArrayList<productHelperClass> list;

    public productAdapter(Context context, ArrayList<productHelperClass> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public productViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.products, parent, false);
        return new productViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull productViewHolder holder, int position) {

        productHelperClass prod = list.get(position);


        holder.prbrand.setText(prod.getBrand());
        holder.prgraphics.setText(prod.getGraphics());
        holder.prmodel.setText(prod.getModel());
        holder.properatingSystem.setText(prod.getOs());
        holder.prprocessor.setText(prod.getProcessor());
        holder.prram.setText(prod.getRam());
        holder.prscreensize.setText(prod.getScreensize());
        holder.pruniqid.setText(prod.getUniqueid());

        holder.productOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productBrand, productGraphics, productModel, productOs, productProcessor, productRam, productScreensize, productUniqueID;
                productBrand = holder.prbrand.getText().toString();
                productGraphics = holder.prgraphics.getText().toString();
                productModel = holder.prmodel.getText().toString();
                productOs = holder.properatingSystem.getText().toString();
                productProcessor = holder.prprocessor.getText().toString();
                productRam = holder.prram.getText().toString();
                productScreensize = holder.prscreensize.getText().toString();
                productUniqueID = holder.pruniqid.getText().toString();


                Intent intent = new Intent(context, placeOrder.class);
                intent.putExtra("productBrand", productBrand);
                intent.putExtra("productGraphics", productGraphics);
                intent.putExtra("productModel", productModel);
                intent.putExtra("productOs", productOs);
                intent.putExtra("productProcessor", productProcessor);
                intent.putExtra("productRam", productRam);
                intent.putExtra("productScreensize", productScreensize);
                intent.putExtra("productUniqueID", productUniqueID);

//                Log.v("Adapter Product :  ","adapterpage  page data    :    "+productBrand);

                context.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class productViewHolder extends RecyclerView.ViewHolder {

        TextView prbrand, prgraphics, prmodel, properatingSystem, prprocessor, prram, prscreensize, pruniqid;
        Button productOrder;

        public productViewHolder(@NonNull View itemView) {
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

            productOrder = itemView.findViewById(R.id.placeOrder);

        }
    }
}
