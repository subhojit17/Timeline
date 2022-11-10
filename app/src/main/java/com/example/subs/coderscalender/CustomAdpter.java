package com.example.subs.coderscalender;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.TransitionManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CustomAdpter extends RecyclerView.Adapter<CustomAdpter.ViewHolder> {

    ArrayList<contestDataSet> localDataSet;
    int[] imagestore ={R.drawable.logo1,R.drawable.logo2,R.drawable.logo3,R.drawable.logo4,R.drawable.logo5,R.drawable.logo6,R.drawable.logo7,R.drawable.logo8,R.drawable.logo9};
    int[] background ={R.drawable.codechef_background,R.drawable.codeforces_background,R.drawable.hackerrank_background,R.drawable.kickstart_background,R.drawable.atcoder_background,R.drawable.hackerearth_background,R.drawable.leetcode_background,R.drawable.topcoder_background,R.drawable.csacademy_background};
    private static final int FLAG_ACTIVITY_CLEAR_TASK = 32768;

    //    Context mContext;
    public   class ViewHolder extends RecyclerView.ViewHolder {
        TextView contestName;
        TextView contestStatus;
        TextView contestDuration;
        ImageView expand;
        ImageView logo;
        RelativeLayout hidden;
        CardView base_card;
        TextView date;
        TextView time;
        TextView link;
        LinearLayout show_less;
        Button set_reminder;
        ImageView timer;
        ImageView background;
        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            contestName=view.findViewById(R.id.contestName);
            contestStatus=view.findViewById(R.id.contestStatus);
            contestDuration=view.findViewById(R.id.duration);
            expand=view.findViewById(R.id.expand);
            logo=view.findViewById(R.id.logo);
            hidden=view.findViewById(R.id.hidden_layout);
            base_card=view.findViewById(R.id.base_card);
            date=view.findViewById(R.id.date);
            time=view.findViewById(R.id.time);
            link=view.findViewById(R.id.link);
            show_less=view.findViewById(R.id.show_less);
            set_reminder=view.findViewById(R.id.set_reminder);
            timer=view.findViewById(R.id.timer);
            background=view.findViewById(R.id.set_back);
//            contestDuration=view.findViewById(R.id.);
//            setAlarm=view.findViewById(R.id.setAlarm);
//            contestStatus=view.findViewById(R.id.contest_status);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * String[] containing the data to populate views to be used
     * by RecyclerView.
     */


    public CustomAdpter(ArrayList<contestDataSet> dataSet) {
        Log.d("custom", "CustomAdpter: dataset received is of size "+dataSet.size());
        localDataSet = dataSet;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycleelement, viewGroup, false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        Log.d("custom", "onBindViewHolder: contest name is  " +localDataSet.get(position).contestName+"size is "+localDataSet.get(position).contestName.length());
        if(localDataSet.get(position).contestName.length()<=1){
            Log.d("size", "onBindViewHolder: " +localDataSet.get(position).siteName);
            viewHolder.contestName.setText(localDataSet.get(position).siteName);
        }
        else viewHolder.contestName.setText(localDataSet.get(position).contestName);
        viewHolder.contestStatus.setText(localDataSet.get(position).status);
        viewHolder.contestDuration.setText(localDataSet.get(position).Duration);
//        Log.d("image", "onBindViewHolder: img val is "+localDataSet.get(position).imgval+"for site "+localDataSet.get(position).siteName);
        viewHolder.logo.setImageResource(imagestore[localDataSet.get(position).imgval]);

        viewHolder.date.setText(localDataSet.get(position).start);
        Log.d("custom", "onBindViewHolder: logo val is "+localDataSet.get(position).logo_val+"for contest "+localDataSet.get(position).siteName);
        if(localDataSet.get(position).logo_val)
            viewHolder.timer.setImageResource(R.drawable.ic_baseline_date_range_24);
        else viewHolder.timer.setImageResource(R.drawable.ic_baseline_timer_24);
        viewHolder.time.setText(localDataSet.get(position).end);
        viewHolder.background.setImageResource(background[localDataSet.get(position).imgval]);
        viewHolder.link.setOnClickListener(view -> {
            Uri webpage = Uri.parse(localDataSet.get(position).url);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            view.getContext().startActivity(webIntent);
        });
        viewHolder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.d("visibility", "onClick: visible for "+position);
                    TransitionManager.beginDelayedTransition(viewHolder.base_card,
                            new AutoTransition());
                viewHolder.hidden.setVisibility(View.VISIBLE);
                viewHolder.expand.setVisibility(View.GONE);

//                    arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);

            }
        });
        viewHolder.show_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("visibility", "onClick: closed for "+position);
                TransitionManager.beginDelayedTransition(viewHolder.base_card,
                        new AutoTransition());
                viewHolder.hidden.setVisibility(View.GONE);
                viewHolder.expand.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.set_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("reminder", "onClick: clicked set reminder");
                Calendar cal = Calendar.getInstance();

                if(cal.getTimeInMillis()-localDataSet.get(position).startTime.getTime()>=0) {
                    Log.d("reminder", "onClick: contest started");
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle(R.string.alert_title)
                            .setMessage(R.string.alert_message)
                            .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Uri webpage = Uri.parse(localDataSet.get(position).url);
                                    Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                                    view.getContext().startActivity(webIntent);
                                }
                            })
                            .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });

                    builder.show();


                }
                else {
                    Log.d("reminder", "onClick: yet to start");

                    cal.setTime(localDataSet.get(position).startTime);
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    long diff = localDataSet.get(position).endTime.getTime() - localDataSet.get(position).startTime.getTime();
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra("beginTime", cal.getTimeInMillis());
                    intent.putExtra("allDay", false);
//                intent.putExtra("rrule", "");
                    intent.putExtra("endTime", cal.getTimeInMillis() + diff);
                    intent.putExtra("title", localDataSet.get(position).contestName);
                    view.getContext().startActivity(intent);
                    viewHolder.hidden.setVisibility(View.GONE);
                    viewHolder.expand.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return localDataSet.size();
    }





}
