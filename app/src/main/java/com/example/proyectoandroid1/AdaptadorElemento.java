package com.example.proyectoandroid1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdaptadorElemento extends BaseAdapter {
    private Context contexto;
    private IncidenciasLista list;
    private LayoutInflater inflador;
    private TextView descripcion, nombre,fecha,telefono,estado;

    public AdaptadorElemento(Context cont, IncidenciasLista lista) {
        this.contexto = cont;
        this.list=lista;
        inflador= (LayoutInflater) this.contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.tamanio();
    }

    @Override
    public Object getItem(int position) {
        return list.obtenerElemento(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=inflador.inflate(R.layout.elementos_lista_all,null);
        }
        LectorGsonIncidencias.IncidenciasBean incidencia=list.obtenerElemento(position);


        descripcion =convertView.findViewById(R.id.descripcion2);
        descripcion.setText(incidencia.getDescripcion());

        fecha = convertView.findViewById(R.id.fecha2);
        fecha.setText(incidencia.getFecha());

        nombre =convertView.findViewById(R.id.nombre2);
        nombre.setText(String.valueOf(incidencia.getAvisonombre()));

        telefono = convertView.findViewById(R.id.telefono2);
        telefono.setText(incidencia.getAvisotfno());

        estado = convertView.findViewById(R.id.estado2);
        estado.setText(incidencia.getEstado());

        return convertView;
    }
}
