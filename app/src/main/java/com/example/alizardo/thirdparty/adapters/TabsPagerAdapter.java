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

    private String token;


    public TabsPagerAdapter(FragmentManager fm, Context context, String token, JSONObject pending,
                            JSONObject rejected, JSONObject invited, JSONObject hosting) {
        super(fm);
        this.token = token;
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
                return EventsTabFragment.newInstance(token, pending, position + 1);
            case 1:
                return EventsTabFragment.newInstance(token, invited, position + 1);
            case 2:
                return EventsTabFragment.newInstance(token, hosting, position + 1);
            case 3:
                return EventsTabFragment.newInstance(token, rejected, position + 1);
            default:
                return EventsTabFragment.newInstance(token, pending, position + 1);
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
