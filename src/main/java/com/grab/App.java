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

        Elements trs1 = doc1.select(".a-c.roundy.eplist.bgl-一般.b-一般.bw-2").select("tbody").select("tr");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        for (Element tr : trs1) {
            if (tr.childNodeSize() == 8) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", tr.child(0).text());
                map.put("ch_name", tr.child(1).text());
                map.put("ja_name", tr.child(2).text());
                map.put("en_name", tr.child(3).text());

                String url2 = "https://wiki.52poke.com/wiki/" + tr.child(1).text();
                Document doc2 = Jsoup.connect(url2).get();

                Elements trs2 = doc2.select(".roundy.a-r.at-c").select("tbody").select("tr");

                System.out.println("*******************************");
                System.out.println(trs2);

                map.put("attr_icon", trs2.select(".roundy.fulltable").select("img").attr("data-url"));
                map.put("img_path", trs2.select(".roundy.bgwhite.fulltable").select("img").attr("data-url"));
                map.put("property", trs2.select(".roundy.bw-1").select("span").select("a").text());

                list.add(map);
                break;
            }
        }

        ExcelExport.exportListMap(list, "编码,中文名,日文名,英文名,属性图标,图片,属性", "id,ch_name,ja_name,en_name,attr_icon,img_path," +
                        "property", "D:\\html\\excel\\pokemon.xls", "宝可梦",
                null);
    }

//    public static void main(String[] args) throws IOException {
//        ExcelImport.importExcelFile("D:\\html\\excel\\pokemon.xls");
//    }
}
