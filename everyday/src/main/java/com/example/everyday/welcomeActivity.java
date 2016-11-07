package com.example.everyday;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.everyday.util.spfUtil;

import java.util.ArrayList;
import java.util.List;

public class welcomeActivity extends AppCompatActivity {

    private LinearLayout ll;
    private Button bt_go;
    private ViewPager vp;
    int pics[];
    List<ImageView> lv=new ArrayList<>();
    private ImageView iv_red;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        initViews();
        addViews();
        intisize();
    }

    private void intisize() {
        iv_red.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                size = ll.getChildAt(1).getLeft()-ll.getChildAt(0).getLeft();
                iv_red.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void addViews() {
        ImageView iv,dian;
        for (int i=0;i<pics.length;i++){
             iv=new ImageView(this);
            iv.setBackgroundResource(pics[i]);
            lv.add(iv);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);

            dian=new ImageView(this);
            dian.setBackgroundResource(R.drawable.shape_dian_hui);
            if (i>0){
                params.leftMargin=10;
                dian.setLayoutParams(params);
            }
            ll.addView(dian);


        }

    }

    private void initViews() {
        pics=new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
        ll = (LinearLayout) findViewById(R.id.ll_dian);
        bt_go = (Button) findViewById(R.id.bt_go);
        iv_red = (ImageView) findViewById(R.id.iv_red);
        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new MyAdapter());
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                int dx= (int) (positionOffset*size+position*size);
                RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) iv_red.getLayoutParams();
                layoutParams.leftMargin = dx;// 修改左边距

                // 重新设置布局参数
                iv_red.setLayoutParams(layoutParams);

            }

            @Override
            public void onPageSelected(int position) {
                if (position==lv.size()-1){
                    bt_go.setVisibility(View.VISIBLE);
                }else {
                    bt_go.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bt_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spfUtil.putBoolean(getBaseContext(),"is_first",false);
                startActivity(new Intent(getBaseContext(),MainActivity.class));
                finish();
            }
        });
    }
    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(lv.get(position));
            return lv.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(lv.get(position));
        }
    }
}
