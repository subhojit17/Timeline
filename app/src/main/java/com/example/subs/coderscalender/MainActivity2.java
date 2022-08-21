package com.example.subs.coderscalender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main2);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        coordinatorLayout=findViewById(R.id.coordinator);
        //Checks if device is connected to interent or not
        Log.d("data", "onCreate: fetch data");


        Internet_connection();

        //Swipe to refresh
        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                Internet_connection();
            }
        });

        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void Internet_connection(){
        if(haveInternet())
        {
            Log.d("data", "onCreate: fetching data");
            //Fetches data from api
            fetchData();
        }
        else {
            Log.d("data", "onCreate: no internet");
//            Toast.makeText(this, "Oops!! Not connected to Internet", Toast.LENGTH_SHORT).show();
            Snackbar snackbar= Snackbar.make(coordinatorLayout,"Oops! Not connected to internet ",Snackbar.LENGTH_INDEFINITE).setAction("Retry", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Internet_connection();
                }
            });
            snackbar.show();
        }

    }
//    USED TO FETCH DATA FROM API
    private void fetchData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://kontests.net/api/v1/all", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<contestDataSet> contestList=new ArrayList<>();
                    for(int i=0;i<response.length();i++)
                    {
                        JSONObject obj= response.getJSONObject(i);
                        String contestName = obj.getString("name");
                        String site=obj.getString("site");
                        String stime=obj.getString("start_time");
                        String etime=obj.getString("end_time");
                        String duration=obj.getString("duration");
                        String url=obj.getString("url");
                        String status=obj.getString("status");

                        //Store fetched data from API to a array list of custom data type
                        contestDataSet contest=new contestDataSet(
                                contestName,
                                url,
                                site,
                                stime,
                                etime,
                                duration,
                                status
                        );
                        contestList.add(contest);

                    }
//                    Log.d("ADAPTER", "onResponse: size is"+contestList.size());

                    //Set adapter to the recyclerview layout
                    CustomAdpter lad= new CustomAdpter(contestList);
                    recyclerView.setAdapter(lad);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //if there is an error in loading data toast is displayed
                Toast.makeText(MainActivity2.this,"Failed To Load Data Make Sure You are Connected to Internet", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

//    checks if connected to internet or not
    private boolean haveInternet(){

        NetworkInfo info=(NetworkInfo)((ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return info != null && info.isConnected();

    }
}
