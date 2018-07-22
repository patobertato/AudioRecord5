package com.patobertato.audiorecord5;


import android.util.Log;

class AppLog {
    private static final String APP_TAG = "AudioRecorder";

    public static int logString(String message){
        return Log.i(APP_TAG,message);
    }
}