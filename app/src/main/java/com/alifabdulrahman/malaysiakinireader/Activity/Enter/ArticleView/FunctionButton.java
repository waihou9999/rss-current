package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.alifabdulrahman.malaysiakinireader.R;

public class FunctionButton implements View.OnClickListener{
    private ImageButton nextArc, prevArc, nextSent, prevSent, stopBtn, sharebutton, rescrapebutton;
    private Activity activity;
    int playImg = android.R.drawable.ic_media_play;
    int pauseImg = android.R.drawable.ic_media_pause;
    private webviewController webviewController;
    private ttsController ttsController;
    private loader loader;
    private saver saver;
    private boolean startTSS;

    public FunctionButton(Activity activity, Context context) throws InterruptedException {
        this.activity = activity;
        stopBtn = this.activity.findViewById(R.id.stopbtn);
        nextArc = this.activity.findViewById(R.id.nxtarcbtn);
        prevArc = this.activity.findViewById(R.id.prevarcbtn);
        nextSent = this.activity.findViewById(R.id.forwbtn);
        prevSent = this.activity.findViewById(R.id.prevbtn);
        sharebutton = this.activity.findViewById(R.id.sharebutton);
        rescrapebutton = this.activity.findViewById(R.id.rescrapebutton);

        prevArc.setEnabled(false);
        nextArc.setEnabled(false);
        sharebutton.setEnabled(false);
        stopBtn.setEnabled(false);
        prevSent.setEnabled(false);
        nextSent.setEnabled(false);

        this.loader = new loader(activity, context);
        this.saver = new saver(activity, context);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stopbtn:

                    startTSS = loader.getTSS();

                    if (startTSS) {
                        stopBtn.setImageResource(pauseImg);
                        saver.setTSS(false);
                        ttsController.stopPlay();
                    }
                    else{
                        stopBtn.setImageResource(playImg);
                        saver.setTSS(true);
                        ttsController.play();
                    }
                    break;

                case R.id.nxtarcbtn:
                    webviewController.nextArc();
                    break;

                case R.id.prevarcbtn:
                    webviewController.prevArc();
                    break;

                case R.id.forwbtn:
                    ttsController.nextSentence();
                    break;

                case R.id.prevbtn:
                    ttsController.previousSentence();
                    break;

                case R.id.sharebutton:
                    webviewController.share();
                    break;
        }
    }

    public void setClickable(boolean clickable){
        stopBtn.setClickable(clickable);
    }

    public void setWebController(webviewController webviewController) {
        this.webviewController = webviewController;

        prevArc.setOnClickListener((View.OnClickListener) this);
        nextArc.setOnClickListener((View.OnClickListener) this);
        sharebutton.setOnClickListener((View.OnClickListener) this);

        prevArc.setEnabled(true);
        nextArc.setEnabled(true);
        sharebutton.setEnabled(true);
    }

    public void setTTSController(ttsController ttsController){
        this.ttsController = ttsController;

        stopBtn.setOnClickListener((View.OnClickListener) this);
        prevSent.setOnClickListener((View.OnClickListener) this);
        nextSent.setOnClickListener((View.OnClickListener) this);

        stopBtn.setEnabled(true);
        prevSent.setEnabled(true);
        nextSent.setEnabled(true);
    }

    public void ttsUnclickable() {
        stopBtn.setClickable(false);
        stopBtn.setEnabled(false);
        prevSent.setEnabled(false);
        nextSent.setEnabled(false);
    }

    public void ttsclickable() {
        stopBtn.setClickable(true);
        stopBtn.setEnabled(true);
        prevSent.setEnabled(true);
        nextSent.setEnabled(true);
    }

    public ttsController getTTSController(){
        return ttsController;
    }
}





