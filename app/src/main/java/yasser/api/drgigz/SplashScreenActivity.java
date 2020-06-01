package yasser.api.drgigz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;



public class SplashScreenActivity extends AppCompatActivity {

    TextView appName, tech, who;
    ImageView imageSplash;

    private static int SPLASH_TIME_OUT = 5000; //2000 means 2seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        appName = findViewById(R.id.appName);
        tech = findViewById(R.id.tech);
        who = findViewById(R.id.who);
        imageSplash = findViewById(R.id.imageSplash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, DashboardActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);

        imageSplash.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_animation));
        appName.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation));
        tech.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation));
        who.setAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_animation));

    }
}
