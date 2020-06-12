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

public class ComboColorAdapter extends RecyclerView.Adapter<ComboColorAdapter.ComboColorViewHolder> {

    private List<ComboColorPrenda> data;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    public ComboColorAdapter(@NonNull List<ComboColorPrenda> data, @NonNull RecyclerViewOnItemClickListener recyclerViewOnItemClickListener)  {
        this.data = data;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public ComboColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_combo_color_view, parent, false);
        return new ComboColorViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ComboColorViewHolder holder, int position) {

        ComboColorPrenda combo = data.get(position);

        holder.getColor1().setBackgroundColor(combo.getColor1().getAndroidColor());
        holder.getColor2().setBackgroundColor(combo.getColor2().getAndroidColor());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ComboColorViewHolder extends RecyclerView.ViewHolder {

        ImageView color1;
        ImageView color2;
        LinearLayout layout;

        ComboColorViewHolder(@NonNull View v) {
            super(v);

            layout = v.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
                }
            });

            color1 = v.findViewById(R.id.imgColor1);
            color2 = v.findViewById(R.id.imgColor2);
        }

        ImageView getColor1() { return color1; }
        ImageView getColor2() { return color2; }
    }

}
