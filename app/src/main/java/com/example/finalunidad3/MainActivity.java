package com.example.finalunidad3;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

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

        casas.add(new Casa(250000, "Calle Principal 123", Casa.Tipo.COMPRAR, R.drawable.ic_launcher_background, 3));
        casas.add(new Casa(150000, "Avenida Secundaria 45", Casa.Tipo.ALQUILAR, R.drawable.ic_launcher_background, 2));
        casas.add(new Casa(100000, "Boulevard del Sol 67", Casa.Tipo.AIRBNB, R.drawable.ic_launcher_background, 1));
        casas.add(new Casa(300000, "Paseo del Parque 89", Casa.Tipo.COMPRAR, R.drawable.ic_launcher_background, 4));
        casas.add(new Casa(180000, "Calle Las Rosas 21", Casa.Tipo.ALQUILAR, R.drawable.ic_launcher_background, 3));
        casas.add(new Casa(120000, "Avenida Las Palmas 33", Casa.Tipo.AIRBNB, R.drawable.ic_launcher_background, 2));
        casas.add(new Casa(280000, "Callejón del Norte 10", Casa.Tipo.COMPRAR, R.drawable.ic_launcher_background, 3));
        casas.add(new Casa(160000, "Camino Real 55", Casa.Tipo.ALQUILAR, R.drawable.ic_launcher_background, 2));
        casas.add(new Casa(90000, "Plaza Central 22", Casa.Tipo.AIRBNB, R.drawable.ic_launcher_background, 1));
        casas.add(new Casa(400000, "Residencial Diamante 88", Casa.Tipo.COMPRAR, R.drawable.ic_launcher_background, 5));

        casasFiltradas = new ArrayList<>();
        casasMostrar = new ArrayList<>();
        for(int i=0;i<casas.size();i++){
            casasMostrar.add(casas.get(i));
            casasFiltradas.add(casas.get(i));
        }

        ((TextView)findViewById(R.id.editDireccion)).setOnClickListener(e->showPopUpMenu(e));


        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.equals(tabLayout.getTabAt(1))){
                    Log.i("PRUEBA","CLICK"+tab.getId());

                    Snackbar mensaje = Snackbar.make(findViewById(R.id.main), "Proximamente", Snackbar.LENGTH_LONG);
                    mensaje.setAction("Esto no hace nada", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Te he dicho que no hacia nada", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mensaje.addCallback(new Snackbar.Callback() {
                         @Override
                         public void onShown(Snackbar sb) {
                         }
                         @Override
                         public void onDismissed(Snackbar transientBottomBar, int event) {
                         }

                    });
                    mensaje.show();


                    TabLayout.Tab tab2 = tabLayout.getTabAt(0);
                    tab2.select();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        Switch switchComprar = findViewById(R.id.switchComprar);
        Switch switchAlquilar = findViewById(R.id.switchAlquilar);
        Switch switchAribnb = findViewById(R.id.switchairbnb);

        Button botonBuscar = findViewById(R.id.button);
        botonBuscar.setOnClickListener(view -> filtrarCasas());

        // Crear el adaptador
        casaAdapter = new CasaAdapter(casasMostrar, evento ->{
            //Toast.makeText(this, R.string.notificar, Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), R.string.notificar, Snackbar.LENGTH_LONG);
            snackbar.setAction("Esto tampoco hace nada", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Que no que no, que no hace nada", Toast.LENGTH_SHORT).show();
                }
            });
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onShown(Snackbar sb) {
                }
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                }

            });
            snackbar.show();
        });

        // Instanciar el RecyclerView
        RecyclerView rvCasas = findViewById(R.id.listaCasas);

        rvCasas.setLayoutManager(new LinearLayoutManager(this));


        // Asignar el adaptador al RecyclerView
        rvCasas.setAdapter(casaAdapter);

        registerForContextMenu(switchComprar);


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
                Log.i("Prueba","entra al bucle");

            }
        }
        casaAdapter.notifyDataSetChanged();
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
            Log.i("InfoDireccion",((TextView)findViewById(R.id.editDireccion)).getText().toString() );
            ubicacion = ((TextView)findViewById(R.id.editDireccion)).getText().toString().toLowerCase();
        }
        Log.i("InfoDireccion", ubicacion);
        int precioMax=999999999;
        if(!((TextView)findViewById(R.id.editPrecio)).getText().toString().equals("") ){
            precioMax=Integer.parseInt(((TextView)findViewById(R.id.editPrecio)).getText().toString());
        }
        int habitacionesMin = 0;
        if(!((TextView)findViewById(R.id.editHabitaciones)).getText().toString().equals("")){
            habitacionesMin =Integer.parseInt(((TextView)findViewById(R.id.editHabitaciones)).getText().toString());
        }
        Log.i("Valores",precioMax+" "+habitacionesMin);
        for(int i=0;i<casas.size();i++){
            if(casas.get(i).getPrecio()<=precioMax && casas.get(i).getHabitaciones()>=habitacionesMin && (ubicacion.equals("") || casas.get(i).getDireccion().toLowerCase().contains(ubicacion))){
                casasFiltradas.add(casas.get(i));
            }
        }
        mostrarCasas();
    }



    // Método para el menú contextual, donde sew asocia el menú contrextual al textView
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
//    {
//        menu.setHeaderTitle("Me han mandado poner esto pero no hace nada");
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.contextmenuejemplo, menu);
//    }
//
//    //    // Método para gestionar los eventos de los elementos del menú contextual
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        return true;
//    }

    //    // Método para asociar un menú emergente popup al pulsar en una vista
    public void showPopUpMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        MenuInflater menuInflater = popupMenu.getMenuInflater();
        menuInflater.inflate(R.menu.contextmenuejemplo, popupMenu.getMenu());

        // Manejador de clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        // mostrarlo
        popupMenu.show();

    }


}




