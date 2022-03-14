package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleList.MalaysiaKini.MKListingActivity;
import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;

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
        NewsStorage newsStorage = new NewsStorage(context, newsType);

        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();
        articleDatas.get(index).setReadNews(true);
        newsStorage.saveData(articleDatas);
    }
}
