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

public class todaysFragment extends Fragment {
    RecyclerView todaysRecyclerView;
    ArrayList<contestDataSet> contests=new ArrayList<>();
    public todaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_todays, container, false);
        MainActivity2 activity = (MainActivity2)getActivity();
        contests= activity.upcoming(2);
        Log.d("create", "onCreateView: todays contest size "+contests.size());
        todaysRecyclerView=view.findViewById(R.id.todaysRecyclerview);
        todaysRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomAdpter lad=new CustomAdpter(contests);
        todaysRecyclerView.setAdapter(lad);

        return view;
    }
}