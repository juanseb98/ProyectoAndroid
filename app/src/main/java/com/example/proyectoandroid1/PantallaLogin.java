package com.example.proyectoandroid1;

import android.app.Fragment;
import android.database.Cursor;
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

public class PantallaLogin extends Fragment {
    private LectorGsonGenerico gsonLogin;
    private String URL ="https://sevilla2017.000webhostapp.com/comunidad/login.php";
    private AsyncHttpClient lecturaasyn;
    private Comunicador comunicacion;
    private AdaptadorBD db;
    View vista;
    EditText txtComunidad, txtContrasenia;
    Button btLogin, btLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.pantalla_login, container, false);

        return  vista;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtComunidad = vista.findViewById(R.id.txtComunidad);
        txtContrasenia = vista.findViewById(R.id.txtContrasenia);
        comunicacion = (Comunicador) getActivity();

        btLogout = vista.findViewById(R.id.btLogout);
        btLogin = vista.findViewById(R.id.btLogin);

        consultarBd();

        if(comunicacion.getComunidad().equals("-123")){
            btLogout.setEnabled(false);
        }else{
            btLogin.setEnabled(false);
            txtComunidad.setText(comunicacion.getComunidad());
            txtComunidad.setEnabled(false);
            txtContrasenia.setEnabled(false);
        }

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comunicacion.setComunidad("-123");
                txtComunidad.setEnabled(true);
                txtContrasenia.setEnabled(true);
                btLogin.setEnabled(true);
                btLogout.setEnabled(false);
                desloguearBd();
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comunidad, contrasenia;
                comunidad = txtComunidad.getText().toString();
                contrasenia= txtContrasenia.getText().toString();

                consultarLogin(comunidad, contrasenia);


            }
        });
    }


    private void consultarLogin(final String comunidad, String contrasenia) {

        lecturaasyn = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.put("comunidad",comunidad);
        requestParams.put("passwd",contrasenia);

        lecturaasyn.get(vista.getContext(), URL, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String newresponse = new String(responseBody);
                Gson gson = new Gson();

                gsonLogin = gson.fromJson(newresponse, LectorGsonGenerico.class);
                int succes = gsonLogin.getSuccess();

                if(succes == 1){
                    //guardarBd(comunidad);
                    Toast.makeText(vista.getContext(), gsonLogin.getMessage(), Toast.LENGTH_SHORT).show();
                    comunicacion.setComunidad(comunidad);
                    btLogin.setEnabled(false);
                    btLogout.setEnabled(true);
                    txtComunidad.setEnabled(false);
                    txtContrasenia.setEnabled(false);

                }else{
                    Toast.makeText(vista.getContext(), gsonLogin.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(vista.getContext(), "Error coneccion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void consultarBd() {
        db = new AdaptadorBD(vista.getContext());
        db.open();
        Cursor cursor = db.getTodasComunidades();
        if(cursor.moveToFirst()){
            String comu=cursor.getString(1);
            int conectado=cursor.getInt(2);
            if(conectado==1){
                comunicacion.setComunidad(comu);
            }
        }
        db.close();
    }

    private void guardarBd(String comunidad) {
        boolean existe=false;
        db = new AdaptadorBD(vista.getContext());
        db.open();
        Toast.makeText(vista.getContext(), "Se abre la base de datos", Toast.LENGTH_SHORT).show();
        Cursor cursor = db.getTodasComunidades();

        if(cursor.moveToFirst()){
            String comu=cursor.getString(1);
            if(comu.equals(comunidad)){
                existe=true;
            }
            while(cursor.moveToNext()&& !existe){
                comu=cursor.getString(1);
                if(comu.equals(comunidad)){
                    existe=true;
                }
            }
        }

        if(existe){
            Toast.makeText(vista.getContext(), "comunidad "+comunidad+" existe", Toast.LENGTH_SHORT).show();
            db.insertarComunidad(comunidad);
        }else{
            Toast.makeText(vista.getContext(), "comunidad "+comunidad+" no existe", Toast.LENGTH_SHORT).show();
            db.loguearComunidad(comunidad);
        }

        db.close();
    }

    private void desloguearBd() {
        db = new AdaptadorBD(vista.getContext());
        db.open();
        db.desloguearComunidad(comunicacion.getComunidad());
        db.close();
    }


}
