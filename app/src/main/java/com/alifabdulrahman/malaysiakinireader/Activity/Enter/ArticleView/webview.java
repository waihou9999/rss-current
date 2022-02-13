package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.alifabdulrahman.malaysiakinireader.R;

public class webview {
    private WebView mWebView;
    private int timex = 1000;
    private Activity activity;
    private Context context;
    private SwipeRefreshLayout pullToRefresh;
    private String url;
    private loader loader;
    private MKScraper mkScraper;

    public webview(Activity activity, Context context, FunctionButton fb) throws InterruptedException {
        mWebView = activity.findViewById(R.id.webview);
        this.activity = activity;
        this.context = context;
        fb.ttsUnclickable();
        this.loader = new loader(activity, context);
        this.url = loader.getUrl();
        mWebView.getSettings().setJavaScriptEnabled(true);

        mkScraper = new MKScraper(activity, context, mWebView, fb);

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