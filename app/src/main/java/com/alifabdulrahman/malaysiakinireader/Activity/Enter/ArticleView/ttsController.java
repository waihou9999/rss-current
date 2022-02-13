package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import java.util.ArrayList;

public class ttsController {
    private TTS tts;

    public ttsController(TTS tts){
        this.tts = tts;
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

    public void initializeTTS() {
        tts.initializeTTS();
    }


}
