package com.esp.sharing.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.esp.sharing.Database.Database;
import com.esp.sharing.Database.DatabaseManager;
import com.esp.sharing.Database.LocalDatabase;
import com.esp.sharing.Login.LoginActivity;
import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;
import com.esp.sharing.Search.SearchActivity;
import com.esp.sharing.Setting.SettingActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final int RC_PERMISSION = 1;
    public static final int RC_SEARCH = 2;
    private GoogleMap mMap;

    private Toolbar toolbar;
    private ImageButton toolbarSearchButton;

//    private ArrayList<Marker> historyMarkerList;
//    private ArrayList<Marker> favoriteMarkerList;
//    private ArrayList<Seller> historyList;
//    private ArrayList<Seller> favoriteList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDB();
        initToolbar();
        initMap();

    }

    private void initDB() {
        DatabaseManager.database = new Database(this);
        DatabaseManager.localDatabase = new LocalDatabase(this);
//        historyList = DatabaseManager.getHistory();
//        favoriteList = DatabaseManager.getFavorite();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        toolbarSearchButton = (ImageButton) toolbar.findViewById(R.id.toolbar_search_button);
        toolbarSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivityForResult(intent, RC_SEARCH);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        View mapView = mapFragment.getView();
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        mapFragment.getMapAsync(this);

//        historyMarkerList = new ArrayList<>();
//        favoriteMarkerList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (id == R.id.nav_setting) {
            Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }else if (id == R.id.nav_favorite) {
//            showFavorite();
        } else if (id == R.id.nav_history) {
//            showHistory();
        } else if (id == R.id.nav_complain) {

        } else if (id == R.id.nav_rate) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logout) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
/*

    private void showFavorite() {
        for (Marker historyMarker : historyMarkerList) {
            historyMarker.setVisible(false);
        }
        if (favoriteList.size() == 0) {
            Toast.makeText(this, "Bạn không có yêu thích", Toast.LENGTH_SHORT).show();
        }
        for (Marker marker : favoriteMarkerList) {
            marker.setVisible(true);
        }
    }

    private void showHistory() {
        for (Marker favoriteMarker : favoriteMarkerList) {
            favoriteMarker.setVisible(false);
        }
        if (historyList.size() == 0) {
            Toast.makeText(this, "Bạn không có lịch sử", Toast.LENGTH_SHORT).show();
            return;
        }
        for (Marker marker : historyMarkerList) {
            marker.setVisible(true);
        }
    }
*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, RC_PERMISSION);
            return;
        }
        mMap.setMyLocationEnabled(true);
        LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = service.getBestProvider(criteria, false);
        Location location = service.getLastKnownLocation(provider);
        if (location != null) {
            LatLng userLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
            Geocoder geocoder = new Geocoder(this);
        }
//        for (Seller seller : historyList) {
//            try {
//                Address address = geocoder.getFromLocationName(seller.getLocation(), 1).get(0);
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(seller.getName()));
//                marker.setVisible(false);
//                historyMarkerList.add(marker);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        for (Seller seller : favoriteList) {
//            try {
//                Address address = geocoder.getFromLocationName(seller.getLocation(), 1).get(0);
//                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(seller.getName()));
//                marker.setVisible(false);
//                favoriteMarkerList.add(marker);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SEARCH) {
            List<Seller> list = DatabaseManager.getHistory();
            Geocoder geocoder = new Geocoder(this);
//            int oldLenght = historyList.size();
//            if (oldLenght >= list.size()) {
//                return;
//            }
//            for (int i = oldLenght - 1; i < list.size(); i++) {
//                Seller seller = list.get(i);
//                historyList.add(seller);
//                try {
//                    Address address = geocoder.getFromLocationName(seller.getLocation(), 1).get(0);
//                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(seller.getName()));
//                    marker.setVisible(false);
//                    historyMarkerList.add(marker);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }
}
