package com.jhacks.vrclassroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button prof = findViewById(R.id.professor);
        Button student = findViewById(R.id.student);

        // This button leads to the professor menu
        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToProf = new Intent(MainActivity.this, Professor.class);
                startActivity(goToProf);
            }
        });

        // This button leads to the student menu
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToStudent = new Intent(MainActivity.this, Student.class);
                startActivity(goToStudent);
            }
        });
    }

}
