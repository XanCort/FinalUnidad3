package com.example.finalunidad3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Casa implements Parcelable {
    private int precio;
    private int habitaciones;
    private String direccion;
    private Tipo tipo;
    private int imagen;




    public Casa(int precio, String direccion, Tipo tipo, int imagen, int habitaciones) {
        this.precio = precio;
        this.direccion = direccion;
        this.tipo = tipo;
        this.imagen=imagen;
        this.habitaciones=habitaciones;
    }

    public Casa() {
    }

    protected Casa(Parcel in) {
        precio = in.readInt();
        habitaciones = in.readInt();
        direccion = in.readString();
        imagen = in.readInt();
    }

    public static final Creator<Casa> CREATOR = new Creator<Casa>() {
        @Override
        public Casa createFromParcel(Parcel in) {
            return new Casa(in);
        }

        @Override
        public Casa[] newArray(int size) {
            return new Casa[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(@NonNull Parcel dest, int i) {
        dest.writeString(direccion);

    }

    public enum Tipo {
        COMPRAR,
        ALQUILAR,
        AIRBNB
    }



    public int getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(int habitaciones) {
        this.habitaciones = habitaciones;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
