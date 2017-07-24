package com.esp.sharing.Search;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.esp.sharing.Model.Seller;
import com.esp.sharing.R;

import java.util.List;

public class Adapter  extends ArrayAdapter<Seller>{

    private Context context;
    private List<Seller> sellerList;

    public Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Seller> sellerList) {
        super(context, resource, sellerList);
        this.context = context;
        this.sellerList = sellerList;
    }

    public void notifyDataSetChanged(List<Seller> sellerList) {
        this.sellerList = sellerList;
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
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Seller seller = sellerList.get(position);
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.search_row_item, null);
        }
        TextView name = (TextView) view.findViewById(R.id.search_name);
        TextView description = (TextView) view.findViewById(R.id.search_description);
        name.setText(seller.getName());
        description.setText(seller.getPhone() + " - " + seller.getDescription());
        return view;

    }
}
