package com.me.safe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.safe.R;
import com.me.safe.dialog.CreatePwdDialog;
import com.me.safe.dialog.InputDialog;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeActivity";
    private String[] mNames;
    private int[] mLogos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        GridView gvBody = (GridView) findViewById(R.id.gv_body);

        mNames = new String[]{"手机防盗", "通讯卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
        mLogos = new int[]{R.drawable.home_safe, R.drawable.home_callmsgsafe, R.drawable.home_apps,
                R.drawable.home_taskmanager, R.drawable.home_netmanager, R.drawable.home_trojan,
                R.drawable.home_sysoptimize, R.drawable.home_tools, R.drawable.home_settings};

        HomeGridAdapter adapter = new HomeGridAdapter();
        if (gvBody != null) {
            gvBody.setAdapter(adapter);
            gvBody.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            showDialog();
                            break;
                        case 2:
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                            startActivity(intent);
                            break;
                        case 8:
                            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //判断权限
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED)) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 0);
        }
    }

    private void showDialog() {
        String savedPwd = getSharedPreferences("setting", MODE_PRIVATE).getString("password", null);
        if (TextUtils.isEmpty(savedPwd)) {
            showCreateDialog();
        } else {
            showPwdDialog(savedPwd);
        }
    }

    /**
     * 输入密码对话框
     */
    private void showPwdDialog(final String savedPwd) {
        InputDialog dialog = new InputDialog(this, "输入密码", "请输入密码", new InputDialog.OnBtnClickListener() {
            @Override
            public void onConfirm(InputDialog dialog, String pwd) {
                if (savedPwd.equals(pwd)) {
                    startActivity(new Intent(HomeActivity.this, FinderActivity.class));
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancel(InputDialog dialog) {

            }
        });
        dialog.show();
    }

    /**
     * 创建密码对话框
     */
    private void showCreateDialog() {
        CreatePwdDialog dialog = new CreatePwdDialog(this, "创建密码", new CreatePwdDialog.OnBtnClickListener() {
            @Override
            public void onConfirm(CreatePwdDialog dialog, String pwd, String pwdConfirm) {
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdConfirm)) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    if (pwd.equals(pwdConfirm)) {
                        getSharedPreferences("setting", MODE_PRIVATE).edit().putString("password", pwd).apply();
                        startActivity(new Intent(HomeActivity.this, FinderActivity.class));
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "两次密码不相同", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancel(CreatePwdDialog dialog) {

            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * GridView的adapter
     */
    private class HomeGridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                view = View.inflate(HomeActivity.this, R.layout.list_item_home, null);
            } else {
                view = convertView;
            }

            ((ImageView) view.findViewById(R.id.iv_logo)).setImageResource(mLogos[position]);
            ((TextView) view.findViewById(R.id.tv_name)).setText(mNames[position]);

            return view;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            Log.d(TAG, "权限申请成功");
        }
    }
}
