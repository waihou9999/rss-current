package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.TTS;

import android.content.Context;
import android.media.AudioManager;

import com.alifabdulrahman.malaysiakinireader.Helper.loader;

public class audioManager implements AudioManager.OnAudioFocusChangeListener {
    private AudioManager am;
    private TTS tts;
    private loader loader;

    public audioManager(Context context, TTS tts, loader loader) {
        this.tts = tts;
        this.loader = loader;

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
                if (loader.getTSS()) {
                    tts.resumePlay();
                }
                break;
        };
    }
}
