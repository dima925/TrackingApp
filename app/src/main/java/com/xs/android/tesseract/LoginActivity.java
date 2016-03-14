package com.xs.android.tesseract;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.beardedhen.androidbootstrap.TypefaceProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

        private EditText inpUserName;
        BootstrapButton btnLogin;
        private EditText inpPassword;

        private String username;
        private String password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_login);
                TypefaceProvider.registerDefaultIconSets();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//

                alreadyLogged();
                inpUserName = (EditText) findViewById(R.id.inpUsername);
                inpPassword = (EditText) findViewById(R.id.inpPassword);
                btnLogin = (BootstrapButton) findViewById(R.id.btnLogin);
                btnLogin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                username = inpUserName.getText().toString();
                                password = inpPassword.getText().toString();
                                doLogin(username, password);
                        }
                });


        }

        private void alreadyLogged() {
                Session session = Session.getInstance(getApplicationContext());
                if (!session.getToken().isEmpty()) {
                        // jump to main
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                }
        }

        private void disableBtn() {
                btnLogin.setEnabled(false);
                btnLogin.setText("Please wait...");
        }

        private void enableBtn() {
                btnLogin.setEnabled(true);
                btnLogin.setText("Login");
        }

        public void doLogin(final String username, final String password) {

                disableBtn();


                StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_ENDPOINT,
                        new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                        try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                String status = jsonObject.getString("status");
                                                if (status.equals("OK")) {
                                                        Log.d("request_code", "onResponse: " + response);
                                                        //
                                                        JSONObject data = jsonObject.getJSONObject("data");
                                                        Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                                                        intent.putExtra("userId", data.getString("userId"));
                                                        startActivity(intent);
                                                        finish();
                                                } else {
                                                        inpPassword.setError("Invalid username/password");
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
                                params.put("LoginForm[username]", username);
                                params.put("LoginForm[password]", password);
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
