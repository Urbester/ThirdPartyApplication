
package com.example.alizardo.thirdparty.activities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alizardo.thirdparty.R;
import com.example.alizardo.thirdparty.adapters.MyAdapter;
import com.example.alizardo.thirdparty.pojo.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {

    private ListView listView;
    private MyAppAdapter myAppAdapter;
    private ArrayList<Event> postArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Search");

        listView = (ListView) findViewById(R.id.listView);
        postArrayList = new ArrayList<>();
        postArrayList.add(new Event("Passagem de Ano no Choupal", "Lizardo", "Marina e Imperial na casa do Lizardo", "12/01/2017 18:30", "12/01/2017 20:30", "10", "https://www.papodebar.com/wp-content/uploads/2015/05/drinks.jpg"));
        postArrayList.add(new Event("Feiras Novas 2017", "Eduardo", "Festas de Ponte de Lima com Vinhaça da boa", "12/01/2017 18:30", "12/01/2017 20:30", "16", "http://www.cm-pontedelima.pt/imagens/noticias/setembro2011/Feiras_Novas_2011_Noite2.jpg"));

        myAppAdapter = new MyAppAdapter(postArrayList, SearchActivity.this);
        listView.setAdapter(myAppAdapter);

    }


    public class MyAppAdapter extends BaseAdapter {

        private RecyclerView mRecyclerView;
        private MyAdapter mAdapter;
        private RecyclerView.LayoutManager mLayoutManager;


        public List<Event> parkingList;

        public Context context;
        ArrayList<Event> arraylist;

        private MyAppAdapter(List<Event> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
            arraylist = new ArrayList<Event>();
            arraylist.addAll(parkingList);

        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            // Inflate the layout for this fragment
            View rowView = inflater.inflate(R.layout.item_search, parent, false);
            this.mRecyclerView = (RecyclerView) rowView.findViewById(R.id.my_recycler_view);

            // specify an adapter
            this.mAdapter = new MyAdapter(this.parkingList);

            // use a linear layout manager
            this.mLayoutManager = new LinearLayoutManager(context);

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            this.mRecyclerView.setHasFixedSize(true);
            this.mRecyclerView.setLayoutManager(this.mLayoutManager);
            this.mRecyclerView.setAdapter(this.mAdapter);

            return rowView;

            /*
            View rowView = convertView;
            ViewHolder viewHolder;

            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.item_search, null);

                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.txtTitle = (TextView) rowView.findViewById(R.id.title);
                viewHolder.txtSubTitle = (TextView) rowView.findViewById(R.id.subtitle);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.txtTitle.setText(parkingList.get(position).getTitle() + "");
            viewHolder.txtSubTitle.setText(parkingList.get(position).getHost() + "");
            return rowView;
*/

        }

        public void filter(String charText) {

            charText = charText.toLowerCase(Locale.getDefault());

            parkingList.clear();
            if (charText.length() == 0) {
                parkingList.addAll(arraylist);

            } else {
                for (Event postDetail : arraylist) {
                    if (charText.length() != 0 && postDetail.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    } else if (charText.length() != 0 && postDetail.getDescription().toLowerCase(Locale.getDefault()).contains(charText)) {
                        parkingList.add(postDetail);
                    }
                }
            }
            notifyDataSetChanged();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //*** setOnQueryTextFocusChangeListener ***
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchQuery) {
                myAppAdapter.filter(searchQuery.toString().trim());
                listView.invalidate();
                return true;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed
                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded
                return true;  // Return true to expand action view
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.action_search) {
            return super.onOptionsItemSelected(item);
        } else {
            onBackPressed();
            return true;
        }
    }


}
