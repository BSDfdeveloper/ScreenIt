package com.screening.brisbane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Step1 extends StepsBase {

    public final static boolean isValidEmail(CharSequence target) {

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step1);
    }

    public void onNextClick(View view) {
        if (((EditText) findViewById(R.id.client_name)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Client Name", Toast.LENGTH_LONG).show();
        else if (((EditText) findViewById(R.id.job_name)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Job Name", Toast.LENGTH_LONG).show();
        else if (((EditText) findViewById(R.id.qunatity)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Quantity", Toast.LENGTH_LONG).show();
        else if (((EditText) findViewById(R.id.ameliorants)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Ameliorants", Toast.LENGTH_LONG).show();
        else if (!(((EditText) findViewById(R.id.client_email)).getText().toString().isEmpty()) && !isValidEmail(((EditText) findViewById(R.id.client_email)).getText().toString()))
            Toast.makeText(this, "You must need to enter a valid email address", Toast.LENGTH_SHORT).show();
        else {
            MyApplication.getInstance().clientName = ((EditText) findViewById(R.id.client_name)).getText().toString();
            MyApplication.getInstance().jobName = ((EditText) findViewById(R.id.job_name)).getText().toString();
            MyApplication.getInstance().quantity = ((EditText) findViewById(R.id.qunatity)).getText().toString();
            MyApplication.getInstance().ameliorants = ((EditText) findViewById(R.id.ameliorants)).getText().toString();
            if (!(((EditText) findViewById(R.id.client_email)).getText().toString().isEmpty()))
                MyApplication.getInstance().clientEmail = ((EditText) findViewById(R.id.client_email)).getText().toString();

            //MyApplication.getInstance().checkBoxes[0]=((CheckBox)findViewById(R.id.checkbox1)).isChecked();
            //MyApplication.getInstance().checkBoxes[1]=((CheckBox)findViewById(R.id.checkbox2)).isChecked();

            Intent signatureActivity = new Intent(this, Step2.class);
            startActivity(signatureActivity);
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
