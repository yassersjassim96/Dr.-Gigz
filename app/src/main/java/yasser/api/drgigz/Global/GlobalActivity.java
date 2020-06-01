package yasser.api.drgigz.Global;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.r0adkll.slidr.Slidr;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import yasser.api.drgigz.R;

public class GlobalActivity extends AppCompatActivity {

    private TextView tvTotalConfirmed, tvTotalDeaths, tvTotalRecovered, tvLastUpdated,
            global, tvLabelTotalConfirmed, tvLabelTotalDeaths, tvLabelTotalRecovered;
    private ProgressBar progressBar;
    boolean wifiConnected, mobileConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global);

        Slidr.attach(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("العالم");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //call view
        tvTotalConfirmed = findViewById(R.id.tvTotalConfirmed);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTotalRecovered = findViewById(R.id.tvTotalRecovered);
        tvLastUpdated = findViewById(R.id.tvLastUpdated);
        progressBar = findViewById(R.id.progress_circular_home);
        global = findViewById(R.id.global);
        tvLabelTotalConfirmed = findViewById(R.id.tvLabelTotalConfirmed);
        tvLabelTotalDeaths = findViewById(R.id.tvLabelTotalDeaths);
        tvLabelTotalRecovered = findViewById(R.id.tvLabelTotalRecovered);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        if (activeInfo != null && activeInfo.isConnected()){
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if (wifiConnected){
                getData();
            } else if (mobileConnected){
                getData();
            }
        } else {
            Toast.makeText(this, "تحقق من اتصالك بالإنترنت", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate(long milliSecond){
        // Mon, 23 Mar 2020 02:01:04 PM
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss aaa");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSecond);
        return formatter.format(calendar.getTime());
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest stringRequest =new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvTotalConfirmed.setText(jsonObject.getString("cases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTotalRecovered.setText(jsonObject.getString("recovered"));
                    tvLastUpdated.setText("آخر تحديث"+"\n"+getDate(jsonObject.getLong("updated")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Log.d("Error Response", error.toString());
            }
        });

        queue.add(stringRequest);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
