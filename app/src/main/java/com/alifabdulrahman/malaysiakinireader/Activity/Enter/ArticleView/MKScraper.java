package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper {
    private Context ctx;
    private ArrayList<ArticleData> articleDatas;
    private WebView webView;
    private NewsStorage newsStorage;
    private MKSectionStorage newsSectionStorage;
    private String newsType;

    //Get the HTML loaded from webview and scrap it to get the article contents

    public MKScraper(Context ctx, ArrayList<ArticleData> articleDatas, WebView webView, NewsStorage newsStorage, MKSectionStorage newsSectionStorage, String newsType) {
        this.ctx = ctx;
        this.articleDatas = articleDatas;
        this.webView = webView;
        this.newsStorage = newsStorage;
        this.newsSectionStorage = newsSectionStorage;
        this.newsType = newsType;
    }




    public void scrap() {
        webView.addJavascriptInterface(new GetHTML(ctx), "Scrap");
    }

    public void rescrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");

    }

    public static class GetHTML {
        private Context ctx;
        private int index;
        private com.alifabdulrahman.malaysiakinireader.storage.substorage.currentArticle currentArticle;
        private ArrayList<ArticleData> articleDatas;
        private MKSectionStorage newsSectionStorage;
        private NewsStorage newsStorage;
        private String newsType;

        public GetHTML(Context ctx) {
            this.ctx = ctx;
            currentArticle = new currentArticle(ctx);
            index = currentArticle.loadIndex();
        }

        @JavascriptInterface
        public ArrayList<String> getHTML(String html) {
            //System.out.println("GETHTMLWORKSSS");
            //third load
            //System.out.println("at gethtml");
            //Basically the same thing as in ArticleListingActivity GetContents
            Document doc = Jsoup.parse(html);

            ArrayList<String> tempList = new ArrayList<>();
            tempList.clear();

            if (articleDatas.get(index).getAuthor().equals("-") || articleDatas.get(index).getAuthor().equals("Bernama") || articleDatas.get(index).getAuthor().equals("Reuters")) {
                tempList.add(articleDatas.get(index).getTitle());
            } else {
                tempList.add(articleDatas.get(index).getTitle() + ". By " + articleDatas.get(index).getAuthor());
            }

            //Elements contentContainer = doc.select("div[class$=content-container]");
            //Elements contentContainer = doc.select("script[id$=__NEXT_DATA__]");
            //Elements classContents =  contentContainer.select("div[class$=content]");
            Elements classContents = doc.select("div[id $= full-content-container]");

            Elements contents = classContents.select("p, li");


            if (classContents == null || classContents.isEmpty()) {
                classContents = doc.select("div[id $= __next]");
                contents = classContents.select("p, li");
            }


            for (Element content : contents) {

                if (!(content.text().equals(""))) {

                    if (!tempList.contains(content.text()))
                        tempList.add(content.text());
                }
            }

            articleDatas.get(index).getContent().clear();
            articleDatas.get(index).setContent(tempList);

            newsSectionStorage = new MKSectionStorage(ctx);
            newsType = newsSectionStorage.getNewsSectionType();
            newsStorage = new NewsStorage(ctx, newsType);

            newsStorage.saveData(articleDatas);

            return tempList;
        }
    }
}

