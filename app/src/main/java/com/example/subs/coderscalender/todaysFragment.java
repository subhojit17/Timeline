package com.example.subs.coderscalender;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class todaysFragment extends Fragment {
    RecyclerView todaysRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayout;
    ArrayList<contestDataSet> contests=new ArrayList<>();
    public todaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view= inflater.inflate(R.layout.fragment_todays, container, false);
        MainActivity2 activity = (MainActivity2)getActivity();
        frameLayout=view.findViewById(R.id.todayFrame);
        //GET THE DATASET FROM THE MAIN ACTIVITY2
        contests= activity.upcoming(2);
//        Log.d("create", "onCreateView: todays contest size "+contests.size());

        //SWIPE TO REFRESH LAYOUT
        swipeRefreshLayout=view.findViewById(R.id.todaysRefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("ongoing", "onRefresh: refreshed");
                swipeRefreshLayout.setRefreshing(false);
                activity.Internet_connection(1);
                setRecyclerview(contests,view);
            }
        });
        setRecyclerview(contests,view);

        return view;
    }
    public void setRecyclerview(ArrayList<contestDataSet>contests, View view){
        if(contests.size()==0)
        {
            TextView textView=new TextView(getContext());
            textView.setText("There is no upcoming contest for today! \nCheck out all the upcoming contests in the Upcoming section");
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(0,50,0,0);

            frameLayout.addView(textView);
        }
        else {
            todaysRecyclerView = view.findViewById(R.id.todaysRecyclerview);
            todaysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CustomAdpter lad = new CustomAdpter(contests);
            todaysRecyclerView.setAdapter(lad);
        }
    }
}