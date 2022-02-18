package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;

import java.util.ArrayList;

public class ttsController {
    private TTS tts;
    private Context context;
    private loader loader;
    private saver saver;
    private ArrayList<String>text;
    private webviewController webviewController;

    public ttsController(Context context, loader loader, saver saver){
        this.context = context;
        this.loader = loader;
        this.saver = saver;
        this.webviewController = null;
    }

    public void setWebviewController(webviewController webviewController) {
        this.webviewController = webviewController;
    }

    public ttsController() {
        tts = null;
    }

    public void stopPlay() {
        if (tts != null)
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
        if (tts != null) {
            tts.onAudioFocusChange(1);
        }
    }


    public void pausing(){
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

    public void checkPlay() {
        tts.checkPlay();
    }

    public void playSilent() {
        tts.playSilent();
    }

    public void shutdown() {
        tts.shutdown();
    }
}
