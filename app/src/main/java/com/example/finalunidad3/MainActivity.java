package com.example.finalunidad3;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        casas.add(new Casa(500000,"Sitio","comprar",R.drawable.ic_launcher_background,3));
        casas.add(new Casa(1000,"Sitio","comprar",R.drawable.ic_launcher_background,3));
        casas.add(new Casa(12000,"Sitio","alquilar",R.drawable.ic_launcher_background,3));
        casas.add(new Casa(1,"Sitio","airbnb",R.drawable.ic_launcher_background,3));

        casasFiltradas = new ArrayList<>();
        casasMostrar = new ArrayList<>();
        for(int i=0;i<casas.size();i++){
            casasMostrar.add(casas.get(i));
            casasFiltradas.add(casas.get(i));
        }

        Switch switchComprar = findViewById(R.id.switchComprar);
        Switch switchAlquilar = findViewById(R.id.switchAlquilar);
        Switch switchAribnb = findViewById(R.id.switchairbnb);

        Button botonBuscar = findViewById(R.id.button);
        botonBuscar.setOnClickListener(view -> filtrarCasas());

        // Crear el adaptador
        casaAdapter = new CasaAdapter(casasMostrar);

        // Instanciar el RecyclerView
        RecyclerView rvCasas = findViewById(R.id.listaCasas);

        rvCasas.setLayoutManager(new LinearLayoutManager(this));


        // Asignar el adaptador al RecyclerView
        rvCasas.setAdapter(casaAdapter);

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
    private void mostrarCasas(){
        casasMostrar.clear();

        boolean comprar = ((Switch)findViewById(R.id.switchComprar)).isChecked();
        boolean alquilar = ((Switch)findViewById(R.id.switchAlquilar)).isChecked();
        boolean airbnb = ((Switch)findViewById(R.id.switchairbnb)).isChecked();
        for(int i=0;i<casasFiltradas.size();i++){
            if((casasFiltradas.get(i).getTipo().equals("comprar") && comprar) || (casasFiltradas.get(i).getTipo().equals("alquilar") && alquilar) || (casasFiltradas.get(i).getTipo().equals("airbnb") && airbnb)
                ){
                    casasMostrar.add(casasFiltradas.get(i));
                Log.i("Prueba","entra al bucle");

            }
        }
        casaAdapter.notifyDataSetChanged();
    }
    private void filtrarCasas(){
        casasFiltradas.clear();

        int precioMax=999999999;
        if(((TextView)findViewById(R.id.textPrecio)).getText().toString()!=null){
            precioMax=Integer.parseInt(((TextView)findViewById(R.id.textPrecio)).getText().toString());
        }
        int habitacionesMin = 0;
        if(((TextView)findViewById(R.id.textHabitaciones)).getText().toString()!=null){
            habitacionesMin =Integer.parseInt(((TextView)findViewById(R.id.textHabitaciones)).getText().toString());
        }
        for(int i=0;i<casas.size();i++){
            if(casas.get(i).getPrecio()<=precioMax && casas.get(i).getHabitaciones()>=habitacionesMin){
                casasFiltradas.add(casas.get(i));
            }
        }
        mostrarCasas();
    }


}




