package com.example.everyday.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.everyday.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 欧宇志 on 2016/11/1.
 */
public class RefreshListView extends ListView {

    private int startY = -1;
    private int fresh;
    static final int DOWN_FRESH = 0x12;
    static final int UP_FRESH = 0x13;
    static final int NOW_FRESH = 0x14;
    private View view;
    private int pandding;
    private int height;
    private RotateAnimation ra;
    private RotateAnimation ra2;
    private ImageView iv;
    private ProgressBar pb;
    private TextView tv1;
    private TextView tv2;

    public RefreshListView(Context context) {
        super(context);
        initView();
    }


    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        view = View.inflate(getContext(), R.layout.refresh, null);
        iv = (ImageView) view.findViewById(R.id.arr);
        tv2 = (TextView) view.findViewById(R.id.tv_date);
        tv1 = (TextView) view.findViewById(R.id.tv_fresh);
        pb = (ProgressBar) view.findViewById(R.id.pb_menu);
        initAnimation();
        addHeaderView(view);
        view.measure(0, 0);
        height = view.getMeasuredHeight();
        view.setPadding(0, -height, 0, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {
                    startY = (int) ev.getY();
                }
                if (fresh == NOW_FRESH) {
                    break;
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                int firstVisiblePosition = getFirstVisiblePosition();
                if (dy > 0 && firstVisiblePosition == 0) {
                    int padding = dy - height;
                    view.setPadding(0, padding, 0, 0);
                    if (padding > 0 && fresh != DOWN_FRESH) {
                        fresh = DOWN_FRESH;
                        startAnimation();
                    } else if (padding < 0
                            && fresh != UP_FRESH) {
                        fresh = UP_FRESH;
                        startAnimation();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (fresh == DOWN_FRESH) {
                    fresh = NOW_FRESH;
                    startAnimation();
                    view.setPadding(0, 0, 0, 0);
                    if (mListener != null) {
                        mListener.onRefresh();
                    }
                } else if (fresh == UP_FRESH) {
                    // 隐藏头布局
                    view.setPadding(0, -height, 0, 0);
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    public void initAnimation() {
        ra = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra.setDuration(700);
        ra.setFillAfter(true);
        ra2 = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        ra2.setDuration(700);
        ra2.setFillAfter(true);
    }

    public void startAnimation() {
        switch (fresh) {
            case UP_FRESH:
                tv1.setText("下拉刷新");
                pb.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.VISIBLE);
                iv.startAnimation(ra2);
                break;
            case DOWN_FRESH:
                tv1.setText("松开刷新");
                pb.setVisibility(View.INVISIBLE);
                iv.setVisibility(View.VISIBLE);
                iv.startAnimation(ra);
                break;
            case NOW_FRESH:
                tv1.setText("正在刷新...");
                iv.clearAnimation();// 清除箭头动画,否则无法隐藏
                pb.setVisibility(View.VISIBLE);
                iv.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String data = sdf.format(new Date());
        tv2.setText(data);
    }
    public void onRefreshComplete(boolean success) {
        view.setPadding(0,-height,0,0);
        fresh = UP_FRESH;
        tv1.setText("下拉刷新");
        pb.setVisibility(View.INVISIBLE);
        iv.setVisibility(View.VISIBLE);

        if (success) {
            currentTime();
        }
    }

    private OnRefreshListener mListener;


    public void setOnRefreshListener(OnRefreshListener listener) {
        mListener = listener;
    }


    public interface OnRefreshListener {
        public void onRefresh();
    }


}
