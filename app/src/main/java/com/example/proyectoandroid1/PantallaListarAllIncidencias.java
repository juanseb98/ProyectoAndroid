package com.example.proyectoandroid1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;


public class PantallaListarAllIncidencias extends Fragment {

    private IncidenciasLista lista;
    private ListView listaDatos;
    private Comunicador comunicacion;

    private LectorGsonIncidencias gsonIncidencias;
    private String URL ="https://sevilla2017.000webhostapp.com/comunidad/get_all_incidencias.php";
    private AsyncHttpClient lecturaasyn;

    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.pantalla_listar_all_incidencias, container, false);


        return vista;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comunicacion = (Comunicador) getActivity();
        metodo();
    }

    private void metodo(){
        if(comunicacion.getComunidad().equals("-123")){
            Toast.makeText(vista.getContext(), "Debes Loguearte primero", Toast.LENGTH_SHORT).show();
        }else {
            lista = new IncidenciasLista();
            lecturaasyn = new AsyncHttpClient();
            RequestParams requestParams = new RequestParams();
            requestParams.put("comu", comunicacion.getComunidad());

            lecturaasyn.get(vista.getContext(), URL, requestParams, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String newresponse = new String(responseBody);
                    Gson gson = new Gson();

                    gsonIncidencias = gson.fromJson(newresponse, LectorGsonIncidencias.class);
                    int succes = gsonIncidencias.getSuccess();
                    if (succes == 1) {
                        List<LectorGsonIncidencias.IncidenciasBean> incidencias = gsonIncidencias.getIncidencias();

                        for (LectorGsonIncidencias.IncidenciasBean incidencia : incidencias) {

                            LectorGsonIncidencias.IncidenciasBean inci = new LectorGsonIncidencias.IncidenciasBean(incidencia.getId(),
                                    incidencia.getDescripcion(), incidencia.getFecha(),
                                    incidencia.getAvisonombre(), incidencia.getAvisotfno(), incidencia.getEstado());
                            lista.aniadirIncidencia(inci);
                        }

                        AdaptadorElemento adaptador = new AdaptadorElemento(vista.getContext(), lista);

                        listaDatos = vista.findViewById(R.id.lista_all);
                        listaDatos = vista.findViewById(R.id.lista_all);
                        listaDatos.setAdapter(adaptador);
                        listaDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        });

                        Toast.makeText(vista.getContext(), "Añadidos " + lista.tamanio(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(vista.getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });
        }
    }

}