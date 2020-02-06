package com.example.hakaticket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HistoryActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

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
                Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                setExpListObject();
            }
        }

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
            for(Produit monProduits : monTicket.getListProduit()){
                String produit = monProduits.getNomProduit()+ " - " + monProduits.getPrixUnitaireProduit();
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
