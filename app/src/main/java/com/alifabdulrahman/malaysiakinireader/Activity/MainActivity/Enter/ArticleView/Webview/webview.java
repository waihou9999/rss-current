package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper.MKScraper;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper.NYTScraper;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;
import com.alifabdulrahman.malaysiakinireader.R;

public class webview {
    private WebView mWebView;
    private int timex = 1000;
    private SwipeRefreshLayout pullToRefresh;
    private String url;
    private MKScraper mkScraper;
    private NYTScraper nytScraper;
    private FunctionButton functionButton;
    private ttsController ttsController;
    private loader loader;

    @SuppressLint("SetJavaScriptEnabled")
    public webview(Activity activity, Context context, loader loader, saver saver, FunctionButton functionButton, Controller Controller) throws InterruptedException {
        mWebView = activity.findViewById(R.id.webview);
        this.url = loader.getUrl();
        mWebView.getSettings().setJavaScriptEnabled(true);
        this.functionButton = functionButton;
        this.ttsController = Controller.getTtsController();
        this.loader = loader;

        mkScraper = new MKScraper(activity, context, mWebView, functionButton, Controller);
        nytScraper = new NYTScraper(activity, context, mWebView, functionButton, Controller);

        loadWebView(url);

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int readIndex;
                url = loader.getUrl();
                if (ttsController.getTTS() == null){
                    readIndex = 0;
                }else {
                    readIndex = ttsController.getTTS().getReadIndex();
                }
                saver.saveReadIndex(readIndex);
                ttsController.pausing();
                loadWebView(url);
                readIndex = loader.getReadIndex();
                ttsController.setReadIndex(readIndex);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    public void loadWebView(String url){
        ttsFunctionButton ttsFunctionButton = functionButton.getTtsFunctionButton();
        ttsFunctionButton.disabled();

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

                if (mWebView.getProgress() == 100) {
                    if (loader.getSource() == 0) {
                        mkScraper.scrap();
                    }
                    else if (loader.getSource() == 1){
                        nytScraper.scrap();
                    }
                }
            }
        });
        mWebView.loadUrl(url);
    }

    public void setFirstLoad() {
        //mkScraper.setFirstLoad();
    }
}