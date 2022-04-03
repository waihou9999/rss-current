package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper;

import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper.NYT.NYTScraper;

public class Scraper {
    protected Activity activity;
    protected Context ctx;
    protected WebView webView;
    protected FunctionButton FunctionButton;
    protected Controller Controller;
    protected boolean firstLoad = true;

    public Scraper(Activity activity, Context ctx, WebView webView, FunctionButton FunctionButton, Controller Controller) {
        this.activity = activity;
        this.ctx = ctx;
        this.webView = webView;
        this.FunctionButton = FunctionButton;
        this.Controller = Controller;
    }

    public void scrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }
}
