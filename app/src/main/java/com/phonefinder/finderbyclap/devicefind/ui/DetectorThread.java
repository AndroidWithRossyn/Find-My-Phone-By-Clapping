package com.phonefinder.finderbyclap.devicefind.ui;

import android.media.AudioRecord;
import android.util.Log;

import com.musicg.api.ClapApi;
import com.musicg.api.WhistleApi;
import com.musicg.wave.WaveHeader;

import java.util.LinkedList;

public class DetectorThread extends Thread {
    private volatile Thread _thread;
    private int numWhistles;
    private int numClaps;
    private OnSignalsDetectedListener onSignalsDetectedListener;
    private RecorderThread recorder;
    private WaveHeader waveHeader;
    private WhistleApi whistleApi;
    private ClapApi clapApi;
    private int whistleCheckLength = 3;
    private int clapCheckLength = 1;
    private int whistlePassScore = 3;
    private int clapPassScore = 1;
    private LinkedList<Boolean> whistleResultList = new LinkedList<>();
    private LinkedList<Boolean> clapResultList = new LinkedList<>();
    String clapValue;
    public DetectorThread(RecorderThread recorderThread, String value) {
        this.clapValue =value;

        this.recorder = recorderThread;
        AudioRecord audioRecord = recorderThread.getAudioRecord();
        int i = 0;
        int i2 = audioRecord.getAudioFormat() == 2 ? 16 : audioRecord.getAudioFormat() == 3 ? 8 : 0;
        i = audioRecord.getChannelConfiguration() == 16 ? 1 : i;
        WaveHeader waveHeader2 = new WaveHeader();
        this.waveHeader = waveHeader2;
        waveHeader2.setChannels(i) ;
        this.waveHeader.setBitsPerSample(i2);
        this.waveHeader.setSampleRate(audioRecord.getSampleRate());
        this.whistleApi = new WhistleApi(this.waveHeader);
        this.clapApi=new ClapApi(this.waveHeader);
    }

    private void initBuffer() {
        this.numWhistles = 0;
        this.numClaps=0;
        this.whistleResultList.clear();
        this.clapResultList.clear();
        for (int i = 0; i < this.whistleCheckLength; i++) {
            this.whistleResultList.add(false);
        }
        for (int i = 0; i < this.clapCheckLength; i++) {
            this.clapResultList.add(false);
        }
    }

    public void start() {
        this._thread = new Thread(this);
        this._thread.start();
    }

    public void stopDetection() {
        this._thread = null;
    }

    public void run() {
        try {
            initBuffer();
            Thread currentThread = Thread.currentThread();
            while (this._thread == currentThread) {
                byte[] frameBytes = this.recorder.getFrameBytes();
                if (frameBytes != null) {
                    boolean isWhistle = this.whistleApi.isWhistle(frameBytes);
                    boolean isClap = this.clapApi.isClap(frameBytes);
                    if (this.whistleResultList.getFirst().booleanValue()) {
                        this.numWhistles--;
                    }
                    if (this.clapResultList.getFirst().booleanValue()) {
                        this.numClaps--;
                    }
                    this.whistleResultList.removeFirst();
                    this.clapResultList.removeFirst();

                    this.whistleResultList.add(Boolean.valueOf(isWhistle));
                    this.clapResultList.add(Boolean.valueOf(isClap));
                    if (isWhistle) {
                        this.numWhistles++;
                    }
                    if (isClap) {
                        this.numClaps ++;
                    }

                    if (this.numWhistles >= this.whistlePassScore) {
                        Log.e("Sound", "Detected");
                        initBuffer();
                        if(clapValue.equals("ON")) {
                            onWhistleDetected();
                        }
                    }

                    if (this.numClaps >= this.clapPassScore) {
                        Log.e("Sound", "Detected");
                        initBuffer();
                        if(clapValue.equals("YES")) {
                            onWhistleDetected();
                        }
                    }
                } else {
                    if (this.whistleResultList.getFirst().booleanValue()) {
                        this.numWhistles--;
                    }
                    this.whistleResultList.removeFirst();
                    this.whistleResultList.add(Boolean.FALSE);
                    if (this.clapResultList.getFirst().booleanValue()) {
                        this.numClaps--;
                    }
                    this.clapResultList.removeFirst();
                    this.clapResultList.add(Boolean.FALSE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onClapDetected() {
        Log.d("TAG", "onClapDetected: ");
    }

    private void onWhistleDetected() {
        OnSignalsDetectedListener onSignalsDetectedListener2 = this.onSignalsDetectedListener;
        if (onSignalsDetectedListener2 != null) {
            onSignalsDetectedListener2.onWhistleDetected();
        }
    }

    public void setOnSignalsDetectedListener(OnSignalsDetectedListener onSignalsDetectedListener2) {
        this.onSignalsDetectedListener = onSignalsDetectedListener2;
    }

    public void interrupt() {
        super.interrupt();
    }
}
