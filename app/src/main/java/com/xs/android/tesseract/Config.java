package com.xs.android.tesseract;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Config {

    //test url
    public static String TEST_ENDPOINT = "http://creativesocialstudios.in/Admin/rest-api/test";
    // login url from gp server
    public static String LOGIN_ENDPOINT = "http://creativesocialstudios.in/Admin/rest-api/login";
    // otp url
    public static String OTP_ENDPOINT = "http://creativesocialstudios.in/Admin/rest-api/validate-otp";
    // get message url
    public static String GETMESSAGE_ENDPOINT = "http://creativesocialstudios.in/Admin/protected-api/get-campaign-messages";

    //selected message data variable;
    public static String selectedMessageTitle = "none";
    public static String selectedMessageContent = "none";
    public static String selectedMessageDatetime = "none";

    //user info from login
    public static String LOGIN_USERID = "NONE";
    public static String LOGIN_USERNAME = "NONE";
    public static String LOGIN_FNAME = "NONE";
    public static String LOGIN_LNAME = "NONE";
    public static String LOGIN_TOKEN = "NONE";

    public static final String APP_TAG = "request_code";

    // setting page shared preference variable and constant
    public static String PREF_REGISTER_FLAG = "tracking_register_flag";
    public static String PREF_USER_ID = "tracking_user_id";
    public static String PREF_NAME = "tracking_preference";
    public static String PREF_USER_NAME = "tracking_user_name";
    public static String PREF_BACKGROUND_MODE = "tracking_background_mode";


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public String convertDateTime(String inp) {
        Date d = new Date(Integer.parseInt(inp));
        SimpleDateFormat df = new SimpleDateFormat("MM/dd HH:mm");
        return df.format(d);
    }



}