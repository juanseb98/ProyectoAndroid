package com.example.proyectoandroid1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PantallaListarIncidenciasEstado extends Fragment {

    private EstadoLista listaEstado;
    private IncidenciasLista lista;
    private Comunicador comunicacion;
    private ListView listaDatos;

    private LectorGsonEstado gsonEstado;
    private LectorGsonIncidencias gsonIncidencias;
    private String URLEstados ="https://sevilla2017.000webhostapp.com/comunidad/get_all_estados.php";
    private String URL ="https://sevilla2017.000webhostapp.com/comunidad/get_all_inci_estado.php";
    private AsyncHttpClient lecturaasyn;

    View vista;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.pantalla_listar_estado, container, false);


        return vista;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        comunicacion = (Comunicador) getActivity();
        spinner = vista.findViewById(R.id.spinner);
        metodo();
    }

    private void metodo() {
        if(comunicacion.getComunidad().equals("-123")){
            Toast.makeText(vista.getContext(), "Debes Loguearte primero", Toast.LENGTH_SHORT).show();
        }else {
            listaEstado = new EstadoLista();
            lecturaasyn = new AsyncHttpClient();

            lecturaasyn.get(vista.getContext(), URLEstados, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String newresponse = new String(responseBody);
                    Gson gson = new Gson();
                    gsonEstado = gson.fromJson(newresponse, LectorGsonEstado.class);
                    int succes = gsonEstado.getSuccess();
                    ArrayList<String> estadoStr = new ArrayList<String>();

                    if (succes == 1) {
                        List<LectorGsonEstado.EstadosBean> estados = gsonEstado.getEstados();

                        for (LectorGsonEstado.EstadosBean estado : estados) {
                            estadoStr.add(estado.getEstado());
                        }

                        ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter(vista.getContext(),
                                android.R.layout.simple_spinner_item, estadoStr);
                        spinner.setAdapter(adapter1);

                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                consultarIncidencias(position + 1);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

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

    private void consultarIncidencias(int id) {
        lista = new IncidenciasLista();
        lecturaasyn = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("comu",comunicacion.getComunidad());
        requestParams.put("estado",id);

        lecturaasyn.get(vista.getContext(), URL, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String newresponse = new String(responseBody);
                Gson gson = new Gson();
                gsonIncidencias = gson.fromJson(newresponse, LectorGsonIncidencias.class);
                int succes = gsonIncidencias.getSuccess();
                if(succes==1){
                    List<LectorGsonIncidencias.IncidenciasBean> incidencias = gsonIncidencias.getIncidencias();
                    for (LectorGsonIncidencias.IncidenciasBean incidencia : incidencias) {

                        LectorGsonIncidencias.IncidenciasBean inci = new LectorGsonIncidencias.IncidenciasBean(incidencia.getId(),
                                incidencia.getDescripcion(), incidencia.getFecha(),
                                incidencia.getAvisonombre(), incidencia.getAvisotfno(), incidencia.getEstado());
                        lista.aniadirIncidencia(inci);
                    }
                    Toast.makeText(vista.getContext(), "añadido "+lista.tamanio(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(vista.getContext(), "No hay datos", Toast.LENGTH_SHORT).show();
                }
                AdaptadorElemento adaptador = new AdaptadorElemento(vista.getContext(), lista);
                listaDatos = vista.findViewById(R.id.listas_estado);
                listaDatos.setAdapter(adaptador);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

    }
}
