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

                System.out.println("***************种群****************");
                System.out.println(trs2.select("td").eq(84).select("td[width='50%']").eq(0).text());
                System.out.println("***************孵化周期****************");
                System.out.println(trs2.select("td").eq(84).select("td[width='50%']").eq(1).text());

                map.put("attr_icon", trs2.select(".roundy.fulltable").select("img").attr("data-url"));
                map.put("img_path", trs2.select(".roundy.bgwhite.fulltable").select("img").attr("data-url"));
                map.put("property", trs2.select("td").eq(7).text());
                map.put("type", trs2.select("td").eq(10).text());
                map.put("feature", trs2.select("td").eq(13).text());
                if (trs2.select("td").eq(14).text().contains("隱藏特性")) {
                    map.put("feature_h", trs2.select("td").eq(14).text());
                    map.put("ex_max", trs2.select("td").eq(15).text());
                } else {
                    map.put("feature_h", "无");
                    map.put("ex_max", trs2.select("td").eq(14).text());
                }
                map.put("height", trs2.select("td").eq(59).text());
                map.put("weight", trs2.select("td").eq(62).text());
                if (trs2.select("td").eq(65).select("img").attr("alt").equals("FUnknown.png") || trs2.select("td").eq(65).select("img").attr("alt").equals("形.png")) {
                    map.put("figure", "未知");
                } else {
                    map.put("figure", trs2.select("td").eq(65).select("img").attr("alt"));
                }

                map.put("figure_icon", trs2.select("td").eq(65).select("img").attr("data-url"));
                if(trs2.select("td").eq(68).text().equals("脚印")) {
                    map.put("footprint", trs2.select("td").eq(68).select("img").attr("data-url"));
                }
                map.put("capturerate", trs2.select("td").eq(74).text());
                map.put("sexratio",trs2.select("td").eq(77).select("[class='']").text());
                map.put("population", trs2.select("td").eq(84).select("td[width='50%']").eq(0).text());
                map.put("hatch", trs2.select("td").eq(84).select("td[width='50%']").eq(1).text());

                list.add(map);
//                break;

            }
        }

        ExcelExport.exportListMap(list,
                "编码,中文名,日文名,英文名,属性图标,图片,属性,分类,特性,隐藏特性,满级经验,身高,体重,体型,体型图标,脚印,捕获率,性别比例,种群,孵化周期",
                "id,ch_name,ja_name,en_name,attr_icon,img_path,property,type,feature,feature_h,ex_max,height,weight," +
                        "figure,figure_icon,footprint,capturerate,sexratio,population,hatch",
                "D:\\html\\excel\\pokemon.xls",
                "宝可梦",
                null);
    }

//    public static void main(String[] args) throws IOException {
//        ExcelImport.importExcelFile("D:\\html\\excel\\pokemon.xls");
//    }
}
