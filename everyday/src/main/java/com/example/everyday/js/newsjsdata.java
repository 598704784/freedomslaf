package com.example.everyday.js;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/11/1.
 */
public class newsjsdata {
    public NewsTab data;

    public class NewsTab {
        public ArrayList<itemsnews> news;
        public ArrayList<titlenews> topnews;
    }

    public class itemsnews {
        public String id;
        public String listimage;
        public String pubdate;
        public String title;
        public String type;
        public String url;


    }


    public class titlenews {
        int id;
        public String pubdate;
        public String title;
        public String topimage;
        public String type;
        public String url;
    }
}
