package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

public class Controller {
    protected Activity activity;
    protected Context context;
    protected loader loader;
    protected saver saver;

    public Controller(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        loader = new loader(activity, context);
        saver = new saver(activity, context);
    }

    public Controller(){

    }
}
