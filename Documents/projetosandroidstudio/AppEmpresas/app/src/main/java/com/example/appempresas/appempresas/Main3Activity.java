package com.example.appempresas.appempresas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<empresa> teste;
    private ArrayList<empresa> Empresa;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main3);

        Bundle extra = getIntent().getExtras();
        final String empre = extra.getString("empre");

        Empresa = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListaEmpresa1);
        teste = new AdapterEmpresa(this, Empresa);
        listView.setAdapter(teste);

        firebase = ConfiguracaoFirebase.getFirebase().child("empresaApp");

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Empresa.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    empresa empresaApp = dados.getValue(empresa.class);

                    String empresaNome = empresaApp.getNome().toLowerCase();
                    String empresaPesquisa = empre.toString().toLowerCase();

                    if (empresaPesquisa.contains(empresaNome)) {
                        Empresa.add(empresaApp);
                    }
                }
                teste.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView local = (TextView) view.findViewById(R.id.textNome);
                TextView img = (TextView) view.findViewById(R.id.recuperarUrl);
                TextView info = (TextView) view.findViewById(R.id.textInforme);
                Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                intent.putExtra("loca", local.getText().toString());
                intent.putExtra("im", img.getText().toString());
                intent.putExtra("inf", info.getText().toString());
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerProdutos);
    }
}
