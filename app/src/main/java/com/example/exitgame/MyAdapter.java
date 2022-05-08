package com.example.exitgame;

import android.content.Context;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoler> {
    private static final String LOG_TAG = MyAdapter.class.getName();
    Context context;
    LayoutInflater inflater;
    int[] itemImages;
    String[] itemNames;
    String[] itemPrices;
    int size;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    public MyAdapter(Context context, String[] itemNames, int[] itemImages, String[] itemPrices, int size) {
        this.context = context;
        this.size = size;
        this.itemImages = new int[size];
        this.itemNames = new String[size];
        this.itemPrices = new String[size];

        for (int i = 0; i < size; i++) {
            this.itemImages[i]=itemImages[i];
            this.itemPrices[i]=itemPrices[i];
            this.itemNames[i]=itemNames[i];
        }
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_item, parent, false);
        return new MyViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.itemName.setText(itemNames[position]);
        holder.itemPrice.setText(itemPrices[position]);
        holder.itemImage.setImageResource(itemImages[position]);

        holder.viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InItemActivity.class);
                intent.putExtra("item_name",itemNames[holder.getAdapterPosition()]);
                intent.putExtra("item_price",itemPrices[holder.getAdapterPosition()]);
                intent.putExtra("item_image",itemImages[holder.getAdapterPosition()]);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemNames.length;
    }

    public class MyViewHoler extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemPrice;
        ImageView itemImage;
        Button viewMoreButton;

        public MyViewHoler(@NonNull View itemView) {
            super(itemView);
           itemName = itemView.findViewById(R.id.item_name);
           itemImage = itemView.findViewById(R.id.grid_image);
           itemPrice = itemView.findViewById(R.id.item_price);
           viewMoreButton = itemView.findViewById(R.id.view_more_button);

        }
    }
}
