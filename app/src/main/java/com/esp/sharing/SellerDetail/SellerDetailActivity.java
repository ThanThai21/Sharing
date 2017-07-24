package com.esp.sharing.SellerDetail;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.esp.sharing.Database.DatabaseManager;
import com.esp.sharing.Helper.DialogHelper;
import com.esp.sharing.Model.Product;
import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SellerDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CircleImageView avatarView;
    private TextView nameView;
    private TextView locationView;
    private TextView infoView;
    private RecyclerView recyclerView;
    private Adapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Product> itemList;

    private View callView;
    private View messageView;
    private View favoriteView;
    private ImageView favoriteIcon;
    private boolean isFavorite = false;

    private Seller seller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_detail);
        getData();
        initToolbar();
        initHeader();
        initRecyclerView();
        initContact();
    }

    private void initContact() {
        callView = findViewById(R.id.call);
        messageView = findViewById(R.id.message);
        favoriteView = findViewById(R.id.favorite);
        favoriteIcon = (ImageView) favoriteView.findViewById(R.id.favorite_icon);
        if (!isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_nofavorite);
            isFavorite = false;
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite);
            isFavorite = true;
        }
        callView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.createMessageDialog(SellerDetailActivity.this, "Gọi", "Tính năng sắp xuất hiện");
            }
        });
        messageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.createMessageDialog(SellerDetailActivity.this, "Gọi", "Tính năng sắp xuất hiện");
            }
        });
        favoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    favoriteIcon.setImageResource(R.drawable.ic_nofavorite);
                    isFavorite = false;
                } else {
                    favoriteIcon.setImageResource(R.drawable.ic_favorite);
                    isFavorite = true;
                }
            }
        });
    }

    private void initHeader() {
        avatarView = (CircleImageView) findViewById(R.id.detail_avatar);
        nameView = (TextView) findViewById(R.id.detail_seller_name);
        infoView = (TextView) findViewById(R.id.detail_seller_info);
        locationView = (TextView) findViewById(R.id.detail_seller_location);
        nameView.setText(seller.getName());
        infoView.setText(seller.getPhone());
        locationView.setText(seller.getLocation());
    }

    private void getData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("seller");
        int id = bundle.getInt("id");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");
        String location = bundle.getString("location");
        String description = bundle.getString("description");
        seller = new Seller(id, name, phone, location, "", description);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
        adapter = new Adapter(this, parseJsonToArray(getJson()));
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new GravitySnapHelper(Gravity.START);
        snapHelper.attachToRecyclerView(recyclerView);
    }

    private ArrayList<Product> parseJsonToArray(String json) {
        ArrayList<Product> itemList = new ArrayList<Product>();
        try {
            JSONObject root = new JSONObject(json);
            JSONArray products = root.getJSONArray("shop");
            for (int i = 0; i < products.length(); i++) {
                JSONObject item = products.getJSONObject(i);
                itemList.add(new Product(item.getInt("id"), item.getString("name"), item.getInt("count")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    private String getJson() {
        return "{\n" +
                "  \"shop\": [\n" +
                "    {\n" +
                "      \"id\" : \"123\",\n" +
                "      \"name\" : \"Áo sơ mi\",\n" +
                "      \"count\" : \"10\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\" : \"12\",\n" +
                "      \"name\": \"Mũ lưỡi chai\",\n" +
                "      \"count\" : \"20\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\" : \"34\",\n" +
                "      \"name\" : \"Giầy\",\n" +
                "      \"count\" : \"5\"\n" +
                "    }, \n" +
                "    {\n" +
                "      \"id\" : \"56\",\n" +
                "      \"name\" : \"Quần jean\",\n" +
                "      \"count\" : \"8\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }
}
