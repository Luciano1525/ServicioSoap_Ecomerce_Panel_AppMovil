package com.example.pasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Contacto> lst;

    public CustomAdapter(Context context, List<Contacto> lst) {
        this.context = context;
        this.lst = lst;
    }

    @Override
    public int getCount() {
        return lst.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView Imagenlist;
        TextView Productolist;
        TextView Categorialist;
        TextView Preciolist;
        Contacto c = lst.get(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_p, null);
            Imagenlist = view.findViewById(R.id.Imagenlist);
            Productolist = view.findViewById(R.id.Productolist);
            Categorialist = view.findViewById(R.id.Categorialist);
            Preciolist = view.findViewById(R.id.Preciolist);

            Imagenlist.setImageResource(c.Imagenlist);
            Productolist.setText(c.Productolist);
            Categorialist.setText(c.Categorialist);
            Preciolist.setText(c.Preciolist);
        }

        return view;
    }
}
