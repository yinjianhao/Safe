package com.me.safe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.me.safe.R;

public class Guide4Activity extends BaseGuideActivity implements View.OnClickListener {

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

        CheckBox cbProtect = (CheckBox) findViewById(R.id.cb_protect);
        if (cbProtect != null) {
            final SharedPreferences sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);

            boolean isProtect = sharedPreferences.getBoolean("isProtect", false);
            if (isProtect) {
                cbProtect.setText("您已开启防盗保护");
                cbProtect.setChecked(true);
            }
            cbProtect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        buttonView.setText("您已开启防盗保护");
                        sharedPreferences.edit().putBoolean("isProtect", true).apply();
                    } else {
                        buttonView.setText("您还没有开启防盗保护");
                        sharedPreferences.edit().remove("isProtect").apply();
                    }
                }
            });
        }
    }

    @Override
    public void SlidingRight() {
        showRight();
    }

    @Override
    public void SlidingLeft() {
        showLeft();
    }

    private void showRight() {
        getSharedPreferences("setting", MODE_PRIVATE).edit().putBoolean("isGuide", true).apply();
        startActivity(new Intent(this, FinderActivity.class));
        finish();
    }

    private void showLeft() {
        startActivity(new Intent(this, Guide3Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_ltr_enter, R.anim.anim_ltr_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                showRight();
                break;
            case R.id.btn_prev:
                showLeft();
                break;
        }
    }
}
