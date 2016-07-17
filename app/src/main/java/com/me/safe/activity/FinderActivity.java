package com.me.safe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.me.safe.R;

public class FinderActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isGuide = getSharedPreferences("setting", MODE_PRIVATE).getBoolean("isGuide", false);
        if(isGuide) {
            setContentView(R.layout.activity_finder);
        } else {
            startActivity(new Intent(this, Guide1Activity.class));
            finish();
            return;
        }

        TextView tvGuide = (TextView) findViewById(R.id.tv_guide);
        if (tvGuide != null) {
            tvGuide.setOnClickListener(this);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        boolean isProtect = sharedPreferences.getBoolean("isProtect", false);

        TextView tvPhone = (TextView) findViewById(R.id.tv_phone);
        if (tvPhone != null) {
            String phone = getSharedPreferences("setting", MODE_PRIVATE).getString("safePhone", "未设置");
            tvPhone.setText(phone);
        }

        TextView tvLockDesc = (TextView) findViewById(R.id.tv_lock_desc);
        ImageView ivLock = (ImageView) findViewById(R.id.iv_lock);
        if(isProtect) {
            if (tvLockDesc != null && ivLock != null) {
                tvLockDesc.setText("防盗保护已开启");
                ivLock.setBackgroundResource(R.drawable.lock);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_guide:
                startActivity(new Intent(this, Guide1Activity.class));
                break;
        }
    }
}
