package com.mehedi.bdrice.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.mehedi.bdrice.R;

public class SplashActivity extends AppCompatActivity {
    private Animation topAnim,bottomAnim,fadeout,leftToRight,rightToLeft;
    private ImageView logoImage;
    private TextView slogan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

      // Actions bar transparent
        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        logoImage = findViewById( R.id.logoImage );
        slogan = findViewById( R.id.slogan );

        //animations
//        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
//        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
//        fadeout = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        leftToRight = AnimationUtils.loadAnimation(this,R.anim.left_to_right_animation);
        rightToLeft = AnimationUtils.loadAnimation(this,R.anim.right_to_left_animation);

        //hooks
        logoImage.setAnimation(leftToRight);
        slogan.setAnimation(rightToLeft);

        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(logoImage,"logo_image");
        pairs[1] = new Pair<View,String>(slogan,"logo_describe");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,pairs);
        }


        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                startActivity( new Intent( SplashActivity.this,RiceActivity.class ) );
                finish();
            }
        },2500 );

    }
}