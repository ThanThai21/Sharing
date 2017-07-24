package com.esp.sharing.SellerDetail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esp.sharing.DemoActivity;
import com.esp.sharing.Model.Product;
import com.esp.sharing.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder>{

    private Context context;
    private List<Product> productList;

    public Adapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.detail_row_item, null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(context.getResources(),
                resId);
    }

    private void showDialog() {
        ViewPager viewPager;
        SwipeImageAdapter adapter;
        Bitmap[] image = new Bitmap[] {getBitmap(R.drawable.img1), getBitmap(R.drawable.img2)};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_buy, null);

        viewPager = (ViewPager) view.findViewById(R.id.buy_view_pager);
        adapter = new SwipeImageAdapter(context, image);
        viewPager.setAdapter(adapter);

        ImageButton up = (ImageButton) view.findViewById(R.id.vol_up);
        ImageButton down = (ImageButton) view.findViewById(R.id.vol_down);
        final TextView quantity = (TextView) view.findViewById(R.id.quantity);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                quantity.setText(n + 1 + "");
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Integer.parseInt(quantity.getText().toString());
                if (n > 0) {
                    quantity.setText(n - 1 + "");
                }
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        Display display =((WindowManager)context.getSystemService(context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        int height=display.getHeight();
        dialog.getWindow().setLayout((19*width)/20,(4*height)/5);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView productImage;
        public TextView productName;

        public ItemViewHolder(View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.product_image_view);
            productName = (TextView) itemView.findViewById(R.id.product_name);
        }
    }
}
