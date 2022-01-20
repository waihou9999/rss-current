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

public class TTS implements AudioManager.OnAudioFocusChangeListener {
    private TextToSpeech tts;
    private AudioManager audioManager;
    private int readIndex = 0;
    private Activity activity;
    private Context context;
    private ArticleData articleDatas;
    private ArrayList<String>text;
    private Boolean startTTS;
    private loader loader;



    public TTS(Activity activity, Context context) {
        this.context = context;
        this.loader = new loader(activity, context);
        this.startTTS = loader.getTSS();
        //this.articleDatas = loader.getArticleData();

        init();
        if (!startTTS)
            stopPlay();
    }

    public void init(){
            this.articleDatas = loader.getArticleData();
            text = loader.getText();

            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                    if(status == TextToSpeech.SUCCESS){
                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String utteranceId) {
                                Toast.makeText(context, "TTS initialization successful", Toast.LENGTH_SHORT).show();
                                speakSentences(articleDatas.getTitle() + "by" + articleDatas.getAuthor());
                            }

                            //What to do after tts has done speaking the current text
                            @Override
                            public void onDone(String utteranceId) {
                                while(isSpeaking() && readIndex < text.size()){
                                    speakSentences(text.get(readIndex));
                                    readIndex++;
                                }
                            }

                            @Override
                            public void onError(String utteranceId) {
                                Log.e("UtteranceError", " " + utteranceId);
                            }
                        });

                        identityLanguage();

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
                ArrayList<String>sentences = articleDatas.getContent();
                if (!tts.isSpeaking()) speakSentences(sentences.get(readIndex));
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // stop playback
                if(tts.isSpeaking()) {
                    tts.stop();
                    //pauseBtn.setImageResource(android.R.drawable.ic_media_play);
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
        return tts.isSpeaking();
    }

    //Speak the array of sentences.
    private void speakSentences(String textToRead){
        if(requestAudioFocus()){
            tts.speak(textToRead, TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }

    public void stopPlay(){
        if (tts != null && tts.isSpeaking()) {
            tts.stop();
        }
        if (tts != null) tts.shutdown();
        removeAudioFocus();
    }

    public void play(){
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void nextSentence(){
        removeAudioFocus();
        readIndex++;
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void previousSentence(){
        removeAudioFocus();
        readIndex--;
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void identityLanguage(){
        String langIDText = articleDatas.getTitle();

        //Set the language of TTS
        FirebaseLanguageIdentification languageIdentifier = FirebaseNaturalLanguage.getInstance().getLanguageIdentification();
        languageIdentifier.identifyLanguage(langIDText).addOnSuccessListener(
                new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(@Nullable String languageCode) {
                        //System.out.println("languageCode: " + languageCode);
                        switch (languageCode) {
                            case "en": tts.setLanguage(Locale.ENGLISH);break;
                            case "ms":
                            case "id":
                                tts.setLanguage(new Locale("id", "ID")); break;

                            case "zh": tts.setLanguage(Locale.CHINESE); break;

                            default: tts.setLanguage(Locale.ENGLISH);break;
                        }
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be loaded or other internal error.
                                // ...
                            }
                        });
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

}
