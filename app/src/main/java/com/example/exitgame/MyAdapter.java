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


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHoler> {
    private static final String LOG_TAG = MyAdapter.class.getName();
    Context context;
    LayoutInflater inflater;
    int[] itemImages;
    String[] itemNames;
    String[] itemPrices;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    public MyAdapter(Context context, String[] itemNames, int[] itemImages, String[] itemPrices) {
        this.context = context;
        this.itemNames = itemNames;
        this.itemImages = itemImages;
        this.itemPrices = itemPrices;
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
                intent.putExtra("item_description_key",holder.getAdapterPosition());
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
