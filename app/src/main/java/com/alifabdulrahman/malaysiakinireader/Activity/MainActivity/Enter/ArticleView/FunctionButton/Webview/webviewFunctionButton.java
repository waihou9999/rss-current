package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.Webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.R;

public class webviewFunctionButton extends FunctionButton implements View.OnClickListener{
    private ImageButton sharebutton, nextArc, prevArc;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller Controller;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController.webviewController webviewController;
    private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController ttsController;

    public webviewFunctionButton(Activity activity, Controller Controller) throws InterruptedException {
        super(activity);
        nextArc = activity.findViewById(R.id.nxtarcbtn);
        prevArc = activity.findViewById(R.id.prevarcbtn);
        sharebutton = activity.findViewById(R.id.sharebutton);

        prevArc.setOnClickListener((View.OnClickListener) this);
        nextArc.setOnClickListener((View.OnClickListener) this);
        sharebutton.setOnClickListener((View.OnClickListener) this);

        prevArc.setEnabled(true);
        nextArc.setEnabled(true);
        sharebutton.setEnabled(true);

        this.Controller = Controller;
        this.webviewController = Controller.getWebviewController();
        this.ttsController = Controller.getTtsController();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sharebutton:
                webviewController.share();
                break;

            case R.id.rereadbutton:
                ttsController.resetReadIndex();
                break;

            case R.id.prevarcbtn:
                ttsController.stopPlay();
                webviewController.prevArc();

                break;

            case R.id.nxtarcbtn:
                ttsController.stopPlay();
                webviewController.nextArc();
                break;
        }
    }

    public void setFirstLoad(){
        webviewController.setFirstLoad();
    }
}
