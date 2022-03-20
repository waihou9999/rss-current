package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alifabdulrahman.malaysiakinireader.R;

public class webview {
    private WebView mWebView;
    private int timex = 1000;
    private SwipeRefreshLayout pullToRefresh;
    private String url;
    private MKScraper mkScraper;
    private FunctionButton functionButton;
    private loader loader;

    public webview(Activity activity, Context context, loader loader, FunctionButton functionButton, Controller Controller) throws InterruptedException {
        mWebView = activity.findViewById(R.id.webview);
        this.loader = loader;
        this.url = loader.getUrl();
        mWebView.getSettings().setJavaScriptEnabled(true);
        this.functionButton = functionButton;

        mkScraper = new MKScraper(activity, context, mWebView, functionButton, Controller);

        loadWebView(url);

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                url = loader.getUrl();
                loadWebView(url);
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
                    mkScraper.scrap();
                }
            }
        });
        mWebView.loadUrl(url);
    }

    public void reloadWebView() throws InterruptedException {
        //url = loader.getUrl();
        mWebView.loadUrl(url);

        try {
            Thread.sleep(timex);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        mkScraper.scrap();
    }

    public void setFirstLoad() {
        mkScraper.setFirstLoad();
    }
}