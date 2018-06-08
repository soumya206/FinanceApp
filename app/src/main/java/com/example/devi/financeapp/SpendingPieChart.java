package com.example.devi.financeapp;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class SpendingPieChart extends AppCompatActivity {
    private String TAG ="MainActivity";
    JSONObject jo;
    JSONArray ja;
    ArrayList<Data> pieData = new ArrayList<Data>();
    private float[] yData = {25.3f, 66.76f, 44.32f, 46.01f};
    private String[] xData ={"Car Maintenance", "Groceries", "Taxes","Bills"};
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Log.d(TAG, "OnCreate: starting to create chart");
        Description description = new Description();
        description.setTextColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        description.setText("Spending Rundown");

        pieChart = (PieChart) findViewById(R.id.PieChart);
        pieChart.setDescription(description);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(0f);
        pieChart.setTransparentCircleAlpha(0);
        addDataSet();
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected Value select from chart");
                Log.d(TAG,"onValueSelected: " + e.toString());
                Log.d(TAG,"onValueSelected: " + h.toString());
                int pos1 = e.toString().indexOf("y: ");
                String cost = e.toString().substring(pos1 + 3);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(cost)){
                        pos1 = i;
                        break;
                    }
                }
                String expense = xData[pos1];
                Toast.makeText(SpendingPieChart.this, "Expense " + expense + "\n" + "Cost: $" + cost, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void addDataSet() {
        Log.d(TAG,"addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();
        TextView textView = findViewById(R.id.textView9);
        textView.setVisibility(View.INVISIBLE);
        try{
            File f = new File(getFilesDir(), "file.ser");
            FileInputStream fi = new FileInputStream(f);
            ObjectInputStream o = new ObjectInputStream(fi);
            // Notice here that we are de-serializing a String object (instead of
            // a JSONObject object) and passing the String to the JSONObject’s
            // constructor. That’s because String is serializable and
            // JSONObject is not. To convert a JSONObject back to a String, simply
            // call the JSONObject’s toString method.
            String j = null;
            try{
                j = (String) o.readObject();
            }
            catch(ClassNotFoundException c){
                c.printStackTrace();
            }
            try {
                jo = new JSONObject(j);
                ja = jo.getJSONArray("data");
            }
            catch(JSONException e){
                e.printStackTrace();
            }
        }
        catch(IOException e){
            // Here, initialize a new JSONObject
            textView.setVisibility(View.VISIBLE);
            jo = new JSONObject();
            ja = new JSONArray();
            try{
                jo.put("data", ja);
            }
            catch(JSONException j){
                j.printStackTrace();
            }
        }


        for(int i = 0; i< ja.length(); i++){
            Data pdata = new Data();
            try {
                pdata.name = ja.getJSONObject(i).getString("name");
                pdata.price = ja.getJSONObject(i).getString("price");
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            pieData.add(pdata);
        }

        int[] temp = new int[ja.length()];

        for(int i = 0; i<ja.length(); i++){
            String s = pieData.get(i).price;
            int j = Integer.parseInt(s);
            temp[i] = j;
        }

        for(int i = 0; i < ja.length(); i++){
            yEntrys.add(new PieEntry(temp[i], i));
        }
        for(int i = 0; i < ja.length(); i++){
            xEntrys.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Spending");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}
