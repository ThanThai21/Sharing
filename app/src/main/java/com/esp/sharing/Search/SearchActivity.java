package com.esp.sharing.Search;

import android.app.AlertDialog;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.esp.sharing.Database.Database;
import com.esp.sharing.Database.DatabaseManager;
import com.esp.sharing.Helper.HistoryManager;
import com.esp.sharing.Home.HomeActivity;
import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;
import com.esp.sharing.SellerDetail.SellerDetailActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final int RC_DETAIL = 2;
    Toolbar toolbar;
    SearchView searchView;

    private ListView listView;
    private Adapter adapter;
    private HistoryManager historyManager;
    private ProgressBar progressBar;
    private TextView noDataNotification;
    private List<Seller> sellerList;
    private String preText="";
    private boolean enableSearch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        initListView();
        initHistory();

    }

    public void initHistory(){

        try {
            historyManager = new HistoryManager(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                setResult(HomeActivity.RC_SEARCH);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        noDataNotification = (TextView) findViewById(R.id.no_data_notification);
        progressBar = (ProgressBar) findViewById(R.id.progress_search);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        setResult(HomeActivity.RC_SEARCH);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //searchView.clearFocus();
        if (searchView != null)
            searchView.setQuery("",false);
        try {
            sellerList = historyManager.getSellers();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        updateList();
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.search_result_listview);
        sellerList = new ArrayList<Seller>();
        adapter = new Adapter(this, R.layout.search_row_item, sellerList);
        listView.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem itemSearch = menu.findItem(R.id.search_view);
        searchView = (SearchView) itemSearch.getActionView();
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(getColor(R.color.colorToolbarWhite));
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocus();
        searchView.requestFocusFromTouch();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && query.length() > 0){
                    onTextChange(query);
                } else {
                    sellerList.clear();
                    preText="";
                    try {
                        sellerList = historyManager.getSellers();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateList();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null && newText.length() > 0){
                    onTextChange(newText);
                } else {
                    sellerList.clear();
                    preText="";
                    try {
                        sellerList = historyManager.getSellers();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    updateList();
                }
                return true;
            }
        });
        return true;
    }


    // Search danh sách
    public void onTextChange(String text){
        if (isInternetAvailable(this)) {
            if (enableSearch && !text.equals(preText)) {
                preText = text;
                sellerList.clear();
                ProgressTask task = new ProgressTask(SearchActivity.this);
                task.execute(text);
                enableSearch = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enableSearch = true;
                        String st = searchView.getQuery().toString();
                        if (!st.equals(preText) && st.length() > 0) {
                            onTextChange(st);
                        }
                    }
                }, 2000);
            }
        } else{
            showInternetDialog();
        }
    }


    public void showNotification(){
        noDataNotification.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    public void showListSellers(){
        noDataNotification.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    public void showProgressBar(){
        noDataNotification.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    public class ProgressTask extends AsyncTask<String,String,ArrayList<Seller>> {
        public ArrayList<Seller> sellers;
        public SearchActivity activity;

        public ProgressTask(SearchActivity activity) {
            this.activity = activity;
        }

        @Override
        protected ArrayList<Seller> doInBackground(String... params) {
            String st = params[0];
            sellers = DatabaseManager.getAllSeller(st);
            publishProgress(st);
            SystemClock.sleep(200);
            return sellers;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            activity.showProgressBar();
            Log.d("HOA","Searching...");
        }

        @Override
        protected void onPostExecute(ArrayList<Seller> sellers) {
            activity.sellerList = sellers;
            updateList();
        }
    }

    public boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    // Update danh sách
    public void updateList(){
        if (sellerList == null || sellerList.size() < 1){
            showNotification();
        } else {
            showListSellers();
        }
        adapter.notifyDataSetChanged(sellerList);
    }
    // Show cảnh báo không có internet
    private void showInternetDialog() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setPositiveButton("Go Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            }});
        builder.setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onTextChange(searchView.getQuery().toString());
            }
        });
        LayoutInflater inflater = this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_no_internet, null));
        builder.show();
    }

    // Bắt sự kiện click vào item
    public void onItemClick(int position){
        Seller seller = adapter.getItem(position);
        // Add lịch sử
        try {
            historyManager.addHistory(seller);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(SearchActivity.this, SellerDetailActivity.class);
        //put data to intent
        Bundle bundle = new Bundle();
        bundle.putInt("id", seller.getId());
        bundle.putString("name", seller.getName());
        bundle.putString("phone", seller.getPhone());
        bundle.putString("location", seller.getLocation());
        bundle.putString("description", seller.getDescription());
        intent.putExtra("seller", bundle);
        //start new activity;
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
