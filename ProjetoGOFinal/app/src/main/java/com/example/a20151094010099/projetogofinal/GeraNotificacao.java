package com.example.a20151094010099.projetogofinal;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.util.Calendar;

public class GeraNotificacao {

    public static Intent geraNotificacao(Context context, int ano,int mes, int dia,int hora,int minuto, String titulo,String descricao,String local){

        Calendar cal = Calendar.getInstance();
        boolean val = false;
        Intent intent = null;
        while (val == false) {
            try {
                cal.set(Calendar.YEAR, ano);                 //
                cal.set(Calendar.MONTH, mes);   // Set de AÑO MES y Dia
                cal.set(Calendar.DAY_OF_MONTH, dia);      //

                cal.set(Calendar.HOUR_OF_DAY,hora);// Set de HORA y MINUTO
                cal.set(Calendar.MINUTE, minuto);            //

                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);

                intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                intent.putExtra(CalendarContract.Events.TITLE, titulo);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descricao);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION,local);

                //startActivity(intent);
                val = true;

            } catch (Exception e) {
                Toast.makeText(context, "Valor", Toast.LENGTH_LONG).show();
            }
        }
        return intent;
    }
}
