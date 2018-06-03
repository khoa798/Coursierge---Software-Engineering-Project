package com.jhacks.vrclassroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

// Base code for RecyclerView taken from https://www.androidhive.info/2016/01/android-working-with-recycler-view/

public class ViewQuestions extends AppCompatActivity {

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

        String parentActivityRef = getIntent().getStringExtra("Student");
        if (parentActivityRef.equals("true")) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("questions");
        } else {
            mDatabase = FirebaseDatabase.getInstance().getReference().child("s_questions");
        }

        //mDatabase = FirebaseDatabase.getInstance().getReference().child("s_questions");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    System.out.println("CURRENT KEY: " + ds.getKey());
                    arrli.add(ds.getKey());
                }
                System.out.println("ARRLI: " + arrli);

                RecycleViewClickListener listener = new RecycleViewClickListener() {
                    @Override
                    public void onClick(View view, int position, String text) {
                        System.out.println("Position: " + position + " " + text);
                        ArrayList<String> choices = new ArrayList<>();
                        mDatabase.addValueEventListener(new ValueEventListener() {





                        });
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
                System.out.println("The read failed.");
            }
        });


    }

}
