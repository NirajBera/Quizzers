package com.example.quizzers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static  int SPLASH_SCREEN=3500;
    Animation top_an,bot_an;
    ImageView image;
    TextView logo,slogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // in above line no:26 is the full screen window it not consider status bar so we use line : 28 setStatusBarColor
        getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.primary));
        setContentView(R.layout.activity_main);
        top_an = AnimationUtils.loadAnimation(this,R.anim.top_a);
        bot_an=  AnimationUtils.loadAnimation(this,R.anim.bot_a);

        image=findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogo = findViewById(R.id.textView2);

        image.setAnimation(top_an);
        logo.setAnimation(bot_an);
        slogo.setAnimation(bot_an);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                //finish();
            }
        },SPLASH_SCREEN);

    }
}