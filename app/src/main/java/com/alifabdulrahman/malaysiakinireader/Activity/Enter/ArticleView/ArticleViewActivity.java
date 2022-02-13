package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.R;

public class ArticleViewActivity extends AppCompatActivity {
    private loader loader;
    private saver saver;
    private webview wb;
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
        this.article = loader.getLastArc();
        this.tts = new TTS(ArticleViewActivity.this, this, article);

        stopBtn = findViewById(R.id.rescrapebutton);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test = new test(ArticleViewActivity.this, getApplicationContext(), stopBtn);
                System.out.println("1");
            }
        });


        this.ttsController = new ttsController(this.tts);

        try {
            this.fb = new FunctionButton(ArticleViewActivity.this, this, webviewController, ttsController);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            wb = new webview(ArticleViewActivity.this, this, fb);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.webviewController = new webviewController(ArticleViewActivity.this,this, wb);
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
