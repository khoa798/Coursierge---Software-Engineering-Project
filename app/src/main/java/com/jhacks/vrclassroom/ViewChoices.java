package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Uncomment Debug statements to DEBUG

public class ViewChoices extends AppCompatActivity {
    String textDebug = "[DEBUG]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Log.d(textDebug,"onCreate");
        super.onCreate(savedInstanceState);

        //System.out.println("WITHIN VIEWResponse");
        final String question = getIntent().getStringExtra("Text");
        int position = getIntent().getIntExtra("Position", 0);
        //System.out.println("QUESTION: " + question + ", POSITION: " + position);
        final ArrayList<String> choices = new ArrayList<>();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("questions").child(question);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    //System.out.println("CURRENT VALUE: " + ds.getValue());
                    choices.add(ds.getValue().toString());
                }
                
                //System.out.println("CHOICES: " + choices);
                Button a = findViewById(R.id.choice1);
                a.setText(choices.get(0));
                a.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
                        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // If there is already a score board for this question, access it
                                // And update the value of selected choice
                                if(dataSnapshot.exists())
                                {
                                    QuestionScores currQuestion;
                                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                                    int scoreA = currQuestion.getScoreA();
                                    currQuestion.setScoreA(scoreA+1);
                                    mScoresDatabase.setValue(currQuestion);
                                    String text = "Answer Selected!";
                                    Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                    confirmChoice.show();
                                    finish();

                                }
                                else
                                {
                                    // First time question is answered with this choice. So,
                                    // Set score of new object to nr the first choice to be selected
                                    // choice and push to database
                                    QuestionScores currQuestion = new QuestionScores();
                                    currQuestion.setScoreA(1);
                                    if (currQuestion != null) {
                                        mScoresDatabase.setValue(currQuestion);
                                        String text = "Answer Selected!";
                                        Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                        confirmChoice.show();
                                        finish();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //System.out.println("The read failed");
                                Log.d(textDebug, "The read failed.");

                            }

                        });


                    }
                });
                Button b = findViewById(R.id.choice2);
                b.setText(choices.get(1));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
                        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // If there is already a score board for this question, access it
                                // And update the value of selected choice
                                if(dataSnapshot.exists())
                                {
                                    QuestionScores currQuestion;
                                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                                    int scoreB = currQuestion.getScoreB();
                                    currQuestion.setScoreB(scoreB+1);
                                    mScoresDatabase.setValue(currQuestion);
                                    String text = "Answer Selected!";
                                    Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                    confirmChoice.show();
                                    finish();

                                }
                                else
                                {
                                    // First time question is answered with this choice. So,
                                    // Set score of new object to nr the first choice to be selected
                                    // choice and push to database
                                    QuestionScores currQuestion = new QuestionScores();
                                    currQuestion.setScoreB(1);
                                    if (currQuestion != null) {
                                        mScoresDatabase.setValue(currQuestion);
                                        String text = "Answer Selected!";
                                        Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                        confirmChoice.show();
                                        finish();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //System.out.println("The read failed");
                                Log.d(textDebug, "The read failed.");

                            }

                        });



                    }
                });

                Button c = findViewById(R.id.choice3);
                c.setText(choices.get(2));
                c.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
                        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // If there is already a score board for this question, access it
                                // And update the value of selected choice
                                if(dataSnapshot.exists())
                                {
                                    QuestionScores currQuestion;
                                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                                    int scoreC = currQuestion.getScoreC();
                                    currQuestion.setScoreC(scoreC+1);
                                    mScoresDatabase.setValue(currQuestion);
                                    String text = "Answer Selected!";
                                    Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                    confirmChoice.show();
                                    finish();

                                }
                                else
                                {
                                    // First time question is answered with this choice. So,
                                    // Set score of new object to nr the first choice to be selected
                                    // choice and push to database
                                    QuestionScores currQuestion = new QuestionScores();
                                    currQuestion.setScoreC(1);
                                    if (currQuestion != null) {
                                        mScoresDatabase.setValue(currQuestion);
                                        String text = "Answer Selected!";
                                        Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                        confirmChoice.show();
                                        finish();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //System.out.println("The read failed");
                                Log.d(textDebug, "The read failed.");

                            }

                        });



                    }
                });

                Button d = findViewById(R.id.choice4);
                d.setText(choices.get(3));
                d.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
                        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                // If there is already a score board for this question, access it
                                // And update the value of selected choice
                                if(dataSnapshot.exists())
                                {
                                    QuestionScores currQuestion;
                                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                                    int scoreD = currQuestion.getScoreD();
                                    currQuestion.setScoreD(scoreD+1);
                                    mScoresDatabase.setValue(currQuestion);
                                    String text = "Answer Selected!";
                                    Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                    confirmChoice.show();
                                    finish();

                                }
                                else
                                {
                                    // First time question is answered with this choice. So,
                                    // Set score of new object to nr the first choice to be selected
                                    // choice and push to database
                                    QuestionScores currQuestion = new QuestionScores();
                                    currQuestion.setScoreD(1);
                                    if (currQuestion != null) {
                                        mScoresDatabase.setValue(currQuestion);
                                        String text = "Answer Selected!";
                                        Toast confirmChoice = Toast.makeText(ViewChoices.this, text, Toast.LENGTH_SHORT);
                                        confirmChoice.show();
                                        finish();
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //System.out.println("The read failed");
                                Log.d(textDebug, "The read failed.");

                            }

                        });



                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(textDebug, "The read failed.");
            }
        });

        setContentView(R.layout.answers);


    }
}
