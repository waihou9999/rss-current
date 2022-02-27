package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import java.util.ArrayList;

public class webviewController extends Controller{
    private Activity activity;
    private ArrayList<ArticleData> articleDatas;
    private int index;
    private String url;
    private webview wb;

    public webviewController(Activity activity, Context context, FunctionButton fb, Controller controller){
        super(activity, context);
        this.activity = activity;
        this.articleDatas = loader.getArticleDatas();
        this.index = loader.getIndex();
        this.url = loader.getUrl();
        try {
            wb = new webview(activity, context, loader, fb, controller);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void prevArc() {
        index = loader.getIndex();
        if (index < 1){
            Toast.makeText(context, "This is the first article", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = loader.getArticleDatas().get(index - 1).getLink();
            saver.saveArc(loader.getArticleDatas().get(index - 1));
            saver.saveIndex(loader.getIndex() - 1);
            wb.loadWebView(url);
            wb.setFirstLoad();
            saver.setURL(url);
        }
    }

    public void nextArc() {
        index = loader.getIndex();

        if((index + 1) > articleDatas.size()-1){
            Toast.makeText(context, "This is the last article", Toast.LENGTH_SHORT).show();
        }
        else {
            String url = loader.getArticleDatas().get(index + 1).getLink();
            saver.saveArc(loader.getArticleDatas().get(index + 1));
            saver.saveIndex(loader.getIndex() + 1);

            wb.loadWebView(url);
            saver.setURL(url);
            wb.setFirstLoad();
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

    public void setFirstLoad() {
        wb.setFirstLoad();
    }
}
