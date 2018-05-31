package com.jhacks.vrclassroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public class StudMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_menu);
    }


    public void onClickViewQuestions(View view)
    {
        Intent intent = new Intent(this, ViewQuestions.class);
        startActivity(intent);
    }
}


/*










<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:layout_editor_absoluteX="0dp"
    tools:layout_editor_absoluteY="0dp"
    tools:context="com.jhacks.vrclassroom.ProfMenu"
    android:background="@drawable/minimal">

    <ImageView
        android:id="@+id/logo"
        android:layout_width="150dp"
        android:layout_height="145dp"
        android:contentDescription="@null"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="parent"
        app:srcCompat="@drawable/googledaydream" />

    <Button
        android:id="@+id/viewResponsesButton"
        android:layout_width="wrap_content"
        android:layout_height="61dp"
        android:layout_marginTop="52dp"
        android:background="@drawable/viewrespbtn"
        android:onClick="onClickViewResponses"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</android.support.constraint.ConstraintLayout>













 */