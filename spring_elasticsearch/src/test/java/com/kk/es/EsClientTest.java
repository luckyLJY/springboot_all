package com.kk.es;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.kk.es.entity.Post;
import com.kk.es.entity.User;
import com.kk.es.utils.EsConstant;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @description: 测试类
 * @author: Kk
 * @create: 2020-11-19 14:02
 **/
@SpringBootTest
public class EsClientTest {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;
    private DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //创建索引
    @Test
    void createIndex() throws IOException {
        //1.创建索引请求  模板
        CreateIndexRequest request = new CreateIndexRequest("kk_index");
        Map<String,Object> properties=new HashMap<>();
        Map<String,Object> property=new HashMap<>();
        property.put("type","text");
        property.put("index","analyzed");
        property.put("analyzer","ik_max_word");
        properties.put("field_name",property);

        XContentBuilder builder= JsonXContent.contentBuilder();
        builder.startObject()
                    .startObject("mappings")
                        .startObject("type_name")
                            .field("properties",properties)
                        .endObject()
                    .endObject()
                    .startObject("settings")
                        .field("number_of_shards",3)
                        .field("number_of_replicas",1)
                    .endObject()
                .endObject();
        request.source(builder);
        //2.执行请求
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        JSON parse = JSONUtil.parse(response);
        System.out.println(parse.toStringPretty());
    }

    //建立索引模板
    @Test
    void createIndexTemplate() throws IOException {
        CreateIndexRequest request=new CreateIndexRequest("post");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                        .startObject("properties")
                            .startObject("id").field("type","integer").field("index",true).endObject()
                            .startObject("title").field("type","text").field("index",true).field("analyzer","ik_max_word").endObject()
                            .startObject("content").field("type","text").field("index",true).field("analyzer","ik_smart").endObject()
                            .startObject("score").field("type","integer").field("index",true).endObject()
                            .startObject("createTime").field("type","date").field("format","yyyy-MM-dd hh:mm:ss || yyyy-MM-dd || yyyy/MM/dd HH:mm:ss|| yyyy/MM/dd ||epoch_millis").field("index",true).endObject()
                        .endObject()
                .endObject();
        request.mapping(builder);
        //request.settings(builder);  //自定义设置
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        JSON parse = JSONUtil.parse(response);
        System.out.println(parse.toStringPretty());
    }

    //获取索引
    @Test
    void getIndex() throws IOException {
        GetIndexRequest request=new GetIndexRequest("post");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
        JSON parse = JSONUtil.parse(response);
        System.out.println(parse.toStringPretty());
    }

    //删除索引
    @Test
    void deleteIndex() throws IOException {
        DeleteIndexRequest request=new DeleteIndexRequest("kk_index");
        AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);
        JSON parse = JSONUtil.parse(response);
        System.out.println(parse.toStringPretty());
    }

    //创建文档
    @Test
    void addDocument() throws IOException {
        //1.创建对象
        User user=new User(1,"kk",new Date());
        //2.创建请求
        IndexRequest request = new IndexRequest("kk_index");
        //3.规则
        request.id(Convert.toStr(user.getId()));
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        //4.将我们的数据放入请求
        String jsonString = JSONUtil.toJsonStr(user);
        request.source(jsonString, XContentType.JSON);
        //5.客户端发送请求,获取响应结果
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.toString());
    }

    //创建文档
    @Test
    void addPostDocument() throws IOException {
        Post post=new Post();
        post.setId(2);
        post.setScore(2);
        post.setTitle("我想去，西藏，跳舞");
        post.setContent("舞名就叫：闪电五连鞭");
        post.setCreateTime(new Date());
        IndexRequest request=new IndexRequest("post");
        request.source(JSONUtil.toJsonStr(post),XContentType.JSON);
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.toString());
    }

    //判断是否存在文档
    @Test
    void isExistDocument() throws IOException {
        GetRequest request = new GetRequest("kk_index","1");
        //不获取放回_source的上下文，效率更高
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = client.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档
    @Test
    void getDocument()throws IOException{
        GetRequest request = new GetRequest("post","6r6t_XUBkGmvgW8P0R1E");
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        System.out.println(response.getSourceAsString());
        System.out.println(JSONUtil.toJsonPrettyStr(response.toString()));
    }

    //删除文档
    @Test
    void deleteDocument()throws  IOException{
        DeleteRequest request=new DeleteRequest("kk_index","1");
        request.timeout("1s");
        DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //更新文档
    @Test
    void updateDocument()throws IOException{
        UpdateRequest request = new UpdateRequest("kk_index", "1");
        request.timeout("1s");
        User user = new User(1, "lkx", new Date());
        request.doc(JSONUtil.toJsonStr(user),XContentType.JSON);
        UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量插入和更新
    @Test
    void bulkRequest()throws IOException{
        BulkRequest request=new BulkRequest();
        request.timeout("10s");
        List<User> list=new ArrayList<>();
        for (int i=1;i<=5;i++){
            User user=new User(i,"lkx"+i,new Date());
            list.add(user);
        }
        //获取list进行遍历
        for (User u : list) {
            u.setName("lkx-"+u.getId());
            String json = JSONUtil.toJsonStr(u);
            //更新调用
           /* request.add(
                    new UpdateRequest("kk_index",Convert.toStr(u.getId()))
                    .doc(json,XContentType.JSON));*/
            //插入、更新都可以调用
            request.add(
                    new IndexRequest("kk_index")
                    .id(Convert.toStr(u.getId()))
                    .source(json,XContentType.JSON));
        }
        //提交请求
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
        System.out.println(response.hasFailures());
    }

    //批量删除
    @Test
    void bulkDeleteRequest()throws IOException{
        BulkRequest request=new BulkRequest();
        request.timeout("10s");
        for (int i=1;i<=5;i++){
            request.add(new DeleteRequest(EsConstant.ES_INDEX)
            .id(Convert.toStr(i)));
        }
        BulkResponse response = client.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    //条件复杂查询
    @Test
    void search()throws IOException{
        //查询请求
        SearchRequest searchRequest=new SearchRequest("post");

        //1.构建搜索条件SearchSourceBuilder
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //2.构建查询器QueryBuilder
        //TermQueryBuilder termQueryBuilder1 = QueryBuilders.termQuery("title", "听话");
        //TermQueryBuilder termQueryBuilder2 = QueryBuilders.termQuery("content", "最好是");
        boolQueryBuilder.should(QueryBuilders.matchQuery("title", "挺好"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content", "最好是"));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        searchRequest.source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSONUtil.toJsonPrettyStr(response.toString()));
        System.out.println("======================");
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getId());
            //System.out.println(hit.getSourceAsMap());
        }
    }

    @Test
    public void searchHighlight() throws IOException {
        List<Map<String, Object>> maps = searchTermPage("跳舞", 1, 10);
        if (maps!=null) {
            System.out.println(JSONUtil.parse(maps).toStringPretty());
        }
    }

    //高亮查询
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
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("title",keyword).analyzer("ik_smart"));
        boolQueryBuilder.should(QueryBuilders.matchQuery("content",keyword).analyzer("ik_smart"));
        //boolQueryBuilder.should(QueryBuilders.wildcardQuery("title","*"+keyword+"*"));
        //boolQueryBuilder.should(QueryBuilders.wildcardQuery("content","*"+keyword+"*"));
        sourceBuilder.query(boolQueryBuilder);
        //全匹配查询
        /*MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        sourceBuilder.query(matchAllQueryBuilder);*/
        sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
        //高亮
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("title"); //高亮查询字段
        highlightBuilder.field("content"); //高亮查询字段
        highlightBuilder.requireFieldMatch(false);  //如果要多个字段高亮,这项要为false
        highlightBuilder.preTags("<span style='color:red>"); //高亮设置
        highlightBuilder.postTags("</span>");
        highlightBuilder.fragmentSize(800000); //最大高亮分片数
        highlightBuilder.numOfFragments(0); //从第一个分片获取高亮片段
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
            //System.out.println(highlightFields);

            HighlightField titleField = highlightFields.get("title"); //获取分片
            HighlightField contentField = highlightFields.get("content"); //获取分片
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            //解析高亮字段，将原来的字段替换成高亮后的字段
            if (titleField!=null){
                Text[] fragments = titleField.fragments();
                String title = "";
                for (Text text : fragments) {
                    title+=text;
                }
                sourceAsMap.put("title", title);   //高亮字段替换掉原本的内容
            }
            if (contentField!=null){
                Text[] fragments = contentField.fragments();
                String content = "";
                for (Text text : fragments) {
                    content+=text;
                }
                sourceAsMap.put("content", content);   //高亮字段替换掉原本的内容
            }

            results.add(sourceAsMap);
        }
        return results;
    }
}
