package com.example.organizze.model;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.activies.DespezaActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class AtividadeUsuario {

    private double valor;
    private String tipoMovimento, tipo, descricao,key;

    public String splitData(String dataDigitada) {
        String[] arrayData = dataDigitada.split("/");


        return arrayData[1] + "-" + arrayData[2];


    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(String tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static void salvar(EditText text) {

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


                text.removeTextChangedListener(this);

                Double clean = Double.parseDouble(editable.toString().replaceAll("[R$,.\\s]", ""));
                Double number1 = clean;
                Double number2 = number1 / 100;
                String val = NumberFormat.getCurrencyInstance().format(number2);
                text.setText(val);
                text.setSelection(val.length());

                text.addTextChangedListener(this);

            }
        });
    }

    public static void maskData(EditText editText){
        editText.addTextChangedListener(new TextWatcher() {
            String anterior="";
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int letra=editText.getText().toString().length();
                if(letra>1){
                    anterior=editText.getText().toString().substring(letra-1);
                }

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int letra=editText.getText().toString().length();
                if (letra==2){
                    if(!anterior.equals("/")){
                        editText.append("/");
                    }else {
                        editText.getText().delete(letra-1,letra);
                    }

                }else if (letra==5) {
                    if (!anterior.equals("/")) {
                        editText.append("/");
                    } else {
                        editText.getText().delete(letra - 1, letra);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }
}