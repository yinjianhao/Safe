package com.me.safe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.me.safe.R;

public class ToolActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);

        TextView tvQuery = (TextView) findViewById(R.id.tv_query_address);
        if (tvQuery != null) {
            tvQuery.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_query_address:
                startActivity(new Intent(this, QueryAddressActivity.class));
                break;
        }
    }
}
