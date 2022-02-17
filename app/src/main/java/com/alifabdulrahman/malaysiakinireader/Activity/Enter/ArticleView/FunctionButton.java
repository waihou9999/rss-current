package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FunctionButton{
    protected Activity activity;
    protected Context context;
    protected loader loader;
    protected saver saver;
    private ttsFunctionButton ttsFunctionButton;
    private webviewFunctionButton webviewFunctionButton;

    public FunctionButton(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
        this.loader = new loader(activity, context);
        this.saver = new saver(activity, context);
    }

    public FunctionButton(Activity activity) {
        this.activity = activity;
    }

    public void setTtsFunctionButton(ttsFunctionButton ttsFunctionButton){
        this.ttsFunctionButton = ttsFunctionButton;
    }

    public void setWebviewFunctionButton(webviewFunctionButton webviewFunctionButton) {
        this.webviewFunctionButton = webviewFunctionButton;
    }

    public FunctionButton() {

    }


}





