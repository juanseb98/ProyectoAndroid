package com.example.proyectoandroid1;

import java.util.ArrayList;

public class EstadoLista {
    private ArrayList<LectorGsonEstado.EstadosBean> listado;

    public EstadoLista() {
        this.listado = new ArrayList<LectorGsonEstado.EstadosBean>();
    }

    public void aniadirEstado(LectorGsonEstado.EstadosBean p){
        listado.add(p);
    }

    public LectorGsonEstado.EstadosBean obtenerElemento(int num){
        LectorGsonEstado.EstadosBean selected;
        selected = listado.get(num);
        return selected;
    }

    public ArrayList<LectorGsonEstado.EstadosBean> getListado(){
        return listado;
    }
    public int tamanio(){
        return listado.size();
    }
}
