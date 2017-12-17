package com.example.android.scorekeeperhsalike;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int p1health = 20;
    int p2health = 20;
    ImageButton p1btn;
    Button p2btn;
    TextView intro;
    TextView p1txt;
    TextView p2txt;
    TextView resettxt;
    CountDownTimer delayTop;
    Animation introani;
    long timer = 2000;

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

        int startRand = new Random().nextInt(2) + 1;

        p1btn = (ImageButton) findViewById(R.id.p1btn);
        p2btn = (Button) findViewById(R.id.p2btn);
        p1txt = (TextView) findViewById(R.id.p1txt);
        p2txt = (TextView) findViewById(R.id.p2txt);
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
        resettxt = (TextView) findViewById(R.id.resetTxt);
        intro = (TextView) findViewById(R.id.intro);
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

        introani = AnimationUtils.loadAnimation(this, R.anim.introalpha);
        introani.setStartTime(AnimationUtils.currentAnimationTimeMillis());
        intro.setAnimation(introani);
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

        intro.startAnimation(AnimationUtils.loadAnimation(this, R.anim.introalpha));

        CountDownTimer delay1 = new CountDownTimer(2000, 1) {

            @Override
            public void onTick(long l) {
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
        RelativeLayout reset = (RelativeLayout) findViewById(R.id.resetOverlay);
        outState.putInt("resetstate", reset.getVisibility());
        outState.putCharSequence("resettext", resettxt.getText());
        outState.putCharSequence("introtext", intro.getText());
        outState.putLong("delayms", timer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        p1health = savedInstanceState.getInt("p1hp");
        p2health = savedInstanceState.getInt("p2hp");
        displayp1hp(p1health);
        displayp2hp(p2health);
        RelativeLayout reset = (RelativeLayout) findViewById(R.id.resetOverlay);
        reset.setVisibility(savedInstanceState.getInt("resetstate"));
        resettxt.setText(savedInstanceState.getCharSequence("resettext"));

        Log.i("msss", "delay ms " +savedInstanceState.getLong("delayms"));
        delayTop = new CountDownTimer(savedInstanceState.getLong("delayms"), 10) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                intro.setText("\n");
                cancel();
            }
        };
        delayTop.start();

        introani.setStartTime(savedInstanceState.getLong("delayms"));
        intro.setText(savedInstanceState.getCharSequence("introtext"));
    }
}