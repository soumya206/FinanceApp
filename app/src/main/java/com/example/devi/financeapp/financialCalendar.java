package com.example.devi.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class financialCalendar extends AppCompatActivity {

    private ArrayList<String> billNames;
    private ArrayList<Double> billAmounts;
    private ArrayList<String> billDates;
    private ArrayList<String> billAddress;

    private EditText inputBudget;
    private TextView outputRemaining;
    private TextView noBillsText;

    // This new Bill uses request code 10
    private Button addBill;
    protected void changeToAddBill(){
        addBill = findViewById(R.id.AddBill);
        addBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newBill = new Intent(financialCalendar.this, addNewBill.class);
                startActivityForResult(newBill, 10);
            }
        });
    }

    protected void initInterface(){
        billNames = new ArrayList<String>();
        billAmounts = new ArrayList<Double>();
        billDates = new ArrayList<String>();
        billAddress = new ArrayList<String>();

        inputBudget = findViewById(R.id.EditExpense);
        outputRemaining = findViewById(R.id.textView12);
        noBillsText = findViewById(R.id.NoUpcoming);

    }

    protected void populateList(){

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_calendar);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        changeToAddBill();
        initInterface();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 10){
            if(resultCode == Activity.RESULT_OK){
                if(data == null){
                    Toast.makeText(this, "ERROR: Null Result Received", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        }
    }
}
