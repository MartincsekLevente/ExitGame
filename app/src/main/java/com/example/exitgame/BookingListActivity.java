package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BookingListActivity extends AppCompatActivity {
    private static final String LOG_TAG = BookingListActivity.class.getName();
    private static final int SECRET_KEY = 69;
    private int limit = 100;


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;
    private ArrayList<itemsToFirestore> mItemsData = new ArrayList<itemsToFirestore>();

    final Handler handler = new Handler();


    RecyclerView recyclerView;
    String[] itemNames;
    String[] itemPrices;
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

    String[] itemNameList;
    String[] itemPriceList;
    int[] itemImagesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);

        itemNames = getResources().getStringArray(R.array.booking_item_names);
        itemPrices = getResources().getStringArray(R.array.booking_item_prizes);

        itemNameList = new String[itemNames.length];
        itemPriceList = new String[itemNames.length];
        itemImagesList = new int[itemNames.length];

        recyclerView = findViewById(R.id.recycle_view);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

      FirestoreCalling();


    }

    public void FirestoreCalling() {
        queryData(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList<itemsToFirestore> list) {
                Log.e(LOG_TAG, "Name: " + mItemsData.get(0).getItemNames());
                itemNameList[0] = mItemsData.get(0).getItemNames();
                for (int i = 0; i < mItemsData.size(); i++) {
                    itemNameList[i] = mItemsData.get(i).getItemNames();
                    itemPriceList[i] = mItemsData.get(i).getItemPrices();
                    itemImagesList[i] = mItemsData.get(i).getItemImages();
                }

                MyAdapter myAdapter = new MyAdapter(BookingListActivity.this, itemNameList, itemImagesList, itemPriceList);
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(BookingListActivity.this));

            }
        });
    }

    public void queryData(FirestoreCallback firestoreCallback) {
        mItemsData.clear();

        mItems.limit(limit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            Log.e(LOG_TAG, "lefut:)");
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                itemsToFirestore item = document.toObject(itemsToFirestore.class);
                mItemsData.add(item);
                Log.e(LOG_TAG, "Item: " + item.getItemNames());
            }
            if (mItemsData.size() == 0) {
                initializeData();
                FirestoreCalling();
            }
            firestoreCallback.onCallback(mItemsData);
        });

    }

    private interface FirestoreCallback {
        void onCallback(ArrayList<itemsToFirestore> list);
    }

    public void initializeData() {


        for (int i = 0; i < itemNames.length; i++) {
            mItemsData.add(i, (new itemsToFirestore(
                    itemNames[i],
                    itemPrices[i],
                    itemImages[i]
            )));
            mItems.add(mItemsData.get(i));
        }
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