package com.example.hakaticket;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button btn_send;
    private TextInputLayout til_prenom,til_nom,til_email,til_password,til_password2;
    private RequestQueue queue;
    private String url = "https://hugosimon.fr/register.php";
    private boolean checkedCGU = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView textViewmenuinscri = findViewById(R.id.textMenuConnexion);
        textViewmenuinscri.setText(R.string.titremenuinscription);
        btn_send = (Button) findViewById(R.id.boutoninscrire);
        til_prenom = (TextInputLayout) findViewById(R.id.saisieprenominscription);
        til_nom = (TextInputLayout) findViewById(R.id.saisienominscription);
        til_email = (TextInputLayout) findViewById(R.id.saisiemailinsciption);
        til_password = (TextInputLayout) findViewById(R.id.saisiemdpinscription);
        til_password2 = (TextInputLayout) findViewById(R.id.saisieverifmdpinscription);


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }
    public void register(){
            final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setTitle("please wait...");
            progressDialog.show();
            final String prenom = til_prenom.getEditText().getText().toString().trim();
            final String nom = til_nom.getEditText().getText().toString().trim();
            final String email = til_email.getEditText().getText().toString().trim();
            final String password = til_password.getEditText().getText().toString().trim();
            final String password2 = til_password2.getEditText().getText().toString().trim();
            boolean estvalide = testerValid(prenom, nom, email, password, password2);
            if (estvalide && checkedCGU) {
                RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Votre inscription est réussie")) {
                            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                            preferences.edit().putString("mail", email).apply();
                            preferences.edit().putString("mdp", password).apply();
                            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterActivity.this, "erreur d'acces  au server", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("prenom",prenom);
                        params.put("nom",nom);
                        params.put("email",email);
                        params.put("password",password);
                        return params;
                    }
                };

                requestQueue.getCache().clear();
                requestQueue.add(request);
            }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("result", 0);
                if(result == 1){
                    this.checkedCGU = true;
                }
            }
        }
    }

    boolean testerValid(String prenom, String nom, String mail, String password, String password2) {
        if (prenom.length() > 0 && nom.length() > 0 && mail.length() > 0 && password.length() > 7 && password.equals(password2)) {
            return true;
        } else {
            if (prenom.length() <= 0) {
                Toast.makeText(this.getApplicationContext(), "Vous n'avez pas renseigné de prenom", Toast.LENGTH_LONG).show();
                return false;
            }
            if (nom.length() <= 0) {
                Toast.makeText(this.getApplicationContext(), "Vous n'avez pas renseigné de nom", Toast.LENGTH_LONG).show();
                return false;
            }
            if (mail.length() <= 0) {
                Toast.makeText(this.getApplicationContext(), "Vous n'avez pas renseigné de mail", Toast.LENGTH_LONG).show();
                return false;
            }
            if (password.length() <= 7) {
                Toast.makeText(this.getApplicationContext(), "Le mot de passe doit être d'au moins 8 caractères", Toast.LENGTH_LONG).show();
                return false;
            }
            if (!(password == password2)) {
                Toast.makeText(this.getApplicationContext(), "Vous n'avez pas renseigné de prenom", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }

    public void showPopup(View v){
        Intent intent = new Intent(getApplicationContext(), CGUActivity.class);
        startActivityForResult(intent,1);
    }
}
