package com.example.organizze.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class EntrarActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button buttonEntrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar);

        getSupportActionBar().setTitle("Entrar");
        email=findViewById(R.id.editTextEmail);
        senha=findViewById(R.id.editTextTeSenha);
        buttonEntrar=findViewById(R.id.buttonEntrar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emaildDigitado=email.getText().toString();
                String senhaDigitada=senha.getText().toString();
                if(!emaildDigitado.isEmpty()){
                    if(!senhaDigitada.isEmpty()){
                        usuario=new Usuario();
                        usuario.setEmail(emaildDigitado);
                        usuario.setSenha(senhaDigitada);
                        entrar();
                    }else {
                        senha.requestFocus();
                        senha.setError("digite uma senha!");
                    }

                }else {
                        email.requestFocus();
                        email.setError("digite uma email!");
                }
            }
        });

    }

    public void entrar(){
        autenticacao= Autentificar.firebaseAuth();

        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    entrarAplicacao();
                }
                else {
                    String excessao="";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        excessao="Email não está cadastrado";

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excessao="Senha está incorreta";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EntrarActivity.this,excessao,Toast.LENGTH_LONG).show();

                }
            }
        });
    }
    public void entrarAplicacao(){
        startActivity(new Intent(EntrarActivity.this,AplicacaoActivity.class));
        finish();
    }
}