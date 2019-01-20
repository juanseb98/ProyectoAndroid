package com.example.proyectoandroid1;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Comunicador {

    Fragment newFragment;
    FragmentTransaction transaction;
    String comunidad="-123";//si tiene ese valor sera que el usuario no se ha autentificado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if(id == R.id.login){

            newFragment = new PantallaLogin();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.framecontainer, newFragment);
            transaction.attach(newFragment);
            transaction.commit();

        }else if (id == R.id.add_incidencia) {

            newFragment = new PantallaAniadir();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.framecontainer, newFragment);
            transaction.attach(newFragment);
            transaction.commit();

         } else if (id == R.id.list_incidencia) {
            newFragment = new PantallaListarIncidenciasEstado();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.framecontainer, newFragment);
            transaction.attach(newFragment);
            transaction.commit();


        } else if (id == R.id.list_all_incidencias) {
            newFragment = new PantallaListarAllIncidencias();
            transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.framecontainer, newFragment);
            transaction.attach(newFragment);
            transaction.commit();

        } else if (id == R.id.call) {
            //Abrimos la aplicacion de telefono con el numero de telefono
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:697366754"));
            startActivity(intent);

        } else if (id == R.id.map) {
            //Abrimos el mapa con la direccion del instituto
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q="+"Instituto de Educación Secundaria Ies Cristóbal de Monroy"));
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        } else if (id == R.id.email) {
            //Se abre la aplicacion de correos con la direccion de correo de envio ya puesta ppara completar el asunto y el mensaje
            intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto","juan-sebastian2009@hotmail.es", null));
            startActivity(Intent.createChooser(intent, "Send email..."));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setComunidad(String datos) {
        comunidad=datos;
    }

    @Override
    public String getComunidad() {
        return comunidad;
    }
}
