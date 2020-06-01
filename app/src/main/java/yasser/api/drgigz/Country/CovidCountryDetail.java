package yasser.api.drgigz.Country;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.r0adkll.slidr.Slidr;

import yasser.api.drgigz.R;

public class CovidCountryDetail extends AppCompatActivity {

    TextView tvDetailCountryName, tvDetailTotalCases, tvDetailTodayCases, tvDetailTotalDeaths,
            tvDetailTodayDeaths, tvDetailTotalRecovered, tvDetailTotalActive, tvDetailTotalCritical;
    ImageView imgCountryFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_country_detail);

        Slidr.attach(this);

        //call view
        tvDetailCountryName = findViewById(R.id.tvDetailCountryName);
        tvDetailTotalCases = findViewById(R.id.tvDetailTotalCases);
        tvDetailTodayCases = findViewById(R.id.tvDetailTodayCases);
        tvDetailTotalDeaths = findViewById(R.id.tvDetailTotalDeaths);
        tvDetailTodayDeaths = findViewById(R.id.tvDetailTodayDeaths);
        tvDetailTotalRecovered = findViewById(R.id.tvDetailTotalRecovered);
        tvDetailTotalActive = findViewById(R.id.tvDetailTotalActive);
        tvDetailTotalCritical = findViewById(R.id.tvDetailTotalCritical);
        imgCountryFlag = findViewById(R.id.imgCountryFlag);

        //call Covid Country
        CovidCountry covidCountry = getIntent().getParcelableExtra("EXTRA_COVID");

        //set text view
        tvDetailCountryName.setText(covidCountry.getmCovidCountry());

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(covidCountry.getmCovidCountry());
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvDetailTotalCases.setText(Integer.toString(covidCountry.getmCases()));
        tvDetailTodayCases.setText(covidCountry.getmTodayCases());
        tvDetailTotalDeaths.setText(Integer.toString(covidCountry.getmDeaths()));
        tvDetailTodayDeaths.setText(covidCountry.getmTodayDeaths());
        tvDetailTotalRecovered.setText(covidCountry.getmRecovered());
        tvDetailTotalActive.setText(covidCountry.getmActive());
        tvDetailTotalCritical.setText(covidCountry.getmCritical());
        //Glide
        Glide.with(this)
                .load(covidCountry.getmFlags())
                .apply(new RequestOptions().override(240, 160))
                .into(imgCountryFlag);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
