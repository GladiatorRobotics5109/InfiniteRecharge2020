package com.joshualee0902.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Screen6 extends AppCompatActivity {


    Button submit;
    TextView finalProduct;
    //String teamNumberDisplay;
    //String matchNumberDisplay
    String total;
    TextView textView;
    TextView textView2;
    String teamNumber;
    String matchNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen6);
        /*
        teamNumberDisplay = getIntent().getStringExtra("team_number");
        matchNumberDisplay = getIntent().getStringExtra("match_number");
        */

        //total = matchNumberDisplay + " | " + teamNumberDisplay;

        teamNumber = getIntent().getExtras().getString("TEAM_NUM");
        matchNumber = getIntent().getExtras().getString("MATCH_NUM");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText(matchNumber + " | " + teamNumber);


        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }



        fileName = (EditText) findViewById(R.id.file_name);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = fileName.getText().toString();
                String total = Screen2.matchNumber + " | " + Screen2.teamNumber;

                if(!filename.equals("")) {
                    saveTextAsFile(filename, total);
                }
            }

            /*
            private void printString(String total) {
                final String fileName = "data.txt";
                try {
                    final PrintWriter outputStream = new PrintWriter(fileName);
                    outputStream.print(total);
                    outputStream.close();
                } catch (final FileNotFoundException e) {
                    e.printStackTrace();
                }
            }


        });

         */
    }

    public void submitData() {

        //finalProduct = (TextView) findViewById(R.id.final_product);
        //finalProduct.setText(total);
        //textView = (TextView) findViewById(R.id.textView);
        //textView2 = (TextView) findViewById(R.id.textView2);
        //textView.setText(teamNumberDisplay);
        //textView2.setText(matchNumberDisplay);

        //textView2.setText(matchNumber + " | " + teamNumber);

    }

    /*

    private void saveTextAsFile(String filename, String total) {
        filename = filename + ".txt";

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(total.getBytes());
            fos.close();
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1000:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission not granted.", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


     */

}
