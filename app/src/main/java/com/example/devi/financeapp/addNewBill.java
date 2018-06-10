package com.example.devi.financeapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class addNewBill extends AppCompatActivity {

    // Buttons
    private Button cancel;
    private Button save;

    // EditTexts
    private EditText billName;
    private EditText billAmount;
    private EditText dateInput;
    private EditText locationInput;

    // Notifications
    private Switch notification;
    private CheckedTextView onDay;
    private CheckedTextView dayBefore;
    private CheckedTextView threeDay;
    private CheckedTextView weekBefore;

    // Priority
    private Switch priority;
    private CheckedTextView low;
    private CheckedTextView medium;
    private CheckedTextView high;
    private CheckedTextView critical;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bill);

        cancelBill(); //Initialize cancel button
        saveBill();   //Initialize save button
        initEdits();  //Initialize EditTexts
        toggleNotifications(); //Initialize notifications toggle
        togglePriority(); //Initialize prioritize toggle
    }

    protected void initEdits(){
        billName = findViewById(R.id.BillName);
        billAmount = findViewById(R.id.BillAmount);
        dateInput = findViewById(R.id.EnteredDate);
        locationInput = findViewById(R.id.Location);
    }

    protected void cancelBill(){
        cancel = findViewById(R.id.Cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });
    }

    protected void saveBill(){
        save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                String name = null;
                String amount = null;
                String date;
                String address;

                if(billName.getText().toString().isEmpty()){
                    Toast.makeText(addNewBill.this, "Please input a Bill Name", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    name = billName.getText().toString();
                    returnIntent.putExtra("name", name);
                }

                if(billAmount.getText().toString().isEmpty()){
                    Toast.makeText(addNewBill.this, "Please input a Bill Amount", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    amount = billAmount.getText().toString();
                    returnIntent.putExtra("amount", amount);
                }

                //Date inputDate = null;
                //Calendar cal = Calendar.getInstance();

                //SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");

                if(dateInput.getText().toString().isEmpty()){
                    Toast.makeText(addNewBill.this, "Please input a '-' separated date", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    date = dateInput.getText().toString();

                }
                returnIntent.putExtra("date", date);


                if(!locationInput.getText().toString().isEmpty()){
                    address = locationInput.getText().toString();
                    returnIntent.putExtra("address", address);
                }

                if(notification.isChecked()){
                    Boolean[] notifSettings = new Boolean[4];
                    notifSettings[0] = onDay.isChecked();
                    notifSettings[1] = dayBefore.isChecked();
                    notifSettings[2] = threeDay.isChecked();
                    notifSettings[3] = weekBefore.isChecked();
                    returnIntent.putExtra("notifSettings", notifSettings);
                }

                int rank = 0;
                if(priority.isChecked()){
                    if(low.isChecked()) rank = 1;
                    if(medium.isChecked()) rank = 2;
                    if(high.isChecked()) rank = 3;
                    if(critical.isChecked()) rank = 4;
                }

                returnIntent.putExtra("rank", rank);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    protected void toggleNotifications(){
        // Switch
        notification = findViewById(R.id.Notifications);

        // Checks
        onDay = findViewById(R.id.DayOfCheck);
        onDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDay.isChecked()){
                    onDay.setChecked(false);
                } else {
                    onDay.setChecked(true);
                }
            }
        });

        dayBefore = findViewById(R.id.DayBeforeCheck);
        dayBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dayBefore.isChecked()){
                    dayBefore.setChecked(false);
                } else {
                    dayBefore.setChecked(true);
                }
            }
        });

        threeDay = findViewById(R.id.ThreeDaysCheck);
        threeDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(threeDay.isChecked()){
                    threeDay.setChecked(false);
                } else {
                    threeDay.setChecked(true);
                }
            }
        });

        weekBefore = findViewById(R.id.WeekBeforeCheck);
        weekBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weekBefore.isChecked()){
                    weekBefore.setChecked(false);
                } else {
                    weekBefore.setChecked(true);
                }
            }
        });

        notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    onDay.setVisibility(View.VISIBLE);
                    dayBefore.setVisibility(View.VISIBLE);
                    threeDay.setVisibility(View.VISIBLE);
                    weekBefore.setVisibility(View.VISIBLE);
                } else {
                    onDay.setVisibility(View.INVISIBLE);
                    dayBefore.setVisibility(View.INVISIBLE);
                    threeDay.setVisibility(View.INVISIBLE);
                    weekBefore.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    protected void togglePriority(){
        // Switch
        priority = findViewById(R.id.Prioritize);

        // Checks
        low = findViewById(R.id.LowCheck);
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(low.isChecked()){
                    low.setChecked(false);
                } else {
                    low.setChecked(true);
                    medium.setChecked(false);
                    high.setChecked(false);
                    critical.setChecked(false);
                }
            }
        });

        medium = findViewById(R.id.MediumCheck);
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(medium.isChecked()){
                    medium.setChecked(false);
                } else {
                    medium.setChecked(true);
                    low.setChecked(false);
                    high.setChecked(false);
                    critical.setChecked(false);
                }
            }
        });

        high = findViewById(R.id.HighCheck);
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(high.isChecked()){
                    high.setChecked(false);
                } else {
                    high.setChecked(true);
                    low.setChecked(false);
                    medium.setChecked(false);
                    critical.setChecked(false);
                }
            }
        });

        critical = findViewById(R.id.CriticalCheck);
        critical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(critical.isChecked()){
                    critical.setChecked(false);
                } else {
                    critical.setChecked(true);
                    low.setChecked(false);
                    medium.setChecked(false);
                    high.setChecked(false);
                }
            }
        });

        priority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    low.setVisibility(View.VISIBLE);
                    medium.setVisibility(View.VISIBLE);
                    high.setVisibility(View.VISIBLE);
                    critical.setVisibility(View.VISIBLE);
                } else {
                    low.setVisibility(View.INVISIBLE);
                    medium.setVisibility(View.INVISIBLE);
                    high.setVisibility(View.INVISIBLE);
                    critical.setVisibility(View.INVISIBLE);
                }
            }
        });
    }


}
