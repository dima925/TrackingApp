package com.xs.android.tesseract;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.beardedhen.androidbootstrap.BootstrapButton;

import net.lateralview.simplerestclienthandler.RestClientManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends AppCompatActivity {

    private String userId;
    private String otp;
    private EditText inpOtp;
    private BootstrapButton btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to disable debugging
        RestClientManager.initialize(getApplicationContext()).enableDebugLog(true);
        setContentView(R.layout.activity_otp_activity);

        Bundle bundle = getIntent().getExtras();

        inpOtp = (EditText) findViewById(R.id.inpPassword);
        btnVerify = (BootstrapButton) findViewById(R.id.btnLogin);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otp = inpOtp.getText().toString();
                verifyOtp();
            }
        });

        userId = bundle.getString("userId");

    }

    private void disableBtn() {
        btnVerify.setEnabled(false);
        btnVerify.setText("Please wait...");
    }

    private void enableBtn() {
        btnVerify.setEnabled(true);
        btnVerify.setText("Login");
    }

    private void verifyOtp() {
        Session session = Session.getInstance(getApplicationContext());

        disableBtn();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.OTP_ENDPOINT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("OK")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                Session session = Session.getInstance(getApplicationContext());
                                session.setFirstName(data.getString("first_name"));
                                session.setLastName(data.getString("last_name"));
                                session.setToken(data.getString("token"));
                                session.setUserName(data.getString("username"));
                                session.setUserId(data.getString("userId"));
                                session.commit();
                                // open main activity
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                inpOtp.setError("Invalid OTP");
                                enableBtn();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            enableBtn();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        enableBtn();
                    }
                }

        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Otp[otpPassword]", otp);
                params.put("Otp[userId]", userId);
                return params;
            }

        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                100000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
