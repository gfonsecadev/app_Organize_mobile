package com.example.organizze.activies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class MainActivity extends IntroActivity {
        private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder().fragment(R.layout.fragment1).background(R.color.white).build());
        addSlide(new FragmentSlide.Builder().fragment(R.layout.fragment2).background(R.color.white).build());
        addSlide(new FragmentSlide.Builder().fragment(R.layout.fragment3).background(R.color.white).build());
        addSlide(new FragmentSlide.Builder().fragment(R.layout.fragment4).background(R.color.white).build());
        addSlide(new FragmentSlide.Builder().fragment(R.layout.fragment5).background(R.color.white).canGoForward(false).build());

    }

    public void cadastrar(View view){
        startActivity(new Intent(this,CadastrarActivity.class));

    }

    public void entrar_conta(View view){

        startActivity(new Intent(this,EntrarActivity.class));


    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();

    }
    public void verificarUsuarioLogado(){
        autenticacao= Autentificar.firebaseAuth();
        //autenticacao.signOut();
        if(autenticacao.getCurrentUser()!=null){
            startActivity(new Intent(this,AplicacaoActivity.class));
        }
    }
}