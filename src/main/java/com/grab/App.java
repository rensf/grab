package com.grab;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        String url1 = "https://wiki.52poke.com/wiki/宝可梦列表（按全国图鉴编号）/简单版";
        Document doc1 = Jsoup.connect(url1).get();

        Elements trs1 = doc1.select("table[class='a-c roundy eplist bgl-一般 b-一般 bw-2']").select("tbody").select("tr");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Element tr : trs1) {
            if (tr.childNodeSize() == 8) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("编码", tr.child(0).text());
                map.put("中文名", tr.child(1).text());
                map.put("日文名", tr.child(2).text());
                map.put("英文名", tr.child(3).text());
                list.add(map);

                String url2 = "https://wiki.52poke.com/wiki/" + tr.child(1).text();
                Document doc2 = Jsoup.connect(url2).get();

                Elements trs2 = doc2.select("div[class='mw-parser-output']").select("table").attr("style", "width: 30%; margin-left: 5px; margin-bottom: 5px;");
                System.out.println("*******************************");
                System.out.println(trs2);
                break;

            }
        }

        ExcelExport.exportListMap(list, "编码,中文名,日文名,英文名", "编码,中文名,日文名,英文名", "D:\\html\\excel\\pokemon.xls", "宝可梦",
                null);
    }

//    public static void main(String[] args) throws IOException {
//        ExcelImport.importExcelFile("D:\\html\\excel\\pokemon.xls");
//    }
}
