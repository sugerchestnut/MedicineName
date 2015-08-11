package com.medicineJsoup.name;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by panlu on 15-8-11.
 */
public class MyRunnable implements Runnable{
    String MainURL = "http://jib.xywy.com/html/";
    Document doc = null;
    List<String> medicineLink;
    List<String> medicineName;
    BufferedWriter bw = null;
    BufferedWriter bw2 = null;
    String pict = null;

    MyRunnable(){
        System.out.println("non-parameter constructor");
    }

    MyRunnable(char ch,BufferedWriter bw, BufferedWriter bw2){
        System.out.println(System.currentTimeMillis());   //输出调用该线程时的系统时间
        MainURL = MainURL + ch + ".html";
        medicineLink = new LinkedList<String>();
        medicineName = new LinkedList<String>();
        this.bw = bw;
        this.bw2 = bw2;
    }

    @Override
    public void run() {
        try {
            doc = Jsoup.connect(MainURL).get();
        }catch (IOException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        Elements pageLinkselect = doc.getElementsByClass("ks-ill-txt");
        Elements elemA = pageLinkselect.select("a");
        for (Element t:elemA) {
            medicineLink.add(t.attr("href"));
        }

        //因为在初始页面获取到的疾病名称有的前几个字会重复，后面用...代替，所以必须进入疾病里面才能获取到完整的疾病名称
        Document doc1 = null;
        for (int i = 0; i < medicineLink.size(); i++) {
            String URL = "http://jib.xywy.com" + medicineLink.get(i);
            try {
                //深入到具体的疾病页面获取完全的病名
                doc1 = Jsoup.connect(URL).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0").timeout(10000).get();//进入到每个疾病的页面并获取到网页的html源代码,（火狐浏览器）
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            // 获取到title然后对title用，进行剪裁
            String patternStr = ",";
            String title = doc1.title();
            String[] attrStr = title.split(patternStr);
            title = attrStr[0];
            medicineName.add(title);
            System.out.println(title);
            String html03 = doc1.toString();

//            Pattern p = Pattern.compile("<div class=\"jib-articl-imgbox bor fr\">");
//            Matcher matcher = p.matcher(html03);
//            System.out.println(matcher.group(1));

            //用分割字符串的方式将整体的html分割出想要的图片链接，想用正则表达式呢　可是没弄出来　　诶！
            String[] locateImg01 = html03.split("<div class=\"jib-articl-imgbox bor fr\">");
            String[] locateImg02 = locateImg01[1].split("</div>");
            String[] locateSrc01 = locateImg02[0].split("src=\"");
            String[] locateSrc02 = locateSrc01[1].split("\" width");
            String img = locateSrc02[0];
//            System.out.println(img);
            pict = title + " " + img;

            try {
                bw.write(title);
                bw.newLine();
                bw2.write(pict);
                bw2.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



