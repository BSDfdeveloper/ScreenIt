package com.screening.brisbane;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class PDFActivity extends StepsBase {

    View[] views;
    ImageView signatureBox;
    ProgressDialog progressDialog;
    String base64PDF;
    String currentFilePath;
    String displayFileName;
    String fileName;
    File fileToSend;
    File fileToSend2;
    private VolleyApiCallsManager apiCall;

    private static String getBase64ImageString(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private static String getBase64PdfString(PdfDocument document) {

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.writeTo(byteArrayOutputStream);
            //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(byteArray, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e("yoyo base64", e.toString());
        }

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);

        //apiCall = new VolleyApiCallsManager(this);

        views = new View[25];
        addViews();

        setupPdfData();

    }

    private void setupPdfData() {
        try {

            ((TextView) views[2]).setText(MyApplication.getInstance().clientName);
            ((TextView) views[4]).setText(MyApplication.getInstance().jobName);
            ((TextView) views[6]).setText(MyApplication.getInstance().quantity);
            ((TextView) views[8]).setText(MyApplication.getInstance().ameliorants);

            ((TextView) views[12]).setText(MyApplication.getInstance().date);
            ((TextView) views[14]).setText(MyApplication.getInstance().name);
            ((TextView) views[16]).setText(MyApplication.getInstance().position);

            ((CheckBox) views[19]).setChecked(MyApplication.getInstance().checkBoxes[1]);
            ((CheckBox) views[20]).setChecked(MyApplication.getInstance().checkBoxes[2]);

            ((CheckBox) views[21]).setChecked(MyApplication.getInstance().checkBoxes[3]);
            if (MyApplication.getInstance().checkBoxes[3]) {
                SpannableString registration = new SpannableString(MyApplication.getInstance().registration);
                registration.setSpan(new UnderlineSpan(), 0, registration.length(), 0);

                SpannableString measurements = new SpannableString(MyApplication.getInstance().measurements);
                measurements.setSpan(new UnderlineSpan(), 0, measurements.length(), 0);

                SpannableString volume = new SpannableString(MyApplication.getInstance().volume);
                volume.setSpan(new UnderlineSpan(), 0, volume.length(), 0);

                SpannableString spannableText1 = new SpannableString("I have been given the opportunity to do test measurements using another truck reg # ");
                SpannableString spannableText2 = new SpannableString(". I agree that the measurements for the bin are: ");
                SpannableString spannableText3 = new SpannableString(" which equals a total volume of ");
                SpannableString spannableText4 = new SpannableString(" .");

                ((CheckBox) views[21]).setText(TextUtils.concat(spannableText1, registration, spannableText2, measurements, spannableText3, volume, spannableText4));
            }
            ((CheckBox) views[22]).setChecked(MyApplication.getInstance().checkBoxes[4]);
            ((CheckBox) views[23]).setChecked(MyApplication.getInstance().checkBoxes[5]);
            ((CheckBox) views[24]).setChecked(MyApplication.getInstance().checkBoxes[6]);


            Bitmap original = MyApplication.getInstance().signature;

            Bitmap decoded = getResizedBitmap(original, original.getWidth() / 10, original.getHeight() / 10);

            Log.i("yoyosizeofbitmap", "Bytes: " + sizeOfMyBitmap(MyApplication.getInstance().signature));
            Log.i("yoyosizeofbitmap", "DecodedBytes: " + sizeOfMyBitmap(decoded));

            signatureBox.setImageBitmap(decoded);

        } catch (Exception e) {
            Log.v("yoyo exception", e.toString());
            showError(e.toString());
        }
    }

    private void savePDF(PdfDocument doc, String imageName) {


        String f1 = imageName + ".pdf";
        String f2 = imageName + "2.pdf";

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/Brisbanescreening/");
        dir.mkdirs();
        fileToSend = new File(dir, f1);
        fileToSend2 = new File(dir, f2);


        try {

            OutputStream output = new FileOutputStream(fileToSend);
            doc.writeTo(output);

            currentFilePath = fileToSend.getAbsolutePath();
            fileName = imageName;
            //fileToSend=file;
            //fileToSend.setR(true,false);
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);

            output.flush();
            output.close();

            PdfReader reader = new PdfReader(currentFilePath);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileToSend2), PdfWriter.VERSION_1_5);

            reader.removeFields();
            reader.removeUnusedObjects();

            int total = reader.getNumberOfPages() + 1;
            for (int i = 1; i < total; i++) {
                reader.setPageContent(i + 1, reader.getPageContent(i + 1));
            }
            try {


                stamper.setFullCompression();
                stamper.close();

                /*
                PdfWriter writer = stamper.getWriter();
                writer.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
                writer.setCompressionLevel(PdfStream.BEST_COMPRESSION);
                writer.close();
                writer.setFullCompression();
                */

            } catch (DocumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("compression error", e.toString());
                showError(e.toString());

            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("File write error", e.toString());
            showError(e.toString());
        }

    }

    public void onPrevClick(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onNextClick(View view) {
        ((Button) findViewById(R.id.back_button)).setClickable(false);
        ((Button) findViewById(R.id.next_button)).setClickable(false);

        Log.e("yoyo", "PDF activity - onNextClick(View view)");
        final PdfDocument document = new PdfDocument();

        View content = findViewById(R.id.Edit_Views);

        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(content.getWidth(),
                content.getHeight(), pageNumber).create();


        PdfDocument.Page page = document.startPage(pageInfo);

        content.draw(page.getCanvas());

        document.finishPage(page);

        if (checkReadWriteExternalPermission()) {
            uploadData(document);
        } else {
            showError("This app requires storage permission to function smoothly. Please enable storage permission from settings and try again.");
        }


        /*

            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Data Submission");
            alertDialog.setMessage("You are about to send data to server, after this you will not be able to modify the submitted data.");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONFIRM",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            progressDialog = ProgressDialog.show(PDFActivity.this, "", "Uploading Data...");
                            uploadData(document);
                            //new EmailTask().execute("");

                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            ((Button)findViewById(R.id.back_button)).setClickable(true);
                            ((Button)findViewById(R.id.next_button)).setClickable(true);
                        }
                    });
            alertDialog.setCancelable(false);
            alertDialog.show();

            */


    }

    private void addViews() {
        signatureBox = (ImageView) findViewById(R.id.signature_box);

        views[1] = findViewById(R.id.text1);
        views[2] = findViewById(R.id.text2);
        views[3] = findViewById(R.id.text3);
        views[4] = findViewById(R.id.text4);
        views[5] = findViewById(R.id.text5);
        views[6] = findViewById(R.id.text6);
        views[7] = findViewById(R.id.text7);
        views[8] = findViewById(R.id.text8);
        views[9] = findViewById(R.id.text9);
        views[11] = findViewById(R.id.text11);
        views[12] = findViewById(R.id.text12);
        views[13] = findViewById(R.id.text13);
        views[14] = findViewById(R.id.text14);
        views[15] = findViewById(R.id.text15);
        views[16] = findViewById(R.id.text16);
        views[17] = findViewById(R.id.textTitle1);
        views[18] = findViewById(R.id.textTitle2);
        views[19] = findViewById(R.id.check1);
        views[20] = findViewById(R.id.check2);
        views[21] = findViewById(R.id.check3);
        views[22] = findViewById(R.id.check4);
        views[23] = findViewById(R.id.check5);
        views[24] = findViewById(R.id.check6);

    }

    private void openMailClient() {
        try {
            String message = "Dear " + MyApplication.getInstance().clientName + "\n\n";
            message = message + "Please find attached completed measurements sign-off form. If you have any queries please give us a call on 1300 748 388. \n\n";
            message = message + "Regards,\nBrisbane Screening";


            Intent myIntent = new Intent(Intent.ACTION_SEND);

            PackageManager pm = getPackageManager();
            Intent tempIntent = new Intent(Intent.ACTION_SEND);
            tempIntent.setType("*/*");
            List<ResolveInfo> resInfo = pm.queryIntentActivities(tempIntent, 0);
            for (int i = 0; i < resInfo.size(); i++) {
                ResolveInfo ri = resInfo.get(i);
                if (ri.activityInfo.packageName.contains("android.gm")) {
                    myIntent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                    myIntent.setAction(Intent.ACTION_SEND);
                    myIntent.setType("message/rfc822");
                    myIntent.putExtra(Intent.EXTRA_EMAIL, new String[]
                            {MyApplication.getInstance().clientEmail});
                    myIntent.putExtra(Intent.EXTRA_CC, new String[]
                            {"admin@brisbanescreening.com.au"});
                    myIntent.putExtra(Intent.EXTRA_SUBJECT,
                            "Brisbane screening completed measurements");
                    myIntent.putExtra(Intent.EXTRA_TEXT, message);

                    Log.i(getClass().getSimpleName(), "yoyoName1=" + Uri.parse(getFilesDir() + "/" + fileName));
                    Log.i(getClass().getSimpleName(), "yoyoName2=" + currentFilePath);
                    Log.i(getClass().getSimpleName(), "yoyoName3=" + Uri.parse(currentFilePath));
                    Log.e("PDFActivity", Uri.fromFile(fileToSend2).toString());
                    myIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileToSend2));
                }
            }
            Log.e("PDFActivity", "openMailClient before startActivity");
            startActivity(Intent.createChooser(myIntent, "Your title"));
            /*
            LINE ABOVE is a quick HACK and needs to be replaced entirely with correct method
            startActivity(myIntent);
             */
            Log.e("PDFActivity", "openMailClient after startActivity");
        } catch (Exception e) {
            Log.i("yoyoexception", e.toString());
            showError(e.toString());
        }
    }

    public void uploadData(PdfDocument document) {

        Log.v("YoYoSuccess", "PDFActivity - uploadData()");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh:mm:ss");
        String line = MyApplication.getInstance().clientName + "_" + MyApplication.getInstance().jobName;
        String lineWithoutSpaces = line.replaceAll("\\s+", "");
        String pdfName = lineWithoutSpaces + "_"
                + sdf.format(Calendar.getInstance().getTime()) + ".pdf";

        displayFileName = MyApplication.getInstance().clientName + "_" + MyApplication.getInstance().jobName + "_" + MyApplication.getInstance().date;

        savePDF(document, pdfName);
        Log.v("YoYoSuccess", "PDFActivity - success");
        openMailClient();
    }

    private long sizeOfMyBitmap(Bitmap data) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return data.getRowBytes() * data.getHeight();
        } else {
            return data.getByteCount();
        }
    }

    public Bitmap getResizedBitmap(Bitmap b, int newWidth, int newHeight) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, newWidth, newHeight), Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
    }

    public void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Storage Issues")
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private boolean checkReadWriteExternalPermission() {

        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = checkCallingOrSelfPermission(permission);
        boolean writePerm = (res == PackageManager.PERMISSION_GRANTED);

        String permission2 = "android.permission.READ_EXTERNAL_STORAGE";
        int res2 = checkCallingOrSelfPermission(permission2);
        boolean readPerm = (res2 == PackageManager.PERMISSION_GRANTED);

        return (writePerm && readPerm);
    }
}
