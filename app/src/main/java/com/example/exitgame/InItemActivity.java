package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;

public class InItemActivity extends AppCompatActivity {
    private static final String LOG_TAG = InItemActivity.class.getName();
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    TextView itemDescription;

    boolean isTrashVisible= false;
    int itemImageVar;
    String itemNameVar, itemPriceVar;

    private FirebaseFirestore mFirestore;
    private CollectionReference mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_item);

        itemImage = findViewById(R.id.grid_image);
        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.item_price);
        itemDescription = findViewById(R.id.item_description);

        mFirestore = FirebaseFirestore.getInstance();
        mItems = mFirestore.collection("Items");

        getExtras();
        setExtras();
    }

    private void getExtras() {

        itemImageVar = getIntent().getIntExtra("item_image", 0);
        itemNameVar = getIntent().getStringExtra("item_name");
        itemPriceVar = getIntent().getStringExtra("item_price");

    }

    private void setExtras() {
        itemImage.setImageResource(itemImageVar);
        itemName.setText(itemNameVar);
        itemPrice.setText(itemPriceVar);
        InputStream in_stream;
        try {
            Resources resources = getResources();
            switch (itemNameVar) {
                case "Alice":
                    in_stream = resources.openRawResource(R.raw.alice);
                    break;
                case "Bank robbing":
                    in_stream = resources.openRawResource(R.raw.bank_robbing);
                    break;
                case "Chernobyl":
                    in_stream = resources.openRawResource(R.raw.chernobyl);
                    break;
                case "Cyber attack":
                    in_stream = resources.openRawResource(R.raw.cyber_attack);
                    break;
                case "Iron throne":
                    in_stream = resources.openRawResource(R.raw.iron_throne);
                    break;
                case "Labyrinth of the mind":
                    in_stream = resources.openRawResource(R.raw.labyrinth_of_the_mind);
                    break;
                case "Nuclear adventure":
                    in_stream = resources.openRawResource(R.raw.nuclear_adventure);
                    break;
                case "Pirate Bay":
                    in_stream = resources.openRawResource(R.raw.pirate_bay);
                    break;
                case "Saw":
                    in_stream = resources.openRawResource(R.raw.saw);
                    break;
                case "Submarine":
                    in_stream = resources.openRawResource(R.raw.submarine);
                    break;
                case "The cathedral":
                    in_stream = resources.openRawResource(R.raw.the_cathedral);
                    break;
                case "The Cube":
                    in_stream = resources.openRawResource(R.raw.the_cube);
                    break;
                case "Titanic":
                    in_stream = resources.openRawResource(R.raw.titanic);
                    break;
                case "Zombie":
                    in_stream = resources.openRawResource(R.raw.zombie);
                    break;
                default:
                    isTrashVisible = true;
                    in_stream = resources.openRawResource(R.raw.default_request);
                    break;
            }

            byte[] b = new byte[in_stream.available()];
            in_stream.read(b);
            itemDescription.setText(new String(b));
        } catch (Exception e) {
            itemDescription.setText("Error: can't show description!");
        }
    }

    public void deleteItem(){
        DocumentReference ref =  mItems.document(itemNameVar);
        ref.delete().addOnSuccessListener(success -> {
            Toast.makeText(this, "Your requested escape room, named "+itemNameVar+ "  is successfully deleted.", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.booking_in_item_menu, menu);
        if (isTrashVisible) {
           menu.getItem(0).setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_req_button:
                deleteItem();
                finish();
                return true;
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

        finish();
    }
}