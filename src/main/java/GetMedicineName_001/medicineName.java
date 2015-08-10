package GetMedicineName_001;

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

public class medicineName  {
    public static void main(String[] args) {
        for (char c = 'a'; c <= 'z'; c++) {
            MyRunnable mr = new MyRunnable(c);
            Thread t = new Thread(mr);
            t.start();
        }
//        long start = System.currentTimeMillis();
//        MyRunnable mr = new MyRunnable('w');
//        Thread t = new Thread(mr);
//        t.start();
        //System.out.println(System.currentTimeMillis()-start);
    }
}

