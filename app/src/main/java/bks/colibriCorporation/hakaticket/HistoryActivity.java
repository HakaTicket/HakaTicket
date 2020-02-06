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

        Intent intent = getIntent();
        if(intent != null){
            String resultintent = intent.getStringExtra("resultQR");
            if(resultintent != null) {
                String message = Ticket.createTicket(resultintent);
                Toast.makeText(this.getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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

        expandableListView = findViewById(R.id.expandable_listview);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter( this,listGroup,listItem);
        expandableListView.setAdapter(adapter);
        initListData();

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
        List<String> listGroup;
        ArrayList<String> listItem = new ArrayList<String>();
        int i =0;
        //while (i<=listTicket.length){
        //boucle pour parcourir la liste des tickets
        /**
         * listGroup.add(listTicket[i].numTransac) //ajout des titres des group
         * reste à créer la chlidlist, avec chaque Item lié au bon ticket
         */
        //}
    }

    private void initListData() {
        listGroup.add(getString(R.string.group1));
        listGroup.add(getString(R.string.group2));
        listGroup.add(getString(R.string.group3));
        listGroup.add(getString(R.string.group4));
        listGroup.add(getString(R.string.group5));

        String[] array;

        List<String> list1 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group1);
        for(String item : array) {
            list1.add(item);
        }

        List<String> list2 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group2);
        for(String item : array) {
            list2.add(item);
        }

        List<String> list3 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group3);
        for(String item : array) {
            list3.add(item);
        }

        List<String> list4 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group4);
        for(String item : array) {
            list4.add(item);
        }

        List<String> list5 = new ArrayList<>();
        array = getResources().getStringArray(R.array.group5);
        for(String item : array) {
            list5.add(item);
        }

        listItem.put(listGroup.get(0),list1);
        listItem.put(listGroup.get(1),list2);
        listItem.put(listGroup.get(2),list3);
        listItem.put(listGroup.get(3),list4);
        listItem.put(listGroup.get(4),list5);
        adapter.notifyDataSetChanged();
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
