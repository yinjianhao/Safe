package com.me.safe.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.me.safe.R;

/**
 * @auther yjh
 * @date 2016/7/10
 */
public class SettingItemView extends LinearLayout {

    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private Context mContext;
    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox cbUpdate;
    private String descOn;
    private String descOff;
    private String title;

    public SettingItemView(Context context) {
        super(context);
        this.mContext = context;
        initView();
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView();
        initAttrs(attrs);
    }

    /**
     * 初始化view
     */
    private void initView() {
        View view = View.inflate(mContext, R.layout.view_setting_item, null);
        this.addView(view);

        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_desc);
        cbUpdate = (CheckBox) view.findViewById(R.id.cb_update);
    }

    /**
     * 初始化自定义属性
     * @param attrs 自定义属性
     */
    private void initAttrs(AttributeSet attrs) {
        title = attrs.getAttributeValue(NAMESPACE, "title");
        descOn = attrs.getAttributeValue(NAMESPACE, "desc_on");
        descOff = attrs.getAttributeValue(NAMESPACE, "desc_off");
        tvTitle.setText(title);
        SharedPreferences setting = mContext.getSharedPreferences("setting", Context.MODE_PRIVATE);
        setChecked(setting.getBoolean("update", true));
    }

    public Boolean isChecked() {
        return cbUpdate.isChecked();
    }

    public void toggleChecked() {
        Boolean checked = isChecked();
        setChecked(!checked);
    }

    public void setChecked(Boolean checked) {
        cbUpdate.setChecked(checked);
        if (checked) {
            tvDesc.setText(descOn);
        } else {
            tvDesc.setText(descOff);
        }
    }
}
