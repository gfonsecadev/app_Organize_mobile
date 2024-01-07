package com.example.organizze.recyclerView;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizze.R;

public class MyHolder extends RecyclerView.ViewHolder {
    public TextView descricao,valor,tipo;


    public MyHolder(@NonNull View itemView) {
        super(itemView);
        descricao=itemView.findViewById(R.id.recyclerDescricao);
        valor=itemView.findViewById(R.id.recyclerValor);
        tipo=itemView.findViewById(R.id.recyclerTipo);
    }
}
