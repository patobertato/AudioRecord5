package com.patobertato.audiorecord5;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    AudioFile createFile = new AudioFile(); // Nombre del archivo wav del audio grabado.
    AudioRec grabar = new AudioRec();




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFile.getFilename();
        createFile.getTempFilename();

        setButtonHandlers();
        enableButtons(false);



    }

    private void setButtonHandlers() {
        findViewById(R.id.btnStart).setOnClickListener(btnClick);
        findViewById(R.id.btnStop).setOnClickListener(btnClick);
    }

    private void enableButton(int id,boolean isEnable){
        findViewById(id).setEnabled(isEnable);
    }

    private void enableButtons(boolean isRecording) {
        enableButton(R.id.btnStart,!isRecording);
        enableButton(R.id.btnStop,isRecording);
    }






    private View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btnStart:{
                    AppLog.logString("Start Recording");

                    enableButtons(true);
                    grabar.startRecording();

                    break;
                }
                case R.id.btnStop:{
                    AppLog.logString("Start Recording");

                    enableButtons(false);
                    grabar.stopRecording();

                    break;
                }
            }
        }
    };
}