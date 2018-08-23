        package com.example.appempresas.appempresas.Email_Auth;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.appempresas.appempresas.LoginActivity;
        import com.example.appempresas.appempresas.R;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.AuthResult;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;


        public class Email_CreateActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,inputName;
    private Button btnSignUp;
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__create);

        auth = FirebaseAuth.getInstance();

        btnSignUp = (Button) findViewById(R.id.createUserBtn);
        inputEmail = (EditText) findViewById(R.id.emailEditTextCreate);
        inputPassword = (EditText) findViewById(R.id.passEditTextCreate);
        inputName = (EditText) findViewById(R.id.nomeEditTextCreate);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean verificador = inputEmail.getText().toString().equals("");
                boolean verificador2 = inputPassword.getText().toString().equals("");
                boolean verificador3 = inputName.getText().toString().equals("");

                if(!verificador && !verificador2 && !verificador3 ) {

                    final String email = inputEmail.getText().toString().trim();
                    final String password = inputPassword.getText().toString().trim();
                    final String name = inputName.getText().toString().trim();


                    if (password.length() < 6) {
                        Toast.makeText(getApplicationContext(), "Senha curta, minimo 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Email_CreateActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (!task.isSuccessful()) {
                                        Toast.makeText(Email_CreateActivity.this, " Erro criar usuario",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        SendEmailVerification();
                                    }
                                }
                            });

                } else {
                    Toast.makeText(Email_CreateActivity.this, "Campo vazio", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void SendEmailVerification() {
        final FirebaseUser user = auth.getCurrentUser();
      //  if(user !=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    findViewById(R.id.emailEditTextCreate).setEnabled(true);
                    if(task.isSuccessful()){
                        Toast.makeText(Email_CreateActivity.this, "Autenticação enviado por email :" + user.getEmail(), Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(Email_CreateActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(Email_CreateActivity.this, "não foi possivel verificar :" + user.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
       // }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



}