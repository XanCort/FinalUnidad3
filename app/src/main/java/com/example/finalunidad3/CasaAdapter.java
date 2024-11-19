package com.example.finalunidad3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CasaAdapter extends RecyclerView.Adapter<CasaAdapter.CasaViewHolder>{
    ArrayList<Casa> coleccion;

    public CasaAdapter(ArrayList<Casa> coleccion) {
        this.coleccion = coleccion;
    }

    public ArrayList<Casa> getColeccion() {
        return coleccion;
    }

    public void setColeccion(ArrayList<Casa> coleccion) {
        this.coleccion = coleccion;
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
        holder.tv_tipo.setText(casa.getTipo());
        holder.imageView.setImageResource(casa.getImagen());
        holder.tv_habitaciones.setText(casa.getHabitaciones()+"");
        holder.tv_precio.setText(casa.getPrecio()+"");
    }

    @Override
    public int getItemCount() {
        return coleccion.size();
    }

    public class CasaViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView tv_titulo;
        TextView tv_precio;
        TextView tv_tipo;
        TextView tv_habitaciones;

        public CasaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageCasa);
            tv_titulo = itemView.findViewById(R.id.textDireccion);
            tv_precio = itemView.findViewById(R.id.textPrecio);
            tv_tipo = itemView.findViewById(R.id.textTipo);
            tv_habitaciones = itemView.findViewById(R.id.textHabitaciones);


        }
    }
}