package com.jhacks.vrclassroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Student extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        Button joinSes = findViewById(R.id.joinSes);
        joinSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickJoinSes(view);
            }
        });
    }

    public void onClickJoinSes(View V) {
        // Get session id text from editText
        EditText sesId = findViewById(R.id.studSesId);
        String sesIdString = sesId.getText().toString();

        if (sesIdString.matches("")) {
            //
            Toast.makeText(this, "Please insert a Class Name", Toast.LENGTH_LONG).show();
        }
        else {
            // Start new activity and send session ID
            Intent goToSession = new Intent(Student.this, StudSession.class);
            goToSession.putExtra("studSesId", sesIdString);
            startActivity(goToSession);
        }
    }
}
