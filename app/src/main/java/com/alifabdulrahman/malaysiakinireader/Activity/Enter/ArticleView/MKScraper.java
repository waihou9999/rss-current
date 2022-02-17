package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper {
    private Activity activity;
    private Context ctx;
    private WebView webView;
    private boolean loading;

    public MKScraper(Activity activity, Context ctx, WebView webView, FunctionButton fb) {
        this.activity = activity;
        this.ctx = ctx;
        this.webView = webView;

        this.webView.addJavascriptInterface(new GetHTML(activity, ctx, fb), "Scrap");
    }

    public void scrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }

    public class GetHTML{
        private Context ctx;
        private Activity activity;
        private FunctionButton fb;
        private loader loader;
        private saver saver;
        private ttsController ttsController;
        boolean firstLoad = true;

        public GetHTML(Activity activity, Context ctx, FunctionButton fb) {
            this.activity = activity;
            this.ctx = ctx;
            this.fb = fb;
            loader = new loader(activity, ctx);
            saver = new saver(this.activity, this.ctx);
        }

        @JavascriptInterface
        public void getHTML(String html) {

            Document doc = Jsoup.parse(html);

            ArrayList<String> tempList = new ArrayList<>();

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

            checkCompleted(tempList);

            ttsController = fb.getTTSController();

            if (loading) {
                    fb.ttsUnclickable();
                    System.out.println("scraping");
                    if (firstLoad) {
                        Toast.makeText(ctx, "Getting contents. Please wait...", Toast.LENGTH_SHORT).show();
                        firstLoad = false;
                    }
                    else {
                        Toast.makeText(ctx, "Please sign in to get full content", Toast.LENGTH_SHORT).show();
                    }
                    saver.clearText();
            }

            if (!loading) {
                System.out.println("done scraping");
                Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
                saver.saveText(tempList);
                ttsController.setText(tempList);
                ttsController.checkPlay();
                fb.ttsclickable();
            }
        }
    }

    public void checkCompleted(ArrayList<String> tempList){
      String lastString = (tempList.get(tempList.size()-1));
      System.out.println("scraping" + lastString);
      loading = (lastString.contains("..."));
    }
}

