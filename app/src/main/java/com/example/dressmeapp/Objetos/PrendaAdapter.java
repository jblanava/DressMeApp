package com.example.dressmeapp.Objetos;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.Activities.Modificar_Prenda;
import com.example.dressmeapp.R;

import java.util.List;




public class PrendaAdapter extends RecyclerView.Adapter<PrendaAdapter.PrendaViewHolder>
{
    private List<Prenda> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public PrendaAdapter(@NonNull List<Prenda> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener)
    {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
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

    public class PrendaViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre;
        TextView tipo;
        TextView color;
        TextView talla;

        TableLayout t;

        public PrendaViewHolder(@NonNull View v) {
            super(v);

            nombre = v.findViewById(R.id.prenda_nombre);
            tipo = v.findViewById(R.id.prenda_tipo);
            color = v.findViewById(R.id.prenda_color);
            talla = v.findViewById(R.id.prenda_talla);
            t = v.findViewById(R.id.boton_prenda);

            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
                }
            });
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
    }
}
