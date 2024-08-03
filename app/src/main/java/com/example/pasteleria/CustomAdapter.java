package com.example.pasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private final Context context;
    private final List<Contacto> contactos;

    public CustomAdapter(Context context, List<Contacto> contactos) {
        this.context = context;
        this.contactos = contactos;
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return contactos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflar el layout para cada item de la lista
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_p, parent, false);
        }

        // Obtener referencias a las vistas del layout
        TextView nombreView = convertView.findViewById(R.id.Productolist);
        TextView estadoView = convertView.findViewById(R.id.Estado);
        TextView precioView = convertView.findViewById(R.id.Preciolist);
        ImageView imagenView = convertView.findViewById(R.id.Imagenlist);

        // Obtener el producto actual
        Contacto contacto = contactos.get(position);

        // Configurar las vistas con los datos del producto
        nombreView.setText(contacto.getNombre());
        estadoView.setText(contacto.getEstado());
        precioView.setText(contacto.getPrecio());

        // Cargar la imagen usando Glide
        Glide.with(context)
                .load(contacto.getImagen())
                .into(imagenView);

        return convertView;
    }

}
