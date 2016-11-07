package com.example.everyday;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

public class webActivity extends AppCompatActivity {

    private WebView wv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ActionBar ab=getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
       // ab.setDefaultDisplayHomeAsUpEnabled(true);
        wv = (WebView) findViewById(R.id.web_view);
        pb = (ProgressBar) findViewById(R.id.pb_web);
        pb.setMax(80);
        initdata();
    }

    private void initdata() {
        Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        wv.loadUrl(url);
        WebSettings settings = wv.getSettings();
        settings.setBuiltInZoomControls(true);// 显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);// 支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);// 支持js功能
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {//加载进度发生变化
                pb.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {//加载网页的标题
                super.onReceivedTitle(view, title);
            }
        });
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//开始加载
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {//加载完成
                super.onPageFinished(view, url);
                pb.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {//点击超链接加载别的网页
                view.loadUrl(url);// 在跳转链接时强制在当前webview中加载
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case 100:
                Toast.makeText(this, "分享可以用shapSDK来做", Toast.LENGTH_SHORT).show();
                break;
            case 101:
                dialo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
        int checkItem=2;
        int selectItem;
    public void dialo() {
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        String str[] =new String[]{"超大号字体","大号字体","标准字体","小号字体","超小号字体"};
       ab.setSingleChoiceItems(str, checkItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectItem=which;
            }
        });
        ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkItem=selectItem;
                WebSettings settings = wv.getSettings();
                switch (selectItem){
                    case 0:
                        settings.setTextZoom(200);
                        break;
                    case 1:
                        settings.setTextZoom(150);
                        break;
                    case 2:
                        settings.setTextZoom(100);
                        break;
                    case 3:
                        settings.setTextZoom(75);
                        break;
                    case 4:
                        settings.setTextZoom(50);
                        break;
                }
            }
        });
        ab.setNegativeButton("取消",null);
        ab.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem m2=menu.add(0,101,0,"字体大小");
        m2.setIcon(R.drawable.icon_textsize);
        m2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        MenuItem m1=menu.add(0,100,0,"分享");
        m1.setIcon(R.drawable.icon_share);
        m1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return super.onCreateOptionsMenu(menu);
    }
}
