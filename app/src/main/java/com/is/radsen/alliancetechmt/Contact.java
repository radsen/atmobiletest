package com.is.radsen.alliancetechmt;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Radsen on 1/6/15.
 */
public class Contact {

    public Contact (JSONObject jsonObject){
        try {
            this.name = jsonObject.getString("first");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.jsonObject = jsonObject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    private JSONObject jsonObject;


}
