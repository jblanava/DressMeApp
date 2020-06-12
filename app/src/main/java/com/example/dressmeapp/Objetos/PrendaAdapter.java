package com.example.dressmeapp.Objetos;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.BaseDatos.GestorBD;
import com.example.dressmeapp.R;

import java.util.List;




public class PrendaAdapter extends RecyclerView.Adapter<PrendaAdapter.PrendaViewHolder>
{
    private List<Prenda> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    static Prenda  prenda;
    private Context context;

    public PrendaAdapter(@NonNull List<Prenda> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener)
    {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }


    @Override
    public PrendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View row = LayoutInflater.from(context).inflate(R.layout.activity_prenda_view, parent, false);
        return new PrendaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull PrendaViewHolder holder, int position) {
         prenda = data.get(position);

        holder.getNombre().setText(prenda.nombre);
        holder.getColor().setText(prenda.color);
        holder.getTipo().setText(prenda.tipo);
        holder.getTalla().setText(prenda.talla);
        GestorBD.cargarFoto(holder.itemView.getContext(), GestorBD.get_foto_de_prenda(context, prenda.id), holder.getImagen2());

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

        ImageView imagen;


        TableLayout t;

        public PrendaViewHolder(@NonNull View v) {
            super(v);

            nombre = v.findViewById(R.id.prenda_nombre);
            tipo = v.findViewById(R.id.prenda_tipo);
            color = v.findViewById(R.id.prenda_color);
            talla = v.findViewById(R.id.prenda_talla);

            imagen = v.findViewById(R.id.imageView2);

            //GestorBD.cargarFoto(v.getContext(), "9", imagen);

           // imagen.setImageResource(R.drawable.logologo);

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
        Bitmap getImagen() { return GestorBD.obtenerFoto(itemView.getContext(), String.valueOf(prenda.id)); }

        ImageView getImagen2() { return imagen; }



    }
}
