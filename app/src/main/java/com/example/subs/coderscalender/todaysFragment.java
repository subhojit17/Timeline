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

public class todaysFragment extends Fragment {
    RecyclerView todaysRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
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
                activity.Internet_connection();
            }
        });
        todaysRecyclerView=view.findViewById(R.id.todaysRecyclerview);
        todaysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomAdpter lad=new CustomAdpter(contests);
        todaysRecyclerView.setAdapter(lad);

        return view;
    }
}