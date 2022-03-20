package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class ttsController {
    private TTS tts;
    private Context context;
    private loader loader;
    private saver saver;
    private Controller controller;

    public ttsController(Context context, loader loader, saver saver, Controller controller){
        this.context = context;
        this.loader = loader;
        this.saver = saver;
        this.controller = controller;
    }


    public void stopPlay() {
        if (tts != null)
         tts.stopPlay();
    }

    public void stop() {
        tts.onStop();
    }

    public void previousSentence() throws InterruptedException {
        tts.previousSentence();
    }

    public void nextSentence() throws InterruptedException {
        tts.nextSentence();
    }


    public void destroy(){
        if (tts != null)
        tts.destroy();
    }


    public void onStop() {
        if (tts != null)
            tts.onStop();
    }

    public ArrayList<String> getText() {
        return tts.getText();
    }

    public void playing() {
        if (tts != null) {
            tts.play();
        }
    }


    public void pausing(){
        if (tts != null){
            tts.stopPlay();
        }
    }

    public void setTts(TTS tts){
        this.tts = tts;
    }

    public TTS getTTS() {
        return tts;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        tts = new TTS(context, loader, saver, controller);
    }

    public void speak(ArrayList<String> tempList) throws InterruptedException {
        if (!tts.isSpeaking()){
            tts.speakSentences(tempList);
        }
    }

    public void checkPlay() {
        tts.checkPlay();
    }

    public void playSilent() {
        tts.playSilent();
    }

    public void shutdown() {
        tts.shutdown();
    }

    public boolean canPrev() {
        int readIndex = tts.getReadIndex();

        if (readIndex == 0){
            return false;
        }
        else
            return true;
    }

    public boolean canNext() {
        int readIndex = tts.getReadIndex() + 1;
        int textSize = tts.getTextSize();

        if (readIndex == textSize){
            return false;
        }
        else
            return true;
    }

    public void onDestroy() {
        if(tts != null){
            tts.destroy();
        }
    }
}
