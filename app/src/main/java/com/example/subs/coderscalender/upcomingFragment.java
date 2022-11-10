package com.example.subs.coderscalender;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class upcomingFragment extends Fragment {
    RecyclerView upcomingRecyclerView;
    ArrayList<contestDataSet>contests=new ArrayList<>();

    public upcomingFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("create", "onCreateView: fragment created upcoming");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_upcoming, container, false);
        MainActivity2 activity = (MainActivity2)getActivity();
        contests= activity.upcoming(3);
        Log.d("create", "onCreateView: ongoing contest size "+contests.size());
        upcomingRecyclerView=view.findViewById(R.id.upcomingRecyclerview);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomAdpter lad=new CustomAdpter(contests);
        upcomingRecyclerView.setAdapter(lad);

        return view;
    }

}