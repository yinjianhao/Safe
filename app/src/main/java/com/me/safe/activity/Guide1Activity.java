package com.me.safe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.me.safe.R;

public class Guide1Activity extends BaseGuideActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide1);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }
    }

    @Override
    public void SlidingRight() {
        showRight();
    }

    @Override
    public void SlidingLeft() {

    }

    private void showRight() {
        startActivity(new Intent(this, Guide2Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_rtl_enter, R.anim.anim_rtl_out);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                showRight();
                break;
        }
    }
}
