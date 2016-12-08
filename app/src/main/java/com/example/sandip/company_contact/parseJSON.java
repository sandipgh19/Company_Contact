package com.example.sandip.company_contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandip on 10/18/2016.
 */

public class parseJSON {
    public static String[] name;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";

    private JSONArray users = null;

    private String json;

    public parseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            name = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                name[i] = jo.getString(KEY_NAME);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
