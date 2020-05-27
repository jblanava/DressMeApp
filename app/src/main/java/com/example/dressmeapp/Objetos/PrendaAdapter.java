package com.example.dressmeapp.Objetos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.Activities.Modificar_Prenda;
import com.example.dressmeapp.R;

import java.util.List;

public class PrendaAdapter extends RecyclerView.Adapter<PrendaAdapter.PrendaViewHolder>
{
    private List<Prenda> data;

    public PrendaAdapter(@NonNull List<Prenda> data) {
        this.data = data;
    }


    @Override
    public PrendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_prenda_view, parent, false);
        return new PrendaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PrendaViewHolder holder, int position) {
        Prenda prenda = data.get(position);

        holder.getNombre().setText(prenda.nombre);
        holder.getColor().setText(prenda.color);
        holder.getTipo().setText(prenda.tipo);
        holder.getTalla().setText(prenda.talla);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PrendaViewHolder extends RecyclerView.ViewHolder //implements View.OnClickListener
    {
        int id;
        TextView nombre;
        TextView tipo;
        TextView color;
        TextView talla;

        public PrendaViewHolder(@NonNull View v) {
            super(v);

            nombre = v.findViewById(R.id.prenda_nombre);
            tipo = v.findViewById(R.id.prenda_tipo);
            color = v.findViewById(R.id.prenda_color);
            talla = v.findViewById(R.id.prenda_talla);
        }

        TextView getNombre()
        {
            return nombre;
        }
        TextView getColor()
        {
            return color;
        }
        TextView getTipo()
        {
            return tipo;
        }
        TextView getTalla()
        {
            return talla;
        }

            /*todo solucionar
        @Override
        public void onClick(View v) {
            Intent modificar = new Intent(, Modificar_Prenda.class);
            modificar.putExtra("intVariableName", id);
            startActivity(modificar);
        }

             */
    }
}
