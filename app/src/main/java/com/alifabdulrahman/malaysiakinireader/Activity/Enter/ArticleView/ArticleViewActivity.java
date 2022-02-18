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
    private ttsController ttsController;
    private ttsFunctionButton ttsFunctionButton;
    private FunctionButton fb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        saver = new saver(ArticleViewActivity.this, this);
        loader = new loader(ArticleViewActivity.this, this);

        ttsController = new ttsController(this, loader, saver);
        ttsFunctionButton = new ttsFunctionButton(ArticleViewActivity.this, this, ttsController, loader);
        ttsFunctionButton.disabled();

        try {
            wb = new webview(ArticleViewActivity.this, this, loader, ttsFunctionButton, ttsController);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webviewController = new webviewController(ArticleViewActivity.this, this, wb, ttsController);
        try {
            webviewFunctionButton = new webviewFunctionButton(ArticleViewActivity.this, webviewController);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        fb = new FunctionButton(ArticleViewActivity.this, this, ttsFunctionButton, webviewFunctionButton);

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
