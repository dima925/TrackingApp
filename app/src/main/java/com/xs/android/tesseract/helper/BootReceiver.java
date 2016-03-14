package com.xs.android.tesseract.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.xs.android.tesseract.service.GetLocationService;

public class BootReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startGetLocationServiceIntent = new Intent(context, GetLocationService.class);
        startWakefulService(context, startGetLocationServiceIntent);
    }
}
