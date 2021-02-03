package com.ict4everyone.tangoflash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void startQuiz(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void displayList(View view) {
        startActivity(new Intent(getApplicationContext(), ListActivity.class));
    }
}