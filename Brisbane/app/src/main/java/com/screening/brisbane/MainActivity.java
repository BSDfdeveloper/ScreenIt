package com.screening.brisbane;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onNextClick(View view) {
        Intent signatureActivity = new Intent(this, Step1.class);
        startActivity(signatureActivity);
        /*
        startActivity(Intent.createChooser(signatureActivity, "Your title"));
         */
    }
}
