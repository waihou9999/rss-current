package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.webviewController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Webview.webview;
import com.alifabdulrahman.malaysiakinireader.Model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.Storage.Substorage.newsStorage;

import java.util.ArrayList;

public class webviewController extends Controller {
    private Activity activity;
    private ArrayList<ArticleData> articleDatas;
    private int index;
    private webview wb;

    public webviewController(Activity activity, Context context, FunctionButton fb, Controller controller){
        super(activity, context);
        this.activity = activity;
        this.articleDatas = loader.getArticleDatas();
        this.index = loader.getIndex();
        try {
            wb = new webview(activity, context, loader, saver, fb, controller);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void prevArc() {
        index = loader.getIndex() - 1;
        if (index < 0){
            Toast.makeText(context, "This is the first article", Toast.LENGTH_SHORT).show();
        }
        else {
            saver.saveIndex(index);
            loadArticle(index);
            saveReadNews(index);
        }
    }

    public void nextArc() {
        index = loader.getIndex() + 1;

        if((index) > articleDatas.size()){
            Toast.makeText(context, "This is the last article", Toast.LENGTH_SHORT).show();
        }
        else {
            saver.saveIndex(index);
            loadArticle(index);
            saveReadNews(index);
        }
    }

    public void share(){
        index = loader.getIndex();
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = articleDatas.get(index).getTitle() + ": " + articleDatas.get(index).getLink();
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        activity.startActivity(Intent.createChooser(myIntent, "Share using"));
    }

    public void setFirstLoad() {
        wb.setFirstLoad();
    }

    public void loadArticle(int index){
        ArticleData a  = loader.getArticleDatas().get(index);
        String url = a.getLink();
        saver.saveArc(a);
        saver.saveIndex(index);
        wb.loadWebView(url);
        saver.setURL(url);
        wb.setFirstLoad();
    }

    public void saveReadNews(int index) {
        String newsType = loader.getNewsType();
        newsStorage newsStorage = new newsStorage(context, newsType);
        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();
        articleDatas.get(index).setReadNews(true);
        newsStorage.saveData(articleDatas);
    }
}
