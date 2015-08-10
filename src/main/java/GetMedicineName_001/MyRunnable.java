package GetMedicineName_001;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by panlu on 15-8-10.
 */
public class MyRunnable implements Runnable{
    String MainURL = "http://jib.xywy.com/html/";
    Document doc = null;
    List<String> medicineLink;
    List<String> medicineName;
    //List<String> pictureLinks;

    MyRunnable(){
        System.out.println("non-parameter constructor");
    }

    MyRunnable(char ch){
        MainURL = MainURL + ch + ".html";
        System.out.println(MainURL);
        medicineLink = new LinkedList<String>();
        medicineName = new LinkedList<String>();
        //pictureLinks = new LinkedList<String>();
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
            //System.out.println(URL);
            try {
                doc1 = Jsoup.connect(URL).userAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:37.0) Gecko/20100101 Firefox/37.0").timeout(10000).get();//进入到每个疾病的页面并获取到网页的html源代码,（火狐浏览器）
                //doc1 = Jsoup.connect(URL).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.71 Safari/537.36").timeout(10000).get();//进入到每个疾病的页面并获取到网页的html源代码，谷歌浏览器
            } catch (IOException e) {
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }

            // 获取到title然后对title用，进行剪裁
            String patternStr = ",";   //正则表达式
            String title = doc1.title();
            String[] attrStr = title.split(patternStr);
            title = attrStr[0];
            medicineName.add(title);
            System.out.println(title);

        }
    }
}
