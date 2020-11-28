package com.example.semanatrece;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactoAdaptador extends BaseAdapter {

    private ArrayList<Contacto> contactos;

    public ContactoAdaptador() {
        contactos = new ArrayList<>();
    }

    public void limpiar() {

        contactos.clear();
        notifyDataSetChanged();

    }

    public void addContacto(Contacto contacto) {

        contactos.add(contacto);
        notifyDataSetChanged();

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
    public View getView(int position, View converView, ViewGroup lista) {

        LayoutInflater inflater = LayoutInflater.from(lista.getContext());
        View celdaView = inflater.inflate(R.layout.celda, null);

        Contacto contacto = contactos.get(position);

        Button borrar = celdaView.findViewById(R.id.eliminar);
        Button llamar = celdaView.findViewById(R.id.llamar);

        TextView nombreView = celdaView.findViewById(R.id.nombreC);
        TextView numeroView = celdaView.findViewById(R.id.numeroC);

        nombreView.setText(contacto.getNombre());
        numeroView.setText(contacto.getNumero());

        return  celdaView;

    }
}
