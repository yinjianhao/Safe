package com.me.safe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.me.safe.R;
import com.me.safe.view.SettingItemView;

public class Guide2Activity extends BaseGuideActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;
    private SettingItemView svSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide2);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }
        Button btnPrev = (Button) findViewById(R.id.btn_prev);
        if (btnPrev != null) {
            btnPrev.setOnClickListener(this);
        }

        sharedPreferences = getSharedPreferences("setting", MODE_PRIVATE);
        svSim = (SettingItemView) findViewById(R.id.sv_sim);
        if (svSim != null) {
            svSim.setOnClickListener(this);
            if (!TextUtils.isEmpty(sharedPreferences.getString("simNum", null))) {
                svSim.setChecked(true);
            }
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
        startActivity(new Intent(this, Guide3Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_rtl_enter, R.anim.anim_rtl_out);
    }

    private void showLeft() {
        startActivity(new Intent(this, Guide1Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_ltr_enter, R.anim.anim_ltr_out);
    }

    private void handleCheckBox() {
        if (!svSim.isChecked()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                handleSim();
            }
        } else {
            sharedPreferences.edit().remove("simNUm").apply();
            svSim.setChecked(false);
        }
    }

    private void handleSim() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String simSerialNumber = telephonyManager.getSimSerialNumber();
        sharedPreferences.edit().putString("simNum", simSerialNumber).apply();
        svSim.setChecked(true);
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
            case R.id.sv_sim:
                handleCheckBox();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleSim();
            }
        }
    }
}

