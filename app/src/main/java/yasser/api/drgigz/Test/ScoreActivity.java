package yasser.api.drgigz.Test;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.r0adkll.slidr.Slidr;

import yasser.api.drgigz.R;

public class ScoreActivity extends AppCompatActivity {

    private TextView scored, total;
    private Button doneBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Slidr.attach(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("اختبر معلوماتك");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        loadAds();

        scored = findViewById(R.id.scored);
        total = findViewById(R.id.total);
        doneBtn = findViewById(R.id.done_btn);

        scored.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        total.setText("من "+String.valueOf(getIntent().getIntExtra("total",0)));

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
