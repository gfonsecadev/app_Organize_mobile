package com.example.organizze.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.helpers.Base64Customizada;
import com.example.organizze.helpers.DataCustomizada;
import com.example.organizze.model.AtividadeUsuario;
import com.example.organizze.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ReceitaActivity extends AppCompatActivity {
    private EditText dataReceita,tipoReceita,descricaoReceita,valorReceita;
    private DatabaseReference databaseReferenc= Autentificar.database();
    private FirebaseAuth firebaseAuth=Autentificar.firebaseAuth();
    private AtividadeUsuario atividadeUsuario=new AtividadeUsuario();
    private Double valorrecuperado ;
    private String emailAtual= Base64Customizada.codificar(firebaseAuth.getCurrentUser().getEmail());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        dataReceita=findViewById(R.id.dataReceita);
        tipoReceita=findViewById(R.id.tipoReceita);
        descricaoReceita=findViewById(R.id.descricaoReceita);
        valorReceita=findViewById(R.id.editTextvalorReceita);

        dataReceita.setText(DataCustomizada.dataCustomizada());
        recuperarReceita();
        AtividadeUsuario.salvar(valorReceita);
        AtividadeUsuario.maskData(dataReceita);


    }

    public void  salvarReceita(View view){
        String datadigitada=dataReceita.getText().toString();
        String tipodigitada=tipoReceita.getText().toString();
        String descricaodigitada= descricaoReceita.getText().toString();
        String valordigitado=valorReceita.getText().toString();
        if(!datadigitada.isEmpty() && datadigitada.length()==10) {
            if (!tipodigitada.isEmpty()) {
                if ( !descricaodigitada.isEmpty()) {
                    if ( !valordigitado.isEmpty()) {
                        String data = dataReceita.getText().toString();
                        String tipoMov ="R";
                        Double receitaDigitada,receitaformatada, receitaTotal;
                        receitaDigitada = Double.parseDouble(valordigitado.replaceAll("[R$,.\\s]",""));
                        receitaformatada=receitaDigitada / 100;
                        atividadeUsuario.setValor(receitaformatada);
                        atividadeUsuario.setTipoMovimento(tipoMov);
                        atividadeUsuario.setTipo(tipoReceita.getText().toString());
                        atividadeUsuario.setDescricao(descricaoReceita.getText().toString());
                        receitaTotal = receitaformatada + valorrecuperado;

                        databaseReferenc.child("usuario").child(emailAtual).child("registrosUsuario").child(atividadeUsuario.splitData(data)).push().setValue(atividadeUsuario);
                        databaseReferenc.child("usuario").child(emailAtual).child("DadosUsuario").child("receitaTotal").setValue(receitaTotal);
                        Toast.makeText(this, "Receita salva com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        valorReceita.setError("digite sua receita!");
                        valorReceita.requestFocus();
                    }
                } else {
                    descricaoReceita.setError("Digite uma descrição para sua receita!");
                    descricaoReceita.requestFocus();
                }
            } else {
                tipoReceita.setError("Digite o tipo de receita!");
                tipoReceita.requestFocus();
            }
        }else {
            dataReceita.setError("Digite a data corretamente!");
            dataReceita.requestFocus();

        }

    }

    public void recuperarReceita(){
        DatabaseReference usuarioRef=databaseReferenc.child("usuario").child(emailAtual).child("DadosUsuario");
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario=snapshot.getValue(Usuario.class);
                valorrecuperado =usuario.getReceitaTotal();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


}