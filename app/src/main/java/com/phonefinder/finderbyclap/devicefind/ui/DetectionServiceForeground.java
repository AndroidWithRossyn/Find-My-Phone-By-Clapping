package com.phonefinder.finderbyclap.devicefind.ui;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.phonefinder.finderbyclap.devicefind.R;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class DetectionServiceForeground extends Service implements OnSignalsDetectedListener {
    public static RemoteViews contentView;
    private static final String PREFS_NAME = "PREFS";
    public static DetectionServiceForeground detectionService;
    public int DETECT_NONE = 0;
    public int DETECT_WHISTLE = 1;
    CameraManager cameraManager;
    int delay = 1200;
    private DetectorThread detectorThread;
    long flash_value;
    HandlerThread handlerThread;
    Camera.Parameters mParams;
    public ServiceHandler mServiceHandler;
    public Looper mServiceLooper;
    boolean on = false;
    private Timer otherAppAudioTimer;
    private RecorderThread recorderThread;
    private Ringtone ringtone;
    Camera screen_camera;
    public int selectedDetection = 0;
    long vib_value;
    private boolean isVibrating = false;
    private Handler handler = new Handler();
    private Runnable runnable;
    public static final String ACTION_STOP_FUNCTIONALITIES = "com.phonefinder.finderbyclap.devicefind.STOP_FUNCTIONALITIES";
    private long lastClapTime = 0;
    private boolean isDoubleClap = false;
    private final long DOUBLE_CLAP_THRESHOLD = 500;

    private BroadcastReceiver stopFunctionalityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Stop flashlight
            turnOff();

            // Stop ringtone
            stopRinging();

            // Stop vibration
            stopVibrating();
        }
    };

    public IBinder onBind(Intent intent) {
        return null;
    }

    public final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            MainActivity.notification = DetectionServiceForeground.this.initNotification("MyService is running");
            DetectionServiceForeground.this.startForeground(message.arg1, MainActivity.notification);
        }
    }

    public DetectionServiceForeground() {
        detectionService = this;
    }

    public void onCreate() {
        startHandler();
        IntentFilter filter = new IntentFilter(ACTION_STOP_FUNCTIONALITIES);
        registerReceiver(stopFunctionalityReceiver, filter);
    }

    private void startHandler() {
        HandlerThread handlerThread2 = new HandlerThread("ServiceStartArguments", 10);
        this.handlerThread = handlerThread2;
        handlerThread2.start();
        this.mServiceLooper = this.handlerThread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        startDetection();
        Message obtainMessage = this.mServiceHandler.obtainMessage();
        obtainMessage.arg1 = i2;
        if (intent == null) {
            return START_STICKY;
        }
        obtainMessage.setData(intent.getExtras());
        this.mServiceHandler.sendMessage(obtainMessage);
        return START_STICKY;
    }

    /* access modifiers changed from: package-private */
    public void microphoneState() {
        if (((AppOpsManager) getSystemService(Context.APP_OPS_SERVICE)).checkOpNoThrow("android:get_usage_stats", Process.myUid(), getPackageName()) != 0) {
            startActivity(new Intent("android.settings.USAGE_ACCESS_SETTINGS"));
        }
        Timer timer = this.otherAppAudioTimer;
        if (timer != null) {
            timer.cancel();
        }
        Timer timer2 = new Timer();
        this.otherAppAudioTimer = timer2;
        timer2.scheduleAtFixedRate(new TimerTask() {
            /* class com.wisetechapps.whistle.find.phone.service.DetectionServiceForeground.AnonymousClass1 */

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                List<UsageStats> queryUsageStats = ((UsageStatsManager) DetectionServiceForeground.this.getSystemService(Context.USAGE_STATS_SERVICE)).queryUsageStats(3, currentTimeMillis - 3600000, currentTimeMillis);
                if (queryUsageStats != null && queryUsageStats.size() > 0) {
                    TreeMap treeMap = new TreeMap();
                    for (UsageStats usageStats : queryUsageStats) {
                        treeMap.put(Long.valueOf(usageStats.getLastTimeUsed()), usageStats);
                    }
                    if (!treeMap.isEmpty()) {
                        String packageName = ((UsageStats) treeMap.get(treeMap.lastKey())).getPackageName();
                        boolean z = false;
                        boolean z2 = DetectionServiceForeground.this.getPackageManager().checkPermission(PermissionsUtils.PERMISSION_RECORD_AUDIO, packageName) == PackageManager.PERMISSION_GRANTED;
                        if (!DetectionServiceForeground.this.getApplicationContext().getPackageName().equals(packageName)) {
                            z = z2;
                        }
                        if (z) {
                            if (DetectionServiceForeground.this.recorderThread != null) {
                                DetectionServiceForeground.this.recorderThread.stopRecording();
                                new Handler().removeCallbacksAndMessages(DetectionServiceForeground.this.recorderThread);
                                DetectionServiceForeground.this.recorderThread = null;
                            }
                            if (DetectionServiceForeground.this.detectorThread != null) {
                                DetectionServiceForeground.this.detectorThread.stopDetection();
                                new Handler().removeCallbacksAndMessages(DetectionServiceForeground.this.detectorThread);
                                DetectionServiceForeground.this.detectorThread = null;
                            }
                            Log.e("run: ", "stop");
                            return;
                        }
                        DetectionServiceForeground.this.startDetection();
                        Log.e("run: ", "start");
                    }
                }
            }
        }, 0, 3000);
    }

    public Notification initNotification(String str) {

        RemoteViews remoteViews = new RemoteViews(getPackageName(), (int) R.layout.custom_notification);
        contentView = remoteViews;
        remoteViews.setTextViewText(R.id.tvNotificationTitle, getResources().getString(R.string.whistle_detection));
        contentView.setTextColor(R.id.tvNotificationTitle, ContextCompat.getColor(this, R.color.app_color));

        Intent notificationIntent = new Intent(this, MainActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, FLAG_ACTIVITY_NEW_TASK | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.ID);
        builder.setSmallIcon(R.drawable.logo).setOngoing(true).setChannelId(MainActivity.ID).setContentTitle(getResources().getString(R.string.service)).setContentText(str).setVisibility(NotificationCompat.VISIBILITY_PUBLIC).setPriority(2).setContent(contentView).setContentIntent(pendingIntent);
        return builder.build();
    }

    private PendingIntent getPendingIntent(Context context, String str) {
        Intent intent = new Intent(context, NotificationListener.class);
        intent.setAction(str);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void startDetection() {
        this.selectedDetection = this.DETECT_WHISTLE;
        RecorderThread recorderThread2 = new RecorderThread();
        this.recorderThread = recorderThread2;
        recorderThread2.start();
        DetectorThread detectorThread2 = new DetectorThread(this.recorderThread, getPreference("startButton"));
        this.detectorThread = detectorThread2;
        detectorThread2.setOnSignalsDetectedListener(this);
        this.detectorThread.start();
    }

    public void onDestroy() {
        setPreference("startButton", "NO");
        RecorderThread recorderThread2 = this.recorderThread;
        if (recorderThread2 != null) {
            recorderThread2.stopRecording();
            this.recorderThread = null;
        }
        DetectorThread detectorThread2 = this.detectorThread;
        if (detectorThread2 != null) {
            detectorThread2.stopDetection();
            this.detectorThread = null;
        }
        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(Integer.parseInt(MainActivity.ID));
        this.selectedDetection = this.DETECT_NONE;
        stopVibrating();
        unregisterReceiver(stopFunctionalityReceiver);

    }

    @Override
    public void onWhistleDetected() {

        if (getPreference("flash_value").equals("slow")) {
            this.flash_value = 400;
        } else if (getPreference("flash_value").equals("medium")) {
            this.flash_value = 800;
        } else if (getPreference("flash_value").equals("fast")) {
            this.flash_value = 1200;
        }
        if (getPreference("vibration_value").equals("slow")) {
            this.vib_value = 300;
        } else if (getPreference("vibration_value").equals("medium")) {
            this.vib_value = 600;
        } else if (getPreference("vibration_value").equals("fast")) {
            this.vib_value = 900;
        }
        long currentTime = System.currentTimeMillis();
        long timeSinceLastClap = currentTime - lastClapTime;
        lastClapTime = currentTime;

        if (timeSinceLastClap <= DOUBLE_CLAP_THRESHOLD) {
            // Double clap detected
            isDoubleClap = true;
        } else {
            // Single clap detected, reset the double clap flag
            isDoubleClap = false;
        }

        if (isDoubleClap) {
            if (getPreference("startButton").equals("YES")) {

                if (getPreference("ring").equals("YES")) {
                    Uri ringtoneUri = Uri.parse(getPreference("ringtone_Name"));
                    if (ringtoneUri == null) {
                        ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                    }

                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(getApplicationContext(), ringtoneUri);
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        Thread.sleep(3000); // Adjust the delay as needed

                        // Stop the media player and release resources
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
             /*   Uri parse = Uri.parse(getPreference("ringtone_Name"));
                this.ringtone = RingtoneManager.getRingtone(getApplicationContext(), parse);
                if (parse == null && (parse = RingtoneManager.getDefaultUri(2)) == null) {
                    parse = RingtoneManager.getDefaultUri(1);
                }
                Ringtone ringtone2 = RingtoneManager.getRingtone(getApplicationContext(), parse);
                this.ringtone = ringtone2;
                ringtone2.setStreamType(4);
                if (this.ringtone != null) {
                    Log.d("Test", "played");
                    this.ringtone.play();
                }
                try {
                    Thread.sleep(3000);
                    stopRinging();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                }

                if (getPreference("vibration").equals("YES")) {
                    // Inside your method
                    if (!isVibrating) {
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        if (vibrator != null && vibrator.hasVibrator()) {
                            // Check if the device has a vibrator and it's enabled
                            if (Build.VERSION.SDK_INT >= 26) {
                                vibrator.vibrate(VibrationEffect.createWaveform(new long[]{3000, 2000, 3000, 2000}, -1));
                            } else {
                                vibrator.vibrate(new long[]{3000, 2000, 3000, 2000}, -1);
                            }
                            isVibrating = true;

                            // Schedule a runnable to stop the vibration after the desired duration (e.g., 5 seconds)
                            runnable = new Runnable() {
                                @Override
                                public void run() {
                                    vibrator.cancel(); // Stop the vibration
                                    isVibrating = false; // Reset the flag
                                }
                            };
                            handler.postDelayed(runnable, 5000); // 5000 milliseconds = 5 seconds
                        }
                    }

               /* Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                long j = this.vib_value;
                long[] jArr = {3000, j, 50, j, 50, j};
                if (Build.VERSION.SDK_INT >= 26) {
                    vibrator.vibrate(VibrationEffect.createWaveform(jArr, -1));
                } else {
                    vibrator.vibrate(jArr, -1);
                }*/
                }
                if (getPreference("flash").equals("YES")) {
                    new Thread() {
                        public void run() {
                            try {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    DetectionServiceForeground.this.cameraManager = (CameraManager) DetectionServiceForeground.this.getSystemService(Context.CAMERA_SERVICE);
                                } else if (DetectionServiceForeground.this.screen_camera == null) {
                                    DetectionServiceForeground.this.screen_camera = Camera.open();
                                    try {
                                        DetectionServiceForeground.this.screen_camera.setPreviewDisplay(null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    DetectionServiceForeground.this.screen_camera.startPreview();
                                }
                                for (int i = 0; i < 3; i++) {
                                    DetectionServiceForeground.this.toggleFlashLight();
                                    sleep((long) DetectionServiceForeground.this.delay);
                                }
                                if (Build.VERSION.SDK_INT >= 23) {
                                    try {
                                        DetectionServiceForeground.this.cameraManager.setTorchMode(DetectionServiceForeground.this.cameraManager.getCameraIdList()[0], false);
                                    } catch (CameraAccessException e2) {
                                        e2.printStackTrace();
                                    }
                                } else if (DetectionServiceForeground.this.screen_camera != null) {
                                    DetectionServiceForeground.this.screen_camera.stopPreview();
                                    DetectionServiceForeground.this.screen_camera.release();
                                    DetectionServiceForeground.this.screen_camera = null;
                                }
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        }
                    }.start();
                }
            }
        }
    }

    public void toggleFlashLight() {
        if (!this.on) {
            turnOn();
        } else {
            turnOff();
        }
    }

    public void turnOn() {
        if (Build.VERSION.SDK_INT >= 23) {
            CameraManager cameraManager2 = this.cameraManager;
            if (cameraManager2 != null) {
                try {
                    cameraManager2.setTorchMode(cameraManager2.getCameraIdList()[0], true);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            this.on = true;
            return;
        }
        Camera camera = this.screen_camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            this.mParams = parameters;
            parameters.setFlashMode("torch");
            this.screen_camera.setParameters(this.mParams);
            this.on = true;
        }
    }

    public void turnOff() {
        if (Build.VERSION.SDK_INT >= 23) {
            CameraManager cameraManager2 = this.cameraManager;
            if (cameraManager2 != null) {
                try {
                    cameraManager2.setTorchMode(cameraManager2.getCameraIdList()[0], false);
                } catch (CameraAccessException e) {
                    e.printStackTrace();
                }
            }
            this.on = false;
            return;
        }
        Camera camera = this.screen_camera;
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            this.mParams = parameters;
            if (parameters.getFlashMode().equals("torch")) {
                this.mParams.setFlashMode("off");
                this.screen_camera.setParameters(this.mParams);
            }
            this.on = false;
        }
    }

    public void stopRinging() {
        try {
            if (this.ringtone.isPlaying()) {
                Log.d("Test", "stopped");
                this.ringtone.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPreference(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return settings.getString(key, "true");
    }

    public boolean setPreference(String key, String value) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    void stopVibrating() {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null && isVibrating) {
            vibrator.cancel();
            isVibrating = false;
        }
    }

}