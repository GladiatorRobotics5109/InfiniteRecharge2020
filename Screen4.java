package com.joshualee0902.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class Screen4 extends AppCompatActivity {

    private Button next3;
    private Button back2;
    private ElegantNumberButton upperScore;
    private ElegantNumberButton lowerScore;
    public boolean checkWheelSpin;
    public boolean checkWheelColor;
    public boolean checkClimbed;
    private ElegantNumberButton foul;
    public String teleUpper;
    public String teleLower;
    public String fouls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen4);

        next3 = (Button) findViewById(R.id.next3);
        next3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen5();
            }
        });

        back2 = (Button) findViewById(R.id.back2);
        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen3();
            }
        });

        upperScore = (ElegantNumberButton) findViewById(R.id.TeleUpper);
        teleUpper = upperScore.getNumber();

        lowerScore = (ElegantNumberButton) findViewById(R.id.TeleLower);
        teleLower = lowerScore.getNumber();

        checkWheelSpin = ((CheckBox) findViewById(R.id.checkWheelSpin)).isChecked();
        checkWheelColor = ((CheckBox) findViewById(R.id.checkWheelColor)).isChecked();
        checkClimbed = ((CheckBox) findViewById(R.id.checkClimb)).isChecked();

        foul = (ElegantNumberButton) findViewById(R.id.Fouls);
        fouls = foul.getNumber();
    }

    public void openScreen5() {
        Intent intent5 = new Intent(this, Screen5.class);
        startActivity(intent5);
    }

    public void openScreen3() {
        Intent intent6 = new Intent(this, Screen3.class);
        startActivity(intent6);
    }
}
