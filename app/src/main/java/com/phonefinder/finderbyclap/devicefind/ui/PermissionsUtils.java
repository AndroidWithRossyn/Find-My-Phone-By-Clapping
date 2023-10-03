package com.phonefinder.finderbyclap.devicefind.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.phonefinder.finderbyclap.devicefind.R;

import java.util.ArrayList;
import java.util.Iterator;

public class PermissionsUtils {
    private static final String PERMISSION_ACCESS_COARSE_LOCATION = "android.permission.ACCESS_COARSE_LOCATION";
    private static final String PERMISSION_ACCESS_FINE_LOCATION = "android.permission.ACCESS_FINE_LOCATION";
    public static final String PERMISSION_ACCESS_INTERNET = "android.permission.INTERNET";
    private static final String PERMISSION_ACCESS_NETWORK_STATE = "android.permission.ACCESS_NETWORK_STATE";
    public static final String PERMISSION_ACCESS_WIFI_STATE = "android.permission.ACCESS_WIFI_STATE";
    public static final String PERMISSION_ADD_VOICE_MAIL = "com.android.voicemail.permission.ADD_VOICEMAIL";
    public static final String PERMISSION_BODY_SENSORS = "android.permission.BODY_SENSORS";
    public static final String PERMISSION_CALL_PHONE = "android.permission.CALL_PHONE";
    private static final String PERMISSION_CAMERA = "android.permission.CAMERA";
    public static final String PERMISSION_CHANGE_WIFI_STATE = "android.permission.CHANGE_WIFI_STATE";
    public static final String PERMISSION_DELETE_PACKAGES = "android.permission.DELETE_PACKAGES";
    public static final String PERMISSION_GET_ACCOUNTS = "android.permission.GET_ACCOUNTS";
    public static final String PERMISSION_INSTALL_PACKAGES = "android.permission.INSTALL_PACKAGES";
    public static final String PERMISSION_PROCESS_OUTGOING_CALL = "android.permission.PROCESS_OUTGOING_CALLS";
    public static final String PERMISSION_READ_CALENDAR = "android.permission.READ_CALENDAR";
    public static final String PERMISSION_READ_CALL_LOG = "android.permission.READ_CALL_LOG";
    public static final String PERMISSION_READ_CONTACTS = "android.permission.READ_CONTACTS";
    private static final String PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final String PERMISSION_READ_PHONE_STATE = "android.permission.READ_PHONE_STATE";
    public static final String PERMISSION_READ_SMS = "android.permission.READ_SMS";
    public static final String PERMISSION_RECEIVE_MMS = "android.permission.RECEIVE_MMS";
    public static final String PERMISSION_RECEIVE_SMS = "android.permission.RECEIVE_SMS";
    public static final String PERMISSION_RECEIVE_WAP_PUSH = "android.permission.RECEIVE_WAP_PUSH";
    public static final String PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO";
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static final String PERMISSION_SEND_SMS = "android.permission.SEND_SMS";
    public static final String PERMISSION_USE_SIP = "android.permission.USE_SIP";
    public static final String PERMISSION_WRITE_CALENDAR = "android.permission.WRITE_CALENDAR";
    public static final String PERMISSION_WRITE_CALL_LOG = "android.permission.WRITE_CALL_LOG";
    public static final String PERMISSION_WRITE_CONTACTS = "android.permission.WRITE_CONTACTS";
    private static final String PERMISSION_WRITE_EXTERNAL_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";
    public static final String PERMISSION_WRITE_SETTING = "android.permission.WRITE_SETTINGS";
    private static PermissionsUtils permissions;
    private Activity activity;
    private ArrayList<String> requiredPermissions;
    private ArrayList<String> ungrantedPermissions = new ArrayList<>();

    private PermissionsUtils(Activity activity2) {
        this.activity = activity2;
    }

    public static synchronized PermissionsUtils getInstance(Activity activity2) {
       PermissionsUtils permissionsUtils;
        synchronized (PermissionsUtils.class) {
            if (permissions == null) {
                permissions = new PermissionsUtils(activity2);
            }
            permissionsUtils = permissions;
        }
        return permissionsUtils;
    }

    private void initPermissions() {
        ArrayList<String> arrayList = new ArrayList<>();
        this.requiredPermissions = arrayList;
        arrayList.add(PERMISSION_CAMERA);
        this.requiredPermissions.add(PERMISSION_RECORD_AUDIO);
        this.requiredPermissions.add(PERMISSION_READ_PHONE_STATE);
    }

    public void requestPermissionsIfDenied() {
        this.ungrantedPermissions = getUnGrantedPermissionsList();
        if (canShowPermissionRationaleDialog()) {
            showMessageOKCancel(this.activity.getResources().getString(R.string.permission_message), new DialogInterface.OnClickListener() {
                /* class com.wisetechapps.whistle.find.phone.utils.PermissionsUtils.AnonymousClass1 */

                public void onClick(DialogInterface dialogInterface, int i) {
                  PermissionsUtils.this.askPermissions();
                }
            });
        } else {
            askPermissions();
        }
    }

    public void requestPermissionsIfDenied(final String str) {
        if (canShowPermissionRationaleDialog(str)) {
            showMessageOKCancel(this.activity.getResources().getString(R.string.permission_message), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialogInterface, int i) {
                  PermissionsUtils.this.askPermission(str);
                }
            });
        } else {
            askPermission(str);
        }
    }

    public void setActivity(Activity activity2) {
        this.activity = activity2;
    }

    private boolean canShowPermissionRationaleDialog() {
        Iterator<String> it = this.ungrantedPermissions.iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity, it.next())) {
                z = true;
            }
        }
        return z;
    }

    private boolean canShowPermissionRationaleDialog(String str) {
        return ActivityCompat.shouldShowRequestPermissionRationale(this.activity, str);
    }


    private void askPermissions() {
        if (this.ungrantedPermissions.size() > 0) {
            ActivityCompat.requestPermissions(this.activity, (String[]) this.ungrantedPermissions.toArray(new String[0]), 1);
        }
    }

    private void askPermission(String str) {
        ActivityCompat.requestPermissions(this.activity, new String[]{str}, 1);
    }

    private void showMessageOKCancel(String str, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this.activity).setMessage(str).setPositiveButton(R.string.ok, onClickListener).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create().show();
    }

    public boolean isAllPermissionAvailable() {
        initPermissions();
        Iterator<String> it = this.requiredPermissions.iterator();
        while (it.hasNext()) {
            if (ContextCompat.checkSelfPermission(this.activity, it.next()) != 0) {
                return false;
            }
        }
        return true;
    }

    private ArrayList<String> getUnGrantedPermissionsList() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<String> it = this.requiredPermissions.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (ActivityCompat.checkSelfPermission(this.activity, next) != 0) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public boolean isPermissionAvailable(String str) {
        return ContextCompat.checkSelfPermission(this.activity, str) == 0;
    }
}
