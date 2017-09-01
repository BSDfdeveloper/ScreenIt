package com.cyclers.soil;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Calendar;

public class Step4 extends StepsBase {


    static String date = "";
    static EditText dateEdit;
    ImageView signatureButton;

    public static void onSetDate(int y, int m, int d) {
        date = Integer.toString(d) + "/" + Integer.toString(m) + "/" + Integer.toString(y) + " ";
        Log.v("YoYoDate", date);
        dateEdit.setText(date);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step4);
        signatureButton = (ImageView) findViewById(R.id.signature_button);

        dateEdit = (EditText) findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        int m = c.get(Calendar.MONTH) + 1;
        String currrentDate = c.get(Calendar.DAY_OF_MONTH) + "/" + m + "/" + c.get(Calendar.YEAR);
        dateEdit.setText(currrentDate);
    }

    public void onNextClick(View view) {
        if (((EditText) findViewById(R.id.name)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Name", Toast.LENGTH_LONG).show();
        else if (((EditText) findViewById(R.id.position)).getText().toString().isEmpty())
            Toast.makeText(this, "You must need to enter Position", Toast.LENGTH_LONG).show();
        else if (MyApplication.getInstance().signature == null)
            Toast.makeText(this, "You must need to add signatures", Toast.LENGTH_LONG).show();
        else {
            MyApplication.getInstance().name = ((EditText) findViewById(R.id.name)).getText().toString();
            MyApplication.getInstance().position = ((EditText) findViewById(R.id.position)).getText().toString();
            MyApplication.getInstance().date = ((EditText) findViewById(R.id.date)).getText().toString();

            Intent signatureActivity = new Intent(this, PDFActivity.class);
            startActivity(signatureActivity);
        }
    }

    public void onPrevClick(View view) {
        onBackPressed();
    }

    public void onDateClick(View view) {
        showDatePickerDialog();
    }

    public void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(this.getFragmentManager(), "datePicker");
    }

    public void onSignatureClick(View view) {
        startActivityForResult(new Intent(this, SignaturePad.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (MyApplication.getInstance().signature != null) {
                signatureButton.setImageBitmap(MyApplication.getInstance().signature);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
