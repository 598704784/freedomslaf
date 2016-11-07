package com.example.everyday.Views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 欧宇志 on 2016/10/31.
 */
public class Menu_Viewpager extends ViewPager {

    private int startY;
    private int startX;

    public Menu_Viewpager(Context context) {
        super(context);
    }

    public Menu_Viewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    int lastItem = getAdapter().getCount();
                    int currentItem = getCurrentItem();
                    if (dx > 0 && currentItem == 0) {
                        getParent().requestDisallowInterceptTouchEvent(false);

                    }else if (dx<0&&currentItem==lastItem-1){
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }


                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;
        }
        return super.onTouchEvent(ev);
    }
}
