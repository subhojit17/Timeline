package com.example.subs.coderscalender;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ongoingFragment extends Fragment {
    RecyclerView ongoingRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    FrameLayout frameLayout;
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
        frameLayout=view.findViewById(R.id.ongoingFrame);
        //GET THE DATASET FROM THE MAIN ACTIVITY2
        contests= activity.upcoming(1);

        //SWIPE TO REFRESH LAYOUT
        swipeRefreshLayout=view.findViewById(R.id.ongoingRefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.d("ongoing", "onRefresh: refreshed");
                swipeRefreshLayout.setRefreshing(false);
                activity.Internet_connection(0);
                setRecyclerview(contests,view);
            }
        });
        Log.d("create", "onCreateView: ongoing contest size "+contests.size());

        setRecyclerview(contests,view);

        return view;
    }

    public void setRecyclerview(ArrayList<contestDataSet>contests, View view) {
        if (contests.size() == 0) {
            TextView textView = new TextView(getContext());
            textView.setText("There is no ongoing contest now! \nCheck out for the upcoming contests");
            textView.setTextSize(18);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(0, 50, 0, 0);

            frameLayout.addView(textView);
        } else {
            ongoingRecyclerView = view.findViewById(R.id.ongoingRecyclerview);
            ongoingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            CustomAdpter lad = new CustomAdpter(contests);
            ongoingRecyclerView.setAdapter(lad);
        }
    }
}