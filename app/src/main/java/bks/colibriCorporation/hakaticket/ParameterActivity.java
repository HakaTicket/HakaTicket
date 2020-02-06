package bks.colibriCorporation.hakaticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import bks.colibriCorporation.hakaticket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class ParameterActivity extends AppCompatActivity {

    private Button btn_deco, btn_update;
    private TextInputLayout til_prenom,til_nom,til_email,til_password,til_password2;
    private final String url="http://hugosimon.fr/bddupdate.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameter);

        TextView textViewMenuParametre = findViewById(R.id.textMenuConnexion);
        textViewMenuParametre.setText(R.string.TitreMenuParametre);
        btn_deco = (Button) findViewById(R.id.boutonDeco);
        btn_update = (Button) findViewById(R.id.boutonConfirmation);
        til_prenom = (TextInputLayout) findViewById(R.id.inputPrenom);
        til_nom = (TextInputLayout) findViewById(R.id.inputNom);
        til_email = (TextInputLayout) findViewById(R.id.inputEmail);
        til_password = (TextInputLayout) findViewById(R.id.inputMdp1);
        til_password2 = (TextInputLayout) findViewById(R.id.inputMdp2);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoupdate();
            }
        });

        btn_deco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deco();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.nav_qrcode);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_qrcode:
                        startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_ticket:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void infoupdate() {
        final ProgressDialog progressDialog = new ProgressDialog(ParameterActivity.this);
        progressDialog.setTitle("please wait...");
        progressDialog.show();
        final String oldemail = getPreferences(MODE_PRIVATE).getString("mail","hugosimo1999@gmail.com");
        final String password = til_password.getEditText().getText().toString().trim();
        final String prenom = til_prenom.getEditText().getText().toString().trim();
        final String nom = til_nom.getEditText().getText().toString().trim();
        final String email = til_email.getEditText().getText().toString().trim();
        final String password2 = til_password2.getEditText().getText().toString().trim();
        if(testerValid(prenom, nom, email, password, password2)) {
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    if (response.equals("1")) {
                        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                        preferences.edit().putString("email", email).apply();
                        preferences.edit().putString("mdp", password).apply();
                        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
                        startActivity(intent);
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(ParameterActivity.this, response, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof NetworkError) {
                        Toast.makeText(ParameterActivity.this, "Can't connect to Internet. Please check your connection.", Toast.LENGTH_LONG).show();
                    }
                    else if (error instanceof ServerError) {
                        Toast.makeText(ParameterActivity.this, "Unable to login. Either the username or password is incorrect.", Toast.LENGTH_LONG).show();
                    }
                    else if (error instanceof ParseError) {
                        Toast.makeText(ParameterActivity.this, "Parsing error. Please try again.", Toast.LENGTH_LONG).show();
                    }
                    else if (error instanceof NoConnectionError) {
                        Toast.makeText(ParameterActivity.this, "Can't connect to internet. Please check your connection.", Toast.LENGTH_LONG).show();
                    }
                    else if (error instanceof TimeoutError) {
                        Toast.makeText(ParameterActivity.this, "Connection timed out. Please check your internet connection.", Toast.LENGTH_LONG).show();
                    }
                    error.printStackTrace();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("email", email);
                    params.put("password", password);
                    params.put("prenom", prenom);
                    params.put("nom", nom);
                    params.put("oldemail", oldemail);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(ParameterActivity.this);
            requestQueue.add(request);
        } else {
            Toast.makeText(ParameterActivity.this, "t'as mal rempli mon gadjo", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    public void deco(){
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        preferences.edit().clear().apply();
        Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
        startActivity(intent);
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
}
