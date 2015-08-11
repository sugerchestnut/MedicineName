package com.medicineJsoup.name;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by panlu on 15-8-11.
 */
/* 本来想着按字母抓取j并疾病名称但是发现有名称有...的情况，所以必须进入到疾病介绍的页面将全名获取到，首先是开启二十四进程，
＊每个进程里面在得到每个疾病的链接，进入之后获取到疾病全名，总体思路是这样的．*/
public class medicineName {
    public static void main(String[] args) {
        FileWriter fw１ = null;
        BufferedWriter bw = null;
        FileWriter fw2 = null;
        BufferedWriter bw2 = null;

        try {
            //给后面追加,这样保证每个线程在写文件的时候都是往文件后面追加
            fw１ = new FileWriter("/home/panlu/IdeaProjects/frame_work/getNameJsoup/fileName",true);
            bw = new BufferedWriter(fw１);
            fw2 = new FileWriter("/home/panlu/IdeaProjects/frame_work/getNameJsoup/pictureImg",true);
            bw2 = new BufferedWriter(fw2);

        } catch (IOException e) {
            e.printStackTrace();
        }

        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'o' || c == 'v'){
                continue;
            } else if (c == 'a' || c == 'b'|| c == 'c' || c == 'd' || c == 'e' || c == 'f' || c == 'g' || c == 'h' || c == 'i' || c == 'j') {
                MyRunnable mr = new MyRunnable(c, bw, bw2);
                Thread t = new Thread(mr);
                t.setPriority(Thread.MAX_PRIORITY);   //设置线程的优先执行权，优先权最高，注意：优先级高并不能保证一定是最先执行的
                t.start();
            }else if ( c == 'k' ||c == 'l' || c == 'm' || c == 'n' || c == 'p' || c == 'q' || c == 'r' ){
                MyRunnable mr = new MyRunnable(c, bw, bw2);
                Thread t = new Thread(mr);
                t.setPriority(Thread.NORM_PRIORITY);  //优先权默认，默认为５
                t.start();
            }else {
                MyRunnable mr = new MyRunnable(c, bw, bw2);
                Thread t = new Thread(mr);
                t.setPriority(Thread.MIN_PRIORITY);   //优先权最低
                t.start();
            }
        }

        try {              //主线程等待然后关闭文件
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            bw.flush();       //只有在文件关闭的时候才会写入数据　　　　　
            bw.close();
            bw2.flush();
            bw2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
