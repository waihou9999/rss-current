package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class TTS implements AudioManager.OnAudioFocusChangeListener {
    private TextToSpeech tts;
    private AudioManager audioManager;
    private Context context;
    private int readIndex = 0;
    private ArrayList<String>text;
    private ArticleData articleData;
    private String newsType;


    public TTS(Context context, loader loader) {
        this.context = context;
        this.articleData = loader.getLastArc();
        this.text = loader.getText();
        this.newsType = loader.getNewsType();
    }

    public void initializeTTS(){
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){

                    identityLanguage();
                    speakSentences(text);
                }
                else
                    Log.e("error", "TTS initialization failed!");
            }
        });
    }

    public boolean removeAudioFocus() {
        try{
            if(audioManager != null){
                return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                        audioManager.abandonAudioFocus(this);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }



    @Override
    public void onAudioFocusChange(int focusState){
        switch (focusState) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (!tts.isSpeaking()) {
                    speakSentences(text);
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // stop playback
                if(tts.isSpeaking()) {
                    tts.stop();
                }
                break;
        }
    }

    public boolean requestAudioFocus() {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    public boolean isSpeaking() {
        if (tts != null) {
            return tts.isSpeaking();
        }
        else return false;
    }

    //Speak the array of sentences.
    public void speakSentences(ArrayList<String> textToRead){
        if(requestAudioFocus()){
            tts.speak(textToRead.get(readIndex), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
            //stopBtn.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    public void nextSentence(){
        removeAudioFocus();
        if (readIndex != text.size()) {
            readIndex++;
        }
        if (tts.isSpeaking()) {
            tts.stop();
            speakSentences(text);
        }
    }

    public void previousSentence(){
        removeAudioFocus();
        if (readIndex != 0) {
            readIndex--;
        }
        if (tts.isSpeaking()) {
            tts.stop();
            speakSentences(text);
        }
    }

    private void speakTitle() {

        if (requestAudioFocus()) {
            tts.speak(articleData.getTitle() + "by" + articleData.getAuthor(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }

    public void stopPlay() {
        if (tts != null && tts.isSpeaking()) {
            tts.stop();
        }
        if (tts != null) tts.shutdown();
        removeAudioFocus();
    }

    public void stop(){
        tts.stop();
        tts.shutdown();
    }

    public void identityLanguage(){
        if (newsType.contains("English")){
            tts.setLanguage(Locale.ENGLISH);
        }
        System.out.println("sohai" + newsType);

        if (newsType.contains("Bahasa Malaysia")){
            Locale localBM = new Locale("id", "ID");
            tts.setLanguage(localBM);
        }


        if (newsType.contains("Chinese")) {
            tts.setLanguage(Locale.CHINESE);
        }
    }

    public void destroy() {
        if(tts != null){
            tts.stop();
            tts.shutdown();
        }
        removeAudioFocus();
    }

    public void setText(ArrayList<String>text){
        this.text = text;
    }

    public void onStop() {
        if (tts != null){
            tts.stop();
        }
    }
    public ArrayList<String> getText() {
        return text;
    }
}
