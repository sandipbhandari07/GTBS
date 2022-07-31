package clz.project.gtbs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;

import android.os.Bundle;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4150;

    Animation bottom,top;
    TextView txtslogan,txt;
    ImageView logo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(Color.BLACK);
        setContentView(R.layout.activity_main);

        bottom= AnimationUtils.loadAnimation(this,R.anim.bottom);
        top=AnimationUtils.loadAnimation(this,R.anim.top);

        txtslogan=findViewById(R.id.slogan);
        txt=findViewById(R.id.gtbs);
        logo=findViewById(R.id.logo);

        txtslogan.setAnimation(bottom);
        txt.setAnimation(bottom);
        logo.setAnimation(bottom);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this,IntroHome.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}