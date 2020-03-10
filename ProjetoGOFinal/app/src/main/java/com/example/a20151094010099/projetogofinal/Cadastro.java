package com.example.a20151094010099.projetogofinal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

import AppBD.BD;
import Helper.Base64Custom;
import Helper.Preferencias;
import Model.Usuario;

public class Cadastro extends AppCompatActivity{

    private EditText email, senha, nome, idade, dataNascimento, senhaConfCadastro;
    private RadioButton rbMasculino, rbFeminino;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private DatePickerDialog datePickerDialog;

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        prepareDatePickerDialog();
        email = (EditText) findViewById(R.id.emailCadastro);
        senha = (EditText) findViewById(R.id.senhaCadastro);
        nome = (EditText) findViewById(R.id.nomeCadastro);
        idade = (EditText) findViewById(R.id.idadeCadastro);
        dataNascimento = (EditText) findViewById(R.id.nascimentoCadastro);
        senhaConfCadastro = (EditText) findViewById(R.id.senhaConfCadastro);
        rbMasculino = (RadioButton) findViewById(R.id.masculino);
        rbFeminino = (RadioButton) findViewById(R.id.feminino);
        button = (Button) findViewById(R.id.catastra);

        dataNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastra();
            }
        });
    }
    private void prepareDatePickerDialog() {
        //Get current date
        Calendar calendar= Calendar.getInstance();

        //Create datePickerDialog with initial date which is current and decide what happens when a date is selected.
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //When a date is selected, it comes here.
                //Change birthdayEdittext's text and dismiss dialog.
                dataNascimento.setText(dayOfMonth+"/"+monthOfYear+"/"+year);

                datePickerDialog.dismiss();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

    }
    public void cadastra() {
        if (senha.getText().toString().equals(senhaConfCadastro.getText().toString())) {
            usuario = new Usuario();
            usuario.setEmail(email.getText().toString());
            usuario.setNome(nome.getText().toString());
            usuario.setSenha(senha.getText().toString());
            usuario.setIdade(Integer.parseInt(idade.getText().toString()));
            usuario.setDataNascimento(dataNascimento.getText().toString());
            if (rbFeminino.isChecked()) {
                usuario.setSexo("Feminino");
            } else {
                usuario.setSexo("Masculino");
            }
            cadastrarUsuario();

        } else {
            Toast.makeText(getApplicationContext(), "As senhas não são correspondentes", Toast.LENGTH_SHORT).show();
        }
    }

    private void cadastrarUsuario() {
        autenticacao = BD.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(identificadorUsuario);
                    usuario.salvar();
                    Preferencias preferencias = new Preferencias(getApplicationContext());
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario,usuario.getNome());
                    abrirLoginUsuario();
                }else{
                    String erro="";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro="Digite uma senha mais forte, contendo no mínimo 8 caracteres, com letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro="Esse email é inválido";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro="Esse email já está cadastrado";
                    }catch (Exception e){
                        erro="Erro ao efetuar o cadastro!";
                    }
                    Toast.makeText(getApplicationContext(), "Erro: "+erro, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
