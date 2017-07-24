package com.esp.sharing.Search;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.esp.sharing.Database.DatabaseManager;
import com.esp.sharing.Home.HomeActivity;
import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;
import com.esp.sharing.SellerDetail.SellerDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private static final int RC_DETAIL = 2;
    Toolbar toolbar;
    SearchView searchView;

    private ListView listView;
    private Adapter adapter;
    private List<Seller> sellerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initToolbar();
        initListView();

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

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        setResult(HomeActivity.RC_SEARCH);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void initListView() {
        listView = (ListView) findViewById(R.id.search_result_listview);
        sellerList = new ArrayList<Seller>();
        adapter = new Adapter(this, R.layout.search_row_item, sellerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Seller seller = adapter.getItem(position);
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
        });
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
                ArrayList<Seller> results = DatabaseManager.getAllSeller(query);
                if (results != null) {
                    adapter.notifyDataSetChanged(results);
                } else {
                    Toast.makeText(SearchActivity.this, "Không tìm thấy nhà cung cấp", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
