package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class ttsController {
    private TTS tts;
    private saver saver;

    public ttsController(saver saver, TTS tts){
        this.tts = tts;
        this.saver = saver;
    }

    public void initializeTTS(){
        tts.initializeTTS();
    }

    public ttsController() {
        tts = null;
    }

    public void stopPlay() {
        tts.stopPlay();
    }

    public void stop() {
        tts.stop();
    }

    public void previousSentence() {
        tts.previousSentence();
    }

    public void nextSentence() {
        tts.nextSentence();
    }

    public boolean isSpeaking(){
        return tts.isSpeaking();
    }

    public void destroy(){
        tts.destroy();
    }

    public void setText(ArrayList<String> tempList) {
        tts.setText(tempList);
    }

    public void onStop() {
        tts.onStop();
    }

    public void setTTS(TTS tts){
        this.tts = tts;
    }

    public void play() {
       // tts.speakSentences(text);
    }

    public ArrayList<String> getText() {
        return tts.getText();
    }

    public void playing() {
        saver.setTSS(true);
        tts.initializeTTS();
        tts.stop();
    }

    public void pausing(){
        saver.setTSS(false);
        tts.stopPlay();
    }

}
