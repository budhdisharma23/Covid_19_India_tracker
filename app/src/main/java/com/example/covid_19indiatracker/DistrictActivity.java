package com.example.covid_19indiatracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DistrictActivity extends AppCompatActivity {

    private static String TAG = DistrictActivity.class.getSimpleName();
    String state;
    int position;
    EditText edtSearch;
    ListView listView;
    SimpleArcLoader simpleArcLoader;

    public static List<DistrictModel> districtModelsList = new ArrayList<>();
    DistrictModel districtModel;
    DistrictAdapter myDistrictAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district);

        edtSearch = findViewById(R.id.edtSearch);
        listView = findViewById(R.id.listView);
        simpleArcLoader = findViewById(R.id.loader);

        Intent intent = getIntent();

        position = intent.getIntExtra("position",0);
        state = intent.getStringExtra("state");


        getSupportActionBar().setTitle("Affected Districts of "+state);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(500);
                view.startAnimation(animation1);

                /*startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
                 */}
        });


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                myDistrictAdapter.getFilter().filter(s);
                myDistrictAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {

        String url  = "https://api.covid19india.org/state_district_wise.json";

        simpleArcLoader.start();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            //JSONObject jsonObject = response.getJSONObject(0);
                            //JSONObject jsonObject1 = new JSONObject(response.toString());

                           // JSONObject stateobj = jsonObject1.getJSONObject(state);

                            JSONArray jsonArray = response.getJSONArray("districtData");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String state = jsonObject.getString("district");
                                String cases = jsonObject.getString("confirmed");

                                String deaths = jsonObject.getString("deceased");
                                String recovered = jsonObject.getString("recovered");
                                String active = jsonObject.getString("active");

                                //String name = jsonObject.getString("delta");

                                JSONArray today = jsonObject.getJSONArray("delta");
                                for(int j=0; j<today.length(); j++)
                                {
                                    JSONObject obj = today.getJSONObject(j);

                                    String tCase = obj.getString("confirmed");
                                    String tDeath = obj.getString("deceased");
                                    String tRecover = obj.getString("recovered");

                                    districtModel = new DistrictModel(state,cases,tCase,deaths,tDeath,
                                            recovered, tRecover, active);
                                    districtModelsList.add(districtModel);
                                }
                            }

                            myDistrictAdapter = new DistrictAdapter(DistrictActivity.this,
                                    districtModelsList);
                            listView.setAdapter(myDistrictAdapter);
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d(TAG, "onResponse: Exception Budhdi:"+e.getMessage());

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                int errorCode = 0;
                if (error instanceof TimeoutError) {
                    errorCode = -7;
                } else if (error instanceof NoConnectionError) {
                    errorCode = -1;
                } else if (error instanceof AuthFailureError) {
                    errorCode = -6;
                } else if (error instanceof ServerError) {
                    errorCode = 0;
                } else if (error instanceof NetworkError) {
                    errorCode = -1;
                } else if (error instanceof ParseError) {
                    errorCode = -8;
                }
                //Toast.makeText(this, ErrorCode.errorCodeMap.get(errorCode), Toast.LENGTH_SHORT)
                // .show();

                Log.d(TAG, "onErrorResponse: Budhdi error Code::"+errorCode);
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                Toast.makeText(DistrictActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }
}
