package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;

import java.util.ArrayList;

public class ttsController {
    private TTS tts;
    private Context context;
    private loader loader;
    private saver saver;
    private ArrayList<String>text;

    public ttsController(Context context, loader loader, saver saver){
        this.context = context;
        this.loader = loader;
        this.saver = saver;
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

    public boolean isSpeaking() {
        if (tts != null)
            return tts.isSpeaking();
        return false;
    }

    public void destroy(){
        if (tts != null)
        tts.destroy();
    }

    public void setText(ArrayList<String> tempList) {
        this.text = tempList;
    }

    public void onStop() {
        tts.onStop();
    }

    public ArrayList<String> getText() {
        return tts.getText();
    }

    public void playing() {
        saver.setTSS(true);
        if (tts != null) {
            tts.onAudioFocusChange(1);
        }
    }

    public void pausing(){
        saver.setTSS(false);
        if (tts != null)
            tts.onAudioFocusChange(-1);
    }

    public void setTts(TTS tts){
        this.tts = tts;
    }

    public TTS getTTS() {
        return tts;
    }

    public void init() {
        tts = new TTS(context, loader, saver);
    }

    public void speak(ArrayList<String> tempList) {
        if (!tts.isSpeaking()){
            tts.speakSentences(tempList);
        }
    }
}
