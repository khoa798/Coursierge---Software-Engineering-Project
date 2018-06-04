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

public class AskQuestionStudent extends AppCompatActivity {
    String question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question_student);
    }
    // method to send questions to firebase
    public void sendQuestionStudent(View view){
        EditText questionEditText;
        //allChoices = grabValidChoices();
        Question questionObject = new Question();
        questionEditText = findViewById(R.id.questionText);
        question = questionEditText.getText().toString().trim();
        Toast confirmQuestion; // Notifier for question input
        final String logTag = "Student database: ";

        // Make sure a question field has input (Question is being asked)
        if (question.length() == 0)
        {
            String text = "No Question Asked!";
            confirmQuestion = Toast.makeText(AskQuestionStudent.this, text, Toast.LENGTH_SHORT);
            confirmQuestion.show();
        }
        else
        {
            questionObject.setChoiceA("false");
            String myQuestionTag = question;
            questionObject.setQuestion(question);
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
                myRef.child("s_questions").child(myQuestionTag).setValue(questionObject);
                String text = "Question sent!";
                confirmQuestion = Toast.makeText(AskQuestionStudent.this, text, Toast.LENGTH_SHORT);
                confirmQuestion.show();
                finish();
            }
            else
            {
                String confirmChoices = "No choices!";
                confirmQuestion = Toast.makeText(AskQuestionStudent.this, confirmChoices, Toast.LENGTH_SHORT);
                confirmQuestion.show();
            }



        }



    }
}
