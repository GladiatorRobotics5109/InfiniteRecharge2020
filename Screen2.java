package com.joshualee0902.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Screen2 extends AppCompatActivity {

    EditText teamNum;
    EditText matchNum;
    Button next1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen2);

        teamNum = (EditText) findViewById(R.id.teamNum);
        matchNum = (EditText) findViewById(R.id.matchNum);

        next1 = (Button) findViewById(R.id.next1);
        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String teamNumber = teamNum.getText().toString();
                String matchNumber = matchNum.getText().toString();

                Intent pass = new Intent(Screen2.this, Screen6.class);
                pass.putExtra("TEAM_NUM", teamNumber);
                pass.putExtra("MATCH_NUM", matchNumber);
                startActivity(pass);

                /*
                Intent pass1 = new Intent(Screen2.this, Screen6.class);
                String teamNumber = teamNum.getText().toString();
                pass1.putExtra("team_number", teamNumber);
                startActivity(pass1);

                Intent pass2 = new Intent(Screen2.this, Screen6.class);
                String matchNumber = matchNum.getText().toString();
                pass1.putExtra("match_number", matchNumber);
                startActivity(pass2);
                */

                openScreen3();
            }
        });

    }


    public void openScreen3() {
        Intent intent2 = new Intent(this, Screen3.class);
        startActivity(intent2);
    }

}
