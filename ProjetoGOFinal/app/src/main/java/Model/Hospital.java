package Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import AppBD.BD;
import Helper.Base64Custom;

public class Hospital {
    private String nome = "Natal";
    private String id;
    private String nomeHospital = "Hospital Naval";

    public String getNomeHospital() {
        return nomeHospital;
    }

    public void setNomeHospital(String nomeHospital) {
        this.nomeHospital = nomeHospital;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void salvar(){
        DatabaseReference reference = BD.getFirebase();
        setId(Base64Custom.codificarBase64(getNomeHospital()));
        reference.child("hospital").child(String.valueOf(getId())).setValue(this);
    }


    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> hashMapUsuario = new HashMap<>();
        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("nomeHospital",getNomeHospital());
        return hashMapUsuario;
    }
}
