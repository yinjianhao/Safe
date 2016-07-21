package com.me.safe.activity;

import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.safe.R;
import com.me.safe.dao.AddressDao;

public class QueryAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);

        final EditText etPhone = (EditText) findViewById(R.id.et_phone);
        Button btnQuery = (Button) findViewById(R.id.btn_query);
        final TextView tvResult = (TextView) findViewById(R.id.tv_result);

        if (btnQuery != null) {
            btnQuery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etPhone != null) {
                        String phone = etPhone.getText().toString();
                        if (TextUtils.isEmpty(phone)) {
                            Animation shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                            etPhone.startAnimation(shake);
                            vibrator();
                        } else {
                            String address = AddressDao.getAddress(getApplicationContext(), phone);
                            if (tvResult != null) {
                                tvResult.setText(address);
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 手机震动
     */
    private void vibrator() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        //arg1:奇数位休息时间,偶数震动时间
        //arg2:-1:不重复,0:第0个位置开始重复....
        vibrator.vibrate(new long[]{0, 400, 100, 400}, -1);

//        vibrator.cancel();  //停止震动
    }
}
