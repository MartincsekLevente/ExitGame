package com.example.exitgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity {

    private static final String LOG_TAG = RegisterActivity.class.getName();
    private static final String PREF_KEY = MainActivity.class.getPackage().toString();
    private static final int SECRET_KEY = 69;

    EditText regUserNameEditText;
    EditText regEmailEditText;
    EditText regPasswordEditText;
    EditText regPasswordConfirmEditText;

    private SharedPreferences preferences;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        int secret_key = getIntent().getIntExtra("SECRET_KEY", 0);

        if (secret_key != SECRET_KEY) {
            finish();
        }

        regUserNameEditText = findViewById(R.id.editTextRegUserName);
        regEmailEditText = findViewById(R.id.editTextRegEmail);
        regPasswordEditText = findViewById(R.id.editTextRegPassword);
        regPasswordConfirmEditText = findViewById(R.id.editTextRegPasswordConf);
        preferences = getSharedPreferences(PREF_KEY, MODE_PRIVATE);
        String savedUserName = preferences.getString("email", "");
        String savedPassword = preferences.getString("password", "");

        regUserNameEditText.setText(savedUserName);
        regPasswordEditText.setText(savedPassword);
        regPasswordConfirmEditText.setText(savedPassword);

        mAuth= FirebaseAuth.getInstance();
    }

    public void Cancel(View view) {
        finish();
    }

    public void Register(View view) {
        String userName = regUserNameEditText.getText().toString();
        String password = regPasswordEditText.getText().toString();
        String passwordConfirm = regPasswordConfirmEditText.getText().toString();
        String email = regEmailEditText.getText().toString();

        if (!password.equals(passwordConfirm)) {
            Log.e(LOG_TAG, "Nem egyenlő a jelszó és a megerősítése!");
        } else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(LOG_TAG, "User created successfully!");
                        startShopping();
                    }
                    else {
                        Log.d(LOG_TAG,"User wasn't created successfully!");
                        Toast.makeText(RegisterActivity.this,"User wasn't created successfully: "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public void startShopping() {
        Intent intent = new Intent(this,BookingListActivity.class);
        intent.putExtra("SECRET_KEY",SECRET_KEY);
        startActivity(intent);
    }
}