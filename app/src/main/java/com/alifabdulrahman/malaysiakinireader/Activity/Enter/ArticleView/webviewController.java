package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import java.util.ArrayList;

public class webviewController {
    private Activity activity;
    private Context context;
    private webview wb;
    private loader loader;
    private saver saver;
    private ArrayList<ArticleData> articleDatas;
    private int index;
    private String url;

    public webviewController(Activity activity, Context context, webview wb){
        this.activity = activity;
        this.context = context;
        this.wb = wb;
        this.loader = new loader(activity, context);
        this.saver = new saver(activity, context);
        this.articleDatas = loader.getArticleDatas();
        this.index = loader.getIndex();
        this.url = loader.getUrl();
    }


    public void prevArc() {
        loader.getIndex();
        if (index < 1){
            Toast.makeText(context, "This is the first article", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = loader.getArticleDatas().get(index - 1).getLink();
            wb.loadWebView(url);
            saver.setURL(url);
        }
    }

    public void nextArc() {
        loader.getIndex();
        if((index + 1) > articleDatas.size()-1){
            Toast.makeText(context, "This is the last article", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = loader.getArticleDatas().get(index + 1).getLink();
            wb.loadWebView(url);
            saver.setURL(url);
        }
    }

    public void share(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = articleDatas.get(index).getTitle() + ": " + url;
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(myIntent, "Share using"));
    }
}