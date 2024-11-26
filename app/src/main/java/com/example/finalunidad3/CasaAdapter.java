package com.example.finalunidad3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.CasaViewHolder>{
    ArrayList<Casa> coleccion;
    private OnItemClickListener listener;

    public CasaAdapter(ArrayList<Casa> coleccion, OnItemClickListener listener) {
        this.coleccion = coleccion;
        this.listener = listener;
    }


    @NonNull
    @Override
    public CasaAdapter.CasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CasaAdapter.CasaViewHolder casaViewHolder =
                new CasaViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.ficha,parent,false)
                );
        return casaViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CasaAdapter.CasaViewHolder holder, int position) {
        Casa casa = coleccion.get(position);
        holder.tv_titulo.setText(casa.getDireccion());
        holder.tv_tipo.setText(casa.getTipo().toString().toLowerCase());
        holder.imageView.setImageResource(casa.getImagen());
        holder.tv_habitaciones.setText(casa.getHabitaciones()+" "+holder.itemView.getContext().getString(R.string.habitaciones));
        holder.tv_precio.setText(casa.getPrecio()+""+holder.itemView.getContext().getString(R.string.moneda));


        holder.botonContactar.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(casa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coleccion.size();
    }

    public interface OnItemClickListener{
        public void onItemClick(Casa casa);
    }


    public class CasaViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_titulo;
        TextView tv_precio;
        TextView tv_tipo;
        TextView tv_habitaciones;
        Button botonContactar;

        public CasaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCasa);
            tv_titulo = itemView.findViewById(R.id.textDireccion);
            tv_precio = itemView.findViewById(R.id.textPrecio);
            tv_tipo = itemView.findViewById(R.id.textTipo);
            tv_habitaciones = itemView.findViewById(R.id.textHabitaciones);
            botonContactar = itemView.findViewById(R.id.buttonContactar);

        }
    }

}