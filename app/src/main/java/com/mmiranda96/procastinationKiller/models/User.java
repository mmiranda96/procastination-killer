package com.mmiranda96.procastinationKiller.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Base64;

public class User implements Serializable {

    private String username;
    private String password;
    private String name;

    public User(String username, String password, String name){
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String name){
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public String buildAuthentication() {
        String authentication = this.username + ":" + this.password;
        return Base64.getEncoder().encodeToString(authentication.getBytes());
    }

    public JSONObject toJSONObject() {
        JSONObject result = new JSONObject();
        try {
            result.put("email", this.username);
            result.put("password", this.password);
            result.put("name", this.name);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
