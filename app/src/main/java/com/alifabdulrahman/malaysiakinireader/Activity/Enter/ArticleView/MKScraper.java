package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper{
    private Activity activity;
    private Context ctx;
    private WebView webView;
    private FunctionButton fb;
    private boolean loading;

    public MKScraper(Activity activity, Context ctx, WebView webView, FunctionButton fb) {
        this.activity = activity;
        this.ctx = ctx;
        this.webView = webView;
        this.fb = fb;

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
        private saver saver;

        public GetHTML(Activity activity, Context ctx, FunctionButton fb) {
            this.activity = activity;
            this.ctx = ctx;
            this.fb = fb;
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

                //if (!(content.text().equals(""))) {

                    if (!tempList.contains(content.text())) {
                        tempList.add(content.text());
                        System.out.println("fker" + content.text());
                    }
               // }
            }

            checkCompleted(tempList);

            if (loading) {
                Toast.makeText(ctx, "Getting contents. Please wait...", Toast.LENGTH_SHORT).show();
                fb.setClickable(false);
            }

            if (!loading) {
                Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
                fb.setClickable(true);
                saver.saveText(tempList);
            }
        }
    }

    public void checkCompleted(ArrayList<String> tempList){
      String lastString = (tempList.get(tempList.size()-1));
      loading = (lastString.contains("..."));
    }
}

