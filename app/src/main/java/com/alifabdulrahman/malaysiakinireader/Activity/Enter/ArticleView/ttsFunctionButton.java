package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.R;

import java.sql.SQLOutput;

public class ttsFunctionButton extends FunctionButton {
    private ImageButton nextSentBtn, prevSentBtn, stopBtn;
    int playImg = android.R.drawable.ic_media_play;
    int pauseImg = android.R.drawable.ic_media_pause;
    private ttsController ttsController;
    private loader loader;
    private boolean startTSS;

    public ttsFunctionButton(Activity activity, ttsController ttsController, loader loader){
        super();
        stopBtn = activity.findViewById(R.id.stopbtn);
        nextSentBtn = activity.findViewById(R.id.forwbtn);
        prevSentBtn = activity.findViewById(R.id.prevbtn);
        stopBtn.setOnClickListener(this::onClick);
        prevSentBtn.setOnClickListener(this::onClick);
        nextSentBtn.setOnClickListener(this::onClick);
        stopBtn.setEnabled(false);
        nextSentBtn.setEnabled(false);
        prevSentBtn.setEnabled(false);

        this.loader = loader;

        startTSS = loader.getTSS();

        if (startTSS){
            stopBtn.setImageResource(pauseImg);
        } else {
            stopBtn.setImageResource(playImg);
        }

        this.ttsController = ttsController;
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stopbtn:
                startTSS = loader.getTSS();
                //stop speaking
                if (startTSS || ttsController.isSpeaking()){
                    stopBtn.setImageResource(pauseImg);
                    ttsController.pausing();
                    System.out.println("stop speaking" + ttsController.isSpeaking());
                    break;
                }

                //start speaking
                if (!startTSS) {
                    stopBtn.setImageResource(playImg);
                    ttsController.initializeTTS();
                    System.out.println("start speaking" + ttsController.isSpeaking());
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
        stopBtn.setEnabled(true);
        nextSentBtn.setClickable(true);
        prevSentBtn.setClickable(true);
        nextSentBtn.setEnabled(true);
        prevSentBtn.setEnabled(true);
    }

    public ttsController getTtsController(){
        return ttsController;
    }
}
