package com.jhacks.vrclassroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

        // Figure out what question we are working with
        final String question = getIntent().getStringExtra("Text");
        // This is what holds the scores of the current question


        // Where we will place all of the possible multiple choice answers
        final ArrayList<String> choices = new ArrayList<>();
        // This is where we grab all the choices to display on the x-axis
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

        // Now we need to grab all the "scores" for all the choices of the question i.e. our data
        final DatabaseReference mScoresDatabase = FirebaseDatabase.getInstance().getReference().child("question-scores").child(question);
        mScoresDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    QuestionScores currQuestion;
                    currQuestion = dataSnapshot.getValue(QuestionScores.class);
                    // Get reference to our Bar Chart
                    BarChart myChart = (BarChart) findViewById(R.id.chart);
                    // ArrayList containing answer scores
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    // Add all scores to list for display
                    entries.add(new BarEntry(0,currQuestion.getScoreA()));
                    entries.add(new BarEntry(1,currQuestion.getScoreB()));
                    entries.add(new BarEntry(2,currQuestion.getScoreC()));
                    entries.add(new BarEntry(3,currQuestion.getScoreD()));
                    // This object holds all data that belongs together and allows
                    // individual styling of this data
                    BarDataSet myDataset = new BarDataSet(entries, "Answers");
                    //myDataset.setColors(ColorTemplate.PASTEL_COLORS);
                    // Colorize each set of data
                    myDataset.setColors(ColorTemplate.COLORFUL_COLORS);
                    // Labels for the data
                    // We are not using choices as it contains one child that we do not need.
                    final ArrayList<String> label = new ArrayList<>();
                    label.add(choices.get(0));
                    label.add(choices.get(1));
                    label.add(choices.get(2));
                    label.add(choices.get(3));
                    // Get X-axis for labels
                    XAxis myXAxis = myChart.getXAxis();
                    myXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    myXAxis.setDrawGridLines(false);
                    // Limit number of choices for label
                    myXAxis.setLabelCount(4);
                    myXAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return label.get((int) value);
                        }
                    });
                    // Initialize the BarData class with argument dataset
                    BarData myData = new BarData(myDataset);
                    myData.setBarWidth(0.9f);
                    myChart.setData(myData);
                    myChart.setFitBars(true);
                    myChart.setBackgroundColor(getColor(R.color.gray));
                    myChart.getDescription().setEnabled(false);
                    myChart.getLegend().setEnabled(false);

                    myChart.invalidate(); // refresh
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(textDebug, "The read failed.");
            }
        });










    }




}
