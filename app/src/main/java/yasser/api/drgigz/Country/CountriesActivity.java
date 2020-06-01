package yasser.api.drgigz.Country;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.r0adkll.slidr.Slidr;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import yasser.api.drgigz.R;

public class CountriesActivity extends AppCompatActivity {

    RecyclerView rvCovidCountry;
    ProgressBar progressBar;
    CasesAdapter covidCountryAdapter;
    boolean wifiConnected, mobileConnected;

    private static final String TAG = CountriesActivity.class.getSimpleName();
    List<CovidCountry> covidCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);

        Slidr.attach(this);

        //call view
        rvCovidCountry = findViewById(R.id.rvCovidCountry);
        progressBar = findViewById(R.id.progress_circular_country);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){
                getDataFromServerSortTotalCases();
            } else if (mobileConnected){
                getDataFromServerSortTotalCases();
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "تحقق من اتصالك بالإنترنت", Toast.LENGTH_SHORT).show();
        }

        rvCovidCountry.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvCovidCountry.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.line_divider));
        rvCovidCountry.addItemDecoration(dividerItemDecoration);

        //call list
        covidCountries = new ArrayList<>();
    }

    private void showRecyclerView(){
        covidCountryAdapter = new CasesAdapter(covidCountries, this);
        rvCovidCountry.setAdapter(covidCountryAdapter);

        ItemClickSupport.addTo(rvCovidCountry).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedCovidCountry(covidCountries.get(position));
            }
        });
    }

    private void showSelectedCovidCountry(CovidCountry covidCountry){
        Intent covidCovidCountryDetail = new Intent(this, CovidCountryDetail.class);
        covidCovidCountryDetail.putExtra("EXTRA_COVID", covidCountry);
        startActivity(covidCovidCountryDetail);
    }

    private void getDataFromServerSortTotalCases() {
        String url = "https://corona.lmao.ninja/v2/countries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                if (response != null) {
                    Log.e(TAG, "onResponse: " + response);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);

                            JSONObject countryInfo = data.getJSONObject("countryInfo");

                            covidCountries.add(new CovidCountry(
                                    data.getString("country"), data.getInt("cases"),
                                    data.getString("todayCases"), data.getInt("deaths"),
                                    data.getString("todayDeaths"), data.getString("recovered"),
                                    data.getString("active"), data.getString("critical"),
                                    countryInfo.getString("flag")
                            ));
                        }

                        Collections.sort(covidCountries, new Comparator<CovidCountry>() {
                            @Override
                            public int compare(CovidCountry o1, CovidCountry o2) {
                                if (o1.getmCases() > o2.getmCases()){
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });

                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle("البلدان "+jsonArray.length());
                        actionBar.setDisplayShowHomeEnabled(true);
                        actionBar.setDisplayHomeAsUpEnabled(true);
                        showRecyclerView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onResponse: "+error);
                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.country_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(this);
        searchView.setQueryHint("Search...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (covidCountryAdapter != null){
                    covidCountryAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
        searchItem.setActionView(searchView);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
