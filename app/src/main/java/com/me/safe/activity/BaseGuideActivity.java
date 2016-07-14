package com.me.safe.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @auther yjh
 * @date 2016/7/14
 */
public abstract class BaseGuideActivity extends AppCompatActivity{

    private GestureDetector mDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                float x = e1.getRawX() - e2.getRawX();
                float y = e1.getRawY() - e2.getRawY();
                if (Math.abs(x / y) > 1) {
                    if (x > 100) {     //向右滑
                        SlidingRight();
                    } else if (x < -100) {    //向左滑
                        SlidingLeft();
                    }
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    /**
     * 向右滑动调用
     */
    public abstract void SlidingRight();

    /**
     * 向左滑动调用
     */
    public abstract void SlidingLeft();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
