package com.example.a20151094010099.projetogofinal.Fragmentos;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.a20151094010099.projetogofinal.GeraNotificacao;
import com.example.a20151094010099.projetogofinal.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.widget.AdapterView.OnItemSelectedListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import AppBD.BD;
import Helper.Base64Custom;
import Model.Exame;
import Model.Hospital;
import Model.Usuario;

public class FazerExame extends Fragment {


    public FazerExame() {

    }

    DatabaseReference databaseReference;
    Button button;
    View view;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    int ano, mes, dia, minuto, horaS;
    Spinner cidade, hospital;
    ArrayAdapter<String> arrayAdapterC, arrayAdapterH;

    EditText usario, hora, data;
    RadioButton sangue, endoscopia, raioX, tomografia;
    Usuario u2 = new Usuario();
    ImageView imagem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fazer_exame, container, false);
        prepareDatePickerDialog();
        prepareTimePickerDialog();

        usario = (EditText) view.findViewById(R.id.usuarioExame);
        getActivity().setTitle("Fazer uma Exame");




        hora = (EditText) view.findViewById(R.id.horaExame);
        data = (EditText) view.findViewById(R.id.dataExame);
        sangue = (RadioButton) view.findViewById(R.id.exameSangue);
        endoscopia = (RadioButton) view.findViewById(R.id.endoscopia);
        raioX = (RadioButton) view.findViewById(R.id.raioX);
        tomografia = (RadioButton) view.findViewById(R.id.tomografia);
        cidade = (Spinner) view.findViewById(R.id.cidadeExame);
        hospital = (Spinner) view.findViewById(R.id.hospitalExame);

        usario.setText(BD.usuario.getNome());

        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();

            }
        });

        Set conjuntoDeBeans = new HashSet(BD.cidadesList);

        final ArrayList<Hospital> t2 = BD.hospitalList;

        ArrayList<String> t = new ArrayList<>(conjuntoDeBeans);

        arrayAdapterC = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, t);
        cidade.setAdapter(arrayAdapterC);

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

        button = (Button) view.findViewById(R.id.marcar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usario.getText().length() != 0 || hora.getText().length() != 0 || data.getText().length() != 0
                        || cidade.getSelectedItem().toString().equals("Cidades")) {

                    Exame exame = new Exame();
                    Usuario u = BD.usuario;

                    exame.setUsuario(u);
                    exame.setCidade(cidade.getSelectedItem().toString());
                    exame.setDataC(data.getText().toString());
                    exame.setHora(hora.getText().toString());
                    exame.setHospital(hospital.getSelectedItem().toString());

                    if (sangue.isChecked()) {
                        exame.setExame("Exame de sangue");
                    }
                    if (endoscopia.isChecked()) {
                        exame.setExame("Endoscopia");
                    }
                    if (raioX.isChecked()) {
                        exame.setExame("Raio X");
                    }
                    if (tomografia.isChecked()) {
                        exame.setExame("Tomografia");
                    }
                    String identificadorExame = Base64Custom.codificarBase64(exame.getUsuario().getNome());
                    exame.setId(identificadorExame);
                    exame.salvar();
                    Toast.makeText(getContext(), "Exame cadastrado!", Toast.LENGTH_SHORT).show();

                    startActivity(GeraNotificacao.geraNotificacao(getContext(), ano, mes, dia, horaS, minuto, "Exame",
                            "Fazer: " + exame.getExame(), hospital.getSelectedItem().toString()));
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

                hora.setText(hourOfDay + ":" + minute);
                horaS = hourOfDay;
                minuto = minute;
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
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
