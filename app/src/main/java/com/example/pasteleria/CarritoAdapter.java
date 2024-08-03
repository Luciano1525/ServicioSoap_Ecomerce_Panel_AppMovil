package com.example.pasteleria;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CarritoAdapter extends BaseAdapter {
    private Context context;
    private List<Productos> productos;
    private LayoutInflater inflater;

    public CarritoAdapter(Context context, List<Productos> productos) {
        this.context = context;
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_carrito, parent, false);
            holder = new ViewHolder();
            holder.ivImagen = convertView.findViewById(R.id.ivImagen);
            holder.tvNombre = convertView.findViewById(R.id.tvNombre);
            holder.tvCantidad = convertView.findViewById(R.id.tvCantidad);
            holder.tvPrecio = convertView.findViewById(R.id.tvPrecio);
            holder.btnEliminar = convertView.findViewById(R.id.btnEliminar);
            holder.btnDisminuirCantidad = convertView.findViewById(R.id.btnDisminuirCantidad);
            holder.btnAumentarCantidad = convertView.findViewById(R.id.btnAumentarCantidad);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener el producto actual
        final Productos producto = productos.get(position);

        // Mostrar los datos en los componentes del layout
        holder.ivImagen.setImageResource(producto.getImagen());
        holder.tvNombre.setText(producto.getNombre());
        holder.tvCantidad.setText(String.valueOf(producto.getCantidad()));
        holder.tvPrecio.setText(producto.getPrecio());

        // Configurar el listener para el botón de eliminar
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Eliminar el producto del carrito
                productos.remove(position);
                notifyDataSetChanged();
                // Actualizar el total después de eliminar un producto
                ((carrito) context).actualizarTotal();
                // Actualizar la cantidad en la base de datos
                ((carrito) context).eliminarProductoDeBaseDeDatos(producto.getNombre());
            }
        });

        // Configurar el listener para el botón de disminuir cantidad
        holder.btnDisminuirCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidadActual = producto.getCantidad();
                if (cantidadActual > 1) {
                    producto.setCantidad(cantidadActual - 1);
                    holder.tvCantidad.setText(String.valueOf(producto.getCantidad()));
                    notifyDataSetChanged();
                    ((carrito) context).actualizarTotal();

                    // Actualizar la cantidad en la base de datos
                    ((carrito) context).actualizarCantidadEnBaseDeDatos(producto.getNombre(), producto.getCantidad());
                }
            }
        });

        // Configurar el listener para el botón de aumentar cantidad
        holder.btnAumentarCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidadActual = producto.getCantidad();
                producto.setCantidad(cantidadActual + 1);
                holder.tvCantidad.setText(String.valueOf(producto.getCantidad()));
                notifyDataSetChanged();
                ((carrito) context).actualizarTotal();

                // Actualizar la cantidad en la base de datos
                ((carrito) context).actualizarCantidadEnBaseDeDatos(producto.getNombre(), producto.getCantidad());
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView ivImagen;
        TextView tvNombre;
        TextView tvCantidad;
        TextView tvPrecio;
        Button btnEliminar;
        Button btnDisminuirCantidad;
        Button btnAumentarCantidad;
    }

}
