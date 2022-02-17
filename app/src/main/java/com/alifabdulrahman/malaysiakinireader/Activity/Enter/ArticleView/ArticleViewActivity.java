package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.R;

import java.util.ArrayList;

public class ArticleViewActivity extends AppCompatActivity {
    private loader loader;
    private saver saver;
    private webview wb;
    private webviewController webviewController;
    private webviewFunctionButton webviewFunctionButton;
    private TTS tts;
    private ttsController ttsController;
    private ttsFunctionButton ttsFunctionButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        saver = new saver(ArticleViewActivity.this, this);
        loader = new loader(ArticleViewActivity.this, this);

        tts = new TTS(this, loader);
        ttsController = new ttsController(saver, tts);
        ttsFunctionButton = new ttsFunctionButton(ArticleViewActivity.this, ttsController, loader);

        try {
            wb = new webview(ArticleViewActivity.this, this, loader, ttsFunctionButton);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webviewController = new webviewController(ArticleViewActivity.this, this, wb);
        try {
            webviewFunctionButton = new webviewFunctionButton(ArticleViewActivity.this, webviewController);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        ttsController.onStop();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        saver.noLastArt();
        webviewController.setFirstLoad();
        ttsController.destroy();
        finish();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }
}
