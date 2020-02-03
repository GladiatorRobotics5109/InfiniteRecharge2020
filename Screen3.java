package com.joshualee0902.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class Screen3 extends AppCompatActivity {

    private Button next2;
    private Button back1;
    public boolean checkCrossedLine;
    private ElegantNumberButton upperScore;
    private ElegantNumberButton lowerScore;
    public String autoUpper;
    public String autoLower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen3);

        next2 = (Button) findViewById(R.id.next2);
        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen4();
            }
        });

        back1 = (Button) findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen2();
            }
        });

        checkCrossedLine = ((CheckBox) findViewById(R.id.checkCrossedLine)).isChecked();

        upperScore = (ElegantNumberButton) findViewById(R.id.AutoUpper);
        autoUpper = upperScore.getNumber();

        lowerScore = (ElegantNumberButton) findViewById(R.id.AutoLower);
        autoLower = lowerScore.getNumber();
    }

    public void openScreen4() {
        Intent intent3 = new Intent(this, Screen4.class);
        startActivity(intent3);
    }

    public void openScreen2() {
        Intent intent4 = new Intent(this, Screen2.class);
        startActivity(intent4);
    }
}
