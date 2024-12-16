package com.example.finalunidad3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Casa> casas;
    private ArrayList<Casa> casasMostrar;
    private ArrayList<Casa> casasFiltradas;
    private CasaAdapter casaAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        casas = new ArrayList<>();

        casas.add(new Casa(250000, "Calle Principal 123", Casa.Tipo.COMPRAR, R.drawable.casa1, 3));
        casas.add(new Casa(150000, "Avenida Secundaria 45", Casa.Tipo.ALQUILAR, R.drawable.casa2, 2));
        casas.add(new Casa(100000, "Boulevard del Sol 67", Casa.Tipo.AIRBNB, R.drawable.casa3, 1));
        casas.add(new Casa(300000, "Paseo del Parque 89", Casa.Tipo.COMPRAR, R.drawable.casa4, 4));
        casas.add(new Casa(180000, "Calle Las Rosas 21", Casa.Tipo.ALQUILAR, R.drawable.casa5, 3));
        casas.add(new Casa(120000, "Avenida Las Palmas 33", Casa.Tipo.AIRBNB, R.drawable.casa6, 2));
        casas.add(new Casa(280000, "Callejón del Norte 10", Casa.Tipo.COMPRAR, R.drawable.casa7, 3));
        casas.add(new Casa(160000, "Camino Real 55", Casa.Tipo.ALQUILAR, R.drawable.casa8, 2));
        casas.add(new Casa(90000, "Plaza Central 22", Casa.Tipo.AIRBNB, R.drawable.casa9, 1));
        casas.add(new Casa(400000, "Residencial Diamante 88", Casa.Tipo.COMPRAR, R.drawable.casa10, 5));
        //casas.add(null);

        casasFiltradas = new ArrayList<>();
        casasMostrar = new ArrayList<>();
        for(int i=0;i<casas.size();i++){
            casasMostrar.add(casas.get(i));
            casasFiltradas.add(casas.get(i));
        }

        Switch switchComprar = findViewById(R.id.switchComprar);
        Switch switchAlquilar = findViewById(R.id.switchAlquilar);
        Switch switchAribnb = findViewById(R.id.switchairbnb);

        NavigationBarView m = findViewById(R.id.navigationBar);
        m.setOnItemSelectedListener(e ->{
            if(e.getItemId() == R.id.favoritos){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragmentContainerListaCasas,
                                FragmentFavoritos.class,null)
                        .commit();
            }else{
                mostrarCasas();
            }
            return true;
        });

        Button botonBuscar = findViewById(R.id.button);
        botonBuscar.setOnClickListener(view -> filtrarCasas());

        // Crear el adaptador
        casaAdapter = new CasaAdapter(casasMostrar, evento ->{
            Toast.makeText(this, R.string.notificar, Toast.LENGTH_SHORT).show();
        });

        /*


        // Instanciar el RecyclerView
        RecyclerView rvCasas = findViewById(R.id.listaCasas);

        rvCasas.setLayoutManager(new LinearLayoutManager(this));


        // Asignar el adaptador al RecyclerView
        rvCasas.setAdapter(casaAdapter);
*/
        FragmentListaCasa fragment = new FragmentListaCasa();

        Bundle b = new Bundle();
        b.putParcelableArrayList("ListaCasas",casas);
        b.putString("Prueba","Prueba");

        fragment.setArguments(b);


        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerListaCasas,
                        fragment)
                .commit();


        switchComprar.setOnCheckedChangeListener((switchButton,isChecked)->{
            mostrarCasas();
        });
        switchAlquilar.setOnCheckedChangeListener((switchButton,isChecked)->{
            mostrarCasas();
        });
        switchAribnb.setOnCheckedChangeListener((switchButton,isChecked)->{
            mostrarCasas();
        });

    }

    /*
    Método que filtra las viviendas por su tipo en funcion de los switch que haya activado(Comprar, alquilar, Airbnb)
    Actualiza el adaptador para que muestre la nueva lista de viviendas disponibles
     */
    private void mostrarCasas(){
        casasMostrar.clear();

        boolean comprar = ((Switch)findViewById(R.id.switchComprar)).isChecked();
        boolean alquilar = ((Switch)findViewById(R.id.switchAlquilar)).isChecked();
        boolean airbnb = ((Switch)findViewById(R.id.switchairbnb)).isChecked();
        for(int i=0;i<casasFiltradas.size();i++){
            if((casasFiltradas.get(i).getTipo().toString().toLowerCase().equals("comprar") && comprar) || (casasFiltradas.get(i).getTipo().toString().toLowerCase().equals("alquilar") && alquilar) || (casasFiltradas.get(i).getTipo().toString().toLowerCase().equals("airbnb") && airbnb)
                ){
                    casasMostrar.add(casasFiltradas.get(i));

            }
        }
        //casaAdapter.notifyDataSetChanged();
        Bundle b = new Bundle();
        b.putParcelableArrayList("ListaCasas",casasMostrar);
        b.putString("Prueba","Prueba");

        FragmentListaCasa fragment = new FragmentListaCasa();

        fragment.setArguments(b);

        getSupportFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerListaCasas,
                        fragment)
                .commit();

    }
    /*
    Método para filtrar por los campos de busqueda(Ubicación, precio y número de habitaciones
    Comprueba si los campos se han rellenado, en caso de que esten vacios no filtra por ese valor
    Al acabar llama al método mostrarCasas para aplicar el filtro de switch y actualizar de forma correcta la lista
     */
    private void filtrarCasas(){
        casasFiltradas.clear();

        String ubicacion="";
        if(!((TextView)findViewById(R.id.editDireccion)).getText().toString().equals("")){
            ubicacion = ((TextView)findViewById(R.id.editDireccion)).getText().toString().toLowerCase();
        }
        Log.i("InfoDireccion", ubicacion);
        int precioMax=999999999;
        //Comprobamos si se ha rellenado el campo de precio y si se ha introducido un valor no válido para la busqueda
        if(!((TextView)findViewById(R.id.editPrecio)).getText().toString().equals("") ){
            try {
                precioMax = Integer.parseInt(((TextView) findViewById(R.id.editPrecio)).getText().toString());
            } catch (NumberFormatException e) {
                Log.w(this.getString(R.string.LogValorErroneo), this.getString(R.string.LogWarningMessageErrorPrice));
            }
        }
        int habitacionesMin = 0;
        //Comprobamos si se ha rellenado el campo de habitaciones y si se ha introducido un valor no válido para la busqueda
        if(!((TextView)findViewById(R.id.editHabitaciones)).getText().toString().equals("")){
            try {
                habitacionesMin = Integer.parseInt(((TextView) findViewById(R.id.editHabitaciones)).getText().toString());
            } catch (NumberFormatException e) {
                Log.w(this.getString(R.string.LogValorErroneo), this.getString(R.string.LogWarningMessageErrorRooms));
            }
        }
        Log.i("Valores",precioMax+" "+habitacionesMin);
        for(int i=0;i<casas.size();i++){
            if(casas.get(i).getPrecio()<=precioMax && casas.get(i).getHabitaciones()>=habitacionesMin && (ubicacion.equals("") || casas.get(i).getDireccion().toLowerCase().contains(ubicacion))){
                casasFiltradas.add(casas.get(i));
            }
        }
        mostrarCasas();
    }


}




