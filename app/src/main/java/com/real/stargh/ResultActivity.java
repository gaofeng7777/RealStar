package com.real.stargh;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.real.stargh.utils.Printscreen;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout iv_a_rl_fail;
    private RelativeLayout iv_b_rl_fail;
    private RelativeLayout iv_a_rl_win;
    private RelativeLayout iv_b_rl_win;
    private ImageView iv_a_fail;
    private ImageView iv_b_fail;
    private ImageView iv_a_win;
    private ImageView iv_b_win;
    private ProgressBar pro;

    private int age_a;
    private int beauty_a;
    private int age_b;
    private int beauty_b;

    private Bitmap bitmap;
    private TextView tv_c;
    private TextView tv_b;
    private TextView tv_a;
    private ImageView loading;
    private AnimationDrawable animationDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.camera_a).setOnClickListener(this);
        iv_a_rl_fail = (RelativeLayout) findViewById(R.id.iv_a_rl_fail);
        iv_b_rl_fail = (RelativeLayout) findViewById(R.id.iv_b_rl_fail);
        iv_a_rl_win = (RelativeLayout) findViewById(R.id.iv_a_rl_win);
        iv_b_rl_win = (RelativeLayout) findViewById(R.id.iv_b_rl_win);
        iv_a_fail = (ImageView) findViewById(R.id.iv_a_fail);
        iv_b_fail = (ImageView) findViewById(R.id.iv_b_fail);
        iv_a_win = (ImageView) findViewById(R.id.iv_a_win);
        iv_b_win = (ImageView) findViewById(R.id.iv_b_win);
        tv_a = (TextView) findViewById(R.id.tv_a);
        tv_b = (TextView) findViewById(R.id.tv_b);
        tv_c = (TextView) findViewById(R.id.tv_c);
        pro = (ProgressBar) findViewById(R.id.pro);
        loading = (ImageView) findViewById(R.id.loading);
        loading.setBackgroundResource(R.drawable.loading);
        animationDrawable = (AnimationDrawable) loading.getBackground();

//        intent.putExtra("tag", tag);//男或女明星
//        intent.putExtra("pos", pos);//第pos位明星
//        intent.putExtra("beauty_star", beauty_star);//明星的魅力值
//
//        intent.putExtra("byte", bytea);//自己
//        intent.putExtra("age",age);//年龄
//        intent.putExtra("beauty", beauty);//用户的魅力值
        Intent intent = getIntent();

        Bitmap bitmapa = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("bytea"), 0, intent.getByteArrayExtra("bytea").length);
        Bitmap bitmapb = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("byteb"), 0, intent.getByteArrayExtra("byteb").length);
        age_a = intent.getIntExtra("age_a", 0);
        beauty_a = intent.getIntExtra("beauty_a", 60);
        age_b = intent.getIntExtra("age_b", 0);
        beauty_b = intent.getIntExtra("beauty_b", 60);

        if (beauty_a >= beauty_b) {
            iv_a_rl_win.setVisibility(View.VISIBLE);
            iv_b_rl_fail.setVisibility(View.VISIBLE);

            iv_a_win.setImageBitmap(bitmapa);
            iv_b_fail.setImageBitmap(bitmapb);

            tv_b.setText(age_a+"");
            tv_c.setText(beauty_a+"POINTS");
            pro.setProgress(beauty_a);

        } else {
            iv_b_rl_win.setVisibility(View.VISIBLE);
            iv_a_rl_fail.setVisibility(View.VISIBLE);

            iv_a_fail.setImageBitmap(bitmapa);
            iv_b_win.setImageBitmap(bitmapb);

            tv_b.setText(age_b+"");
            tv_c.setText(beauty_b+"POINTS");
            pro.setProgress(beauty_b);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                Printscreen.takePrintscreen(ResultActivity.this);//截屏
                break;
            case R.id.camera_a:
                finish();
                break;
        }
    }
}
