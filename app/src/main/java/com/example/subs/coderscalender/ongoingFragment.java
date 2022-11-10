package com.example.subs.coderscalender;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ongoingFragment extends Fragment {
    RecyclerView ongoingRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    public static ArrayList<contestDataSet> contests=new ArrayList<>();

    public ongoingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Log.d("create", "onCreateView: fragment created ongoing");

        // INFLATE THE LAYOUT FOR THIS FRAGMENT
        View view= inflater.inflate(R.layout.fragment_ongoing, container, false);
        MainActivity2 activity = (MainActivity2)getActivity();
        //GET THE DATASET FROM THE MAIN ACTIVITY2
        contests= activity.upcoming(1);

        //SWIPE TO REFRESH LAYOUT
        swipeRefreshLayout=view.findViewById(R.id.ongoingRefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.d("ongoing", "onRefresh: refreshed");
                swipeRefreshLayout.setRefreshing(false);
                activity.Internet_connection();
            }
        });
//        Log.d("create", "onCreateView: ongoing contest size "+contests.size());
        ongoingRecyclerView=view.findViewById(R.id.ongoingRecyclerview);
        ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomAdpter lad=new CustomAdpter(contests);
//        Log.d("create", "onCreateView: ongoing adapter set");
        ongoingRecyclerView.setAdapter(lad);



        return view;
    }
}