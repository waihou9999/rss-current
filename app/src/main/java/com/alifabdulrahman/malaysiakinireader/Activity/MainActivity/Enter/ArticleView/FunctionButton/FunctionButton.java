package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.Webview.webviewFunctionButton;
import com.alifabdulrahman.malaysiakinireader.R;

public class FunctionButton{
    protected Activity activity;
    protected Context context;
    protected com.alifabdulrahman.malaysiakinireader.Helper.loader loader;
    protected com.alifabdulrahman.malaysiakinireader.Helper.saver saver;
    private ttsFunctionButton ttsFunctionButton;
    private webviewFunctionButton webviewFunctionButton;

    public FunctionButton(Activity activity) {
        this.activity = activity;
    }

    public FunctionButton(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.ttsFunctionButton = null;
        this.webviewFunctionButton = null;
        activity.setContentView(R.layout.activity_news_view);
    }

    public void setTtsFunctionButton(ttsFunctionButton ttsFunctionButton){
        this.ttsFunctionButton = ttsFunctionButton;
    }

    public void setWebviewFunctionButton(webviewFunctionButton webviewFunctionButton) {
        this.webviewFunctionButton = webviewFunctionButton;
    }

    public ttsFunctionButton getTtsFunctionButton(){
        return ttsFunctionButton;
    }

    public webviewFunctionButton getWebviewFunctionButton(){
        return webviewFunctionButton;
    }
}





