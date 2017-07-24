package com.esp.sharing.Search;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SearchView;
import android.widget.TextView;

import com.esp.sharing.Helper.FavoriteHandler;
import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter  extends ArrayAdapter<Seller>{

    private Context context;
    private List<Seller> sellerList;
    private FavoriteHandler db;
    private ArrayList<String> list;

    public Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Seller> sellerList) {
        super(context, resource, sellerList);
        this.context = context;
        this.sellerList = sellerList;
        db = new FavoriteHandler((SearchActivity)context);
        list = db.favSellers();
    }

    public void notifyDataSetChanged(List<Seller> sellerList) {
        this.sellerList = sellerList;
        list = db.favSellers();
        super.notifyDataSetChanged();
    }

    @Nullable
    @Override
    public Seller getItem(int position) {
        return sellerList.get(position);
    }

    @Override
    public int getCount() {
        return sellerList.size();
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Seller seller = sellerList.get(position);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.search_row_item, null);
        }
        TextView name = (TextView) view.findViewById(R.id.seller_item_name);
        TextView address = (TextView) view.findViewById(R.id.seller_item_add);
        TextView phone = (TextView) view.findViewById(R.id.seller_item_phone);
        final CheckBox favoriteBtn = (CheckBox) view.findViewById(R.id.seller_item_favorite);
        CardView cardView = (CardView) view.findViewById(R.id.seller_item);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SearchActivity) context).onItemClick(position);
            }
        });
        if (list.contains(String.valueOf(seller.getId()))) {
            favoriteBtn.setChecked(true);
        } else {
            favoriteBtn.setChecked(false);
        }
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = favoriteBtn.isChecked();
                if (isChecked) {
                    db.addFav_Seller(String.valueOf(seller.getId()));
                    list.add(String.valueOf(seller.getId()));
                } else {
                    db.deleteFav_Seller(String.valueOf(seller.getId()));
                    list.remove(String.valueOf(seller.getId()));
                }
            }
        });
        name.setText(seller.getName());
        address.setText(seller.getLocation());
        phone.setText(seller.getPhone());
        return view;
    }
}
