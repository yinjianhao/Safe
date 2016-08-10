package com.me.safe.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.me.safe.R;
import com.me.safe.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    private SettingItemView svUpdate;
    private SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting = getSharedPreferences("setting", MODE_PRIVATE);

        svUpdate = (SettingItemView) findViewById(R.id.sv_update);
        if (svUpdate != null) {
            svUpdate.setOnChangeListener(new SettingItemView.onChangeListener() {
                @Override
                public void onChange(View v, Boolean isChecked) {
                    setting.edit().putBoolean("update", isChecked).apply();
                }
            });

            svUpdate.setChecked(setting.getBoolean("update", true));
        }
    }
}
