package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.R;

import java.sql.SQLOutput;

public class ttsFunctionButton extends FunctionButton implements View.OnClickListener {
    private final ImageButton nextSentBtn;
    private final ImageButton prevSentBtn;
    private final ImageButton stopBtn;
    int playImg = android.R.drawable.ic_media_play;
    int pauseImg = android.R.drawable.ic_media_pause;
    private ttsController ttsController;
    private loader loader;
    private boolean startTSS;

    public ttsFunctionButton(Activity activity, Context context, ttsController ttsController, loader loader){
        super(activity, context);
        stopBtn = activity.findViewById(R.id.stopbtn);
        nextSentBtn = activity.findViewById(R.id.forwbtn);
        prevSentBtn = activity.findViewById(R.id.prevbtn);
        stopBtn.setOnClickListener(this);
        prevSentBtn.setOnClickListener(this);
        nextSentBtn.setOnClickListener(this);


        this.ttsController = ttsController;

        this.loader = loader;

        startTSS = loader.getTSS();

        if (startTSS){
            stopBtn.setImageResource(pauseImg);
        } else {
            stopBtn.setImageResource(playImg);
        }


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopbtn:

                startTSS = loader.getTSS();
                //stop speaking
                if (startTSS){
                    stopBtn.setImageResource(playImg);
                    ttsController.pausing();
                    break;
                }

                startTSS = loader.getTSS();
                //start speaking
                if (!startTSS) {
                    stopBtn.setImageResource(pauseImg);
                    ttsController.playing();
                    break;
                }
                break;

            case R.id.forwbtn:
                ttsController.nextSentence();
                break;

            case R.id.prevbtn:
                ttsController.previousSentence();
                break;
        }
    }

    public void enable() {
        stopBtn.setClickable(true);
        prevSentBtn.setClickable(true);
        nextSentBtn.setClickable(true);
        /*
        if (tts == null) {
            tts = new TTS(context, loader, saver);
            System.out.println("sohaitts" +tts);
            ttsController.setTts(tts);
        }

         */
    }

    public void disabled() {
        stopBtn.setClickable(false);
        prevSentBtn.setClickable(false);
        nextSentBtn.setClickable(false);
    }

    public ttsController getTtsController(){
        return ttsController;
    }


}
