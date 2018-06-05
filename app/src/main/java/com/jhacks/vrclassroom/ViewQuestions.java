package com.jhacks.vrclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

// Base code for RecyclerView taken from https://www.androidhive.info/2016/01/android-working-with-recycler-view/

public class ViewQuestions extends AppCompatActivity {
    String textDebug = "[DEBUG]";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions2);

        DatabaseReference mDatabase;
        final ArrayList<String> arrli = new ArrayList<String>();

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        /*mRecyclerView = findViewById(R.id.questionView);
        mAdapter = new RVAdapter(arrli);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);*/
        //get questions from firebase here

        String isStudent = getIntent().getStringExtra("Student");
        if (isStudent.equals("true")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("questions");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        arrli.add(ds.getKey());
                    }
                    //Log.d("ARRLI: " + arrli);
    

                    RecycleViewClickListener listener = new RecycleViewClickListener() {
                        @Override
                        public void onClick(View view, int position, String text) {
                            //System.out.println("Position: " + position + " " + text);
                            Intent intent = new Intent(ViewQuestions.this, ViewChoices.class);
                            intent.putExtra("Text", text);
                            intent.putExtra("Position", position);
                            startActivity(intent);

                        }
                    };

                    mRecyclerView = findViewById(R.id.questionView);
                    mAdapter = new RVAdapter(arrli, listener);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    //mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(textDebug, "The read failed.");
                }
            });
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("s_questions");
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //System.out.println("CURRENT KEY: " + ds.getKey());
                        arrli.add(ds.getKey());
                    }


                    RecycleViewClickListener listener = new RecycleViewClickListener() {
                        @Override
                        public void onClick(View view, int position, String text) {
                            Log.d(textDebug, "Position: " + position + " " + text);

                        }
                    };

                    mRecyclerView = findViewById(R.id.questionView);
                    mAdapter = new RVAdapter(arrli, listener);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);
                    //mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.d(textDebug, "The read failed.");
                }
            });
        }



    }

}
