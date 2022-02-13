package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.Locale;

public class test implements TextToSpeech.OnInitListener {
    private TextToSpeech tts;
    private Context context;
    private ImageButton buttonSpeak;

    public test(Activity activity, Context context, ImageButton stopBtn){
        this.context = context;
        buttonSpeak = stopBtn;
        tts = new TextToSpeech(context, this::onInit);
    }

    @Override
    public void onInit(int status) {
        System.out.println("hello" + status);
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);
            speakOut();

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                buttonSpeak.setEnabled(true);
                Log.e("TTS", "Successfully initialize");
            }

        } else { Log.e("TTS", "Initilization Failed!");}
    }

    private void speakOut() {
        Toast.makeText(context, "cibai",Toast.LENGTH_SHORT).show();
        System.out.println("hello");
        tts.speak("hello", TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void destroy() {
            if (tts != null) {
                tts.stop();
                tts.shutdown();
            }
        }
}

