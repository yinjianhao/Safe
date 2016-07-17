package com.me.safe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 短信广播
 *
 * @auther yjh
 * @date 2016/7/16
 */
public class SmsReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("aaa", "bbb");
    }
}
