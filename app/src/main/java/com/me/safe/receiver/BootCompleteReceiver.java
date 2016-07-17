package com.me.safe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * 开机广播
 *
 * @auther yjh
 * @date 2016/7/16
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String simNum = context.getSharedPreferences("setting", Context.MODE_PRIVATE).getString("simNUm", "");
        if (!TextUtils.isEmpty(simNum)) {
            TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String simSerialNumber = manager.getSimSerialNumber();
            if (!simSerialNumber.equals(simNum)) {
                Log.d(TAG, "sim卡已改变");
            }
        }
    }
}
