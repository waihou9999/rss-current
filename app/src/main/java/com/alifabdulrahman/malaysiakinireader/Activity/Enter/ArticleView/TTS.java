package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;
import com.example.myappname.TinyDB;
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
    private Context context;
    private ArticleData articleDatas;
    private ArrayList<String>text;


    public TTS(Context context, ArticleData articleDatas, ArrayList<String>text){
        this.context = context;
        //Initialize the Text to Speech
        this.articleDatas = articleDatas;
        this.text = text;
            tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    ArrayList<String>sentences = text;
                    if(status == TextToSpeech.SUCCESS){
                        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                            @Override
                            public void onStart(String utteranceId) {
                                Toast.makeText(context, "TTS initialization successful", Toast.LENGTH_SHORT).show();
                                speakSentences(sentences.get(readIndex));
                            }

                            //What to do after tts has done speaking the current text
                            @Override
                            public void onDone(String utteranceId) {
                                readIndex++;

                                System.out.println("fk" + sentences.get(readIndex));
                                speakSentences(sentences.get(readIndex));
                            }

                            @Override
                            public void onError(String utteranceId) {
                                Log.e("UtteranceError", " " + utteranceId);
                            }
                        });


                        //String langIDText = articleDatas.get(index).getContent().toString();

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
                    /*
                    //int result=tts.setLanguage(Locale.ENGLISH);
                    //int result=tts.setLanguage(new Locale("id","ID"));
                    //int result=tts.setLanguage(Locale.CHINESE);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("error", "This language is not supported");
                    }
*/
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
        removeAudioFocus();
    }

    public void play(){
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void nextSentence(){
        readIndex++;
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }

    public void previousSentence(){
        readIndex--;
        onAudioFocusChange(AudioManager.AUDIOFOCUS_GAIN);
    }
}
