package com.example.everyday.js;

import java.util.ArrayList;

/**
 * Created by 欧宇志 on 2016/10/30.
 */
public class newsjs {
    public ArrayList<newsdata> data;
    public ArrayList<Integer> extend;
    public int retcode;
    public class newsdata{
        public int id;
        public String title;
        public int type;
        public ArrayList<childrens> children;

        @Override
        public String toString() {
            return "newsdata{" +
                    "children=" + children +
                    ", id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    '}';
        }
    }
    public class childrens{
        public int id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "childrens{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", type=" + type +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "newsjs{" +
                "data=" + data +
                ", extend=" + extend +
                ", retcode=" + retcode +
                '}';
    }
}
