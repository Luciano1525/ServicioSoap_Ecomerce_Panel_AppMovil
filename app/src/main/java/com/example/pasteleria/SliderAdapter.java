package com.example.pasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class SliderAdapter extends PagerAdapter {
    private List<Pastel> pasteles;
    private String urlBase; // Base URL para las imágenes

    public SliderAdapter(List<Pastel> pasteles, String urlBase) {
        this.pasteles = pasteles;
        this.urlBase = urlBase;
    }

    @Override
    public int getCount() {
        return pasteles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View view = inflater.inflate(R.layout.slider_item, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        Pastel pastel = pasteles.get(position);

        // Concatenar la base URL con la URL parcial
        String imageUrl = urlBase + pastel.getImagen();
        Glide.with(container.getContext())
                .load(imageUrl)
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void setPasteles(List<Pastel> pasteles) {
        this.pasteles = pasteles;
        notifyDataSetChanged();
    }
}
