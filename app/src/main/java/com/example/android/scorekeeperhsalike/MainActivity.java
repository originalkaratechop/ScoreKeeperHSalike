package com.example.android.scorekeeperhsalike;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int p1health = 20;
    int p2health = 20;
    Button p1btn;
    Button p2btn;
    TextView intro;
    TextView p1txt;
    TextView p2txt;
    TextView resettxt;
    CountDownTimer delayTop;
    RelativeLayout resetLO;
    long timer = 2000;
    long timer2 = 2000;
    public static final long SHOW_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //3 strings to get rid off action bar + buttons + fullscreen
        setContentView(R.layout.activity_main);
        displayp1hp(p1health);
        displayp2hp(p2health);

        p1btn = (Button) findViewById(R.id.p1btn);
        p2btn = (Button) findViewById(R.id.p2btn);
        p1txt = (TextView) findViewById(R.id.p1txt);
        p2txt = (TextView) findViewById(R.id.p2txt);
        resettxt = (TextView) findViewById(R.id.resetTxt);
        intro = (TextView) findViewById(R.id.intro);
        resetLO = (RelativeLayout) findViewById(R.id.resetOverlay);

        int startRand = new Random().nextInt(2) + 1;

        if (startRand == 1) {
            p1btn.setEnabled(true);
            p2btn.setEnabled(false);
            p1txt.setVisibility(View.VISIBLE);
            p2txt.setVisibility(View.INVISIBLE);
        }
        if (startRand == 2) {
            p1btn.setEnabled(false);
            p2btn.setEnabled(true);
            p1txt.setVisibility(View.INVISIBLE);
            p2txt.setVisibility(View.VISIBLE);
        }

        intro.setText("Let the battle begin! \n Player " + startRand + " starts this round!");

        delayTop = new CountDownTimer(timer, 10) {
            @Override
            public void onTick(long ms) {
                timer = ms;
//                Log.i("ss","onTick: ms " + timer);
            }

            @Override
            public void onFinish() {
                intro.setText("\n");
                timer = 0;
                cancel();
            }
        };

        delayTop.start();

        ImageView imgP1 = (ImageView) findViewById(R.id.p1img);
        Bitmap src1 = BitmapFactory.decodeResource(getResources(), R.drawable.img_p1port);
        RoundedBitmapDrawable dr1 = RoundedBitmapDrawableFactory.create(getResources(), src1);
        dr1.setCornerRadius(Math.max(src1.getWidth(), src1.getHeight()) / 10f);
        imgP1.setImageDrawable(dr1);
//        imgP1.setClipToOutline(true); //simple stuff for future

        ImageView imgP2 = (ImageView) findViewById(R.id.p2img);
        Bitmap src2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_p2port);
        RoundedBitmapDrawable dr2 = RoundedBitmapDrawableFactory.create(getResources(), src2);
        dr2.setCornerRadius(Math.max(src2.getWidth(), src2.getHeight()) / 10f);
        imgP2.setImageDrawable(dr2);

        objAniIntro(intro, 0);

/*        introani = AnimationUtils.loadAnimation(this, R.anim.introalpha);  // old and useless
        final long currAnimTime = AnimationUtils.currentAnimationTimeMillis();
        introani.setFillEnabled(true);
        introani.setFillBefore(true);
        intro.setAnimation(introani);*/

  /*      AnimatorSet aniSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.introani);
        aniSet.setTarget(sss);
//        aniSet.setCurrentPlayTime(1500); //api 26
        aniSet.start();*/

//////////////////////////////
/// animation via pub valueAnimator API11 no fail tons of code

/*        final ValueAnimator animator = ValueAnimator.ofFloat(1,1.5f);
        animator.setDuration(2000);
        animator.setCurrentPlayTime(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float f = (Float) animation.getAnimatedValue();
                p1name.setScaleX(f);
                p1name.setScaleY(f);
            //    p1name.setAlpha(f);
            }
        });
        animator.start();   // works fine*/

//////////////////////////////
/// animation via pub ObjectAnimator API11 no fail tons of code

/*        ObjectAnimator an = ObjectAnimator.ofFloat(p1name, View.SCALE_X, 1, 2);
        an.setDuration(2000);
        an.setCurrentPlayTime(1200);
        an.start();*/ //okay-ish

//////////////////////////////

/// animation via pub local animationSet API 26 fail
 /*       AlphaAnimation showAlphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        showAlphaAnimation.setDuration(2000);
        ScaleAnimation showScaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f,
                android.view.animation.Animation.RELATIVE_TO_SELF, 0.5f);
    //    showScaleAnimation.setDuration(2000);
        AnimationSet showAnimationSet = new AnimationSet(false);
        showAnimationSet.addAnimation(showAlphaAnimation);
        showAnimationSet.addAnimation(showScaleAnimation);
        showAnimationSet.setDuration(2000);
    //    showAnimationSet.setCurrentPlayTime(2000); //API 26
        p1name.startAnimation(showAnimationSet);  //perfect!*/


//////////////////////////////
/// animation via pub valueAnimator API11 no fail tons of code

        //      alphaAnimator(0,1,sss,2000);

//////////////////////////////
/// animationSet via pubs API 26 fail

      /*  AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(showAlphaAnimation());
        animationSet.addAnimation(showScaleAnimation());
        animationSet.setAnimationListener(new OnEndAnimationListener() {  //OnEndAnimationListener from code (google)  import com.princeparadoxes.animationvsanimator.misc.OnEndAnimationListener;
            @Override
            public void onAnimationEnd(Animation animation) {
                p1name.setVisibility(View.GONE);
            }
        });
        p1name.startAnimation(animationSet);*/

//////////////////////////////
/// animationSet via pubs API 26 fail w/o Listener

 /*       AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(showAlphaAnimation());
        animationSet.addAnimation(showScaleAnimation());
        p1name.startAnimation(animationSet);*/

//////////////////////////////
/// loading animation old
/*        long startOffSet = 1500; //delete
        introani = AnimationUtils.loadAnimation(this, R.anim.introalpha);
        introani.setStartOffset(startOffSet);
        intro.setAnimation(introani); */

//////////////////////////////
/// gradient   fail?

/*        GradientDrawable gradDraw = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{ContextCompat.getColor(this, R.color.colorBg),
                        ContextCompat.getColor(this, R.color.colorAccent),
                        ContextCompat.getColor(this, R.color.colorPrimary),
                        ContextCompat.getColor(this, R.color.colorAccent)});
        imgP2.setBackground(gradDraw);*/ //until next time
    }

    public void displayp1hp(int score1) {
        TextView scoreView = (TextView) findViewById(R.id.p1hp);
        scoreView.setText(String.valueOf(score1));
    }

    public void displayp2hp(int score2) {
        TextView scoreView = (TextView) findViewById(R.id.p2hp);
        scoreView.setText(String.valueOf(score2));
    }

    public void attackp1(View v) {
        int rand = new Random().nextInt(5) + 1;
        p2health = p2health - rand;
        if (rand > p2health) {
            p2health = 0;
        }
        displayp2hp(p2health);
        if (p2health == 0) {
            RelativeLayout lay = (RelativeLayout) findViewById(R.id.resetOverlay);
            lay.setVisibility(View.VISIBLE);
            resettxt.setText("Player 2 has lost this battle! \n Play again?");
        }

        p1btn.setEnabled(false);
        p2btn.setEnabled(true);
        p1txt.setVisibility(View.INVISIBLE);
        p2txt.setVisibility(View.VISIBLE);

        final ImageView imgP2dmg = (ImageView) findViewById(R.id.p2dmg);
        final TextView txtP2dmg = (TextView) findViewById(R.id.p2dmgTxt);
        Bitmap src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit1);
        if (rand >= 3 && rand <= 4) {
            src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit2);
        } else if (rand >= 5 && rand <= 6) {
            src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit3);
        }
        RoundedBitmapDrawable dr2dmg = RoundedBitmapDrawableFactory.create(getResources(), src2dmg);
        dr2dmg.setCornerRadius(Math.max(src2dmg.getWidth(), src2dmg.getHeight()) / 10f);
        imgP2dmg.setVisibility(View.VISIBLE);
        imgP2dmg.setImageDrawable(dr2dmg);
        imgP2dmg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.dmgsplash));
        txtP2dmg.setText("-" + rand);
        txtP2dmg.setVisibility(View.VISIBLE);
        txtP2dmg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.txtmove));

//        Log.i("onClick", "onTick: ms " + timer);
        CountDownTimer delay1 = new CountDownTimer(300, 1) {

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                imgP2dmg.setVisibility(View.GONE);
                txtP2dmg.setVisibility(View.GONE);
                cancel();
            }
        };
        delay1.start();
    }

    public void attackp2(View v) {
        int rand = new Random().nextInt(5) + 1;
        p1health = p1health - rand;
        if (rand > p1health) {
            p1health = 0;
        }
        displayp1hp(p1health);
        if (p1health == 0) {
            RelativeLayout lay = (RelativeLayout) findViewById(R.id.resetOverlay);
            lay.setVisibility(View.VISIBLE);
            resettxt.setText("Player 1 has lost this battle! \n Play again?");
        }

        p1btn.setEnabled(true);
        p2btn.setEnabled(false);
        p1txt.setVisibility(View.VISIBLE);
        p2txt.setVisibility(View.INVISIBLE);

        final ImageView imgP1dmg = (ImageView) findViewById(R.id.p1dmg);
        final TextView txtP1dmg = (TextView) findViewById(R.id.p1dmgTxt);
        Bitmap src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit1);
        if (rand >= 3 && rand <= 4) {
            src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit2);
        } else if (rand >= 5 && rand <= 6) {
            src2dmg = BitmapFactory.decodeResource(getResources(), R.drawable.img_hit3);
        }
        RoundedBitmapDrawable dr2dmg = RoundedBitmapDrawableFactory.create(getResources(), src2dmg);
        dr2dmg.setCornerRadius(Math.max(src2dmg.getWidth(), src2dmg.getHeight()) / 10f);
        imgP1dmg.setVisibility(View.VISIBLE);
        imgP1dmg.setImageDrawable(dr2dmg);
        imgP1dmg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.dmgsplash));
        txtP1dmg.setText("-" + rand);
        txtP1dmg.setVisibility(View.VISIBLE);
        txtP1dmg.startAnimation(AnimationUtils.loadAnimation(this, R.anim.txtmove));

//        Log.i("onClick", "onTick: ms " + timer);
        CountDownTimer delay1 = new CountDownTimer(300, 1) {

            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                imgP1dmg.setVisibility(View.GONE);
                txtP1dmg.setVisibility(View.GONE);
                cancel();
            }
        };
        delay1.start();
    }

    public void resetGame(View view) {
        p1health = 20;
        p2health = 20;
        displayp1hp(p1health);
        displayp2hp(p2health);

        int resetRand = new Random().nextInt(2) + 1;

        if (resetRand == 1) {
            p1btn.setEnabled(true);
            p2btn.setEnabled(false);
            p1txt.setVisibility(View.VISIBLE);
            p2txt.setVisibility(View.INVISIBLE);
        }
        if (resetRand == 2) {
            p1btn.setEnabled(false);
            p2btn.setEnabled(true);
            p1txt.setVisibility(View.INVISIBLE);
            p2txt.setVisibility(View.VISIBLE);
        }
        intro.setText("Let the battle begin ! \n Player " + resetRand + " starts this round!");

        RelativeLayout resetLayout = (RelativeLayout) findViewById(R.id.resetOverlay);
        resetLayout.setVisibility(View.GONE);

        objAniIntro(intro, 0);

        CountDownTimer delay1 = new CountDownTimer(SHOW_DURATION, 1) {

            @Override
            public void onTick(long ms) {
                timer = ms;
            }

            @Override
            public void onFinish() {
                intro.setText("\n");
                cancel();
            }
        };
        delay1.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("p1hp", p1health);
        outState.putInt("p2hp", p2health);
        outState.putInt("resetstate", resetLO.getVisibility());
        outState.putInt("p1txtstate", p1txt.getVisibility());
        outState.putInt("p2txtstate", p2txt.getVisibility());
        outState.putBoolean("btn1state", p1btn.isEnabled());
        outState.putBoolean("btn2state", p2btn.isEnabled());
        outState.putCharSequence("resettext", resettxt.getText());
        outState.putCharSequence("introtext", intro.getText());
        outState.putLong("delayms", timer);
        outState.putLong("delayms2", timer2);

        Log.i("onSave", "delay ms " + timer);
        Log.i("onSave", "delay ms " + timer2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        p1health = savedInstanceState.getInt("p1hp");
        p2health = savedInstanceState.getInt("p2hp");
        displayp1hp(p1health);
        displayp2hp(p2health);
        resetLO.setVisibility(savedInstanceState.getInt("resetstate"));
        p1txt.setVisibility(savedInstanceState.getInt("p1txtstate"));
        p2txt.setVisibility(savedInstanceState.getInt("p2txtstate"));
        p1btn.setEnabled(savedInstanceState.getBoolean("btn1state"));
        p2btn.setEnabled(savedInstanceState.getBoolean("btn2state"));
        resettxt.setText(savedInstanceState.getCharSequence("resettext"));
        timer = savedInstanceState.getLong("delayms");
        timer2 = savedInstanceState.getLong("delayms2");

        Log.i("onRestore", "delay ms " + timer);
        Log.i("onRestore", "delay ms " + timer2);
        delayTop = new CountDownTimer(timer, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                intro.setText("\n");
                cancel();
            }
        };
        delayTop.start();

        intro.setText(savedInstanceState.getCharSequence("introtext"));
    }

    /*   public static Animator alphaAnimator(int start, int end, View view, int duration) {
           ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, start, end);
           alphaAnimator.setDuration(duration);
           alphaAnimator.addListener(getLayerTypeListener(view));
           return alphaAnimator;
       }*/

    // 3 animations separately
    private static ObjectAnimator showAlphaObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showAlphaObj = ObjectAnimator.ofFloat(view, View.ALPHA, start, stop);
        showAlphaObj.setDuration(duration);
        showAlphaObj.setCurrentPlayTime(curPlayTime);
        showAlphaObj.start();
        showAlphaObj.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //insert something already
            }
        });
        return showAlphaObj;
    }

    private static ObjectAnimator showScaleXObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleXObj = ObjectAnimator.ofFloat(view, View.SCALE_X, start, stop);
        showScaleXObj.setDuration(duration);
        showScaleXObj.setCurrentPlayTime(curPlayTime);
        showScaleXObj.start();
        return showScaleXObj;
    }

    private static ObjectAnimator showScaleYObj(View view, long duration, long curPlayTime, float start, float stop) {
        ObjectAnimator showScaleYObj = ObjectAnimator.ofFloat(view, View.SCALE_Y, start, stop);
        showScaleYObj.setDuration(duration);
        showScaleYObj.setCurrentPlayTime(curPlayTime);
        showScaleYObj.start();
        return showScaleYObj;
    }

    // all 3 combined
    private static void objAniIntro(View view, long current) {
        showAlphaObj(view, SHOW_DURATION, current, 1f, 0f);
        showScaleXObj(view, SHOW_DURATION, current, 1f, 1.2f);
        showScaleYObj(view, SHOW_DURATION, current, 1f, 1.2f);
    }

/*    //  fancy stuff API 26 for curPlayTime
    private static Animation showAlphaAnimation() {
        AlphaAnimation showAlphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        showAlphaAnimation.setDuration(SHOW_DURATION);
        return showAlphaAnimation;
    }

    private static Animation showScaleAnimation() {
        ScaleAnimation showScaleAnimation = new ScaleAnimation(
                1.0f, 1.2f, 1.0f, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        showScaleAnimation.setDuration(SHOW_DURATION);
        return showScaleAnimation;
    }*/
}