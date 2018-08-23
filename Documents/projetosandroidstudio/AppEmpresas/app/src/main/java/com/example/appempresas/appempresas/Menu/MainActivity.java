package com.example.appempresas.appempresas.Menu;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appempresas.appempresas.classes.AdapterEmpresa;
import com.example.appempresas.appempresas.classes.ConfiguracaoFirebase;
import com.example.appempresas.appempresas.LoginActivity;
import com.example.appempresas.appempresas.R;
import com.example.appempresas.appempresas.classes.empresa;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements GoogleApiClient.OnConnectionFailedListener {
    private ListView listView;
    private ArrayAdapter<empresa> teste;
    private ArrayList<empresa> Empresa;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerProdutos;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private FirebaseAuth firebaseAuth;
    private Button pesquisa,excluir,logout;
    private EditText text_empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Empresa = new ArrayList<>();
        listView = (ListView) findViewById(R.id.ListaEmpresa);
        teste = new AdapterEmpresa(this, Empresa);
        logout = (Button) findViewById(R.id.sair);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        pesquisa = (Button) findViewById(R.id.pesquisar);
        excluir = (Button) findViewById(R.id.excluir);
        text_empresa = (EditText) findViewById(R.id.empresa_1);
        listView.setAdapter(teste);

        firebase = ConfiguracaoFirebase.getFirebase().child("empresaApp");

        valueEventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Empresa.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    empresa empresaApp = dados.getValue(empresa.class);

                    Empresa.add(empresaApp);
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
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("loca", local.getText().toString());
                intent.putExtra("im", img.getText().toString());
                intent.putExtra("inf", info.getText().toString());
                startActivity(intent);
            }
        });

        pesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean verificador = text_empresa.getText().toString().equals("");

                if (!verificador) {

                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    intent.putExtra("empre", text_empresa.getText().toString());
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "Campo Vazio ", Toast.LENGTH_LONG).show();
                }
            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String email = user.getEmail();
            System.out.println(email);
            boolean emailVerified = user.isEmailVerified();
            System.out.println(emailVerified);
        } else {
            goLoginScreen();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                goLoginScreen();
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDelete();
                FirebaseAuth.getInstance().signOut();
                goLoginScreen();
            }
        });
    }

    private void UserDelete() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           System.out.println("User account deleted.");
                        }
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
        if (firebaseAuthListener != null) {
            firebaseAuth.removeAuthStateListener(firebaseAuthListener);
        }
    }

    @Override
    protected void onStart() {
       super.onStart();
       firebase.addValueEventListener(valueEventListenerProdutos);
   }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
