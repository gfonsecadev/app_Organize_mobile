package com.example.organizze.helpers;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

public  class  Base64Customizada {

    public static String  codificar(String email){
      return   Base64.encodeToString(email.getBytes(),Base64.DEFAULT).replaceAll("\\n|\\r","");
    }

    public static String decodificar(String codificado){
      return new String(Base64.decode(codificado,Base64.DEFAULT));
    }

}
