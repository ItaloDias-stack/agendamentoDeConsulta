package com.example.a20151094010099.projetogofinal.Fragmentos;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a20151094010099.projetogofinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import AppBD.BD;
import Model.Consulta;
import Model.Exame;
import Model.Usuario;

public class ListaConsultasExames extends Fragment {

    ListView listaConsultas, listaExames;

    public ListaConsultasExames() {
        // Required empty public constructor
    }

    private List<Consulta> listaDeConsultas = new ArrayList();
    private List<Exame> exameList = new ArrayList();
    private ArrayAdapter<Consulta> consultaArrayAdapter;
    private ArrayAdapter<Exame> exameArrayAdapter;
    View view;
    ImageView imagem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_lista_consultas_exames, container, false);
        listaConsultas = (ListView) view.findViewById(R.id.lista);
        getActivity().setTitle("Lista de Consultas e Exames");
        listaExames = (ListView) view.findViewById(R.id.lista2);
        listaDeConsultas.clear();
        exameList.clear();




        if (BD.cont == 1) {
            listaDeConsultas.add(BD.consulta);
            consultaArrayAdapter = new ArrayAdapter<Consulta>(getActivity(), android.R.layout.simple_list_item_1, listaDeConsultas);
            listaConsultas.setAdapter(consultaArrayAdapter);

        }
        if (BD.cont2 == 1){
            exameList.add(BD.exame);
            exameArrayAdapter = new ArrayAdapter<Exame>(getActivity(), android.R.layout.simple_list_item_1, exameList);
            listaExames.setAdapter(exameArrayAdapter);
        }
        return view;
    }
}
