package com.example.semanatrece;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth auth;

    private EditText correo;
    private EditText password;
    private TextView cambioReg;

    private Button logear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.correoL);
        password = findViewById(R.id.passwordL);
        logear = findViewById(R.id.logear);
        cambioReg = findViewById(R.id.pasarRegistro);

        logear.setOnClickListener(this);
        cambioReg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch(view.getId()){

            case R.id.logear:

                auth.signInWithEmailAndPassword(correo.getText().toString().trim(), password.getText().toString().trim())
                        .addOnCompleteListener(
                                task -> {
                                    if(task.isSuccessful()){

                                        Intent l = new Intent(this, Contactos.class);
                                        startActivity(l);
                                        finish();

                                    }else{

                                        Toast.makeText(this,task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                }
                        );

                break;

            case R.id.pasarRegistro:

                Intent r = new Intent(this, Registro.class);
                startActivity(r);
                finish();

                break;
        }

    }
}