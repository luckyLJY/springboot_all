package com.kk.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 构建Elasticsearch模板
 * @author: Kk
 * @create: 2020-11-23 20:31
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "post",type = "_doc",indexStoreType = "fs",shards = 5,replicas = 1,refreshInterval = "-1")
public class Post implements Serializable {
    @Id
    private Integer id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;

    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;

    @Field(type = FieldType.Integer)
    private int score;

    @Field(type = FieldType.Date,format = DateFormat.custom,
            pattern = "yyyy-MM-dd hh:mm:ss || yyyy-MM-dd || yyyy/MM/dd HH:mm:ss|| yyyy/MM/dd ||epoch_millis")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
