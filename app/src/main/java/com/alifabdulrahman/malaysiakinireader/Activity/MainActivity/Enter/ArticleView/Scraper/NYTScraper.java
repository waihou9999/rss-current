package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class NYTScraper {
    private WebView webView;
    boolean firstLoad = true;

    public NYTScraper(Activity activity, Context ctx, WebView webView, FunctionButton FunctionButton, Controller Controller) {
        this.webView = webView;
        this.webView.addJavascriptInterface(new NYTScraper.GetHTML(activity, ctx, FunctionButton, Controller, webView), "Scrap");
    }

    public void scrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
    }

    public class GetHTML {
        private Context ctx;
        private Activity activity;
        private com.alifabdulrahman.malaysiakinireader.Helper.saver saver;
        private com.alifabdulrahman.malaysiakinireader.Helper.loader loader;
        private FunctionButton fb;
        private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton ttsFunctionButton;
        private Controller controller;
        private com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController ttsController;
        private WebView WebView;

        public GetHTML(Activity activity, Context ctx, FunctionButton FunctionButton, Controller Controller, WebView WebView) {
            this.activity = activity;
            this.ctx = ctx;
            saver = new saver(this.activity, this.ctx);
            loader = new loader(this.activity, this.ctx);
            this.fb = FunctionButton;
            this.controller = Controller;
            this.WebView = WebView;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @JavascriptInterface
        public void getHTML(String html) {

            Document doc = Jsoup.parse(html);

            ArrayList<String> tempList = new ArrayList<>();

            Elements contents = doc.select("section[name $= articleBody]"); // NYT
            if (contents == null || contents.isEmpty())
                contents = doc.select("div[data-component $= text-block]"); // BBC
            if (contents == null || contents.isEmpty())
                contents = doc.select("div[class $= text]"); // Sun (Malaysia)
            if (contents == null || contents.isEmpty())
                contents = doc.select("p, li"); // Default - take everything
            else
                contents = contents.select("p, li");

            contents = contents.select("p, li");

            for (Element content : contents) {

                if (!(content.text().equals(""))) {

                    if (!tempList.contains(content.text())) {
                        tempList.add(content.text());
                    }
                }

            }
            ttsController = controller.getTtsController();
            ttsFunctionButton = fb.getTtsFunctionButton();

            Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
            ttsFunctionButton.enable();
            saver.saveText(tempList);

            if (loader.getTSS()) {
                ttsController.init();
            }
        }
    }

    public void setFirstLoad(){
        firstLoad = true;
    }
}
