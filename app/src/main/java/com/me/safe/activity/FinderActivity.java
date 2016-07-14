package com.me.safe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
        }

        TextView tvGuide = (TextView) findViewById(R.id.tv_guide);
        if (tvGuide != null) {
            tvGuide.setOnClickListener(this);
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
