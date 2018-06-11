package com.example.devi.financeapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class financialCalendar extends AppCompatActivity {

    private ArrayList<String> billNames;
    private ArrayList<String> billAmounts;
    private ArrayList<String> billDates;
    private ArrayList<String> billAddress;
    private Boolean[]  notificationPrefs;
    private ArrayList<Integer> priorities;
    private ArrayList<Double> temp;
    private int itemSelected;
    private int listSize = 0;

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

    protected void initData() throws IOException {
        File filePath = new File(this.getFilesDir().getAbsolutePath()+"/appData/CalData.txt");
        try {
            FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line;

            line = br.readLine();
            billNames = new ArrayList<String>(Arrays.asList(line.split(",")));
            line = br.readLine();
            billAmounts = new ArrayList<String>(Arrays.asList(line.split(",")));
            line = br.readLine();
            billDates = new ArrayList<String>(Arrays.asList(line.split(",")));
            line = br.readLine();
            billAddress = new ArrayList<String>(Arrays.asList(line.split(",")));

            fis.close();
            isr.close();

            populateList();
            updateRemaining();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        //try {
            //initData();
        //} catch (IOException e) {
            //e.printStackTrace();
        //}
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
                    listSize++;

                    CustomObject customObject =  new CustomObject(data.getStringExtra("name"), "$"+data.getStringExtra("amount"), data.getStringExtra("date"));
                    listObject.add(customObject);
                    populateList();
                    updateRemaining();
                }


            }
        }
    }

    @Override
    public void onBackPressed(){
        File file = new File(this.getFilesDir(), "appData");
        if(!file.exists()){
            file.mkdir();
        }

        try{
            String fileName = "CalData.txt";
            File data = new File(file, fileName);
            FileWriter writer = new FileWriter(data);
            writer.write(billNames.toString());
            writer.append("\n");
            writer.append(billAmounts.toString());
            writer.append("\n");
            writer.append(billDates.toString());
            writer.append("\n");
            writer.append(billAddress.toString());
            writer.flush();
            writer.close();
            finish();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}