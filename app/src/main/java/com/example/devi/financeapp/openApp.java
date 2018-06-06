package com.example.devi.financeapp;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

public class openApp extends AppCompatActivity {

    Button enterButton;
    AnimationDrawable animationDraw;
    ConstraintLayout thelayout;

    public void enterMainActivity(){
        enterButton=findViewById(R.id.enterApp);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent enterMainActivity=new Intent(openApp.this,MainActivity.class);
                startActivity(enterMainActivity);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_app);

        thelayout=findViewById(R.id.thelayouts);
        animationDraw=(AnimationDrawable)thelayout.getBackground();
        animationDraw.setEnterFadeDuration(3000);
        animationDraw.setExitFadeDuration(3000);
        animationDraw.start();
        enterMainActivity();



    }
}
