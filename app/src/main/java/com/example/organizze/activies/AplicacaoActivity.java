package com.example.organizze.activies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.helpers.Base64Customizada;
import com.example.organizze.helpers.DataCustomizada;
import com.example.organizze.model.AtividadeUsuario;
import com.example.organizze.model.Usuario;
import com.example.organizze.recyclerView.MyAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AplicacaoActivity extends AppCompatActivity {
    private ImageButton antes, depois;
    private TextView textValor, textNome, mes;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference = Autentificar.database();
    private FirebaseAuth firebaseAuth = Autentificar.firebaseAuth();
    private String email = Base64Customizada.codificar(firebaseAuth.getCurrentUser().getEmail());
    private Double valorReceitaTotal, valorDespesaTotal, valorTotal;
    private String nome;
    MyAdapter myAdapter;
    private List<AtividadeUsuario> list_atividadeUsuarios = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicacao);
        getSupportActionBar().setElevation(0);

        antes = findViewById(R.id.buttomAntes);
        depois = findViewById(R.id.buttomDepois);
        mes = findViewById(R.id.textData);
        textNome = findViewById(R.id.textNome);
        textValor = findViewById(R.id.textValor);
        recyclerView = findViewById(R.id.recycler);
        mes.setText(DataCustomizada.mesAtual());
        recuperarDados();
        listaUsuario();

        myAdapter = new MyAdapter(AplicacaoActivity.this, list_atividadeUsuarios);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);
        arrastar();
    }


    public void arrastar() {
        ItemTouchHelper.Callback arrastar = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int drag = ItemTouchHelper.ACTION_STATE_IDLE;
                int swiped = ItemTouchHelper.START | ItemTouchHelper.END;

                return makeMovementFlags(drag, swiped);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                arrastar_e_excluir(viewHolder);
            }
        };
        new ItemTouchHelper(arrastar).attachToRecyclerView(recyclerView);
    }

    public void arrastar_e_excluir(RecyclerView.ViewHolder viewHolder){
        int position=viewHolder.getAdapterPosition();
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Exclusão de dados");
        alert.setMessage("Tem certeza que deseja excluir essa movimentação?");
        alert.setCancelable(false);
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(AplicacaoActivity.this,"Cancelado",Toast.LENGTH_LONG).show();
                myAdapter.notifyDataSetChanged();
            }
        });

        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AtividadeUsuario usuario=list_atividadeUsuarios.get(position);
                String mesConsulta=DataCustomizada.convertData(mes);
                DatabaseReference databaseLista=databaseReference.child("usuario").child(email).child("registrosUsuario").child(mesConsulta);
                databaseLista.child(usuario.getKey()).removeValue();
                Toast.makeText(AplicacaoActivity.this,"Apagado!",Toast.LENGTH_LONG).show();
                atualizar_saldo(usuario);
                myAdapter.notifyItemChanged(position);

            }
        });
        AlertDialog alertDialog=alert.create();
        alertDialog.show();

    }

    public void atualizar_saldo(AtividadeUsuario usuario){

        if(usuario.getTipoMovimento().equals("R")){
            valorReceitaTotal  =valorReceitaTotal-usuario.getValor();
            DatabaseReference RecuperarDados = databaseReference.child("usuario")
                    .child(email).child("DadosUsuario");

            RecuperarDados.child("receitaTotal").setValue(valorReceitaTotal);


        }else if(usuario.getTipoMovimento().equals("D")) {
            valorDespesaTotal=valorDespesaTotal-usuario.getValor();
            DatabaseReference RecuperarDados = databaseReference.child("usuario")
                    .child(email).child("DadosUsuario");

            RecuperarDados.child("despejaTotal").setValue(valorDespesaTotal);

        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menuSair:
                firebaseAuth.signOut();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    public void dataPoste(View view) {
        DataCustomizada.dataPosterior(mes);
        listaUsuario();
    }

    public void dataAnte(View view) {
        DataCustomizada.dataAnterior(mes);
        listaUsuario();
    }



    public void adicionarReceita(View view) {
        startActivity(new Intent(this, ReceitaActivity.class));
    }

    public void adicionarDespesa(View view) {
        startActivity(new Intent(this, DespezaActivity.class));
    }

    public void recuperarDados() {
        DatabaseReference RecuperarDados = databaseReference.child("usuario").child(email).child("DadosUsuario");
        RecuperarDados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Usuario usuario = snapshot.getValue(Usuario.class);
                nome = "Olá, " + usuario.getNome() + "!";
                valorDespesaTotal = usuario.getDespejaTotal();
                valorReceitaTotal= usuario.getReceitaTotal();
                valorTotal = valorReceitaTotal - valorDespesaTotal;
                DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
                String valorFormat = decimalFormat.format(valorTotal);
                textValor.setText("R$ " + valorFormat);
                textNome.setText(nome);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void listaUsuario(){

        String mesConsulta=DataCustomizada.convertData(mes);
        DatabaseReference databaseLista=databaseReference.child("usuario").child(email).child("registrosUsuario").child(mesConsulta);

        databaseLista.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_atividadeUsuarios.clear();
                for (DataSnapshot dados:snapshot.getChildren()) {
                    AtividadeUsuario usuario=dados.getValue(AtividadeUsuario.class);
                    usuario.setKey(dados.getKey());
                    list_atividadeUsuarios.add(usuario);

                }
                myAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    protected void onStart() {
        listaUsuario();
        super.onStart();
    }
}