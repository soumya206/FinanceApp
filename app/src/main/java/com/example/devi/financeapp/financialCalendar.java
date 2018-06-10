package com.example.devi.financeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class financialCalendar extends AppCompatActivity {

    private ArrayList<String> billNames;
    private ArrayList<String> billAmounts;
    private ArrayList<String> billDates;
    private ArrayList<String> billAddress;
    private Boolean[]  notificationPrefs;
    private ArrayList<Integer> priorities;
    private int itemSelected;

    private EditText inputBudget;
    private TextView outputRemaining;
    private TextView noBillsText;

    private FloatingActionButton delete;

    private ListView list;
    private ArrayList<CustomObject> listObject;

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
        billAmounts = new ArrayList<String>();
        billDates = new ArrayList<String>();
        billAddress = new ArrayList<String>();
        listObject = new ArrayList<CustomObject>();
        //notificationPrefs = new Boolean[4];
        //priorities = new ArrayList<Integer>();

        inputBudget = findViewById(R.id.EditExpense);
        outputRemaining = findViewById(R.id.textView12);
        noBillsText = findViewById(R.id.NoUpcoming);

        delete = findViewById(R.id.floatingActionButton);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listObject.remove(itemSelected);
                populateList();
                updateRemaining();
                Toast.makeText(financialCalendar.this, "Bill Deleted", Toast.LENGTH_LONG).show();
            }
        });

        list = findViewById(R.id.BillView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itemSelected == -1){
                    return;
                } else {
                    itemSelected = position;
                }
            }
        });


    }

    protected void populateList(){
        CustomAdapter customAdapter = new CustomAdapter(this, listObject);
        list = findViewById(R.id.BillView);
        list.setAdapter(customAdapter);
        if(listObject.size() > 0){
            noBillsText.setVisibility(View.INVISIBLE);
        } else {
            noBillsText.setVisibility(View.VISIBLE);
        }
    }

    protected void updateRemaining(){
        double budget = Double.parseDouble(inputBudget.getText().toString());
        int i;
        double sum = 0;
        for(i = 0; i < billAmounts.size(); i++) {
            sum += Double.parseDouble(billAmounts.get(i));
        }
        String total = Double.toString(budget-sum);
        outputRemaining.setText(total);
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
                } else {
                    billNames.add(data.getStringExtra("name"));
                    billAmounts.add(data.getStringExtra("amount"));
                    billDates.add(data.getStringExtra("date"));
                    billAddress.add(data.getStringExtra("address"));

                    CustomObject customObject =  new CustomObject(data.getStringExtra("name"), "$"+data.getStringExtra("amount"), data.getStringExtra("date"));
                    listObject.add(customObject);
                    populateList();
                    updateRemaining();
                }


            }
        }
    }
}