package com.example.organizze.autentificacao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Autentificar {
    public static FirebaseAuth auth;
    public static DatabaseReference database;

    public static FirebaseAuth firebaseAuth(){

        if (auth==null){
            auth=FirebaseAuth.getInstance();
        }
        return auth;

    }

    public static DatabaseReference database(){
        if (database==null) {
            database= FirebaseDatabase.getInstance().getReference();
        }
        return database;
    }





}
