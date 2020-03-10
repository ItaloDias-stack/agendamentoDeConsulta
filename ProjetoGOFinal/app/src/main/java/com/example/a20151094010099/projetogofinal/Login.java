package com.example.a20151094010099.projetogofinal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import AppBD.BD;
import Model.Usuario;

import static android.Manifest.permission.READ_CONTACTS;

public class Login extends AppCompatActivity {

    public EditText senha, email;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Context context = getApplicationContext();
        SharedPreferences sharedPreferences = getSharedPreferences("ArquivoSP", context.MODE_PRIVATE);

        email = (EditText) findViewById(R.id.emailLogin);
        senha = (EditText) findViewById(R.id.passwordLogin);
    }

    public void login(View view){
        if (!email.getText().equals("") && !senha.getText().equals("")){
            usuario = new Usuario();
            usuario.setEmail(email.getText().toString());
            usuario.setSenha(senha.getText().toString());
            validarLogin();
        }else{
            Toast.makeText(getApplicationContext(), "Preenxa todos os campos !", Toast.LENGTH_SHORT).show();
        }
    }

    private void validarLogin(){
        autenticacao = BD.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    BD.email = usuario.getEmail();
                    Intent intent = new Intent(getApplicationContext(),Carrega.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login efetuado com sucesso !", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        
    }
    public void CriarACC(View view){
        Intent intent = new Intent(getApplicationContext(),Cadastro.class);
        startActivity(intent);
    }
}

