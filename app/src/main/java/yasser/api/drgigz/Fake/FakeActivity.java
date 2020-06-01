package yasser.api.drgigz.Fake;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.util.Locale;

import yasser.api.drgigz.R;

public class FakeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideAdapterFake myadapter;
    private ImageButton left, right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Locale.getDefault().getLanguage().contentEquals("ar")){
            setContentView(R.layout.activity_fake_ar);
        } else {
            setContentView(R.layout.activity_fake);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("شائعات");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        left = (ImageButton) findViewById(R.id.left_nav);
        right = (ImageButton) findViewById(R.id.right_nav);

        left.setVisibility(View.INVISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    left.setVisibility(View.INVISIBLE);
                } else {
                    left.setVisibility(View.VISIBLE);
                }
                if (position < viewPager.getAdapter().getCount()-1){
                    right.setVisibility(View.VISIBLE);
                } else {
                    right.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        myadapter = new SlideAdapterFake(this);
        viewPager.setAdapter(myadapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
