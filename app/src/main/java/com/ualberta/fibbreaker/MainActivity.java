


/*
 * Copyright 2018 Yongjia Huang University Of Alberta. All Rights reserved
 */

package com.ualberta.fibbreaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private EditText number1;
    private EditText number2;
    private Button go;
    private String input1,input2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        number1 = (EditText) findViewById(R.id.number1);
        number2 = (EditText) findViewById(R.id.number2);
        configureGoButton();

 }
    private void configureGoButton(){
        go = (Button) findViewById(R.id.go);
        go.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                // Here I used android:inputType="number" to force inputs to be numeric
                try{
                    input1 = number1.getText().toString();
                    input2 = number2.getText().toString();
                    // send number1 and number2 to goog
                    Intent i = new Intent(MainActivity.this, FibActivity.class);

                    i.putExtra("INPUT1", input1);
                    i.putExtra("INPUT2", input2);
                    startActivity(i);
                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "Please enter two numbers!", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }

}
