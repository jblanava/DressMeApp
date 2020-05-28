package com.example.dressmeapp.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.R;

import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ColorViewHolder> {

    private List<ColorPrenda> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public ColorAdapter(@NonNull List<ColorPrenda> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener)  {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_color_prenda, parent, false);
        return new ColorViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {

        ColorPrenda color = data.get(position);

        holder.getNombreColor().setText(color.getNombre());
        holder.getImagenColor().setBackgroundColor(color.getAndroidColor());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ColorViewHolder extends RecyclerView.ViewHolder {
        TextView txtColor;
        ImageView cuadrado;

        LinearLayout layout;

        public ColorViewHolder(@NonNull View v) {
            super(v);

            txtColor = v.findViewById(R.id.txtColor);
            cuadrado = v.findViewById(R.id.imgColor);
        }

        TextView getNombreColor()
        {
            return txtColor;
        }
        ImageView getImagenColor()
        {
            return cuadrado;
        }
    }

}
