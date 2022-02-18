package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.MainActivity;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import java.util.ArrayList;
import java.util.Locale;

public class TTS implements TextToSpeech.OnInitListener, AudioManager.OnAudioFocusChangeListener {
    private TextToSpeech tts;
    private AudioManager audioManager;
    private Context context;
    private int readIndex = 0;
    private ArrayList<String>text;
    private ArticleData articleData;
    private String newsType;
    private saver saver;
    private loader loader;

    public TTS(Context context, loader loader, saver saver) {
        this.context = context;
        this.articleData = loader.getLastArc();
        this.newsType = loader.getNewsType();
        this.saver = saver;
        this.loader = loader;
        tts = new TextToSpeech(context, this);
        text = loader.getText();
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            identityLanguage();

            onAudioFocusChange(-1);

            speakTitle();
            speakSentences(text);


            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {
                    Toast.makeText(context, "TTS initialization successful", Toast.LENGTH_SHORT).show();
                }

                //What to do after tts has done speaking the current text
                @Override
                public void onDone(String utteranceId) {
                    readIndex++;

                    //if there are still more sentences in article, continue reading
                    if(readIndex < text.size() && !tts.isSpeaking()){
                        speakSentences(text);
                        //System.out.println("whatever");
                        //System.out.println(articleDatas.get(index).getContent());
                    }
                    //Else, update UI with next article and read it
                    //
                }

                @Override
                public void onError(String utteranceId) {
                    Log.e("UtteranceError", " " + utteranceId);
                }
            });


        }
    }

    public void fuckyou(String fkyou){
        tts.speak(fkyou, TextToSpeech.QUEUE_FLUSH, null, null);
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
            return tts.isSpeaking();
    }

    //Speak the array of sentences.
    public void speakSentences(ArrayList<String> textToRead){
        if(requestAudioFocus()){
            saver.saveReadIndex(readIndex);
            tts.speak(textToRead.get(readIndex), TextToSpeech.QUEUE_ADD, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }

    public void nextSentence(){
        removeAudioFocus();
        if (readIndex != text.size()) {
            readIndex++;
        }
        if ((tts.isSpeaking()) && tts != null){
            tts.stop();
            speakSentences(text);
        }
    }

    public void previousSentence(){
        removeAudioFocus();
        if (readIndex != 0) {
            readIndex--;
        }
        if ((tts.isSpeaking()) && tts != null){
            tts.stop();
            speakSentences(text);
        }
    }

    private void speakTitle() {
        articleData = loader.getLastArc();
        if (requestAudioFocus()) {
            tts.speak(articleData.getTitle() + "by" + articleData.getAuthor(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }


    public void stopPlay() {
        if (tts != null && tts.isSpeaking()) {
            tts.stop();
        }
        removeAudioFocus();
    }

    public void stop(){
        tts.stop();
        removeAudioFocus();
        //tts.shutdown();
    }

    public void identityLanguage(){
        if (newsType.contains("English")){
            tts.setLanguage(Locale.ENGLISH);
        }

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

    public TextToSpeech getNull() {
        return tts;
    }

    public void checkPlay() {
            if (tts != null) {
                tts.stop();
            }
        removeAudioFocus();
    }

    public void playSilent(){
        tts.playSilentUtterance(750, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void shutdown(){
        tts.shutdown();
    }
}
