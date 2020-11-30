package com.kk.es.utils;

import com.kk.es.entity.Content;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 解析网页工具类
 * @author: Kk
 * @create: 2020-11-20 10:05
 **/
public class HtmlParseUtil {
    public static void main(String[] args) throws IOException {
        HtmlParseUtil.parseJD("java").forEach(System.out::println);
    }

    public static List<Content> parseJD(String keyword) throws IOException {
        //获取请求
        String url="https://search.jd.com/Search?keyword="+keyword+"&enc=utf-8";
        //解析网页
        Document document = Jsoup.parse(new URL(url), 30000);
        Element list = document.getElementById("J_goodsList");
        //System.out.println(list.html());
        Elements lis = list.getElementsByTag("li");
        List<Content> contentList=new ArrayList<>();
        for(Element li:lis){
            //String img = li.getElementsByTag("img").eq(0).attr("src");
            String price = li.getElementsByClass("p-price").eq(0).text();
            String title = li.getElementsByClass("p-name").eq(0).text();
            Content content=new Content();
            content.setPrice(price);
            content.setTitle(title);
            contentList.add(content);
        }
        return contentList;
    }
}
