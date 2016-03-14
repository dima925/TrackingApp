package com.xs.android.tesseract;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by xp on 11/27/2015.
 */

public class Session {

    Activity activity;
    Context context;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ////
    private String userId;
    private String userName;
    private String token;
    private String firstName;
    private String lastName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static Session getInstance() {
        return instance;
    }

    public static void setInstance(Session instance) {
        Session.instance = instance;
    }

    private static Session instance = null;

    public static Session getInstance(Context context) {
        if (instance == null) {
            instance = new Session(context);
        }
        return instance;
    }


    public void destroy() {


        commit();
    }

    private Session(Context context) {


        sharedPref = context.getSharedPreferences("userdetails", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        try {
            userId = sharedPref.getString("userId", "");
            userName = sharedPref.getString("userName", "");
            firstName = sharedPref.getString("firstName", "");
            lastName = sharedPref.getString("lastName", "");
            token = sharedPref.getString("token", "");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void commit() {
        editor.putString("userId", userId);
        editor.putString("userName", userName);
        editor.putString("firstName", firstName);
        editor.putString("lastName", lastName);
        editor.putString("token", token);
        editor.commit();
    }


}
