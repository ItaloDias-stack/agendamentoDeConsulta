package com.example.a20151094010099.projetogofinal;



import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;


import com.example.a20151094010099.projetogofinal.Fragmentos.FazerConsulta;
import com.example.a20151094010099.projetogofinal.Fragmentos.FazerExame;
import com.example.a20151094010099.projetogofinal.Fragmentos.ListaConsultasExames;

import Model.Consulta;

public class Inicio extends AppCompatActivity{
    BottomNavigationView navigation;
    FrameLayout frameLayout;


    private FazerConsulta fazerConsultaFragment;
    private FazerExame fazerExameFragment;

    private ListaConsultasExames listaConsultasExames;
    ArrayAdapter<Consulta> t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        navigation = (BottomNavigationView) findViewById(R.id.main_nav);
        fazerConsultaFragment = new FazerConsulta();
        fazerExameFragment = new FazerExame();


        listaConsultasExames = new ListaConsultasExames();
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.consultasExames:
                        setFragment(listaConsultasExames);
                        navigation.setItemBackgroundResource(R.color.colorPrimary);

                        return true;
                    case R.id.fazer_consultas:
                        setFragment(fazerConsultaFragment);
                        navigation.setItemBackgroundResource(R.color.Turquoise);
                        return true;

                    case R.id.fazer_exames:
                        setFragment(fazerExameFragment);
                        navigation.setItemBackgroundResource(R.color.colorAccent);
                        return true;

                    default:
                        return false;

                }
            }

            private void setFragment(Fragment fragment) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_frame,fragment);
                fragmentTransaction.commit();
            }
        });

    }

}
