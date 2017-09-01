package com.cyclers.soil;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import static com.android.volley.VolleyLog.TAG;

/**
 * Original Code:Created by Farhan on 11/26/2016.
 */

public class MyApplication extends Application {

    private static MyApplication instance;
    public Bitmap signature = null;
    public String clientName = null;
    public String clientEmail = "";
    public String jobName = null;
    public String quantity = null;
    public String ameliorants = null;
    public String registration = null;
    public String measurements = null;
    public String volume = null;
    public String name = null;
    public String date = null;
    public String position = null;
    Boolean[] checkBoxes = null;
    private RequestQueue mRequestQueue;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {

        //file uri exposure issue
        if (android.os.Build.VERSION.SDK_INT >= 24) {
            try {
                java.lang.reflect.Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/calibril.ttf");
        instance = this;
        checkBoxes = new Boolean[7];
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
