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
    private int input1;
    private int input2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                    input1 = Integer.parseInt(number1.getText().toString());
                    input2 = Integer.parseInt(number2.getText().toString());
                    // send number1 and number2 to goog
                    Intent i = new Intent(MainActivity.this, FibActivity.class);

                    i.putExtra("INPUT1", input1);

                    i.putExtra("INPUT2", input2);
                    startActivity(i);
                } catch(Exception e) {
                    Toast.makeText(MainActivity.this, "Please enter two numbers!", Toast.LENGTH_SHORT).show();
                }


//                String query = "{\n" +
//                        "	\"query\": {\n" +
//                        "		\"term\": {\"_id\":\"" + name + "\"}\n" +
//                        "	}\n" +
//                        "}";
//                // query server in type "admin"
//                ElasticsearchController.GetAdminsTask getAdminsTask = new ElasticsearchController.GetAdminsTask();
//                getAdminsTask.execute(query);
//                // query server in type "participant"
//                ElasticsearchController.GetParticipantsTask getParticipantsTask = new ElasticsearchController.GetParticipantsTask();
//                getParticipantsTask.execute(query);
//
//                try {
//                    if (getAdminsTask.get().isEmpty() == false) { // check if this is an admin user
//                        //TODO maybe passing currentUser rather than name
//                        Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
//                        adminIntent.putExtra("ADMINID", name);
//                        startActivity(adminIntent);
//                    } else if (getParticipantsTask.get().isEmpty() == false) { // if not an admin, check if this is a participant user
//                        currentUser = getParticipantsTask.get().get(0); // currentUser size is 0 if there is no ID matched
//
//                        Intent i = new Intent(MainActivity.this, MenuPage.class);
//                        i.putExtra("LOGINUSER", currentUser);
//                        startActivity(i);
//                    } else { // if not admin nor participant, show Toast
//                        Toast.makeText(MainActivity.this, "Invalid User ID !", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (Exception e) {
//                    Log.i("Error", "Failed to get the participant from the asyc object");
//                }

            }

        });
    }
}
