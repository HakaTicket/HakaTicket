package bks.colibriCorporation.hakaticket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import bks.colibriCorporation.hakaticket.R;
import com.google.android.material.textfield.TextInputLayout;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private String url = "https://hugosimon.fr/login.php";
    private RequestQueue queue;
    private Button btn_login;
    private TextInputLayout til_mail, til_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // permet d'acceder a la page activity_login
        String email = getPreferences(MODE_PRIVATE).getString("mail", null);
        String mdp = getPreferences(MODE_PRIVATE).getString("mdp", null);
        if(email != null && !email.isEmpty() && mdp != null && !mdp.isEmpty()) {
            Toast.makeText(this.getApplicationContext(),"Connexion réussie",Toast.LENGTH_LONG);
            Intent intent = new Intent(getApplicationContext(), ScanActivity.class);
            startActivity(intent);
        }

        btn_login = findViewById(R.id.boutonconnexion);

        til_mail = findViewById(R.id.saisieidentifiantlogin);
        til_password = findViewById(R.id.saisiemdplogin);

        TextView tv_inscription = (TextView)findViewById(R.id.lieninscription);
        tv_inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("please wait...");
                progressDialog.show();
                String email = getPreferences(MODE_PRIVATE).getString("mail", null);
                String mdp = getPreferences(MODE_PRIVATE).getString("mdp", null);
                    final String mail = til_mail.getEditText().getText().toString().trim();
                    final String password = til_password.getEditText().getText().toString().trim();
                    if (testerValid(mail, password)) {
                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("1")) {
                                    SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                                    preferences.edit().putString("mail", mail).apply();
                                    preferences.edit().putString("mdp", password).apply();
                                    Intent intent = new Intent(getApplicationContext(), ParameterActivity.class);
                                    startActivity(intent);
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(LoginActivity.this, "ooooooo", Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("mail", mail);
                                params.put("password", password);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
                        requestQueue.add(request);
                    } else {
                        Toast.makeText(LoginActivity.this, "t'as mal rempli mon gadjo", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
        });
    }

    boolean testerValid(String mail, String password) {
        if (mail.length() > 0 && password.length() > 7) {
            return true;
        } else {
            if (mail.length() <= 0) {
                Toast.makeText(this.getApplicationContext(), "Vous n'avez pas renseigné de mail", Toast.LENGTH_LONG).show();
                return false;
            }
            if (password.length() <= 7) {
                Toast.makeText(this.getApplicationContext(), "Le mot de passe doit être d'au moins 8 caractères", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return false;
    }
}