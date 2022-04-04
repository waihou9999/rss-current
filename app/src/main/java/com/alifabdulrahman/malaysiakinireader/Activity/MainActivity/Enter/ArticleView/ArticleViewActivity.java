package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.Webview.webviewFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;
import com.alifabdulrahman.malaysiakinireader.R;

public class ArticleViewActivity extends AppCompatActivity {
    private loader loader;
    private saver saver;
    private webviewController webviewController;
    private webviewFunctionButton webviewFunctionButton;
    private ttsController ttsController;
    private ttsFunctionButton ttsFunctionButton;
    private FunctionButton fb;
    private Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        saver = new saver(ArticleViewActivity.this, this);
        loader = new loader(ArticleViewActivity.this, this);
        fb = new FunctionButton(ArticleViewActivity.this, this);
        controller = new Controller(ArticleViewActivity.this, this);

        ttsController = new ttsController(ArticleViewActivity.this, this, controller);
        controller.setTtsController(ttsController);
        ttsFunctionButton = new ttsFunctionButton(ArticleViewActivity.this, this, loader, saver);
        ttsFunctionButton.disabled();
        fb.setTtsFunctionButton(ttsFunctionButton);

        webviewController = new webviewController(ArticleViewActivity.this, this, fb, controller);
        controller.setWebviewController(webviewController);

        try {
            webviewFunctionButton = new webviewFunctionButton(ArticleViewActivity.this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fb.setWebviewFunctionButton(webviewFunctionButton);
        fb.setController(controller);
    }

    @Override
    protected void onDestroy() {
        ttsController.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume(){
        ttsController.onResume();
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
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
