package com.example.alizardo.thirdparty.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.fragments.EventsTabFragment;

import org.json.JSONObject;


public class TabsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    private JSONObject pending, rejected, invited, hosting;


    public TabsPagerAdapter(FragmentManager fm, Context context, JSONObject pending,
                            JSONObject hosting, JSONObject invited, JSONObject rejected) {
        super(fm);
        this.context = context;
        this.pending = pending;
        this.hosting = hosting;
        this.rejected = rejected;
        this.invited = invited;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return EventsTabFragment.newInstance(pending);
            case 1:
                return EventsTabFragment.newInstance(invited);
            case 2:
                return EventsTabFragment.newInstance(hosting);
            case 3:
                return EventsTabFragment.newInstance(rejected);
            default:
                return EventsTabFragment.newInstance(pending);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String output;
        switch (position) {
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
