package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.TTS;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;
import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class TTS implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private audioManager audioManager;
    private Context context;
    private int readIndex = 0;
    private ArrayList<String>text;
    private ArticleData articleData;
    private Controller controller;
    private webviewController webviewController;
    private String newsType;
    private saver saver;
    private loader loader;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TTS(Context context, loader loader, saver saver, Controller controller) {
        this.context = context;
        audioManager = new audioManager(context, this, loader);
        this.saver = saver;
        this.loader = loader;
        this.articleData = loader.getLastArc();
        this.newsType = loader.getNewsType();
        this.controller = controller;
        tts = new TextToSpeech(context, this);
        text = loader.getText();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            identifyLanguage();

            webviewController = controller.getWebviewController();
            readIndex = loader.getReadIndex();

            textMerging();
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

                    if(readIndex < text.size()){
                        saver.saveReadIndex(readIndex);
                        speakSentences(text);
                    }

                    if (readIndex == text.size()){
                        ((Activity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                webviewController.nextArc();
                                saver.saveReadIndex(0);
                                readIndex = 0;
                            }
                        });
                    }
                }

                @Override
                public void onError(String utteranceId) {
                    Log.e("UtteranceError", " " + utteranceId);
                }
            });
        }
    }

    public boolean isSpeaking() {
            return tts.isSpeaking();
    }

    //Speak the array of sentences.
    public void speakSentences(ArrayList<String> textToRead){
        if (audioManager.requestAudioFocus()) {
            tts.speak(textToRead.get(readIndex), TextToSpeech.QUEUE_ADD, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }

    public void nextSentence() {
        audioManager.removeAudioFocus();
        if (readIndex != text.size()) {
            readIndex++;
        }
        if ((tts.isSpeaking()) && tts != null) {
            tts.stop();
            speakSentences(text);
            saver.saveReadIndex(readIndex);
        }
    }

    public void previousSentence() {
        audioManager.removeAudioFocus();
        if (readIndex != 0) {
            readIndex--;
        }
        if ((tts.isSpeaking()) && tts != null) {
            tts.stop();
            speakSentences(text);
        }
    }

    public void speakTitle() {
        articleData = loader.getLastArc();
        if (audioManager.requestAudioFocus() && articleData.getAuthor() != null || !Objects.equals(articleData.getAuthor(), "")) {
            tts.speak(articleData.getTitle() + "by " + articleData.getAuthor(), TextToSpeech.QUEUE_FLUSH, null, TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED);
        }
    }

    public void textMerging(){
        articleData = loader.getLastArc();
        String a = articleData.getTitle() + " by " + articleData.getAuthor();
        String b = articleData.getTitle();
        if (!articleData.getAuthor().equals("-")) {
            text.add(0, a);
        }
        else {
            text.add(0, b);
        }
    }


    public void stopPlay() {
        if (tts != null && tts.isSpeaking()) {
            tts.stop();
            saver.saveReadIndex(readIndex);
        }
    }

    public void onStop() {
        if (tts != null){
            tts.stop();
        }
    }

    public void play() {
        if (tts != null && !tts.isSpeaking()) {
            speakSentences(text);
        }
    }


    public void identifyLanguage() {
        if (newsType.contains("English")) {
            tts.setLanguage(Locale.ENGLISH);
        } else if (newsType.contains("Bahasa Malaysia")) {
            Locale localBM = new Locale("id", "ID");
            tts.setLanguage(localBM);
        } else if (newsType.contains("Chinese")) {
            tts.setLanguage(Locale.CHINESE);
        } else {
            String langIDText = text.get(0);

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
                            e -> {
                                // Model couldn’t be loaded or other internal error.
                                // ...
                            });
        }
    }

    public void destroy() {
        if (tts != null) {
            tts.stop();
            //tts.shutdown();
        }
    }

    public void setText(ArrayList<String>text){
        this.text = text;
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
    }

    public void playSilent(){
        tts.playSilentUtterance(750, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void shutdown(){
        tts.shutdown();
    }

    public int getTextSize(){
        return text.size();
    }

    public int getReadIndex(){
        return readIndex;
    }

    public void resumePlay() {
        readIndex = loader.getReadIndex();
        if (loader.getTSS()) {
            play();
        }
    }

    public void setReadIndex(int lastReadIndex) {
        audioManager.removeAudioFocus();
        readIndex = lastReadIndex;

        if ((tts.isSpeaking()) && tts != null) {
            tts.stop();
            speakSentences(text);
        }
    }

    public void updateText() {
        readIndex = 0;
        saver.saveReadIndex(0);
        audioManager.removeAudioFocus();
        text = loader.getText();
        textMerging();
    }
}
