package com.example.organizze.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.helpers.Base64Customizada;
import com.example.organizze.helpers.DataCustomizada;
import com.example.organizze.model.AtividadeUsuario;
import com.example.organizze.model.MoneyTextWatcher;
import com.example.organizze.model.Usuario;
import com.google.firebase.auth.AdditionalUserInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DespezaActivity extends AppCompatActivity {
    private EditText dataDespesa,tipoDespesa,descricaoDespesa;
    private EditText valorDespesa;
    private DatabaseReference databaseReferenc= Autentificar.database();
    private FirebaseAuth firebaseAuth=Autentificar.firebaseAuth();
    private AtividadeUsuario atividadeUsuario=new AtividadeUsuario();
    private Double valorrecuperado ;
    private String emailAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despeza);

        dataDespesa=findViewById(R.id.dataDespesa);
        tipoDespesa=findViewById(R.id.tipoDespesa);
        descricaoDespesa=findViewById(R.id.descricaoDespesa);
        valorDespesa=findViewById(R.id.editTextvalorDespesa);

        dataDespesa.setText(DataCustomizada.dataCustomizada());
        emailAtual= Base64Customizada.codificar(firebaseAuth.getCurrentUser().getEmail());
        recuperarDespesa();

        AtividadeUsuario.salvar(valorDespesa);
        AtividadeUsuario.maskData(dataDespesa);



    }




    public void  salvarDespesa(View view){
        String datadigitada=dataDespesa.getText().toString();
        String tipodigitada=tipoDespesa.getText().toString();
        String descricaodigitada= descricaoDespesa.getText().toString();
        String valordigitado=valorDespesa.getText().toString();
        if(!datadigitada.isEmpty() && datadigitada.length()==10) {
            if (!tipodigitada.isEmpty()) {
                if ( !descricaodigitada.isEmpty()) {
                    if ( !valordigitado.isEmpty()) {
                        String data = dataDespesa.getText().toString();
                        String tipoMov="D";
                        Double despesaDigitada, despesaformatada, despesaTotal;
                        despesaDigitada = Double.parseDouble(valordigitado.replaceAll("[R$,.\\s]",""));
                        despesaformatada=despesaDigitada / 100;
                        atividadeUsuario.setValor(despesaformatada);
                        atividadeUsuario.setTipoMovimento(tipoMov);
                        atividadeUsuario.setTipo(tipoDespesa.getText().toString());
                        atividadeUsuario.setDescricao(descricaoDespesa.getText().toString());
                        despesaTotal = despesaformatada + valorrecuperado;

                        databaseReferenc.child("usuario").child(emailAtual).child("registrosUsuario").child(atividadeUsuario.splitData(data)).push().setValue(atividadeUsuario);
                        databaseReferenc.child("usuario").child(emailAtual).child("DadosUsuario").child("despejaTotal").setValue(despesaTotal);
                        Toast.makeText(this, "Despesa salva com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        valorDespesa.setError("digite sua despesa!");
                        valorDespesa.requestFocus();
                    }
                } else {
                    descricaoDespesa.setError("Digite uma descrição para despesa!");
                    descricaoDespesa.requestFocus();
                }
            } else {
                tipoDespesa.setError("Digite o tipo de despesa!");
                tipoDespesa.requestFocus();
            }
        }else {
            dataDespesa.setError("Digite a data corretamente!");
            dataDespesa.requestFocus();

                }

    }

    public void recuperarDespesa(){
        DatabaseReference usuarioRef=databaseReferenc.child("usuario").child(emailAtual).child("DadosUsuario");
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario=snapshot.getValue(Usuario.class);
                valorrecuperado =usuario.getDespejaTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

}



}