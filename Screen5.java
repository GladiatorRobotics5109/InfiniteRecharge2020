package com.joshualee0902.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

public class Screen5 extends AppCompatActivity {

    private Button back3;
    private Button next4;
    public EditText totalPoints;
    private ElegantNumberButton rankingPoint;
    public boolean checkBricked;
    public String defense;
    public EditText comment;
    public String rankingPoints;
    //private static final String FILE_NAME = "data.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen5);

        Spinner defenseSpinner = findViewById(R.id.defense_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.defenseOption, android.R.layout.simple_dropdown_item_1line);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        defenseSpinner.setAdapter((adapter));

        next4 = (Button) findViewById(R.id.submit);
        next4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen6();
            }
        });

        back3 = (Button) findViewById(R.id.back3);
        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScreen4();
            }
        });

        totalPoints = (EditText) findViewById(R.id.totalPoints);

        rankingPoint = (ElegantNumberButton) findViewById(R.id.RankingPoints);
        rankingPoints = rankingPoint.getNumber();

        checkBricked = ((CheckBox) findViewById(R.id.checkBricked)).isChecked();

        defense = defenseSpinner.getSelectedItem().toString();

        comment = (EditText) findViewById(R.id.Comment);


    }

    public void openScreen6() {
        Intent intent7 = new Intent(this, Screen6.class);
        startActivity(intent7);
    }

    public void openScreen4() {
        Intent intent8 = new Intent(this, Screen4.class);
        startActivity(intent8);
    }


}

