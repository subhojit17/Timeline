package com.example.subs.coderscalender;


import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class contestDataSet {
    String contestName;
    String url;
    String siteName;
    Date startTime;
    Date endTime;
    String start;
    String end;
    String Duration;
    Integer imgval;
    String status;
    String[] month= new String[]{"Jan", "Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    boolean logo_val=false;

    contestDataSet(String name, String ur, String site, String stime, String etime, String dur,String stat) {
        contestName = name;
        url = ur;
        siteName = site;
        switch (site) {
            case "CodeChef": {
                imgval = 0;
                break;
            }
            case "CodeForces": {
                imgval = 1;
                break;
            }
            case "HackerRank": {
                imgval = 2;
                break;
            }
            case "Kick Start": {
                imgval = 3;
                break;
            }
            case "AtCoder": {
                imgval = 4;
                break;
            }
            case "HackerEarth": {
                imgval = 5;
                break;
            }
            case "LeetCode":{
                imgval=6;
                break;
            }
            case "Topcoder":{
                imgval=7;
                break;
            }
            case "CS Academy":{
                imgval=8;
                break;
            }
            case "CodeForces::Gym":{
                imgval=1;
                break;
            }

        }
        Log.d("time", "contestDataSet: start time before is "+stime);
        Date dt=parseDate(stime);
        Date dte=parseDate(etime);
        startTime=dt;
        endTime=dte;
        String st=format_time(dt);
        String en=format_time(dte);
        if(time_set(st,en))
        {
            logo_val=true;
            start=st.substring(0,2)+" "+month[Integer.parseInt(st.substring(3,5))-1]+" "+st.substring(6,19);
            end=en.substring(0,2)+" "+month[Integer.parseInt(en.substring(3,5))-1]+" "+en.substring(6,19);
        }
        else{
            start=st.substring(0,2)+" "+month[Integer.parseInt(st.substring(3,5))-1]+" "+st.substring(6,10);
            end=st.substring(11,19)+" - "+en.substring(11,19);
        }
        status=check_status(stat,startTime);
        Duration = calcDuration(dur) ;
    }

    public static Date parseDate(String strDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(strDate);


        } catch (ParseException e) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss 'UTC'");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                return sdf.parse(strDate);
            } catch (ParseException f) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(strDate);
                } catch (ParseException p) {
                    return null;
                }
            }
        }
    }
    public String check_status(String str,Date startDate){

        String status="";
        if(str.equals("CODING"))
            status= "Started";
        else if(str.equals("BEFORE")){
            Date new_date=new Date();
            long difference=startDate.getTime()-new_date.getTime();
            difference/=1000;
            String time_diff=difference+"";
             status="Starts in "+calcDuration(time_diff);
        }
    return status;
    }
    private String calcDuration(String dur)
    {
        float sec=Float.parseFloat(dur);
        int d=(int)(sec/86400);
        sec%=86400;
        int h=(int)(sec/3600);
        sec%=3600;
        int m=(int)(sec/60);
        sec%=60;
        String str="";
        if(d==0)
        {
            if(h==0)
            {
                str= m +" minutes";
            }
            else
            {
                if(m==0 || h>9)
                    str= h +" hour";
                else
                    str= h +" hr "+ m +" min";
            }

        }
        else{
            if (h==0)
                str=d+" Days";
            else{
                if(d==1)
                    str=d+"D "+h+" hr";
                else str=d+" Days";
            }

        }
        return str;
    }
    public boolean time_set(String str1, String str2){
        Log.d("year", "time_set: sub string year is "+str1.substring(6,9));
        if(str1.substring(6,10).equals(str2.substring(6,10))){
            if(str1.substring(3,5).equals(str2.substring(3,5))){
                if(str1.substring(0,2).equals(str2.substring(0,2)))
                    return false;
                else return true;
            }
            else return true;
        }
        else  return true;

    }
    public String format_time(Date dt)
    {
        DateFormat df=new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        return df.format(dt);
    }
}
