package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.TTS.TTS;

import java.util.ArrayList;

public class ttsController extends Controller {
    private TTS tts;
    private Controller controller;

    public ttsController(Activity activity, Context context, Controller controller){
        super(activity, context);
        this.controller = controller;
    }

    public void stopPlay() {
        if (tts != null)
         tts.stopPlay();
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

    public TTS getTTS() {
        return tts;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init() {
        if (tts == null) {
            tts = new TTS(context, loader, saver, controller);
            saver.saveReadIndex(0);
        }
        else
            tts.updateText();
            playing();
    }

    public boolean canPrev() {
        if (tts != null) {
            int readIndex = loader.getReadIndex() + 1;
            return readIndex != 0;
        }
        else return false;
    }

    public boolean canNext() {
        if (tts != null) {
            int readIndex = loader.getReadIndex() + 1;
            int textSize = tts.getTextSize();
            return readIndex != textSize;
        }
        else return false;
    }

    public void onDestroy() {
        if(tts != null){
            tts.destroy();
        }
    }

    public void setReadIndex(int readIndex) {
        if (tts != null) {
            tts.setReadIndex(readIndex);
        }
    }

    public void resetReadIndex() {
        if (tts != null) {
            tts.setReadIndex(0);
        }
    }

    public void onResume() {
        if (tts != null){
            tts.setReadIndex(loader.getReadIndex());
        }
    }
}
