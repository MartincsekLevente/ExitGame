package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.StructuredQuery;

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
    private MyAdapter myAdapter;
    private Query.Direction direction;


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
    int databaseSize=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_list);


        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != SECRET_KEY) {
            finish();
        }



        if (getIntent().getStringExtra("order").equals("asc")) {
            direction = Query.Direction.ASCENDING;

        }else {
            direction = Query.Direction.DESCENDING;
        }


        itemNames = getResources().getStringArray(R.array.booking_item_names);
        itemPrices = getResources().getStringArray(R.array.booking_item_prizes);



        recyclerView = findViewById(R.id.recycle_view);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");


        itemNameList = new String[databaseSize];
        itemPriceList = new String[databaseSize];
        itemImagesList = new int[databaseSize];
        FirestoreCalling();


    }



    public void FirestoreCalling() {
        queryData(new FirestoreCallback() {
            @Override
            public void onCallback(ArrayList<itemsToFirestore> list) {
                for (int i = 0; i < mItemsData.size(); i++) {
                    itemNameList[i] = mItemsData.get(i).getItemNames();
                    itemPriceList[i] = mItemsData.get(i).getItemPrices();
                    itemImagesList[i] = mItemsData.get(i).getItemImages();

                }

                myAdapter = new MyAdapter(BookingListActivity.this, itemNameList, itemImagesList, itemPriceList, mItemsData.size());
                recyclerView.setAdapter(myAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(BookingListActivity.this));

            }
        });
    }

    public void queryData(FirestoreCallback firestoreCallback) {
        mItemsData.clear();

        mItems.limit(limit).orderBy("itemNames", direction).get().addOnSuccessListener(queryDocumentSnapshots -> {

            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                itemsToFirestore item = document.toObject(itemsToFirestore.class);
                mItemsData.add(item);

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


        if (getIntent().getStringExtra("order").equals("asc")) {
            menu.getItem(0).setIcon(R.drawable.orderby_arrow_down);
        }else {
            menu.getItem(0).setIcon(R.drawable.orderby_arrow_up);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.orderBy:
                Intent callback_intent = new Intent(this, BookingListActivity.class);
                if (getIntent().getStringExtra("order").equals("asc")) {
                    callback_intent.putExtra("order","desc");
                }else {
                    callback_intent.putExtra("order","asc");
                }
                callback_intent.putExtra("SECRET_KEY",SECRET_KEY);
                finish();
                startActivity(callback_intent);
                return true;
            case R.id.request_new_item:
                Intent req_intent = new Intent(this, RequestActivity.class);
                finish();
                startActivity(req_intent);
                return true;
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}