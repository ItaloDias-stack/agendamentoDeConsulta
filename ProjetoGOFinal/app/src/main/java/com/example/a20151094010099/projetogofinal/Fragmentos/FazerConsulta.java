package com.example.a20151094010099.projetogofinal.Fragmentos;


import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;


import java.util.Calendar;


import com.example.a20151094010099.projetogofinal.GeraNotificacao;
import com.example.a20151094010099.projetogofinal.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import AppBD.BD;
import Helper.Base64Custom;
import Model.Consulta;
import Model.Hospital;
import Model.Usuario;

public class FazerConsulta extends Fragment{


    public FazerConsulta() {
        // Required empty public constructor
    }

    int ano,mes,dia,minuto,horaS;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    DatabaseReference databaseReference;


    View view;
    Button button;
    EditText usario, peso, altura, hora, alergia, data;
    Spinner cidade, hospital;
    ArrayAdapter<String> arrayAdapterC, arrayAdapterH;
    String cidadeS, hospitalS;
    ImageView imagem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        prepareDatePickerDialog();
        prepareTimePickerDialog();

        view = inflater.inflate(R.layout.fragment_fazer_consulta, container, false);
        getActivity().setTitle("Fazer uma Consulta");



        usario = (EditText) view.findViewById(R.id.usuarioConsulta);
        peso = (EditText) view.findViewById(R.id.pesoConsulta);
        altura = (EditText) view.findViewById(R.id.alturaConsulta);


        hora = (EditText) view.findViewById(R.id.horaConsulta);
        alergia = (EditText) view.findViewById(R.id.alergiaConsulta);
        data = (EditText) view.findViewById(R.id.dataConsulta);
        button = (Button) view.findViewById(R.id.fazerConsulta);

        hospital = (Spinner) view.findViewById(R.id.hospitalConsulta);
        cidade = (Spinner) view.findViewById(R.id.cidadeConsulta);

        hospital.setEnabled(false);

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getFragmentManager(),"Hora da Consulta");*/
                timePickerDialog.show();

            }
        });

        Set conjuntoDeBeans = new HashSet(BD.cidadesList);
        final Set conjuntoDeBeans2 = new HashSet(BD.hospitalList);

        ArrayList<String> t = new ArrayList<>(conjuntoDeBeans);
        final ArrayList<Hospital> t2 = BD.hospitalList;
        arrayAdapterC = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, t);
        cidade.setAdapter(arrayAdapterC);

        usario.setText(BD.usuario.getNome());
        final ArrayList<String> testeH = new ArrayList<>();
        cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                testeH.clear();
                if (position > 0) {
                    hospital.setEnabled(true);
                    for (int i = 0; i < t2.size(); i++) {
                        if (cidade.getSelectedItem().toString().equals(t2.get(i).getNome())) {
                            testeH.add(t2.get(i).getNomeHospital());
                            arrayAdapterH = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, testeH);
                            hospital.setAdapter(arrayAdapterH);
                        }
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usario.getText().length() != 0 || peso.getText().length() != 0 || altura.getText().length() != 0
                        || hora.getText().length() != 0
                        || data.getText().length() != 0) {
                    Usuario u = BD.usuario;

                    u.setPeso(Double.parseDouble(peso.getText().toString()));
                    u.setAltura(Double.parseDouble(altura.getText().toString()));
                    u.setAlergia(alergia.getText().toString());

                    Consulta c = new Consulta();
                    c.setUsuario(u);
                    c.setHora(hora.getText().toString());
                    c.setHospital(hospital.getSelectedItem().toString());
                    c.setCidade(cidade.getSelectedItem().toString());
                    c.setDataC(data.getText().toString());
                    String identificadorConsulta = Base64Custom.codificarBase64(BD.usuario.getNome());
                    c.setId(identificadorConsulta);
                    c.salvar();
                    Toast.makeText(getContext(), "Consulta marcada", Toast.LENGTH_SHORT).show();
                    startActivity(GeraNotificacao.geraNotificacao(getContext(),ano,mes,dia,horaS,minuto,"Consulta","Ir ao hospital se consultar",hospital.getSelectedItem().toString()));
                } else {
                    Toast.makeText(getContext(), "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void prepareTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                hora.setText(hourOfDay+":"+minute);
                horaS = hourOfDay;
                minuto = minute;
            }
        }, calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),true);
    }

    private void prepareDatePickerDialog() {
        //Get current date
        Calendar calendar = Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //When a date is selected, it comes here.
                //Change birthdayEdittext's text and dismiss dialog.
                data.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                ano = year;
                mes = monthOfYear;
                dia = dayOfMonth;
                datePickerDialog.dismiss();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

    }
}
