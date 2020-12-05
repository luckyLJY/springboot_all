package com.kk.es.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.kk.es.entity.Content;
import com.kk.es.utils.HtmlParseUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @description:
 * @author: Kk
 * @create: 2020-11-20 10:29
 **/
@Service
public class RestClientService {
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    //解析数据放入ES
    public Boolean parseContent(String keyword)throws Exception{
        List<Content> contents = HtmlParseUtil.parseJD(keyword);
        //批量插入
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i=0;i<contents.size();i++){
           // System.out.println(JSONUtil.toJsonStr(contents.get(i)));
            bulkRequest.add(new IndexRequest("jd_goods")
                    .source(JSONUtil.toJsonStr(contents.get(i)), XContentType.JSON));
        }
        BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !response.hasFailures();
    }

    //分页精准查询
    public List<Map<String,Object>> searchTermPage(String keyword, int page, int limit) throws IOException {
        if(page<=1){
            page=1;
        }
        //条件查询
        //1.构造查询请求
        SearchRequest searchRequest = new SearchRequest("post");
        //2.条件构造器
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //3.匹配规则构造器
        //分页搜索
        sourceBuilder.from(page);
        sourceBuilder.size(limit);
        //精准查询
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        sourceBuilder.query(termQueryBuilder);
        //全匹配查询
        /*MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(matchAllQueryBuilder);*/
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //排序
        //sourceBuilder.sort("price", SortOrder.ASC);
        //sourceBuilder.trackTotalHits(true);  //超过1w
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> results=new ArrayList<>();
        for (SearchHit hit : response.getHits().getHits()) {
            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段替换成高亮后的字段
            if (title!=null){
                Text[] fragments = title.fragments();
                System.out.println(fragments);
                String new_title="";
                for (Text text:fragments){
                    new_title += text;
                    System.out.println(new_title);
                }
                sourceAsMap.put("title",new_title);
            }
            results.add(sourceAsMap);
        }
        return results;
    }

    //分页查询
    public List<Map<String,Object>> searchMatchPage(String keyword, int page, int limit) throws IOException {
        //条件查询
        //1.构造查询请求
        SearchRequest searchRequest = new SearchRequest("jd_goods");
        //2.条件构造器
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //3.匹配规则构造器
        //分页搜索
        sourceBuilder.from(page);
        sourceBuilder.size(limit);
        //全匹配查询
        //MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", keyword);
       /* MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("title", keyword)
                .analyzer("ik_smart");*/
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(true);
        highlightBuilder.preTags("<span style='color:red>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        //价格升序排序
        sourceBuilder.sort(new FieldSortBuilder("price").order(SortOrder.ASC));

        sourceBuilder.trackTotalHits(true);  //超过1w
        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String,Object>> results=new ArrayList<>();
        //回填高亮字段
        for (SearchHit hit : response.getHits().getHits()) {
            //解析高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段替换成高亮后的字段
            if (title!=null){
                Text[] fragments = title.fragments();
                System.out.println(fragments);
                String new_title="";
                for (Text text:fragments){
                    new_title += text;
                    System.out.println(new_title);
                }
                sourceAsMap.put("title",new_title);
            }
            results.add(sourceAsMap);
        }
        return results;
    }

}
