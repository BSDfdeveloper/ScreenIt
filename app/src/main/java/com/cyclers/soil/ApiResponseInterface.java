package com.cyclers.soil;

import org.json.JSONArray;
import org.json.JSONObject;


public interface ApiResponseInterface {
    void onSuccess(JSONObject jsonObject, String url);

    void onError(String error);

    void onSuccess(JSONArray jsonArray);
}
