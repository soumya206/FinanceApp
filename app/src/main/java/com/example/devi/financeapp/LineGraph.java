package com.example.devi.financeapp;
// By: Devi Manivannan
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;



public class LineGraph extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
     /*   //find the top three expenses from miscellaneous purchases,must be in order
        String titles[] = {"random expense 1","etc",""};
        int values[] = {685,76,39};
        Button bubble = findViewById(R.id.button4);
        Button bubble2 = findViewById(R.id.button5);
        Button bubble3 = findViewById(R.id.button6);
        //set the bubbles text appropriately
        String one = titles[0]+" $" + values[0];
        bubble.setText(one);
        String two= titles[1]+" $" + values[1];
        bubble2.setText(two);
        String three = titles[2]+" $" + values[2];
        bubble3.setText(three);
        */
        //starts with weekly view, array of expenses for past week and array of corresponding dates
        int expenses[] = { 300, 510, 420, 250, 280};
        String dates[] = {"4/18","4/25", "5/2", "5/9","5/16"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] points = new DataPoint[expenses.length];
        for (int i = 0; i < points.length; i++) {
            points[i] = new DataPoint(i, expenses[i]);
        }


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(dates);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.addSeries(series);
        series.setColor(0xFF0B7469);
        series.setDrawBackground(true);

        Button wkButton = findViewById(R.id.button2);
        Button mtButton = findViewById(R.id.button);
        Button yrButton = findViewById(R.id.button3);

        final TextView graphTitle = findViewById(R.id.textView3);
        wkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //each time this button is clicked, these 2 arrays will read data for the past week
                graphTitle.setText("                 " + "Weekly Expenses");
                int expenses[] = { 300, 510, 420, 250, 280};
                String dates[] = {"4/18","4/25", "5/2", "5/9","5/16"};
                GraphView graph2 = (GraphView) findViewById(R.id.graph);
                graph2.removeAllSeries();
                DataPoint[] points = new DataPoint[expenses.length];
                for (int i = 0; i < points.length; i++) {
                    points[i] = new DataPoint(i, expenses[i]);
                }


                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph2);
                staticLabelsFormatter.setHorizontalLabels(dates);
                graph2.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                graph2.addSeries(series);

                series.setColor(0xFF0B7469);
                series.setDrawBackground(true);
            }
        });
        mtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //each time this button is clicked, these 2 arrays will read data for the past month
                graphTitle.setText("                 " + "Monthly Expenses");
                int expenses[] = { 968, 1300, 250};
                String dates[] = {"4","5","6"};
                GraphView graph3 = (GraphView) findViewById(R.id.graph);
                graph3.removeAllSeries();
                DataPoint[] points = new DataPoint[expenses.length];
                for (int i = 0; i < points.length; i++) {
                    points[i] = new DataPoint(i, expenses[i]);
                }


                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph3);
                staticLabelsFormatter.setHorizontalLabels(dates);
                graph3.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                graph3.addSeries(series);

                series.setColor(0xFF0B7469);
                series.setDrawBackground(true);
            }
        });
        yrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //each time this button is clicked, these 2 arrays will read data for the past year
                graphTitle.setText("                 " + "Yearly Expenses");
                int expenses[] = {9727, 7327,8125};
                String dates[] = {"2016","2017", "2018"};
                GraphView graph4 = (GraphView) findViewById(R.id.graph);
                graph4.removeAllSeries();
                DataPoint[] points = new DataPoint[expenses.length];
                for (int i = 0; i < points.length; i++) {
                    points[i] = new DataPoint(i, expenses[i]);
                }


                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

                StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph4);
                staticLabelsFormatter.setHorizontalLabels(dates);
                graph4.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
                graph4.addSeries(series);
                series.setColor(0xFF0B7469);
                series.setDrawBackground(true);
            }
        });
    }

}