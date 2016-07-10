package com.me.safe.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.me.safe.R;
import com.me.safe.view.SettingItemView;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingItemView svUpdate;
    private SharedPreferences setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting = getSharedPreferences("setting", MODE_PRIVATE);

        svUpdate = (SettingItemView) findViewById(R.id.sv_update);
        if (svUpdate != null) {
            svUpdate.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sv_update:
                clickUpdateItem();
                break;
            default:
                break;
        }
    }

    /**
     * 点击更新选项
     */
    private void clickUpdateItem() {
        Boolean checked = svUpdate.isChecked();
        setting.edit().putBoolean("update", !checked).apply();
        svUpdate.setChecked(!checked);
    }
}
