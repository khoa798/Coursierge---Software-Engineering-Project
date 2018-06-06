package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chart extends AppCompatActivity {
    String textDebug = "[DEBUG]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);


        final String question = getIntent().getStringExtra("Text");

        final ArrayList<String> choices = new ArrayList<>();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("questions").child(question);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    choices.add(ds.getValue().toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(textDebug, "The read failed.");
            }



        });


        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    QuestionScores currQuestion;
                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    //entries.add(currQuestion.)

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(textDebug, "The read failed.");
            }
        });










    }




}
