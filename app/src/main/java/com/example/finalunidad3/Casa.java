package com.example.finalunidad3;

public class Casa {
    private int precio;
    private int habitaciones;
    private String direccion;
    private String tipo;
    private int imagen;



    public Casa(int precio, String direccion, String tipo, int imagen, int habitaciones) {
        this.precio = precio;
        this.direccion = direccion;
        this.tipo = tipo;
        this.imagen=imagen;
        this.habitaciones=habitaciones;
    }

    public Casa() {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }
}
