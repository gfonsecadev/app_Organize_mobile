package com.example.organizze.model;

import com.example.organizze.autentificacao.Autentificar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private double despejaTotal=0.00;
    private double receitaTotal=0.00;
    private String idUsuario;
    private String nome;
    private String email;
    private String senha;
    private DatabaseReference database;

    public Usuario() {
    }

    public void savar(){
        database= Autentificar.database();

        database.child("usuario").child(this.idUsuario).child("DadosUsuario").setValue(this);


    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }
    @Exclude
    public String getIdUsuario() {
        return idUsuario;
    }

    public double getDespejaTotal() {
        return despejaTotal;
    }

    public void setDespejaTotal(double despejaTotal) {
        this.despejaTotal = despejaTotal;
    }

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
