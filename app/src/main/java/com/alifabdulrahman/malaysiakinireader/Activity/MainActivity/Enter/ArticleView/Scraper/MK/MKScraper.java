package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper.MK;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.Controller;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Controller.ttsController.ttsController;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.FunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.FunctionButton.TTS.ttsFunctionButton;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.Scraper.Scraper;
import com.alifabdulrahman.malaysiakinireader.Helper.loader;
import com.alifabdulrahman.malaysiakinireader.Helper.saver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper extends Scraper {

    public MKScraper(Activity activity, Context ctx, WebView webView, FunctionButton FunctionButton, Controller Controller) {
        super(activity, ctx, webView, FunctionButton, Controller);
        this.webView.addJavascriptInterface(new MKScraper.GetHTML(activity, ctx, FunctionButton, Controller, webView), "Scrap");
    }

    public class GetHTML {
        private Context ctx;
        private Activity activity;
        private saver saver;
        private loader loader;
        private FunctionButton fb;
        private ttsFunctionButton ttsFunctionButton;
        private Controller controller;
        private ttsController ttsController;
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

            Elements classContents = doc.select("div[id $= full-content-container]");

            Elements contents = classContents.select("p, li");

            if (classContents == null || classContents.isEmpty()) {
                classContents = doc.select("div[id $= __next]");
                contents = classContents.select("p, li");
            }

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

