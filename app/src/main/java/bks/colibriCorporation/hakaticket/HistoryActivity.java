package bks.colibriCorporation.hakaticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import bks.colibriCorporation.hakaticket.R;

public class HistoryActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private EditText datesaisie;
    private Spinner  categorie,type;
    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f};
    private String[] xData = {"Loisir", "Course Hebdomadaire", "Divers", "Sport", "Santé", "Vêtement"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        expandableListView = findViewById(R.id.expandable_listview);

        Intent intent = getIntent();
        if(intent != null){
            String resultintent = intent.getStringExtra("resultQR");
            if(resultintent != null) {
                String message = Ticket.createTicket(resultintent);
                Toast.makeText(this.getApplicationContext(),message,Toast.LENGTH_LONG).show();
                setExpListObject();
            }
        }

        mydb = new DatabaseHelper(this);
        datesaisie = (EditText)findViewById(R.id.tridate);
        categorie = (Spinner)findViewById(R.id.tricateg);
        type = (Spinner)findViewById(R.id.tritype);

        ArrayList<String> listeEnseignes = new ArrayList<>();
        /*
        * agrementation des enseignes
        * */
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(HistoryActivity.this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.type));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(myAdapter);
        ArrayAdapter<String> adapterEnseignes = new ArrayAdapter<String>(HistoryActivity.this,android.R.layout.simple_list_item_1, listeEnseignes);
        adapterEnseignes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorie.setAdapter(adapterEnseignes);
        datesaisie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saisieDate();
            }
        });
        setupPieChart();
        TextView textViewMenuHisto = findViewById(R.id.textMenuConnexion);
            textViewMenuHisto.setText(R.string.historiqueTitre);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.nav_qrcode);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), ParameterActivity.class));
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

    public void saisieDate(){
        Calendar cal = Calendar.getInstance();
        int annee = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH);
        int jour = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(HistoryActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,annee,mois,jour);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                datesaisie.setText((month+1) + "/" + year);
            }
        };
    }

    private void setExpListObject() {
        ArrayList<Ticket> listTicket = new ArrayList(Ticket.getListTicket()) ;
        //formé listGroup avec les premières infos du ticket
        //formé listItem avec toute les infos
        ArrayList<String> listGroup = new ArrayList<String>();
        ArrayList<ArrayList<String>> listItem = new ArrayList<ArrayList<String>>();

        for(Ticket monTicket : listTicket){
            //boucle pour parcourir la liste des tickets
            String titre = monTicket.getDateAchat()+ " - " + monTicket.getVendeur();
            ArrayList<String> items = new ArrayList<String>();
            String enTete = monTicket.getAdresse()+" - "+monTicket.getHeureAchat()+" - "+monTicket.getPrixHTC()+"€ - "+monTicket.getPrixTTC()+"€ - "+monTicket.getMoyenPayement()+" - "+monTicket.getInfoCarte()+" - "+monTicket.getReduction()+" - "+monTicket.getNumTransac();
            items.add(enTete);
            for(Produit monProduit : monTicket.getListProduit()){
                String produit = monProduit.getNomProduit()+ " - " + monProduit.getMarqueProduit()+ " - " + monProduit.getPrixUnitaireProduit() + "€ - " + monProduit.getQuantiteProduit() ;
                items.add(produit);
            }
            listGroup.add(titre);
            listItem.add(items);
        }
        adapter = new MainAdapter( this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
    }

    private void setupPieChart() {
        // Populating a list of PieEntries
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0; i < yData.length; i++) {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Liste");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        //pieChart.animateY(1000);
        PieData data = new PieData(dataSet);

        //get the chart
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }
}
