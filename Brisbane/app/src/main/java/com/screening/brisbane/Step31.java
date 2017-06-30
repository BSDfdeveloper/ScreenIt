package com.screening.brisbane;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

public class Step31 extends StepsBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step31);
    }

    /*
    public void onCheckBoxClick(View view)
    {
        if(view.getId()==R.id.checkbox4) {
            if (((CheckBox) findViewById(R.id.checkbox4)).isChecked()) {
                ((CheckBox) findViewById(R.id.checkbox6)).setChecked(false);
            }
        }
        else
        {
            if (((CheckBox) findViewById(R.id.checkbox6)).isChecked()) {
                ((CheckBox) findViewById(R.id.checkbox4)).setChecked(false);
            }
        }
    }
    */

    public void onNextClick(View view) {
        if (!(((CheckBox) findViewById(R.id.checkbox1)).isChecked()) || !(((CheckBox) findViewById(R.id.checkbox5)).isChecked()))
            Toast.makeText(this, "These items are all compulsory", Toast.LENGTH_LONG).show();
        else {

            MyApplication.getInstance().checkBoxes[1] = ((CheckBox) findViewById(R.id.checkbox1)).isChecked();
            //MyApplication.getInstance().checkBoxes[4] = ((CheckBox) findViewById(R.id.checkbox4)).isChecked();
            MyApplication.getInstance().checkBoxes[5] = ((CheckBox) findViewById(R.id.checkbox5)).isChecked();
            //MyApplication.getInstance().checkBoxes[6] = ((CheckBox) findViewById(R.id.checkbox6)).isChecked();

            Intent signatureActivity = new Intent(this, Step4.class);
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
