package com.phonefinder.finderbyclap.devicefind.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationListener extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction() == null) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
