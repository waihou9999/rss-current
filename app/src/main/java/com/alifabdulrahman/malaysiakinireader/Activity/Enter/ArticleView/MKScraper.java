package com.alifabdulrahman.malaysiakinireader.Activity.Enter.ArticleView;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alifabdulrahman.malaysiakinireader.model.ArticleData;
import com.alifabdulrahman.malaysiakinireader.storage.substorage.NewsStorage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MKScraper {
    private WebView webView;
    private boolean loading;
    boolean firstLoad = true;

    public MKScraper(Activity activity, Context ctx, WebView webView, FunctionButton FunctionButton, Controller Controller) {
        this.webView = webView;
        this.webView.addJavascriptInterface(new GetHTML(activity, ctx, FunctionButton, Controller, webView), "Scrap");
    }


    public void scrap() {
        webView.loadUrl("javascript:window.Scrap.getHTML" +
                "(document.getElementsByTagName('html')[0].outerHTML);");
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

                    /*
                    if (!firstLoad){
                        Toast.makeText(ctx, "Please sign in to get full content", Toast.LENGTH_SHORT).show();
                        saver.saveText(tempList);
                        tts = new TTS(ctx, loader, saver);
                        ttsController.setTts(tts);
                        ttsFunctionButton.enable();
                    }
                    firstLoad = false;


                     */
            }
            ttsController = controller.getTtsController();
            ttsFunctionButton = fb.getTtsFunctionButton();

            Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
            ttsFunctionButton.enable();
            saver.saveText(tempList);

            if (loader.getTSS() && !ttsController.getTTS().isSpeaking()) {
                ttsController.init();


            }

/*
            if (WebView.getProgress() != 100){
                Toast.makeText(ctx, "Getting contents. Please wait...", Toast.LENGTH_SHORT).show();
                ttsController.stop();
                saver.clearText();
            }
            else{
                Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
                ttsFunctionButton.enable();
                saver.saveText(tempList);

                if (loader.getTSS()) {
                    ttsController.init();
                }
            }


 */
            /*
                    if (checkLoading(tempList)) {
                        Toast.makeText(ctx, "Getting contents. Please wait...", Toast.LENGTH_SHORT).show();
                        ttsController.stop();
                        saver.clearText();

                    } else {
                        Toast.makeText(ctx, "Finished getting content", Toast.LENGTH_SHORT).show();
                        ttsFunctionButton.enable();
                        saver.saveText(tempList);

                        if (loader.getTSS()) {
                            ttsController.init();
                        }
                    }

             */
                }


        public boolean checkLoading(ArrayList<String> tempList){
            String lastString = (tempList.get(tempList.size()-1));
            loading = (lastString.contains("..."));
            return loading;
        }
    }

    public void setFirstLoad(){
        firstLoad = true;
    }
}

