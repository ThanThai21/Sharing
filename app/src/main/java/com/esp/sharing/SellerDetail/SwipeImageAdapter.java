package com.esp.sharing.SellerDetail;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.esp.sharing.R;

public class SwipeImageAdapter extends PagerAdapter{

    private Context context;
    private Bitmap[] image;

    public SwipeImageAdapter(Context context, Bitmap[] image) {
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_image, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.product_image_detail);
        imageView.setImageBitmap(image[position]);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
