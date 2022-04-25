package com.example.exitgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BookingListActivity extends AppCompatActivity {
    private static final String LOG_TAG = BookingListActivity.class.getName();

    private static final int SECRET_KEY = 69;

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(LOG_TAG, "Authenticated user!");
        } else {
            Log.d(LOG_TAG, "Unauthenticated user!");
            finish();
        }
        initializeDat();
    }

    private void initializeDat() {
        String[] itemNames = getResources().getStringArray(R.array.booking_item_names);
        TypedArray itemImages = getResources().obtainTypedArray(R.array.booking_item_images);


    }



}