package utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 欧宇志 on 2016/10/11.
 */
public class Read {
   public static String getStr(InputStream is){
       ByteArrayOutputStream baos=new ByteArrayOutputStream();
       int len =0;
       byte bt[]= new byte[1024];
       try {
           while ((len=is.read(bt))!=-1){
               baos.write(bt,0,len);
           }
           return baos.toString();
       } catch (IOException e) {
           e.printStackTrace();
       }finally {
           try {
               is.close();
               baos.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       return null;
   }
}
