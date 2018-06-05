package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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
        String question = getIntent().getStringExtra("Text");
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
                TextView a = findViewById(R.id.choice1);
                a.setText(choices.get(0));
                a = findViewById(R.id.choice2);
                a.setText(choices.get(1));
                a = findViewById(R.id.choice3);
                a.setText(choices.get(2));
                a = findViewById(R.id.choice4);
                a.setText(choices.get(3));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(textDebug, "The read failed.");
            }
        });

        setContentView(R.layout.answers);


    }
}
