package com.example.hakaticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textViewmenuinscri = findViewById(R.id.textMenuConnexion);
        textViewmenuinscri.setText(R.string.titreMenuInscription);
    }
}
