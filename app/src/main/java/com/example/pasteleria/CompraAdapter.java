package com.example.pasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CompraAdapter extends ArrayAdapter<Compra> {
    private Context context;
    private List<Compra> compraList;

    public CompraAdapter(Context context, List<Compra> compras) {
        super(context, 0, compras);
        this.context = context;
        this.compraList = compras;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar la vista del elemento de la lista
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_p, parent, false);
        }

        // Obtener los elementos de la vista
        ImageView imagenView = convertView.findViewById(R.id.Imagenlist);
        TextView nombreView = convertView.findViewById(R.id.Productolist);
        TextView estadoView = convertView.findViewById(R.id.Estado);
        TextView precioView = convertView.findViewById(R.id.Preciolist);

        // Obtener la compra actual
        Compra compra = compraList.get(position);

        // Configurar los datos en los elementos de la vista
        nombreView.setText(compra.getNombre());
        estadoView.setText("Precio: $" + compra.getPrecio());
        precioView.setText("Fecha :" + compra.getFecha());

        // Cargar la imagen usando Glide
        Glide.with(context)
                .load(compra.getImagen())
                .into(imagenView);

        return convertView;
    }
}
