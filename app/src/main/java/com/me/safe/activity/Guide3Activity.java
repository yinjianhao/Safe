package com.me.safe.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.me.safe.R;

public class Guide3Activity extends BaseGuideActivity implements View.OnClickListener {

    private final static String TAG = "Guide3Activity";
    private final static int CONTACT_REQUEST_CODE = 1;
    private EditText etContactNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide3);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        if (btnNext != null) {
            btnNext.setOnClickListener(this);
        }
        Button btnPrev = (Button) findViewById(R.id.btn_prev);
        if (btnPrev != null) {
            btnPrev.setOnClickListener(this);
        }

        Button btnChoose = (Button) findViewById(R.id.btn_choose_contact);
        if (btnChoose != null) {
            btnChoose.setOnClickListener(this);
        }

        etContactNum = (EditText) findViewById(R.id.et_contact_num);
        String phone = getSharedPreferences("setting", MODE_PRIVATE).getString("safePhone", "");
        etContactNum.setText(phone);
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
        String num = etContactNum.getText().toString();
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(this, "安全号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        getSharedPreferences("setting", MODE_PRIVATE).edit().putString("safePhone", num).apply();

        startActivity(new Intent(this, Guide4Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_rtl_enter, R.anim.anim_rtl_out);
    }

    private void showLeft() {
        startActivity(new Intent(this, Guide2Activity.class));
        finish();
        overridePendingTransition(R.anim.anim_ltr_enter, R.anim.anim_ltr_out);
    }

    private void goContact() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("vnd.android.cursor.dir/phone");
        startActivityForResult(i, CONTACT_REQUEST_CODE);
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
            case R.id.btn_choose_contact:
                goContact();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CONTACT_REQUEST_CODE) {
            if (data != null) {
                Uri mContactUri = data.getData();
                Cursor cursor = getContentResolver().query(mContactUri, null, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();
                    String number = cursor.getString(cursor.getColumnIndex("number"));
//                    String number = cursor.getString(cursor.getColumnIndex("data4"));
                    for (int i = 0, l = cursor.getColumnCount(); i < l; i++) {
                        Log.d(TAG, cursor.getColumnName(i) + "--->" + cursor.getString(i));
                    }
                    number = number.replaceAll(" ", "").replaceAll("-", "");
                    etContactNum.setText(number);
                    cursor.close();
                }
            }
        }
    }

//    private String[] getPhoneContacts() {
//        String[] contact = new String[2];
//        //得到ContentResolver对象
//        ContentResolver cr = getContentResolver();
//        //取得电话本中开始一项的光标
//        Cursor cursor = cr.query(mContactUri, null, null, null, null);
//        if (cursor != null) {
//            cursor.moveToFirst();
//            for (int i = 0, l = cursor.getColumnCount(); i < l; i++) {
//                Log.d(TAG, cursor.getColumnName(i) + "--->" + cursor.getString(i));
//            }
//            cursor.close();
//            return contact;
//        }
//        return null;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 0) {
//            String[] contact = getPhoneContacts();
//            if (contact != null) {
//                etContactNum.setText(contact[1]);
//            }
//        }
//    }
}
