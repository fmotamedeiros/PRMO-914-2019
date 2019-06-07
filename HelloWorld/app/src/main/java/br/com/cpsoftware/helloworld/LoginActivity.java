package br.com.cpsoftware.helloworld;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);
        this.mAuth = FirebaseAuth.getInstance();

        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mEmailSignInButton = (Button) findViewById(R.id.login);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        Button mEmailRegisterButton = (Button) findViewById(R.id.register);
        mEmailRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

    }

    private void login(){
        String password = this.mPasswordView.getText().toString();
        String email = this.mEmailView.getText().toString();

        Log.d("LoginActivity", "Iniciando login!");

        this.mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("LoginActivity", "No OnComplete do login!");

                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        finish();
                        Intent homepage = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homepage);
                    } else {
                        Log.d("LoginActivity", "Erro no login!");
                        Toast.makeText(LoginActivity.this, "Login failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("LoginActivity", "No OnFailure do login!");

                Toast.makeText(LoginActivity.this, "CHEGOU2!.",
                        Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUser(){
        String password = this.mPasswordView.getText().toString();
        String email = this.mEmailView.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("LoginActivity", "No OnComplete do registro!");
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Registration succeed.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Registration failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }

            })
            .addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("LoginActivity", "No OnFailure do registro!");

                    Toast.makeText(LoginActivity.this, "CHEGOU2!.",
                            Toast.LENGTH_SHORT).show();
                    Toast.makeText(LoginActivity.this, e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }


}

