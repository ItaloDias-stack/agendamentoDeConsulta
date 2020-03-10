package Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import AppBD.BD;

public class Consulta {

    private String id;
    private Usuario usuario;
    private String dataC;
    private String Hospital;
    private String hora;
    private String cidade;

    public Consulta() {

    }


    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDataC() {
        return dataC;
    }

    public void setDataC(String dataC) {
        this.dataC = dataC;
    }

    public String getHospital() {
        return Hospital;
    }

    public void setHospital(String hospital) {
        Hospital = hospital;
    }

    public void salvar() {
        DatabaseReference reference = BD.getFirebase();
        reference.child("consultas").child(String.valueOf(getId())).setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapConsulta = new HashMap<>();
        hashMapConsulta.put("id", getId());
        hashMapConsulta.put("Usuario", getUsuario());
        hashMapConsulta.put("Hospital",getHospital());
        hashMapConsulta.put("Cidade",getCidade());
        hashMapConsulta.put("Hora",getHora());
        hashMapConsulta.put("DataConsulta",getDataC());
        return hashMapConsulta;
    }

    @Override
    public String toString() {
        return "Cidade: "+getCidade()+"Dia: "+getDataC()+" - "+getHospital();
    }
}

