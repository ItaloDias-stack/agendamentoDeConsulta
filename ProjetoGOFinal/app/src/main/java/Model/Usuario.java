package Model;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import AppBD.BD;

public class Usuario {

    private String nome;
    private String email;
    private String senha;
    private String dataNascimento;
    private String sexo;
    private String alergia;
    private int idade;
    private String id;
    private double altura,peso;


    public Usuario() {
    }

    public String getAlergia() {
        return alergia;
    }

    public void setAlergia(String alergia) {
        this.alergia = alergia;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public double getPeso() {
        return peso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getIdade() {
        return idade;
    }


    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void salvar(){
        DatabaseReference reference = BD.getFirebase();
        reference.child("usuario").child(String.valueOf(getId())).setValue(this);

    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object>  hashMapUsuario = new HashMap<>();
        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("dataNascimento",getDataNascimento());
        hashMapUsuario.put("idade",getIdade());
        hashMapUsuario.put("sexo",getSexo());

        return hashMapUsuario;
    }
}
