package com.me.safe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.me.safe.R;

public class Guide4Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide4);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }
        Button btnPrev = (Button) findViewById(R.id.btn_prev);
        if (btnPrev != null) {
            btnPrev.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
//                getSharedPreferences("setting", MODE_PRIVATE).edit().putBoolean("isGuide", true).apply();
                startActivity(new Intent(this, FinderActivity.class));
                finish();
                break;
            case R.id.btn_prev:
                startActivity(new Intent(this, Guide3Activity.class));
                finish();
                break;
        }
    }
}