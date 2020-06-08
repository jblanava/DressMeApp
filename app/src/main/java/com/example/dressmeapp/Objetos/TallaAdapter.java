package com.example.dressmeapp.Objetos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dressmeapp.R;

import java.util.List;

public class TallaAdapter extends RecyclerView.Adapter<TallaAdapter.TallaViewHolder> {

    private List<String> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public TallaAdapter(@NonNull List<String> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull @Override
    public TallaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_talla_view, parent, false);
        return new TallaViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TallaViewHolder holder, int position) {
        String talla = data.get(position);
        holder.getNombreTalla().setText(talla);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TallaViewHolder extends RecyclerView.ViewHolder {

        TextView nombreTalla;
        LinearLayout layout;

        TallaViewHolder(@NonNull View v) {
            super(v);

            nombreTalla = v.findViewById(R.id.txtTalla);

            layout = v.findViewById(R.id.tallaLayout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
                }
            });
        }

        TextView getNombreTalla() { return nombreTalla; }
    }

}
