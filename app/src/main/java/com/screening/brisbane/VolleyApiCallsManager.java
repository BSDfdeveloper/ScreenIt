package com.screening.brisbane;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyApiCallsManager {

    private static final String TAG = "VolleyApiCallsManager";

    private ApiResponseInterface apiResponse;

    public VolleyApiCallsManager(ApiResponseInterface apiResponse) {
        this.apiResponse = apiResponse;
    }

    public void sendApiCall(final Context context, JSONObject jsonObject, final String url, int requestType) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(requestType, url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        apiResponse.onSuccess(jsonObject, url);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        apiResponse.onError(findErrorType(context, volleyError));
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                params.put("Accept-Encoding", "utf-8");
                return params;
            }
        };
        MyApplication.getInstance().addToRequestQueue(jsonRequest);
    }

    private String findErrorType(Context context, VolleyError volleyError) {
        String errorString = "Server is not Responding";
        return errorString;
    }


    private String getErrorMessage(VolleyError volleyError) {
        String json = "";
        NetworkResponse response = volleyError.networkResponse;
        if (response != null && response.data != null) {
            switch (response.statusCode) {
                case 401:
                    json = new String(response.data);
                    json = trimMessage(json, "errors");
                    if (json != null) displayMessage(json);
                    break;
            }
        }
        return json;
    }

    private String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    private void displayMessage(String toastString) {
        Log.v(TAG, toastString);
    }
}
