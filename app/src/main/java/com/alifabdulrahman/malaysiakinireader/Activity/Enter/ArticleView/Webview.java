package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;

import java.util.ArrayList;

public class Webview {
    private WebView mWebView;
    private int timex = 1000;
    private currentArticle currentArticle;
    private Activity activity;
    private Context context;
    private String url;
    private SwipeRefreshLayout pullToRefresh;
    private MKScraper MKScraper;
    private ArrayList<String> sentences;

    public Webview(Activity activity, Context context) {
        mWebView = activity.findViewById(R.id.webview);
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        url = currentArticle.loadData();

        mWebView.getSettings().setJavaScriptEnabled(true);
        loadWebView();

        pullToRefresh = activity.findViewById(R.id.pullToRefresh2);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWebView.loadUrl(url);
                pullToRefresh.setRefreshing(false);
            }
        });
    }

    @SuppressLint("JavascriptInterface")
    void loadWebView(){
        mWebView.addJavascriptInterface(new MKScraper.GetHTML(context), "Scrap");
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

                mWebView.loadUrl("javascript:window.Scrap.getHTML" +
                        "(document.getElementsByTagName('html')[0].outerHTML);");
                System.out.println("fk3");


                //String cookies = CookieManager.getInstance().getCookie(url);
                //System.out.println( "All the cookies in a string:" + cookies);
            }
        });

        //first load
        //System.out.println("beforewebload");
        mWebView.loadUrl(url);
        //System.out.println("afterwebload");
    }


    public void reloadWebView(String url) {

        try {
            Thread.sleep(timex);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        mWebView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }



}
