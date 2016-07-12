package com.me.safe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.me.safe.R;

public class FinderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isGuide = getSharedPreferences("setting", MODE_PRIVATE).getBoolean("isGuide", false);
        if(isGuide) {
            setContentView(R.layout.activity_finder);
        } else {
            startActivity(new Intent(this, Guide1Activity.class));
            finish();
        }
    }
}
