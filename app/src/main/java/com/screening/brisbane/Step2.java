package com.screening.brisbane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Step2 extends StepsBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
    }

    public void onCheckBoxClick(View view) {
        if (view.getId() == R.id.checkbox2) {
            if (((RadioButton) findViewById(R.id.checkbox2)).isChecked()) {
                ((RadioButton) findViewById(R.id.checkbox3)).setChecked(false);
            }
        } else {
            if (((RadioButton) findViewById(R.id.checkbox3)).isChecked()) {
                ((RadioButton) findViewById(R.id.checkbox2)).setChecked(false);
            }
        }
    }

    public void onNextClick(View view) {
        if (((RadioButton) findViewById(R.id.checkbox2)).isChecked() || ((RadioButton) findViewById(R.id.checkbox3)).isChecked()) {
            if (((RadioButton) findViewById(R.id.checkbox3)).isChecked() && ((EditText) findViewById(R.id.registration_number)).getText().toString().isEmpty())
                Toast.makeText(this, "You must need to enter Registration #", Toast.LENGTH_LONG).show();
            else if (((RadioButton) findViewById(R.id.checkbox3)).isChecked() && ((EditText) findViewById(R.id.measurements)).getText().toString().isEmpty())
                Toast.makeText(this, "You must need to enter Measurements", Toast.LENGTH_LONG).show();
            else if (((RadioButton) findViewById(R.id.checkbox3)).isChecked() && ((EditText) findViewById(R.id.total_volume)).getText().toString().isEmpty())
                Toast.makeText(this, "You must need to enter Total Volume", Toast.LENGTH_LONG).show();
            else {
                MyApplication.getInstance().registration = ((EditText) findViewById(R.id.registration_number)).getText().toString();
                MyApplication.getInstance().measurements = ((EditText) findViewById(R.id.measurements)).getText().toString();
                MyApplication.getInstance().volume = ((EditText) findViewById(R.id.total_volume)).getText().toString();
                MyApplication.getInstance().checkBoxes[2] = ((RadioButton) findViewById(R.id.checkbox2)).isChecked();
                MyApplication.getInstance().checkBoxes[3] = ((RadioButton) findViewById(R.id.checkbox3)).isChecked();

                Intent signatureActivity = new Intent(this, Step3.class);
                startActivity(signatureActivity);
            }
        } else {
            Toast.makeText(this, "Please select one of these options before proceeding", Toast.LENGTH_LONG).show();
        }
    }

    public void onPrevClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
