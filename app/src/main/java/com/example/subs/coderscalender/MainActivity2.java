package com.example.subs.coderscalender;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView;
    SwipeRefreshLayout swipeRefreshLayout;
    CoordinatorLayout coordinatorLayout;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    FragmentpagerAdapter fragmentpagerAdapter;
    ArrayList<contestDataSet> contestList=new ArrayList<>();
    ArrayList<contestDataSet> upcomingcontestlist=new ArrayList<>();
    ArrayList<contestDataSet> ongoingcontestList=new ArrayList<>();
    ArrayList<contestDataSet> todaycontestList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main2);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        coordinatorLayout=findViewById(R.id.coordinator);
        //Checks if device is connected to internet or not
        Log.d("data", "onCreate: fetch data");

        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tablayout);
        Log.d("data", "onCreate: calling internet connection");
        Internet_connection();
        //Swipe to refresh
//        swipeRefreshLayout=findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                Internet_connection();
//            }
//        });

//        recyclerView=findViewById(R.id.ongoingRecyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void Internet_connection(){
        if(haveInternet())
        {
            //Fetches data from api
            Log.d("data", "onCreate: fetching data");
            Log.d("size", "Internet_connection: calling fetch data ");
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

            Log.d("size", "Internet_connection: inside fetch data");
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://kontests.net/api/v1/all", null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
//                    ArrayList<contestDataSet> contestList=new ArrayList<>();
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
                        int val=checkData(contest);
                        if(val==1)
                            ongoingcontestList.add(contest);
                        else if(val==2)
                            todaycontestList.add(contest);
                        else upcomingcontestlist.add(contest);
                        contestList.add(contest);

                    }
                    Log.d("size", "onResponse: size of arraylist after fetching data is "+contestList.size());
                    Log.d("size", "Internet_connection: size of arraylist when sending to fragment is "+contestList.size());

                    // FRAGMENT PAGER ADAPTER IS SET HERE so that fragment view gets created
                    // only after data is fetched
                    FragmentManager fm=getSupportFragmentManager();
                    fragmentpagerAdapter =new FragmentpagerAdapter(fm,getLifecycle());
                    viewPager.setAdapter(fragmentpagerAdapter);
                    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            viewPager.setCurrentItem(tab.getPosition());
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    });

                    viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                        @Override
                        public void onPageSelected(int position) {
                            tabLayout.selectTab(tabLayout.getTabAt(position));
                        }
                    });
//

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
        Log.d("data", "Internet and data fetch: check if have internet");
        NetworkInfo info=(NetworkInfo)((ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        Log.d("data", "Internet and data fetch: returning "+ (info != null && info.isConnected()));
        return info != null && info.isConnected();

    }

    public int checkData(contestDataSet contest){
        Calendar present=Calendar.getInstance();
        Calendar start=Calendar.getInstance();
        Date dt=new Date();
        present.setTime(dt);
        start.setTime(contest.startTime);

        long diff=start.getTimeInMillis()-present.getTimeInMillis();

        if(diff<=0)
        {
            //already started : ongoing list
            return 1;
        }
        else{
            if(start.get(Calendar.YEAR)==present.get(Calendar.YEAR))
            {
                if(start.get(Calendar.MONTH)==present.get(Calendar.MONTH))
                {
                    if(start.get(Calendar.DAY_OF_MONTH)==present.get(Calendar.DAY_OF_MONTH))
                    {
                        // starting today , on the same day : today list
                        return 2;
                    }
                    else return 3;
                }
                else return 3;
            }
            //start in future : upcoming list
            else return 3;

        }

    }
    public ArrayList<contestDataSet> upcoming(int val){
        Log.d("create", "upcoming: sending dataset of size "+contestList.size());
        if(val==1)
            return ongoingcontestList;
        else if(val==2)
            return todaycontestList;
        else return upcomingcontestlist;
    }
}
