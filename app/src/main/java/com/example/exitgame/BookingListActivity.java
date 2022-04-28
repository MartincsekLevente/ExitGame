package com.example.exitgame;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BookingListActivity extends AppCompatActivity {
    private static final String LOG_TAG = BookingListActivity.class.getName();
    private static final int SECRET_KEY = 69;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    RecyclerView recyclerView;
    String[] itemNames;
    int[] itemImages = {
            R.drawable.alice,
            R.drawable.bank_robbing,
            R.drawable.chernobyl,
            R.drawable.cyber_attack,
            R.drawable.iron_throne,
            R.drawable.labyrinth_of_the_mind,
            R.drawable.nuclear_adventure,
            R.drawable.pirate_bay,
            R.drawable.saw,
            R.drawable.submarine,
            R.drawable.the_cathedral,
            R.drawable.the_cube,
            R.drawable.titanic,
            R.drawable.zombie};

    String[] itemPrices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        recyclerView = findViewById(R.id.recycle_view);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }

        itemNames = getResources().getStringArray(R.array.booking_item_names);

        itemPrices = getResources().getStringArray(R.array.booking_item_prizes);

        MyAdapter myAdapter = new MyAdapter(this, itemNames, itemImages, itemPrices);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


}