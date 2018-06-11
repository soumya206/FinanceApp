package com.example.devi.financeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TodayTotal extends AppCompatActivity {
    EditText totalMoneyInput;
    EditText foodAnswer;
    EditText clothing;
    EditText miscellaneous;
    EditText category;
    JSONObject jo;
    JSONArray ja;
    JSONObject temp = new JSONObject();
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_total);
        totalMoneyInput = findViewById(R.id.totalMoneyInput);
        foodAnswer = findViewById(R.id.foodAnswer);
        clothing = findViewById(R.id.editText2);
        miscellaneous = findViewById(R.id.editText4);
        button = findViewById(R.id.button);
        category = findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    File f = new File(getFilesDir(), "file.ser");
                    FileInputStream fi = new FileInputStream(f);
                    ObjectInputStream o = new ObjectInputStream(fi);
                    // Notice here that we are de-serializing a String object (instead of
                    // a JSONObject object) and passing the String to the JSONObject’s
                    // constructor. That’s because String is serializable and
                    // JSONObject is not. To convert a JSONObject back to a String, simply
                    // call the JSONObject’s toString method.
                    String j = null;
                    try {
                        j = (String) o.readObject();
                    } catch (ClassNotFoundException c) {
                        c.printStackTrace();
                    }
                    try {
                        jo = new JSONObject(j);
                        ja = jo.getJSONArray("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch(IOException e){
                    jo = new JSONObject();
                    ja = new JSONArray();
                    try{
                        jo.put("data", ja);
                    }
                    catch(JSONException j){
                        j.printStackTrace();
                    }
                }
                String total = totalMoneyInput.getText().toString();
                try{
                    temp.put("total", total);
                    temp.put("food", foodAnswer.getText().toString());
                    temp.put("clothing", clothing.getText().toString());
                    temp.put("misc",miscellaneous.getText().toString());
                    temp.put("category",category.getText().toString());

                }catch(JSONException e){

                }
                ja.put(temp);
                //serilaize the file
                try{
                    File f = new File(getFilesDir(), "file.ser");
                    FileOutputStream fo = new FileOutputStream(f);
                    ObjectOutputStream o = new ObjectOutputStream(fo);
                    String j = jo.toString();
                    o.writeObject(j);
                    o.close();
                    fo.close();
                }
                catch(IOException e){

                }
                //Intent i = new Intent(TodayTotal.this, MainActivity.class);
                //startActivity(i);
                finish();

            }
        });



    }
}
