package com.me.safe.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.me.safe.R;
import com.me.safe.utils.StreamUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";

    private static final int CODE_UPDATE_DIALOG = 1;
    private static final int CODE_ENTER_HOME = 2;
    private static final int CODE_WRITE_STORAGE = 3;

    private String mVersionName;
    private int mVersionCode;
    private String mVersionDes;
    private String mVersionUrl;

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_UPDATE_DIALOG:
                    showUpdateDialog();
                    break;
                case CODE_ENTER_HOME:
                    enterHome();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView tvVersionName = (TextView) findViewById(R.id.tv_version_name);

        if (tvVersionName != null) {
            tvVersionName.setText("版本号:" + getVersionName());
        }

        checkUpdate();
    }

    /**
     * 从服务器获取版本信息,检查更新
     */
    private void checkUpdate() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL("http://10.0.2.2:8080/version.json");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        InputStream is = httpURLConnection.getInputStream();
                        String inputString = StreamUtil.stream2String(is);
                        JSONObject jsonObject = new JSONObject(inputString);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionCode = jsonObject.getInt("versionCode");
                        mVersionDes = jsonObject.getString("des");
                        mVersionUrl = jsonObject.getString("url");

                        if (mVersionCode > getVersionCode()) {
                            msg.what = CODE_UPDATE_DIALOG;
                        } else {
                            msg.what = CODE_ENTER_HOME;
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    msg.what = CODE_ENTER_HOME;
                } finally {
                    long endTime = System.currentTimeMillis();
                    long usedTime = endTime - startTime;
                    if (usedTime < 2000) {
                        try {
                            Thread.sleep(2000 - usedTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    /**
     * 升级对话框
     */
    private void showUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("发现新版本:" + mVersionName);
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //判断运行时权限
                if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    downloadApk();
                } else {
                    applyPermission();
                }
            }
        });
        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });
        builder.show();
    }

    /**
     * 申请权限
     */
    private void applyPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, CODE_WRITE_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODE_WRITE_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadApk();
        }
    }

    /**
     * 下载apk
     */
    private void downloadApk() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            RequestParams requestParams = new RequestParams(mVersionUrl);
            requestParams.setSaveFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/app-debug.apk");
            Callback.Cancelable cancelable = x.http().get(requestParams, new Callback.CommonCallback<File>() {
                @Override
                public void onSuccess(File result) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(result);
                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                    startActivity(intent);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    ex.printStackTrace();
                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {
                    Log.d(TAG, "onFinished");
                }
            });
        } else {
            Toast.makeText(this, "SD卡不存在,下载失败", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 进入主页面
     */
    private void enterHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
