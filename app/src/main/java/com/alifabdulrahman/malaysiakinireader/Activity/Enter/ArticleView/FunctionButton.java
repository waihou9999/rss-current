package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
    private ImageButton pauseBtn, nextArc, prevArc, nextSent, prevSent, stopBtn, sharebutton, rescrapebutton;
    private TTS tts;
    private Activity activity;
    private Context context;
    private currentArticle currentArticle;
    private boolean startTTS;
    private Webview webview;
    int starbigoff_ = android.R.drawable.ic_media_play;
    int starbigon_ = android.R.drawable.ic_media_pause;
    private NewsStorage newsStorage;
    private MKSectionStorage newsSectionStorage;
    private String newsType;
    private ArrayList<ArticleData> articleDatas;
    private String url;
    private int index;
    private ArrayList<String>text;
    private com.example.myappname.TinyDB tinyDB;

    public FunctionButton(Activity activity, Context context, Webview webview) throws InterruptedException {
        this.activity = activity;
        this.webview = webview;

        stopBtn = this.activity.findViewById(R.id.stopbtn);
        nextArc = this.activity.findViewById(R.id.nxtarcbtn);
        prevArc = this.activity.findViewById(R.id.prevarcbtn);
        nextSent = this.activity.findViewById(R.id.forwbtn);
        prevSent = this.activity.findViewById(R.id.prevbtn);
        sharebutton = this.activity.findViewById(R.id.sharebutton);
        rescrapebutton = this.activity.findViewById(R.id.rescrapebutton);

        this.context = context;
        currentArticle = new currentArticle(context);
        startTTS = currentArticle.startTSS();
        newsSectionStorage = new MKSectionStorage(context);
        newsType = newsSectionStorage.getNewsSectionType();
        newsStorage = new NewsStorage(context, newsType);
        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();
        startTTS = currentArticle.startTSS();
        index = currentArticle.loadIndex();


        tinyDB = new TinyDB(context);
        text = tinyDB.getListString("MyContent");

        tts = new TTS(context, articleDatas.get(index), text, startTTS);


        nextArc.setOnClickListener((View.OnClickListener) this);
        stopBtn.setOnClickListener((View.OnClickListener) this);
        prevArc.setOnClickListener((View.OnClickListener) this);
        nextSent.setOnClickListener((View.OnClickListener) this);
        prevSent.setOnClickListener((View.OnClickListener) this);
        sharebutton.setOnClickListener((View.OnClickListener) this);
        rescrapebutton.setOnClickListener((View.OnClickListener) this);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stopbtn:
                        if (tts.isSpeaking()) {
                            tts.stopPlay();
                            stopBtn.setImageResource(starbigoff_);
                            currentArticle.setTTS(false);
                            break;
                        }
                        if(!tts.isSpeaking() ) {
                            tts.play();
                            stopBtn.setImageResource(starbigon_);
                            currentArticle.setTTS(true);
                        }

                        if (!tts.isSpeaking() && tts.getDone()){
                            url = articleDatas.get(index+1).getLink();
                            try {
                                webview.reloadWebView();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }




                    break;

                case R.id.rescrapebutton:
                    try {
                        webview.reloadWebView();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                case R.id.nxtarcbtn:
                    url = articleDatas.get(index+1).getLink();
                    try {
                        webview.reloadWebView();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.prevarcbtn:

                    break;

                case R.id.forwbtn:
                    tts.nextSentence();
                    break;

                case R.id.prevbtn:
                    tts.previousSentence();
                    break;

                case R.id.sharebutton:
                    Intent myIntent = new Intent(Intent.ACTION_SEND);
                    myIntent.setType("text/plain");
                    String shareBody = articleDatas.get(index).getTitle() + ": " + url;
                    myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                    myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    activity.startActivity(Intent.createChooser(myIntent, "Share using"));
                    break;
        }
    }

    public void destroy() {
            tts.destroy();
    }
}





