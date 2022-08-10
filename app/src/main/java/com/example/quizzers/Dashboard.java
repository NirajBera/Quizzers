package com.example.quizzers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quizzers.databinding.ActivityDashboardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Dashboard extends AppCompatActivity {
    ActivityDashboardBinding binding;
    FirebaseAuth auth;
    FirebaseFirestore database;
    ProgressDialog dialog;
    Button createNewBtn;
    EditText email,pass,name,raferCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inflate
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        // firebase instance
        auth = FirebaseAuth.getInstance();
        database=FirebaseFirestore.getInstance();
        dialog=new ProgressDialog(this);
        dialog.setMessage("We are creating new account...");

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // setContentView(R.layout.activity_dashboard);
        createNewBtn=findViewById(R.id.createNewBtn);
        email=findViewById(R.id.emailBox);
        pass=findViewById(R.id.passwordBox);
        name=findViewById(R.id.nameBox);
        raferCode=findViewById(R.id.raferBox);
        createNewBtn.setOnClickListener(v -> {
            String email1, pass1,name1,referCode1;

            // create account page user's email and papassss
            email1 = email.getText().toString();
            pass1 = pass.getText().toString();
            name1=name.getText().toString();
            referCode1=raferCode.getText().toString();

            //create user model class user object
           final User user=new User(email1,name1,pass1,referCode1);
           dialog.show();
            //create user with email and pass
            //onCompletelistener:to run task
            auth.createUserWithEmailAndPassword(email1,pass1).addOnCompleteListener(task -> {
                //store result is successful or unsuccessful
                if (task.isSuccessful()) {
                    // get user unique authentication UID form firebase
                    String uid =task.getResult().getUser().getUid();
                    database
                            .collection("users")
                            .document(uid)
                            .set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        dialog.dismiss();
                                        startActivity(new Intent(Dashboard.this, MainActivity2.class));
                                        finish();
                                    } else {
                                        dialog.dismiss();
                                        Toast.makeText(Dashboard.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                   // Intent i=new Intent(getApplicationContext(),Login.class);
                    //startActivity(i);
                    //Toast.makeText(Dashboard.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(Dashboard.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(Dashboard.this, "Success", Toast.LENGTH_SHORT).show();
            });
        });
        binding.Account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                startActivity(i);
                finish();
            }
        });

    }
}