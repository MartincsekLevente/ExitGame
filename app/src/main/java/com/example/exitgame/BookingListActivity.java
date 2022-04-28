package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.booking_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            case R.id.settings:
                Log.d(LOG_TAG, "SETTINGS PRESSED");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}