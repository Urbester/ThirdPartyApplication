package com.example.alizardo.thirdparty.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.adapters.MyAdapter;
import com.example.alizardo.thirdparty.libs.Utils;
import com.example.alizardo.thirdparty.pojo.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    TabLayout tabLayout;

    private JSONObject data;

    private OnFragmentInteractionListener mListener;

    public DiscoverFragment() {
        // Required empty public constructor

    }

    public static DiscoverFragment newInstance(JSONObject data) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putString("events", data.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            try {
                this.data = new JSONObject(getArguments().getString("events"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover, container, false);

        this.mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        Utils u = new Utils();

        JSONArray jsonArray = null;
        List<Event> myDataset = new ArrayList<>();
        try {
            jsonArray = this.data.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                // String title, String host, String description, String startDate, String endDate, String numGuests, String url
                Event e = new Event(explrObject.get("title").toString(), explrObject.get("local").toString(),
                        explrObject.get("description").toString(), explrObject.get("startDate").toString(), explrObject.get("endDate").toString(),
                        explrObject.get("maxGuests").toString(), explrObject.get("URL").toString(), explrObject.get("slotsLeft").toString(),
                        explrObject.get("host_name").toString(), explrObject.get("host_email").toString(),
                        explrObject.get("host_URL").toString(), explrObject.get("id").toString(),
                        Boolean.parseBoolean((String) explrObject.get("isHost")), Boolean.parseBoolean((String) explrObject.get("isAccepted")),
                        Boolean.parseBoolean((String) explrObject.get("isInvited")), Boolean.parseBoolean((String) explrObject.get("isPending"))
                        , Boolean.parseBoolean((String) explrObject.get("isRejected"))
                );
                myDataset.add(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // specify an adapter
        this.mAdapter = new MyAdapter(myDataset);

        // use a linear layout manager
        this.mLayoutManager = new LinearLayoutManager(getActivity());

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
        this.mRecyclerView.setAdapter(this.mAdapter);

        return v;
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
        // Removed, not necessary
        /*
        if (context instanceof OnFragmentInteractionListener) {

            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
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
