package com.example.semanatrece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private FirebaseAuth auth;

    private EditText nombre;
    private EditText correo;
    private EditText numeroCel;
    private EditText password;
    private EditText confPassword;
    private TextView cambioLog;

    private Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        nombre = findViewById(R.id.NombreR);
        correo = findViewById(R.id.correoR);
        numeroCel = findViewById(R.id.numeroR);
        password =findViewById(R.id.passwordR);
        confPassword = findViewById(R.id.confPassword);
        registrar = findViewById(R.id.registrar);
        cambioLog = findViewById(R.id.pasarLogin);

        registrar.setOnClickListener(this);
        cambioLog.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.registrar:

                if(password.getText().toString().equals(confPassword.getText().toString())){

                    auth.createUserWithEmailAndPassword(correo.getText().toString().trim(), password.getText().toString().trim())
                            .addOnCompleteListener(

                                    task -> {

                                        if(task.isSuccessful()){

                                            String id = auth.getCurrentUser().getUid();

                                            Usuario usuario = new Usuario(

                                                    id,
                                                    nombre.getText().toString(),
                                                    numeroCel.getText().toString(),
                                                    correo.getText().toString().trim()

                                            );
                                            database.getReference().child("usuarios").child(id).setValue(usuario)
                                                    .addOnCompleteListener(

                                                            taskDb -> {
                                                                if(taskDb.isSuccessful()){

                                                                    Intent c = new Intent(this,Contactos.class);
                                                                    startActivity(c);
                                                                    finish();

                                                                }
                                                            }
                                                    );
                                        }else{

                                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    }
                            );
                }

                break;

            case R.id.pasarLogin:

                Intent l = new Intent(this,Login.class);
                startActivity(l);
                finish();

                break;

        }

    }
}