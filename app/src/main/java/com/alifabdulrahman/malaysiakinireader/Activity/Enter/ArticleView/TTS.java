package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;

import java.util.ArrayList;
import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener, AudioManager.OnAudioFocusChangeListener {
    private Activity activity;
    private TextToSpeech tts;
    private AudioManager audioManager;
    private int readIndex = 0;
    private Context context;
    private ArrayList<String>text;
    private Boolean startTTS;
    private loader loader;
    private ArticleData articleData;
    private boolean readable;
    private String newsType;

    public TTS(Activity activity, Context context, ArticleData articleData, String newsType) {
        this.context = context;
        this.articleData = articleData;
        this.loader = new loader(activity, context);
        this.newsType = newsType;
        tts = new TextToSpeech(context, this::onInit);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Toast.makeText(context, "TTS initialization successful", Toast.LENGTH_SHORT).show();
                    speakTitle();
                }

                //What to do after tts has done speaking the current text
                @Override
                public void onDone(String utteranceId) {
                    readIndex++;

                    //if there are still more sentences in article, continue reading
                    if (readIndex < text.size() && !tts.isSpeaking()) {
                        speakSentences();
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    Log.e("UtteranceError", " " + utteranceId);
                }
            });
            identityLanguage();
        }
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
                    speakSentences();
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
    public void speakSentences() {
        text = loader.getText();
        System.out.println("fkla" + text);
        if (requestAudioFocus()) {
            tts.speak(text.get(readIndex), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
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


    public void nextSentence(){
        removeAudioFocus();
        if (readIndex != text.size()) {
            readIndex++;
        }
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void previousSentence(){
        removeAudioFocus();
        if (readIndex != 0) {
            readIndex--;
        }
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void identityLanguage(){
        if (newsType.contains("English")){
            tts.setLanguage(Locale.ENGLISH);
        }

        if (newsType.contains("Bahasa Malaysia")){
            Locale localBM = new Locale("id", "MY");
            tts.setLanguage(localBM);
        }


        if (newsType.contains("Chinese")) {
            tts.setLanguage(Locale.CHINA);
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

    public void checkPlay() {
        if (startTTS)
            speakSentences();
        else
            stopPlay();
    }
}
