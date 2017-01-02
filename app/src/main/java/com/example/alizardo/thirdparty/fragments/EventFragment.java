package com.example.alizardo.thirdparty.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.adapters.EventAdapter;
import com.example.alizardo.thirdparty.adapters.TabsPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {

    private JSONObject data;
    private JSONObject pending, rejected, invited, hosting;
    private String token;


    private OnFragmentInteractionListener mListener;

    TabLayout tabLayout;
    private static final String ARG_PAGE_NUMBER = "page_number";
    private RecyclerView mRecyclerView;
    private EventAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public EventFragment() {
    }

    public static EventFragment newInstance(String token, JSONObject pending,
                                            JSONObject hosting, JSONObject invited, JSONObject rejected) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString("pending", pending.toString());
        args.putString("hosting", hosting.toString());
        args.putString("invited", invited.toString());
        args.putString("rejected", rejected.toString());
        args.putString("token", token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                this.token = getArguments().getString("token");
                this.pending = new JSONObject(getArguments().getString("pending"));
                this.hosting = new JSONObject(getArguments().getString("hosting"));
                this.rejected = new JSONObject(getArguments().getString("rejected"));
                this.invited = new JSONObject(getArguments().getString("invited"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = (View) inflater.inflate(R.layout.fragment_event, container, false);

        //TabLayout Setup
        setupTablayout(v);

        final ViewPager pager = (ViewPager) v.findViewById(R.id.pager);
        TabsPagerAdapter adapter = new TabsPagerAdapter(getFragmentManager(), getContext(), this.token,
                this.pending, this.rejected, this.invited, this.hosting);

        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {


            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                Context context = getContext();

                switch (tab.getPosition()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    default:
                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        return v;
    }

    private void setupTablayout(View v) {
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
