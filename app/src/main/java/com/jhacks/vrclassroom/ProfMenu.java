package com.jhacks.vrclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ProfMenu extends AppCompatActivity {

    // Initializes the buttons
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_menu);
    }

    // This method connects a button leading to the Ask Question activity
    public void onClickAskQuestion(View view){
        Intent intent = new Intent(this, AskQuestion.class);
        startActivity(intent);
    }

    // This method connects a button leading to the View Responses activity
    public void onClickViewResponses(View view){
        Intent intent = new Intent(this, ViewResponses.class);
        startActivity(intent);
    }

    // This method connects a button leading to the View Questions Activity
    public void onClickViewQuestions(View view){
        Intent intent = new Intent(this, ViewQuestions.class);
        intent.putExtra("Student", "false");
        startActivity(intent);
    }



}
