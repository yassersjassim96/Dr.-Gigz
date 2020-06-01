package yasser.api.drgigz.Test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.r0adkll.slidr.Slidr;

import yasser.api.drgigz.R;

public class TestActivity extends AppCompatActivity {

    private Button startBtn, bookmarkBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Slidr.attach(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("اختبر معلوماتك");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        startBtn = findViewById(R.id.start_btn);
        bookmarkBtn = findViewById(R.id.bookmarks_btn);

        MobileAds.initialize(this, "ca-app-pub-5682477291764471~8937697033");

        loadAds();

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(TestActivity.this, QuestionsActivity.class);
                startActivity(startIntent);
            }
        });

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bookmarksIntent = new Intent(TestActivity.this, BookmarkActivity.class);
                startActivity(bookmarksIntent);
            }
        });
    }

    private void loadAds(){
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
