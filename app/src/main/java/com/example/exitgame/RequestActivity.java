package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RequestActivity extends AppCompatActivity {

    private static final String LOG_TAG = RequestActivity.class.getName();

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    EditText reqNameEditText;
    EditText reqPriceEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        reqNameEditText = findViewById(R.id.req_name);
        reqPriceEditText = findViewById(R.id.req_price);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.booking_in_item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.log_out_button:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void backToSearch(View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);

        Intent intent = new Intent(this, BookingListActivity.class);
        intent.putExtra("SECRET_KEY",69);
        intent.putExtra("order","asc");
        startActivity(intent);
    }

    public void requestNew(View view) {
            String reqName = reqNameEditText.getText().toString();
            String reqPrice = reqPriceEditText.getText().toString();
            int reqImg = R.drawable.requested;
        if(TextUtils.isEmpty(reqName)) {
            reqNameEditText.setError("Cannot be empty");
            return;
        }
        if(TextUtils.isEmpty(reqPrice)) {
            reqPriceEditText.setError("Cannot be empty");
            return;
        }

        mItems.add(new itemsToFirestore(reqName,reqPrice,reqImg));
        Intent intent = new Intent(this, BookingListActivity.class);
        intent.putExtra("SECRET_KEY",69);
        intent.putExtra("order","asc");
        startActivity(intent);
    }

}
