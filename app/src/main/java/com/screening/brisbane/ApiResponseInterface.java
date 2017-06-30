package com.screening.brisbane;

import org.json.JSONArray;
import org.json.JSONObject;


public interface ApiResponseInterface {
    public void onSuccess(JSONObject jsonObject, String url);

    public void onError(String error);

    public void onSuccess(JSONArray jsonArray);
}
