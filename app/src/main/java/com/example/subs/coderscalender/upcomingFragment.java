package com.example.subs.coderscalender;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class upcomingFragment extends Fragment {
    RecyclerView upcomingRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<contestDataSet>contests=new ArrayList<>();

    public upcomingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("create", "onCreateView: fragment created upcoming");
        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        MainActivity2 activity = (MainActivity2)getActivity();
        //GET THE DATASET FROM THE MAIN ACTIVITY2
        contests= activity.upcoming(3);

//        Log.d("create", "onCreateView: ongoing contest size "+contests.size());

        //SWIPE TO REFRESH LAYOUT
        swipeRefreshLayout=view.findViewById(R.id.upcomingRefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("ongoing", "onRefresh: refreshed");
                swipeRefreshLayout.setRefreshing(false);
                activity.Internet_connection();
            }
        });
        upcomingRecyclerView=view.findViewById(R.id.upcomingRecyclerview);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomAdpter lad=new CustomAdpter(contests);
        upcomingRecyclerView.setAdapter(lad);

        return view;
    }

}