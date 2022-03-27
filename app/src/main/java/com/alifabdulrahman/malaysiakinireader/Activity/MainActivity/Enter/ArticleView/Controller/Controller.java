package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;

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
