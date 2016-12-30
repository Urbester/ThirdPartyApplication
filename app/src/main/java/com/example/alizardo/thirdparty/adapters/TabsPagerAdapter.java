package com.example.alizardo.thirdparty.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.fragments.EventsTabFragment;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return EventsTabFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String output;
        switch (position){
            case 0:
                output = context.getResources().getString(R.string.myevents_tab_pending);
                break;
            case 1:
                output = context.getResources().getString(R.string.myevents_tab_invited);
                break;
            case 2:
                output = context.getResources().getString(R.string.myevents_tab_hosting);
                break;
            case 3:
                output = context.getResources().getString(R.string.myevents_tab_rejected);
                break;
            default:
                output = "";
                break;

        }
        return output;
    }
}
