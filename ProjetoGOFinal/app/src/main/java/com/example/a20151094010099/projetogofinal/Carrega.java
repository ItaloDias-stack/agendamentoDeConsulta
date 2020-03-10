package com.example.a20151094010099.projetogofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import AppBD.BD;
import Model.Consulta;
import Model.Exame;
import Model.Hospital;
import Model.Usuario;

public class Carrega extends AppCompatActivity {
    int cont = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrega);

        BD.getFirebase().child("usuario").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Usuario u = d.getValue(Usuario.class);
                    if (BD.email.equals(u.getEmail())) {
                        BD.usuario = u;
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        BD.getFirebase().child("consultas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Consulta c = d.getValue(Consulta.class);
                    if (c.getUsuario().getEmail().equals(BD.email)) {
                        BD.consulta = c;
                        BD.cont=1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        BD.getFirebase().child("exames").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Exame e = d.getValue(Exame.class);
                    if(BD.email.equals(e.getUsuario().getEmail())){
                        BD.exame = e;
                        cont = cont+1;
                        BD.cont2=1;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        BD.cidadesList.add("Cidades");
        BD.getFirebase().child("hospital").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot d: dataSnapshot.getChildren()){
                    Hospital h = d.getValue(Hospital.class);
                    BD.cidadesList.add(h.getNome());
                    BD.hospitalList.add(h);
                    vai();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void vai(){
        Intent intent = new Intent(getApplicationContext(),Inicio.class);
        startActivity(intent);
    }
}
