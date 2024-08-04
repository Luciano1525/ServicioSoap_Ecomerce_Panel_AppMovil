package com.example.pasteleria;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class TicketAdapter extends ArrayAdapter<Tickets> {
    private Context context;
    private List<Tickets> ticketsList;

    public TicketAdapter(Context context, List<Tickets> tickets) {
        super(context, 0, tickets);
        this.context = context;
        this.ticketsList = tickets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reutilizar la vista si es posible
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_p, parent, false);
        }

        // Obtener el objeto Tickets en la posición actual
        Tickets ticket = ticketsList.get(position);

        // Encontrar las vistas del layout
        ImageView imagenList = convertView.findViewById(R.id.Imagenlist);
        TextView productoList = convertView.findViewById(R.id.Productolist);
        TextView estado = convertView.findViewById(R.id.Estado);
        TextView precioList = convertView.findViewById(R.id.Preciolist);

        // Asignar valores a las vistas
        productoList.setText(ticket.getNombre());
        estado.setText("Cantidad: " + ticket.getCantidad()); // O ajusta según tus necesidades
        precioList.setText(ticket.getPrecio());

        return convertView;
    }
}
