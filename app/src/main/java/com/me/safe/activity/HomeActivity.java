package com.me.safe.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.me.safe.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvTitle;
    private GridView gvBody;
    private String[] names;
    private int[] logos;
    private HomeGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        gvBody = (GridView) findViewById(R.id.gv_body);


        names = new String[]{"杀毒", "杀毒", "杀毒", "杀毒", "杀毒", "杀毒", "杀毒", "杀毒", "杀毒"};
        logos = new int[]{R.drawable.home_apps, R.drawable.home_apps, R.drawable.home_apps,
                R.drawable.home_apps, R.drawable.home_apps, R.drawable.home_apps,
                R.drawable.home_apps, R.drawable.home_apps, R.drawable.home_apps};

        adapter = new HomeGridAdapter();
        gvBody.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {

    }

    private class HomeGridAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return names.length;
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

            ((ImageView)view.findViewById(R.id.iv_logo)).setImageResource(logos[position]);
            ((TextView) view.findViewById(R.id.tv_name)).setText(names[position]);

            return view;
        }
    }
}
