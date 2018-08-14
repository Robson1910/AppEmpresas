package com.example.appempresas.appempresas;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail1, inputPassword1;
    private FirebaseAuth auth;
    private Button btnLogin1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        inputEmail1 = (EditText) findViewById(R.id.inputEmail);
        inputPassword1 = (EditText) findViewById(R.id.inputPassword);
        btnLogin1 = (Button) findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        btnLogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificador = inputEmail1.getText().toString().equals("");
                boolean verificador2 = inputPassword1.getText().toString().equals("");

                if (!verificador && !verificador2) {

                    String email = inputEmail1.getText().toString();
                    final String password = inputPassword1.getText().toString();

                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            inputPassword1.setError(getString(R.string.minimum_password));
                                        } else {
                                            Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
