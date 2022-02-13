package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.R;

public class ArticleViewActivity extends AppCompatActivity {
    private loader loader;
    private saver saver;
    private webview wb;
    private ArticleData articleData;
    private TTS tts;
    private webviewController webviewController;
    private ttsController ttsController;
    private FunctionButton fb;
    private ArticleData article;
    private ImageButton stopBtn;
    private test test;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        saver = new saver(ArticleViewActivity.this, this);
        loader = new loader(ArticleViewActivity.this, this);

        try {
            fb = new FunctionButton(ArticleViewActivity.this, this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (loader.getLastArc() != null){
            try {
                articleData = loader.getLastArc();
                wb = new webview(ArticleViewActivity.this, this);
                webviewController = new webviewController(ArticleViewActivity.this, this, wb);
                fb.setWebController(webviewController);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        while()

        if (loader.getTSS()){
            tts = new TTS(getApplicationContext(), articleData);
            ttsController = new ttsController(tts);
            fb.setTTSController(ttsController);
        }

        else{
            tts = new TTS(getApplicationContext(), articleData);
            ttsController = new ttsController(tts);
            ttsController.stopPlay();
            fb.setTTSController(ttsController);
        }



    }

    @Override
    protected void onDestroy() {
        ttsController.destroy();
        test.destroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        ttsController.onStop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        finish();
        saver.noLastArt();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
