package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;

import java.util.ArrayList;

public class loader {
    private Activity activity;
    private Context context;
    private currentArticle currentArticle;
    private NewsStorage newsStorage;
    private MKSectionStorage newsSectionStorage;
    private ArrayList<ArticleData> articleDatas;
    private boolean startTTS;
    private String url;
    private int index;
    private ArrayList<String>text;
    private com.example.myappname.TinyDB tinyDB;

    public loader(Activity activity, Context context){
        this.activity = activity;
        this.context = context;
        currentArticle = new currentArticle(context);
        newsSectionStorage = new MKSectionStorage(context);

        startTTS = currentArticle.startTSS();
        index = currentArticle.loadIndex();
        url = currentArticle.loadLastArticle();

        String newsType = currentArticle.loadNewsType();

        newsStorage = new NewsStorage(context, newsType);
        newsStorage.loadData();
        articleDatas = newsStorage.loadArt1();

        tinyDB = new com.example.myappname.TinyDB(context);
        text = tinyDB.getListString("MyContent");
    }

    public ArrayList<ArticleData> getArticleDatas() {
        return articleDatas;
    }

    public int getIndex(){
        return index;
    }

    public boolean getTSS() {
        return startTTS;
    }

    public String getUrl(){
        return url;
    }

    public ArrayList<String>getText(){
        return text;
    }

    public ArticleData getArticleData(){
        return articleDatas.get(index);
    }
}
