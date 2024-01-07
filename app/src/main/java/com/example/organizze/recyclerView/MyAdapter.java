package com.example.organizze.recyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizze.R;
import com.example.organizze.autentificacao.Autentificar;
import com.example.organizze.model.AtividadeUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    private FirebaseAuth firebaseAuth= Autentificar.firebaseAuth();
    private DatabaseReference databaseReference=Autentificar.database();
    private List<AtividadeUsuario> list;
    private Context contexto;

    public MyAdapter(Context context, List<AtividadeUsuario> lista) {
        contexto=context;
        list=lista;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false);


        return new MyHolder(layout);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        AtividadeUsuario usuario=list.get(position);
        holder.tipo.setText(usuario.getTipo());
        holder.descricao.setText(usuario.getDescricao());

        if(usuario.getTipoMovimento().equals("D")){
            holder.valor.setTextColor(contexto.getColor(R.color.primaryDespeza));
            double i=usuario.getValor();
            DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
            String formato=decimalFormat.format(i);
            holder.valor.setText("R$ -"+formato);
        }
        if(usuario.getTipoMovimento().equals("R")){
            holder.valor.setTextColor(contexto.getColor(R.color.primaryReceita));
            double i=usuario.getValor();
            DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
            String formato=decimalFormat.format(i);
            holder.valor.setText("R$ "+formato);


        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
