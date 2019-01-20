package com.example.proyectoandroid1;

import java.util.ArrayList;

public class IncidenciasLista {
    private ArrayList<LectorGsonIncidencias.IncidenciasBean> listado;

    public IncidenciasLista() {
        this.listado = new ArrayList<LectorGsonIncidencias.IncidenciasBean>();
    }

    public void aniadirIncidencia(LectorGsonIncidencias.IncidenciasBean p){
        listado.add(p);
    }

    public LectorGsonIncidencias.IncidenciasBean obtenerElemento(int num){
        LectorGsonIncidencias.IncidenciasBean selected;
        selected = listado.get(num);
        return selected;
    }

    public ArrayList<LectorGsonIncidencias.IncidenciasBean> getListado(){
        return listado;
    }
    public int tamanio(){
        return listado.size();
    }
}
