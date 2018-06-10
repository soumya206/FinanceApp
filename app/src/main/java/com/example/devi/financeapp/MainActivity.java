package com.example.devi.financeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public ImageButton totalButton;
    public void changeToTodayTotal() {
        totalButton = findViewById(R.id.todaysTotalButton);
        totalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newPage=new Intent(MainActivity.this,TodayTotal.class);
                startActivity(newPage);
            }
        });
    }

    // Matt: This code handles the transition to the financial calendar
    public ImageButton financialCalendarButton;
    public void changeToFinancialCalender(){
        financialCalendarButton = findViewById(R.id.calendarButton);
        financialCalendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent finCal = new Intent(MainActivity.this, financialCalendar.class);
                startActivity(finCal);
            }
        });
    }

    //Sahil: This code handles the transition to the spending Chart
    public ImageButton scButton;
    public void changetoSpendingChart(){
        scButton = findViewById(R.id.scButton);
        scButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent spendingChart = new Intent(MainActivity.this, SpendingPieChart.class);
                startActivity(spendingChart);
            }
        });
    }

    public ImageButton tips;
    public void changeToTips(){
        tips = findViewById(R.id.bLButton);
        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ti = new Intent(MainActivity.this, Tips.class);
                startActivity(ti);
            }
        });
    }

    public ImageButton grButton;
    public void changeToLineGraph() {
        grButton = findViewById(R.id.graphButton);
        grButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graph = new Intent(MainActivity.this, LineGraph.class);
                startActivity(graph);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeToTodayTotal();
        changeToFinancialCalender();
        changetoSpendingChart();
        changeToTips();
        changeToLineGraph();
    }
}
