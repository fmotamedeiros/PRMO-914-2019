package br.com.cpsoftware.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText nomeTxtView;
    private EditText sobrenomeTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseApp.initializeApp(this);

        this.mAuth = FirebaseAuth.getInstance();

        nomeTxtView = (EditText) findViewById(R.id.nome);
        sobrenomeTxtView = (EditText) findViewById(R.id.sobrenome);

        Button mEmailSignInButton = (Button) findViewById(R.id.logout);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        Button mCadastroButton = (Button) findViewById(R.id.btCadastro);
        mCadastroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        Button mListButton = (Button) findViewById(R.id.btList);
        mListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list();
            }
        });
    }

    private void logout(){
        this.mAuth.signOut();

        finish();
        Intent homepage = new Intent(this, LoginActivity.class);
        startActivity(homepage);
    }

    private void add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("clients").push();

        Map<String, String> client = new HashMap<String, String>();
        client.put("nome", this.nomeTxtView.getText().toString());
        client.put("sobrenome", this.sobrenomeTxtView.getText().toString());

        Log.i("Home", "Adicionando: " + this.nomeTxtView.getText().toString());
        myRef.setValue(client);

        // remover o push e adicionar um identificador manualmente
        // myRef.child(myRef.getKey()).setValue(client);

        this.nomeTxtView.setText("");
        this.sobrenomeTxtView.setText("");

    }

    private void list(){
        Log.i("Home", "Método list");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("clients");


        ValueEventListener clientListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Home", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addValueEventListener(clientListener);
    }

}
