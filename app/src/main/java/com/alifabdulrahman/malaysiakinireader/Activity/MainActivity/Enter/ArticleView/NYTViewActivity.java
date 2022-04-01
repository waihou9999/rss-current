package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.Webview.webviewFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;
import com.alifabdulrahman.malaysiakinireader.R;

public class NYTViewActivity extends AppCompatActivity {
    private com.alifabdulrahman.malaysiakinireader.Helper.loader loader;
    private com.alifabdulrahman.malaysiakinireader.Helper.saver saver;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController webviewController;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.Webview.webviewFunctionButton webviewFunctionButton;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController ttsController;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton ttsFunctionButton;
    private FunctionButton fb;
    private Controller controller;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);

        saver = new saver(NYTViewActivity.this, this);
        loader = new loader(NYTViewActivity.this, this);
        fb = new FunctionButton(NYTViewActivity.this, this);
        controller = new Controller(NYTViewActivity.this, this);

        ttsController = new ttsController(NYTViewActivity.this, this, controller);
        controller.setTtsController(ttsController);
        ttsFunctionButton = new ttsFunctionButton(NYTViewActivity.this, this, controller, loader, saver);
        ttsFunctionButton.disabled();
        fb.setTtsFunctionButton(ttsFunctionButton);

        webviewController = new webviewController(NYTViewActivity.this, this, fb, controller);
        controller.setWebviewController(webviewController);

        try {
            webviewFunctionButton = new webviewFunctionButton(NYTViewActivity.this, controller);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fb.setWebviewFunctionButton(webviewFunctionButton);
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
