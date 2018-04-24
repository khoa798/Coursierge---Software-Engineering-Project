package com.jhacks.vrclassroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Professor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor);

        Button startSes = findViewById(R.id.startSes);
        startSes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStartSes(view);
            }
        });
    }

    public void onClickStartSes(View v) {
        EditText sesId = findViewById(R.id.profSesId);
        String sesIdString = sesId.getText().toString();

        if (sesIdString.matches("")) {
            Toast.makeText(this, "Please insert a Class Name", Toast.LENGTH_LONG).show();
        }
        else {
            Intent startSession = new Intent(Professor.this, ProfSession.class);
            startSession.putExtra("profSesId", sesIdString);
            startActivity(startSession);
        }
    }
}
