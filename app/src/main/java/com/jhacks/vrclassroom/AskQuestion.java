package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.jhacks.vrclassroom.ProfSession.mSession;

public class AskQuestion extends AppCompatActivity {

    private EditText question;
    private String q;
    private EditText choiceA;
    private String cA;
    private EditText choiceB;
    private String cB;
    private EditText choiceC;
    private String cC;
    private EditText choiceD;
    private String cD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
    }

    public void sendQuestion(View view){
        if(textsValid()) {
            String sendThis = q + "," + cA + "," + cB + "," + cC + "," + cD;
            mSession.sendSignal("mcQuest", sendThis);
            Toast.makeText(this, sendThis, Toast.LENGTH_SHORT).show();
            finish();
        }else {
            Toast.makeText(this, "Please fill out entirely", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean textsValid(){
        question = findViewById(R.id.questionText);
        q = question.getText().toString().trim();
        if(q.length()<=0){return false;}
        choiceA = findViewById(R.id.editTextA);
        cA = choiceA.getText().toString().trim();
        if(cA.length()<=0){return false;}
        choiceB = findViewById(R.id.editTextB);
        cB = choiceB.getText().toString().trim();
        if(cB.length()<=0){return false;}
        choiceC = findViewById(R.id.editTextC);
        cC = choiceC.getText().toString().trim();
        if(cC.length()<=0){return false;}
        choiceD = findViewById(R.id.editTextD);
        cD = choiceD.getText().toString().trim();
        if(cA.length()<=0){return false;}else{ return true;}

    }

}
