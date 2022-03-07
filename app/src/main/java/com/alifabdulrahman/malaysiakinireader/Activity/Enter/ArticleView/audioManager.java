package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;

public class audioManager implements AudioManager.OnAudioFocusChangeListener {
    AudioManager am;
    Activity activity;
    Context context;
    TTS tts;


    public audioManager(Activity activity, Context context, TTS tts) {
        this.activity = activity;
        this.context = context;
        this.tts = tts;

        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int result = am.requestAudioFocus(this,// Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback.
            tts.play();
        }
    }

    public boolean requestAudioFocus() {
        int result = am.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        return result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
    }

    public void removeAudioFocus() {
        try{
            if(am != null){
                am.abandonAudioFocus(this);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case (AudioManager.AUDIOFOCUS_LOSS):
                am.abandonAudioFocus(this);
                tts.onStop();
                break;

            case (AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK):
                break;

            case (AudioManager.AUDIOFOCUS_LOSS_TRANSIENT):
                tts.stopPlay();
                break;
            case (AudioManager.AUDIOFOCUS_GAIN):
                tts.resumePlay();
                break;
        };

    }


}
