package com.patobertato.audiorecord5;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioRec {

    private AudioRecord recorder = null;
    private static final byte RECORDER_BPP = 16;
    private static final int RECORDER_SAMPLERATE = 44100;
    private static final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_STEREO;
    private static final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private boolean isRecording = false;
    private Thread recordingThread = null;
    private int bufferSize = 0;
    AudioFile createFile = new AudioFile();


    public  void startRecording(){

                bufferSize = AudioRecord.getMinBufferSize(44100,
                AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT);

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                RECORDER_SAMPLERATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, bufferSize);

        int i = recorder.getState();
        if(i==1)
            recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                writeAudioDataToFile(bufferSize);
            }
        },"AudioRecorder Thread");

        recordingThread.start();
    }



    public void writeAudioDataToFile(int bufferSize){


        byte data[] = new byte[bufferSize];
        String filename = createFile.getTempFilename();
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }

        int read = 0;

        if(null != os){
            while(isRecording){
                read = recorder.read(data, 0, bufferSize);

                if(AudioRecord.ERROR_INVALID_OPERATION != read){
                    try {
                        os.write(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRecording(){
        if(null != recorder){
            isRecording = false;

            int i = recorder.getState();
            if(i==1)
                recorder.stop();
            recorder.release();

            recorder = null;
            recordingThread = null;
        }

        createFile.copyWaveFile(createFile.getTempFilename(),createFile.getFilename(), RECORDER_SAMPLERATE, RECORDER_BPP,bufferSize );
        deleteTempFile();
    }

    private void deleteTempFile() {
        File file = new File(createFile.getTempFilename());

        file.delete();
    }



}
