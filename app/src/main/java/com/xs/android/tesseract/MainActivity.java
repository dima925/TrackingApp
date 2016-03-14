package com.xs.android.tesseract;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xs.android.tesseract.fragment.DetailsMessageFragment;
import com.xs.android.tesseract.fragment.FirstFragment;

import com.xs.android.tesseract.fragment.GetMsgFragment;
import com.xs.android.tesseract.fragment.PopulationFragment;
import com.xs.android.tesseract.service.GetLocationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xs.android.tesseract.fragment.GetMsgFragment.newInstance;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LocationManager mLocationManager;
    SharedPreferences sharedPreferences;
    Boolean pref_flag;
    TextView tvLat, tvLon, tvSpd, tvAlt;
    Button startBtn;
    Session session;
    Location location;
    android.support.v4.app.FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = Session.getInstance(getApplicationContext());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = FirstFragment.newInstance();
        ft.add(R.id.base_container, fragment);

        if (!isLocationServiceRunning()){
            Toast.makeText(this, "LocationService was not running.", Toast.LENGTH_SHORT).show();
            startService(new Intent(this, GetLocationService.class));
        } else {
            Toast.makeText(this, "LocationService already is running in background.", Toast.LENGTH_SHORT).show();
        }

        //starts ending data
//        sendLocationData();
    }

    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service.service.getClassName().toString()
                    .equals(GetLocationService.class.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        replaceFragment(id);

//
//        if (id == R.id.nav_camera) {
////            Dialog dialog = new Dialog(MainActivity.this);
////            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); //before
////            dialog.setContentView(R.layout.submenu);
////            dialog.show();
//            // Handle the camera action
//
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(int id){
        FragmentTransaction ft = fm.beginTransaction();
        Fragment fragment = null;
        switch (id) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                fragment = PopulationFragment.newInstance();
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_message:
                Log.d("Request_code","message_fragment");
                fragment = GetMsgFragment.newInstance();
                break;
            case 1001:
                fragment = DetailsMessageFragment.newInstance();
                break;
        }

        if (fragment != null) {
            ft.replace(R.id.base_container, fragment);
            ft.commit();
        }
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                //return TODO;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    private void sendLocationData() {
        final Handler h = new Handler();
        final int delay = 30000; // send data every 30 Seconds
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        h.postDelayed(new Runnable() {

            public void run() {

                Log.e("Test", "run: asdasd ");

                location = getLastKnownLocation();
                //do something
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.TEST_ENDPOINT,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.e("Response", "onResponse: " + response);
                                Toast.makeText(getApplicationContext(), "OK " + Double.toString(location.getLongitude()) + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("PromoterTracker[promoter_id]", session.getUserId());
                        params.put("PromoterTracker[lat]", Double.toString(location.getLatitude()));
                        params.put("PromoterTracker[lng]", Double.toString(location.getLongitude()));
                        params.put("token", session.getToken());
                        return params;
                    }


                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        100000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                RequestQueue requestQue = Volley.newRequestQueue(getApplicationContext());
                requestQue.add(stringRequest);
                //
                h.postDelayed(this, delay);
            }
        }, delay);
    }


    private class MyLocationListener implements LocationListener {
        private double vlat = 0.0;
        private double vlon = 0.0;
        private double valt = 0.0;
        private double vspeed = 0.0;

        @Override
        public void onLocationChanged(Location location) {
            vlat = location.getLatitude();
            vlon = location.getLongitude();

            vspeed = location.getSpeed();
            String lat = String.valueOf(vlat);
            String lng = String.valueOf(vlon);
            String speed = String.valueOf(vspeed);

            // location data sending depending on user setting
            //sendLocationData();
        }

        private void sendLocationData() {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
    //menu processing
    public void promOnClick(View v) {
        Toast.makeText(getApplicationContext(),"Promo Click!", Toast.LENGTH_SHORT).show();

    }


}
