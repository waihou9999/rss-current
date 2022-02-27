package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

public class Controller {
    protected Activity activity;
    protected Context context;
    protected loader loader;
    protected saver saver;
    private ttsController ttsController;
    private webviewController webviewController;

    public Controller(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        loader = new loader(activity, context);
        saver = new saver(activity, context);
    }

    public void setController(ttsController ttsController, webviewController webviewController) {
        this.ttsController = ttsController;
        this.webviewController = webviewController;
    }

    public void setTtsController(ttsController ttsController) {
        this.ttsController = ttsController;
    }

    public void setWebviewController(webviewController webviewController) {
        this.webviewController = webviewController;
    }

    public ttsController getTtsController(){
        return ttsController;
    }

    public webviewController getWebviewController(){
        return webviewController;
    }

}
