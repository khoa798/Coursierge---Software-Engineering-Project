package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AskQuestion extends AppCompatActivity {
    String question,optionA,optionB,optionC,optionD;
    EditText choiceA, choiceB, choiceC, choiceD;
    Question questionObject = new Question();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
    }


    // method to send questions to firebase
    public void sendQuestion(View view){
        EditText questionEditText;
        questionObject = grabValidChoices();
        questionEditText = findViewById(R.id.questionText);
        question = questionEditText.getText().toString().trim();
        Toast confirmQuestion; // Notifier for question input
        final String logTag = "Professor database: ";
        String allChoicesSelected = questionObject.getFlag();

        // Make sure a question field has input (Question is being asked)
        if (question.length() == 0 || allChoicesSelected.equals("false"))
        {
            String text = "No Question Asked!";
            confirmQuestion = Toast.makeText(AskQuestion.this, text, Toast.LENGTH_SHORT);
            confirmQuestion.show();
        }
        else
        {
            String myQuestionTag = question;
            //questionObject.setQuestion(question);
            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();
            //DatabaseReference questionsRef = database.child("questions");
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    //String value = dataSnapshot.getValue(String.class);
                    //Log.d(logTag, "Value is: " + value);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(logTag, "Failed to read value.", error.toException());
                }
            });

            // If we do have choices to choose from then save the data to the database.
            if (questionObject != null) {
                myRef.child("questions").child(myQuestionTag).setValue(questionObject);
                String text = "Question sent!";
                confirmQuestion = Toast.makeText(AskQuestion.this, text, Toast.LENGTH_SHORT);
                confirmQuestion.show();
                finish();
            }
            else
            {
                String confirmChoices = "No choices!";
                confirmQuestion = Toast.makeText(AskQuestion.this, confirmChoices, Toast.LENGTH_SHORT);
                confirmQuestion.show();
            }



        }



    }

    private Question grabValidChoices(){


        Toast notifyValidInput; // Notifier for question input
        String text = "Please have an input for all choices.";
        // Remember to delimit by comma for questions

        //String choicesToSend = "";
        Question myQuestion = new Question();

        choiceA = findViewById(R.id.editTextA);
        choiceB = findViewById(R.id.editTextB);
        choiceC = findViewById(R.id.editTextC);
        choiceD = findViewById(R.id.editTextD);

        // Parse the choices
        optionA = choiceA.getText().toString().trim();
        optionB = choiceB.getText().toString().trim();
        optionC = choiceC.getText().toString().trim();
        optionD = choiceD.getText().toString().trim();

        // Check if there was an option typed in for choices.
        // If so, concatenate to be used later.
        if (optionA.length() > 0 && optionB.length() > 0 && optionC.length() > 0 && optionD.length() > 0)
        {

            myQuestion.setChoiceA(optionA);
            myQuestion.setChoiceB(optionB);
            myQuestion.setChoiceC(optionC);
            myQuestion.setChoiceD(optionD);
            myQuestion.setFlag("true");
        }

        else
        {
            notifyValidInput = Toast.makeText(AskQuestion.this, text, Toast.LENGTH_SHORT);
            myQuestion.setFlag("false");
            notifyValidInput.show();
        }

        return myQuestion;
    }

}
