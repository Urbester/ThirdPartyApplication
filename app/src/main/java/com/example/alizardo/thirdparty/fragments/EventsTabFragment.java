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

import com.example.alizardo.thirdparty.adapters.MyAdapter;
import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.pojo.Event;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventsTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventsTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    EventsTabFragment extends Fragment {

    private static final String ARG_PAGE_NUMBER = "page_number";

    private OnFragmentInteractionListener mListener;


    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    TabLayout tabLayout;

    public EventsTabFragment() {
        // Required empty public constructor
    }

    public static EventsTabFragment newInstance(int page) {
        EventsTabFragment fragment = new EventsTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_events_tab, container, false);


        this.mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);

        // Initialize dummy data
        List<Event> myDataset = new ArrayList<>();
        myDataset.add(new Event("Passagem de Ano no Choupal", "Lizardo", "Marina e Imperial na casa do Lizardo", "12/01/2017 18:30", "12/01/2017 20:30", "10", "https://www.papodebar.com/wp-content/uploads/2015/05/drinks.jpg"));
        myDataset.add(new Event("Feiras Novas 2017", "Eduardo", "Festas de Ponte de Lima com Vinhaça da boa", "12/01/2017 18:30", "12/01/2017 20:30", "16", "http://www.cm-pontedelima.pt/imagens/noticias/setembro2011/Feiras_Novas_2011_Noite2.jpg"));

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
