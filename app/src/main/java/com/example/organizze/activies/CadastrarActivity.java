package com.example.organizze.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.helpers.Base64Customizada;
import com.example.organizze.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastrarActivity extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button cadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        nome=findViewById(R.id.editTextNome);
        email=findViewById(R.id.editTextEmailCad);
        senha=findViewById(R.id.editTextSenhaCad);
        cadastrar=findViewById(R.id.buttonCadastrar);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailDigitado=email.getText().toString();
                String senhaDigitada=senha.getText().toString();
                String nomeDigitado=nome.getText().toString();
                String emailcodificado=Base64Customizada.codificar(emailDigitado);

                if(!emailDigitado.isEmpty()){
                    if (!senhaDigitada.isEmpty()){
                        usuario=new Usuario();
                        usuario.setEmail(emailDigitado);
                        usuario.setSenha(senhaDigitada);
                        usuario.setNome(nomeDigitado);
                        usuario.setIdUsuario(emailcodificado);
                        entrar();

                    }else {
                        senha.requestFocus();
                        senha.setError("digite uma senha!");

                    }
                }else {
                    email.requestFocus();
                    email.setError("digite email!");

                }

            }
        });


    }

    public void entrar(){
        autenticacao= Autentificar.firebaseAuth();

        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    usuario.savar();
                    finish();
                }
                else {
                    String excessao="";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthInvalidUserException e) {
                        excessao="Email não está cadastrado";

                    } catch (FirebaseAuthWeakPasswordException e){
                        excessao="Senha muito fraca, insira uma senha mais forte!";

                    } catch (FirebaseAuthInvalidCredentialsException e){
                        excessao="Este email não é válido";
                    } catch (FirebaseAuthUserCollisionException e){
                        excessao="Este email já está em uso, digite outro!";

                    }

                    catch (Exception e) {
                        excessao=e.getMessage();
                    }
                    Toast.makeText(CadastrarActivity.this,excessao,Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}