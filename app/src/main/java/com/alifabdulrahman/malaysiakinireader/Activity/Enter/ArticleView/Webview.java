package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleList.MalaysiaKini.MKListingActivity;
import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;
import com.example.myappname.TinyDB;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Webview {
    private WebView mWebView;
    private FunctionButton fb;
    private int timex = 1000;
    private Activity activity;
    private Context context;
    private SwipeRefreshLayout pullToRefresh;
    private String url;
    private TTS tts;
    private loader loader;
    private MKScraper mkScraper;
    private ArticleData article;


    public Webview(Activity activity, Context context) throws InterruptedException {
        mWebView = activity.findViewById(R.id.webview);
        this.activity = activity;
        this.context = context;
        this.loader = new loader(activity, context);
        this.article = loader.getLastArc();
        this.tts = new TTS(activity, context, article);
        this.fb = new FunctionButton(activity, context, this, tts);
        this.url = loader.getUrl();
        mWebView.getSettings().setJavaScriptEnabled(true);
        mkScraper = new MKScraper(activity, context, mWebView, fb, tts);

        loadWebView(url);

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    reloadWebView();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void loadWebView(String url){
        mkScraper.scrap();
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url){
                return false;
            }

            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                try {
                    Thread.sleep(timex);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                mkScraper.scrap();
            }
        });
        mWebView.loadUrl(url);
    }


    public void reloadWebView() throws InterruptedException {
        mWebView.loadUrl(url);

        try {
            Thread.sleep(timex);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        mkScraper.scrap();
    }
}

