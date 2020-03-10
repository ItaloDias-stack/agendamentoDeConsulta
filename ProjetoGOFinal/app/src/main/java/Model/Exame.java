package Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import AppBD.BD;

public class Exame {

    private String id;
    private Usuario usuario;
    private String dataC;
    private String Hospital;
    private String hora;
    private String cidade;
    private String exame;

    public Exame(){

    }

    public String getExame() {
        return exame;
    }

    public void setExame(String exame) {
        this.exame = exame;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void salvar() {
        DatabaseReference reference = BD.getFirebase();
        reference.child("exames").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMapConsulta = new HashMap<>();
        hashMapConsulta.put("id", getId());
        hashMapConsulta.put("Usuario", getUsuario());
        hashMapConsulta.put("Hospital",getHospital());
        hashMapConsulta.put("Cidade",getCidade());
        hashMapConsulta.put("Hora",getHora());
        hashMapConsulta.put("DataExame",getDataC());
        hashMapConsulta.put("Exame",getExame());
        return hashMapConsulta;
    }

    @Override
    public String toString() {
        return "Cidade: "+getCidade()+"Dia: "+ getDataC()+" - "+getHora()+" - "+getHospital()+"Exame :"+getExame();
    }
}
