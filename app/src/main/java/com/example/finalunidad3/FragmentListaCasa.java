package com.example.finalunidad3;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaCasa#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaCasa extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<Casa> casas;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentListaCasa() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListaCasa.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaCasa newInstance(String param1, String param2) {
        FragmentListaCasa fragment = new FragmentListaCasa();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            casas = getArguments().getParcelableArrayList("ListaCasas");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("FragmentListaCasa", "onCreateView - casas: " + casas);

        View view = inflater.inflate(R.layout.fragment_lista_casa, container, false);
        if(casas == null){
            Log.i("PRUEBA","ES NULL");
        }else{
            // Crear el adaptador
            CasaAdapter casaAdapter = new CasaAdapter(casas, evento ->{
            });

            RecyclerView rvCasas = view.findViewById(R.id.ListaCasas);

            rvCasas.setLayoutManager(new LinearLayoutManager(getContext()));

            // Asignar el adaptador al RecyclerView
            rvCasas.setAdapter(casaAdapter);
            // Inflate the layout for this fragment
        }
        // Inflate the layout for this fragment

        return view;
    }
}