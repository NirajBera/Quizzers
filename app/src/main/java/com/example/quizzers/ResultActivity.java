package com.example.quizzers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.quizzers.databinding.ActivityResultBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivity extends AppCompatActivity {
    ActivityResultBinding binding;
    int POINTS=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResultBinding.inflate(getLayoutInflater());
       setContentView(binding.getRoot());
       int correctAnswers=getIntent().getIntExtra("correct",0);
       int totalQuestions = getIntent().getIntExtra("total",0);
       // coin point
       long points=correctAnswers*POINTS;
       binding.score.setText(String.format("%d/%d",correctAnswers,totalQuestions));
       binding.earnedCoins.setText(String.valueOf(points));
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                // coins increment
                .update("coins", FieldValue.increment(points));

        binding.restartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
                startActivity(intent);

            }

    });
        binding.shareBtn.setOnClickListener(V->{
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(i.EXTRA_TEXT,"https://drive.google.com/file/d/11wankjUfUwIGZCcmnZVzUybxP2iRZeF7/view?usp=sharing");
            startActivity(Intent.createChooser(i,"Shared To:"));

        });




}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity2.class));
    }
}