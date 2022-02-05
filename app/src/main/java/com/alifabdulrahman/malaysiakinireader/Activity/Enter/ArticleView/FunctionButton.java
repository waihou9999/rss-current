package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;
import com.example.myappname.TinyDB;

import java.util.ArrayList;

public class FunctionButton implements View.OnClickListener{
    private ImageButton nextArc, prevArc, nextSent, prevSent, stopBtn, sharebutton, rescrapebutton;
    private Activity activity;
    int playImg = android.R.drawable.ic_media_play;
    int pauseImg = android.R.drawable.ic_media_pause;
    private controller controller;
    private loader loader;
    private saver saver;

    public FunctionButton(Activity activity, Context context, Webview wb, TTS tts) throws InterruptedException {
        this.activity = activity;

        stopBtn = this.activity.findViewById(R.id.stopbtn);
        stopBtn.setClickable(false);
        nextArc = this.activity.findViewById(R.id.nxtarcbtn);
        prevArc = this.activity.findViewById(R.id.prevarcbtn);
        nextSent = this.activity.findViewById(R.id.forwbtn);
        prevSent = this.activity.findViewById(R.id.prevbtn);
        sharebutton = this.activity.findViewById(R.id.sharebutton);
        rescrapebutton = this.activity.findViewById(R.id.rescrapebutton);

        nextArc.setOnClickListener((View.OnClickListener) this);
        stopBtn.setOnClickListener((View.OnClickListener) this);
        prevArc.setOnClickListener((View.OnClickListener) this);
        nextSent.setOnClickListener((View.OnClickListener) this);
        prevSent.setOnClickListener((View.OnClickListener) this);
        sharebutton.setOnClickListener((View.OnClickListener) this);
        rescrapebutton.setOnClickListener((View.OnClickListener) this);

        this.loader = new loader(activity, context);
        this.saver = new saver(activity, context);
        controller = new controller(activity, context, wb, tts);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stopbtn:

                    if (loader.getTSS()) {
                        stopBtn.setImageResource(pauseImg);
                        saver.setTSS(false);
                    }
                    else {
                        stopBtn.setImageResource(playImg);
                        saver.setTSS(true);
                    }

                    controller.stopBtn();

                    break;

                case R.id.nxtarcbtn:
                    controller.nextArc();
                    break;

                case R.id.prevarcbtn:
                    controller.prevArc();
                    break;

                case R.id.forwbtn:
                    controller.nextSentence();
                    break;

                case R.id.prevbtn:
                    controller.previousSentence();
                    break;

                case R.id.sharebutton:
                    controller.share();
                    break;
        }
    }

    public void setClickable(boolean clickable){
        stopBtn.setClickable(clickable);
    }



    public void destroy() {
            controller.destroy();
    }
}





