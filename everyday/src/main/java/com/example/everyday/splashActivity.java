package com.example.everyday;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.everyday.util.spfUtil;

public class splashActivity extends AppCompatActivity {

    private RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        rl = (RelativeLayout) findViewById(R.id.rl_root);
        initAnimation();
    }

    private void initAnimation() {
        RotateAnimation ra=new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        ra.setDuration(2000);
        ra.setFillAfter(true);
        ScaleAnimation sa=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        sa.setDuration(2000);
        ra.setFillAfter(true);
        AlphaAnimation aa=new AlphaAnimation(0,1);
        aa.setDuration(2000);
        aa.setFillAfter(true);
        AnimationSet as=new AnimationSet(true);
        as.addAnimation(ra);
        as.addAnimation(sa);
        as.addAnimation(aa);
        rl.startAnimation(as);
        as.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                boolean flash=spfUtil.getBoolean(getBaseContext(),"is_first",true);
                Intent intent;
                if (flash){
                    intent=new Intent(getBaseContext(),welcomeActivity.class);
                }else {
                    intent=new Intent(getBaseContext(),MainActivity.class);
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
