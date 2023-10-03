package com.adsmodule.api.AdsModule.Utils;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.facebook.ads.BuildConfig;
import com.tools.downaloader.adsmodule.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Global {
    public static final String PLEASE_WAIT = "We are processing your request...";
    public static ArrayList<String> mSelectedImgPath = new ArrayList<>();

    private Context context;

    public Global(Context context) {
        this.context = context;
    }

    static AlertDialog alertDialog;
    private static ProgressDialog dialog;
    private static Dialog alertLoaderDialog;


    public static void showUpdateAppDialog(Activity activity) {
        Dialog updateDialog = new Dialog(activity);
        updateDialog.setContentView(LayoutInflater.from(activity).inflate(R.layout.update_dialog, null));
        updateDialog.setCancelable(false);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDialog.findViewById(R.id.tv_install).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialog.dismiss();
                activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.PLAYSTORE_BASE + activity.getPackageName())));
            }
        });
        updateDialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDialog.dismiss();
                activity.finish();
                System.exit(0);
            }
        });
        updateDialog.show();

    }

    public static boolean isNull(String val) {
        return val == null || val.trim().equalsIgnoreCase("") || val.trim().equalsIgnoreCase("null") || val.trim() == "" || val.trim() == "null";
    }


    public static void showAlertProgressDialog(Activity activity) {
        alertLoaderDialog = new Dialog(activity);
        alertLoaderDialog.setContentView(LayoutInflater.from(activity).inflate(R.layout.loading_dialog, null));
        alertLoaderDialog.setCancelable(false);
        alertLoaderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (!alertLoaderDialog.isShowing())
            alertLoaderDialog.show();
        else alertLoaderDialog.dismiss();
    }

    public static void hideAlertProgressDialog() {
        if (alertLoaderDialog != null && alertLoaderDialog.isShowing()) {
            alertLoaderDialog.dismiss();
        }
    }

    public static View getDefaultImage(Context context, Drawable imgRes) {
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setImageDrawable(imgRes);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    public static void hideAlertProgressDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }

            if (alertDialog != null && alertDialog.isShowing()) {
                alertDialog.dismiss();
                alertDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sout(String TagToString, Object whatToPrint) {
        if (BuildConfig.DEBUG) {
            System.out.println(TagToString + " " + whatToPrint);
        }
    }


    public static long mLastClickTime = 0;

    public static void checkClick() {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }

    public static boolean isLatestVersion() {
        return SDK_INT >= Build.VERSION_CODES.R;
    }

    public static Uri getContentMediaUri() {
        if (isLatestVersion()) {
            return MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        }
    }

    public static void printLog(String key, String val) {
        if (BuildConfig.DEBUG) Log.e(key + "*===", "===*" + val);
    }

    public static boolean isEmptyStr(String str) {
        if (str == null || str.trim().isEmpty() || str.equalsIgnoreCase("")) return true;
        return false;
    }

    public static String getRootFileForSave() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
        } else {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }

    public static Uri getUri(File file, Context context) {
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }
        return contentUri;
    }

    public static boolean isArrayListNull(ArrayList arrayList) {
        try {
            if (arrayList == null) {
                return true;
            } else if (arrayList != null && arrayList.size() <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isListNull(List list) {
        try {
            if (list == null) {
                return true;
            } else if (list != null && list.size() <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isClassNull(Object objectToCheck) {
        return objectToCheck == null;
    }

    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }


    public static String getDeviceId(Context activity) {

        String device_id = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                Log.e("getDeviceId: ", device_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                TelephonyManager tm = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return device_id;
                }
                if (tm != null && tm.getDeviceId() != null) {
                    device_id = tm.getDeviceId();
                    Log.e("getDeviceId: ", device_id);
                } else {
                    device_id = Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
                    Log.e("getDeviceId: ", device_id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return device_id;
    }

    public static boolean checkAppVersion(String apiVersionName, String appVersionName) {
        int appversion = Integer.parseInt(appVersionName.split("\\.").toString());
        int apiversionname = Integer.parseInt(removeAlphabets(apiVersionName).split("\\.").toString());
        return apiversionname > appversion;
    }

    private static String removeAlphabets(String apiVersionName) {
        return apiVersionName.replaceAll("[A-Za-z]", "");
    }
}
