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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;

public class InItemActivity extends AppCompatActivity {
    private static final String LOG_TAG = InItemActivity.class.getName();
    ImageView itemImage;
    TextView itemName;
    TextView itemPrice;
    TextView itemDescription;
    int itemDescriptionKey;
    Button backButton;

    int itemImageVar;
    String itemNameVar, itemPriceVar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_item);

        itemImage = findViewById(R.id.grid_image);
        itemName = findViewById(R.id.item_name);
        itemPrice = findViewById(R.id.item_price);
        itemDescription = findViewById(R.id.item_description);
        backButton = findViewById(R.id.back_button);
        getExtras();
        setExtras();
    }

    private void getExtras() {

        itemImageVar = getIntent().getIntExtra("item_image", 0);
        itemNameVar = getIntent().getStringExtra("item_name");
        itemPriceVar = getIntent().getStringExtra("item_price");
        itemDescriptionKey = getIntent().getIntExtra("item_description_key",0);
    }

    private void setExtras() {
        itemImage.setImageResource(itemImageVar);
        itemName.setText(itemNameVar);
        itemPrice.setText(itemPriceVar);
        InputStream in_stream;
        try {
            Resources resources = getResources();
            switch (itemDescriptionKey) {
                case 0:
                    in_stream = resources.openRawResource(R.raw.alice);
                    break;
                case 1:
                    in_stream = resources.openRawResource(R.raw.bank_robbing);
                    break;
                case 2:
                    in_stream = resources.openRawResource(R.raw.chernobyl);
                    break;
                case 3:
                    in_stream = resources.openRawResource(R.raw.cyber_attack);
                    break;
                case 4:
                    in_stream = resources.openRawResource(R.raw.iron_throne);
                    break;
                case 5:
                    in_stream = resources.openRawResource(R.raw.labyrinth_of_the_mind);
                    break;
                case 6:
                    in_stream = resources.openRawResource(R.raw.nuclear_adventure);
                    break;
                case 7:
                    in_stream = resources.openRawResource(R.raw.pirate_bay);
                    break;
                case 8:
                    in_stream = resources.openRawResource(R.raw.saw);
                    break;
                case 9:
                    in_stream = resources.openRawResource(R.raw.submarine);
                    break;
                case 10:
                    in_stream = resources.openRawResource(R.raw.the_cathedral);
                    break;
                case 11:
                    in_stream = resources.openRawResource(R.raw.the_cube);
                    break;
                case 12:
                    in_stream = resources.openRawResource(R.raw.titanic);
                    break;
                case 13:
                    in_stream = resources.openRawResource(R.raw.zombie);
                    break;
                default:
                    in_stream = resources.openRawResource(R.raw.zombie);
                    break;
            }

            byte[] b = new byte[in_stream.available()];
            in_stream.read(b);
            itemDescription.setText(new String(b));
        } catch (Exception e) {
            itemDescription.setText("Error: can't show terms.");
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
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.settings:
                Log.d(LOG_TAG, "SETTINGS PRESSED");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void backToSearch(View view) {
        finish();
    }
}