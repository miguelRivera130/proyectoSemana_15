package com.example.semanatrece;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class Contactos extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private Usuario usuario;

    private EditText numero;
    private EditText nombre;

    private Button agregar;
    private TextView signOut;

    private ListView listaContantactos;
    private ContactoAdaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() == null) regresoLogin();
        else {

            listaContantactos = findViewById(R.id.listaContacto);

            /*problemas al tratar de pintar el adaptador, no muestra nad aaunque ya este todo su respectivo codigo*/
            adaptador = new ContactoAdaptador();
            listaContantactos.setAdapter(adaptador);

            agregar = findViewById(R.id.agregar);
            numero = findViewById(R.id.telefono);
            nombre = findViewById(R.id.nombreC);
            signOut = findViewById(R.id.signOut);

            agregar.setOnClickListener(this);
            signOut.setOnClickListener(this);

        }
    }

    private void regresoLogin() {

        Intent l = new Intent(this,Login.class);
        startActivity(l);
        finish();

    }

    public void aggContacto(){

        if(auth.getCurrentUser() != null){

            String idUser = auth.getCurrentUser().getUid();

            database.getReference().child("usuarios").child(idUser).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {

                            String idCont = UUID.randomUUID().toString();
                            usuario = snapshot.getValue(Usuario.class);

                            Contacto contacto = new Contacto(

                                    idCont,
                                    nombre.getText().toString(),
                                    numero.getText().toString()

                            );
                            database.getReference().child("contactos").child(usuario.getId()).child(idCont).setValue(contacto);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    }
            );
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.agregar:

                aggContacto();

                break;

            case R.id.signOut:

                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("Cerrar sesión")
                        .setMessage("¿Estás seguro que deceas cerrar sesión?")
                        .setPositiveButton("Si", (dialog, idD) -> {

                            auth.signOut();
                            regresoLogin();
                        })
                        .setNegativeButton("No", (dialog, idD) -> {

                            dialog.dismiss();

                        });
                builder.show();
                break;
        }

    }

    public void loadDatabase() {

        DatabaseReference ref = database.getReference().child("contactos").child(usuario.getId());
        ref.addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child : snapshot.getChildren()){

                            Contacto contacto = child.getValue(Contacto.class);
                            adaptador.addContacto(contacto);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }
        );
    }
}