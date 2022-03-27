package com.alifabdulrahman.malaysiakinireader.Activity.MainActivity;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.alifabdulrahman.malaysiakinireader.Activity.About.NewsSectionActivity2;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleList.MalaysiaKini.MKListingActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleList.NewYorkTimes.NYTListingActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.ArticleView.ArticleViewActivity;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.NewsSectionActivity.MKSection;
import com.alifabdulrahman.malaysiakinireader.Activity.MainActivity.Enter.NewsSectionActivity.NYTSection;
import com.alifabdulrahman.malaysiakinireader.Activity.Help.NewsSectionActivity1;

public class sectionManager extends AppCompatActivity {
    private Context context;
    private Intent intent = null;

    public sectionManager(Context context) {
        this.context = context;
    }

    public Intent section(int pos) {

        switch(pos){
            case 0:   intent = new Intent(context, MKSection.class); break;
            case 1:   intent = new Intent(context, NYTSection.class); break;
            case 2:   intent = new Intent(context, NewsSectionActivity1.class); break;
            case 3:   intent = new Intent(context, NewsSectionActivity2.class); break;
            case 4:   intent = new Intent(context, MKListingActivity.class); break;
            case 5:   intent = new Intent(context, MainActivity.class); break;
            case 6:   intent = new Intent(context, ArticleViewActivity.class); break;
        }
        return intent;
    }

    public Intent toNYTList(){
        return intent = new Intent(context, NYTListingActivity.class);
    }



    public Intent toView(int lastIndex3, String lastNewsType2, boolean lastOrder2){
        intent = new Intent(context, ArticleViewActivity.class);
        intent.putExtra("index", lastIndex3);
        intent.putExtra("NewsType", lastNewsType2);
        intent.putExtra("OrderLatest", lastOrder2);

        return intent;
    }
}
