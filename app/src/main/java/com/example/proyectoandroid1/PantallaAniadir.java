package com.example.proyectoandroid1;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

public class PantallaAniadir extends Fragment {
    private LectorGsonGenerico lectorGson;
    private String URL ="https://sevilla2017.000webhostapp.com/comunidad/create_incidencia.php";
    private AsyncHttpClient lecturaasyn;
    private Comunicador comunicacion;

    Button botonAniadir;
    EditText comunidad,descrip, telefonoaviso,nombre;
    View vista;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        vista = inflater.inflate(R.layout.pantalla_aniadir, container, false);

        return  vista;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        comunicacion = (Comunicador) getActivity();
        botonAniadir = vista.findViewById(R.id.boton_aniadir);
        descrip = vista.findViewById(R.id.txtDesc);
        telefonoaviso= vista.findViewById(R.id.txtTelefono);
        comunidad = vista.findViewById(R.id.txtComunidad);
        nombre = vista.findViewById(R.id.txtNombre);
        comunidad.setEnabled(false);

        if(comunicacion.getComunidad().equals("-123")){
            comunidad.setText("Debes Loguearte");
            botonAniadir.setEnabled(false);
            descrip.setEnabled(false);
            telefonoaviso.setEnabled(false);
            nombre.setEnabled(false);

            Toast.makeText(vista.getContext(), "Debes Loguearte primero", Toast.LENGTH_SHORT).show();
        }else {
            comunidad.setText(comunicacion.getComunidad());
            botonAniadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String descripcion, telefono, txcomunidad, txnombre;
                    descripcion = descrip.getText().toString();
                    telefono = telefonoaviso.getText().toString();
                    txcomunidad = comunidad.getText().toString();
                    txnombre = nombre.getText().toString();

                    insertarIncidencia(descripcion, telefono, txcomunidad, txnombre);

                }
            });
        }

    }

    private void insertarIncidencia(String descripcion, String telefono, String txcomunidad, String txnombre) {
        lecturaasyn = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("comu",txcomunidad);
        requestParams.put("descripcion",descripcion);
        requestParams.put("avisonombre",txnombre);
        requestParams.put("avisotfno",telefono);

        lecturaasyn.get(vista.getContext(), URL, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String newresponse = new String(responseBody);
                Gson gson = new Gson();
                lectorGson = gson.fromJson(newresponse, LectorGsonGenerico.class);

                int sucess = lectorGson.getSuccess();

                if (sucess==1) {
                    Toast.makeText(vista.getContext(), lectorGson.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(vista.getContext(), lectorGson.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(vista.getContext(), "Error de conección", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
