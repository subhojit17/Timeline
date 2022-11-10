package com.example.subs.coderscalender;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentpagerAdapter extends FragmentStateAdapter {


    public FragmentpagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position==0)
            return new ongoingFragment();
        else if(position==1)
            return new todaysFragment();
        else return new upcomingFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
