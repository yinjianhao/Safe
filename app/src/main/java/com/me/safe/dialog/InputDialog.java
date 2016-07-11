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
 * @auther yjh
 * @date 2016/7/11
 */
public class InputDialog extends Dialog implements View.OnClickListener {
    private String mTitle;
    private String mDesc;
    private EditText etPwd;
    private OnBtnClickListener mListener;

    public InputDialog(Context context, String title, String desc, OnBtnClickListener listener) {
        super(context);
        this.mTitle = title;
        this.mDesc = desc;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);

        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etPwd.setHint(mDesc);

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
                    mListener.onConfirm(this, pwd);
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
        void onConfirm(InputDialog dialog, String pwd);

        void onCancel(InputDialog dialog);
    }
}
