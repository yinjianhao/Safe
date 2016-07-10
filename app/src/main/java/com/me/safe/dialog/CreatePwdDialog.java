package com.me.safe.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.me.safe.R;

/**
 * 创建密码对话框
 *
 * @auther yjh
 * @date 2016/7/10
 */
public class CreatePwdDialog extends Dialog implements View.OnClickListener {
    private String mTitle;
    private EditText etPwd;
    private EditText etPwdConfirm;
    private OnBtnClickListener mListener;

    public CreatePwdDialog(Context context, String title, OnBtnClickListener listener) {
        super(context);
        this.mTitle = title;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_create_pwd);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwdConfirm = (EditText) findViewById(R.id.et_pwd_confirm);
        Button btnConfirm = (Button) findViewById(R.id.btn_confirm);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (mListener != null) {
                    String pwd = etPwd.getText().toString().trim();
                    String pwdConfirm = etPwdConfirm.getText().toString().trim();
                    mListener.onConfirm(this, pwd, pwdConfirm);
                }
                break;
            case R.id.btn_cancel:
                if (mListener != null) {
                    mListener.onCancel(this);
                }
                dismiss();
                break;
        }
    }

    public interface OnBtnClickListener {
        void onConfirm(CreatePwdDialog dialog, String pwd, String pwdConfirm);

        void onCancel(CreatePwdDialog dialog);
    }
}
