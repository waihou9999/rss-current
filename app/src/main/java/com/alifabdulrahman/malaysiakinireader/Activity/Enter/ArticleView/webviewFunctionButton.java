package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.R;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import java.util.ArrayList;

public class webviewFunctionButton extends FunctionButton implements View.OnClickListener{
    private ImageButton sharebutton, rescrapebutton, nextArc, prevArc;
    private webviewController webviewController;
    private ttsController ttsController;

    public webviewFunctionButton(Activity activity, webviewController webviewController) throws InterruptedException {
        super(activity);
        nextArc = activity.findViewById(R.id.nxtarcbtn);
        prevArc = activity.findViewById(R.id.prevarcbtn);
        sharebutton = activity.findViewById(R.id.sharebutton);
        rescrapebutton = activity.findViewById(R.id.rescrapebutton);

        prevArc.setOnClickListener((View.OnClickListener) this);
        nextArc.setOnClickListener((View.OnClickListener) this);
        sharebutton.setOnClickListener((View.OnClickListener) this);

        prevArc.setEnabled(true);
        nextArc.setEnabled(true);
        sharebutton.setEnabled(true);

        this.webviewController = webviewController;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sharebutton:
                webviewController.share();
                break;

            case R.id.rescrapebutton:
                break;

            case R.id.prevarcbtn:
                webviewController.prevArc();

                break;

            case R.id.nxtarcbtn:
                webviewController.nextArc();
                break;
        }
    }

    public void setFirstLoad(){
        webviewController.setFirstLoad();
    }
}
