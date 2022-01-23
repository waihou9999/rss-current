package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsSectionStorage.MKSectionStorage;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper{
    private Activity activity;
    private Context ctx;
    private WebView webView;
    private TTS tts;

    public MKScraper(Activity activity, Context ctx, WebView webView, TTS tts) {
        this.activity = activity;
        this.ctx = ctx;
        this.webView = webView;
        this.tts = tts;
        this.webView.addJavascriptInterface(new GetHTML(activity, ctx, tts), "Scrap");
    }

    public void scrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }

    public class GetHTML {
        private Context ctx;
        private Activity activity;
        private TTS tts;
        private saver saver;

        public GetHTML(Activity activity, Context ctx, TTS tts) {
            this.activity = activity;
            this.ctx = ctx;
            this.tts = tts;
            saver = new saver(this.activity, this.ctx);
        }

        @JavascriptInterface
        public void getHTML(String html) {

            Document doc = Jsoup.parse(html);

            ArrayList<String>tempList = new ArrayList<>();

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

            saver.saveText(tempList);

            tts.setText(tempList);
        }
    }
}

