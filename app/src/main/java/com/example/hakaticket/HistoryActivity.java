package com.example.hakaticket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import android.widget.Toast;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

public class HistoryActivity extends AppCompatActivity {

    private static String TAG = "HistoryActivity";

    private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
    private String[] xData = {"Mitch", "Jessica", "Mohammad", "Kelsey", "Sam", "Robert", "Ashley"};
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Log.d(TAG, "onCreate : starting to create chart");

        setupPieChart();
    }

    private void setupPieChart() {
        // Populating a list of PieEntries
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i=0; i < yData.length; i++) {
            pieEntries.add(new PieEntry(yData[i], xData[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Liste");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.animateY(1000);
        PieData data = new PieData(dataSet);

        //get the chart
        PieChart chart = (PieChart) findViewById(R.id.chart);
        chart.setData(data);
        chart.invalidate();
    }
}
