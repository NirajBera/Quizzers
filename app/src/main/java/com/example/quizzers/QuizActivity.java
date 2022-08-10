package com.example.quizzers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import com.example.quizzers.databinding.ActivityQuizBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
ActivityQuizBinding binding;
ArrayList<Question> questions;
int index =0;
Question question;
// android on class for timer
CountDownTimer timer;
FirebaseFirestore database;
int correctAnswer=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityQuizBinding.inflate(getLayoutInflater());


        //setContentView(R.layout.activity_quiz);
        setContentView(binding.getRoot());
        questions=new ArrayList<>();
        database=FirebaseFirestore.getInstance();
        //use catid for categories
        final String catId=getIntent().getStringExtra("catId");
        //for use rendon question use rendom class
        Random random=new Random();
        final int rand =random.nextInt(8);

        database.collection("categories")
                                .document(catId)
                                .collection("questions")
                                .whereGreaterThanOrEqualTo("index",rand)
                                .orderBy("index")
                //limit qustion
                                     .limit(5)
                                             .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.getDocuments().size()<5){
                            database.collection("categories")
                                    .document(catId)
                                    .collection("questions")
                                    .whereLessThanOrEqualTo("index",rand)
                                    .orderBy("index")
                                    //limit qustion
                                    .limit(5)
                                    .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                                //all qustion are retrive to convert in snapshort object and question
                                                for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                                                    Question question= snapshot.toObject(Question.class);
                                                    questions.add(question);
                                                }
                                            setNextQuestion();

                                        }
                                    });

                        }else {
                            //all qustion are retrive to convert in snapshort object and question
                            for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                                Question question= snapshot.toObject(Question.class);
                                questions.add(question);
                            }
                            setNextQuestion();
                        }
                    }
                });

        resetTimer();


    }
    void resetTimer(){
        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //left secound
                binding.timer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {

            }
        };
    }
    void showAnswer() {
        if(question.getAnswer().equals(binding.option1.getText().toString()))
            binding.option1.setBackground(getResources().getDrawable(R.drawable.option_right));
        else  if(question.getAnswer().equals(binding.option2.getText().toString()))
            binding.option2.setBackground(getResources().getDrawable(R.drawable.option_right));
        else  if(question.getAnswer().equals(binding.option3.getText().toString()))
            binding.option3.setBackground(getResources().getDrawable(R.drawable.option_right));
        else  if(question.getAnswer().equals(binding.option4.getText().toString()))
            binding.option4.setBackground(getResources().getDrawable(R.drawable.option_right));

    }
    void setNextQuestion(){
        if(timer !=null)
            timer.cancel();

        timer.start();
        if(index< questions.size()){
            //set question number count
            binding.questionCounter.setText(String.format("%d/%d",(index+1),questions.size()));
            question=questions.get(index);
            binding.question.setText(question.getQuestion());
            binding.option1.setText(question.getOption1());
            binding.option2.setText(question.getOption2());
            binding.option3.setText(question.getOption3());
            binding.option4.setText(question.getOption4());

        }

    }
    void checkAnswer(TextView textView){
        // select answer
        String selectedAnswer=textView.getText().toString();
        if(selectedAnswer.equals(question.getAnswer())){
            correctAnswer++;
            textView.setBackground(getResources().getDrawable(R.drawable.option_right));
        }else {

            showAnswer();
            textView.setBackground(getResources().getDrawable(R.drawable.option_wrong));
        }
    }
    //for reseat answer after one qus
    void reset(){
        binding.option1.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option2.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option3.setBackground(getResources().getDrawable(R.drawable.option_unselected));
        binding.option4.setBackground(getResources().getDrawable(R.drawable.option_unselected));

    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.option_1:
            case R.id.option_2:
            case R.id.option_3:
            case R.id.option_4:
                if(timer !=null)
                    timer.cancel();
                TextView selected =(TextView) view;
                checkAnswer(selected);
                break;
            case R.id.nextBtn:
                reset();
                if(index<4) {
                    index++;
                    setNextQuestion();
                }else {
                    Intent intent=new Intent(QuizActivity.this,ResultActivity.class);
                    intent.putExtra("correct",correctAnswer);
                    intent.putExtra("total",questions.size());
                    startActivity(intent);
                    //finish();
                    //Toast.makeText(this, "Quize finished", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        binding.quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(QuizActivity.this,MainActivity2.class);
                startActivity(intent);

            }
        });

    }
}