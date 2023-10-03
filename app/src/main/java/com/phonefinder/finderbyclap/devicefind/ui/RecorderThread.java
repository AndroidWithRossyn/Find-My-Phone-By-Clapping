package com.phonefinder.finderbyclap.devicefind.ui;

import android.media.AudioRecord;

public class RecorderThread extends Thread {
    private int audioEncoding = 2;
    private AudioRecord audioRecord;
    byte[] buffer;
    private int channelConfiguration = 16;
    private int frameByteSize = 2048;
    private boolean isRecording = false;
    private int sampleRate = 44100;

    public RecorderThread() {
        int i = this.sampleRate;
        int i2 = this.channelConfiguration;
        int i3 = this.audioEncoding;
        this.audioRecord = new AudioRecord(1, i, i2, i3, AudioRecord.getMinBufferSize(i, i2, i3));
        this.buffer = new byte[this.frameByteSize];
    }

    public AudioRecord getAudioRecord() {
        return this.audioRecord;
    }

    public boolean isRecording() {
        return isAlive() && this.isRecording;
    }

    public void startRecording() {
        try {
            this.audioRecord.startRecording();
            this.isRecording = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        try {
            this.audioRecord.stop();
            this.audioRecord.release();
            this.isRecording = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getFrameBytes() {
        int i;
        int i2 = 0;
        this.audioRecord.read(this.buffer, 0, this.frameByteSize);
        int i3 = 0;
        while (true) {
            i = this.frameByteSize;
            if (i2 >= i) {
                break;
            }
            byte[] bArr = this.buffer;
            i3 += Math.abs((int) ((short) ((bArr[i2 + 1] << 8) | bArr[i2])));
            i2 += 2;
        }
        if (((float) ((i3 / i) / 2)) < 30.0f) {
            return null;
        }
        return this.buffer;
    }

    public void run() {
        startRecording();
    }

    public void interrupt() {
        super.interrupt();
    }
}
