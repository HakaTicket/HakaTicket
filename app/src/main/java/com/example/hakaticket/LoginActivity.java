package com.example.hakaticket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView textViewmenuinscri = findViewById(R.id.textMenuConnexion);
            textViewmenuinscri.setText(R.string.TitreMenuConnexion);
    }
}
