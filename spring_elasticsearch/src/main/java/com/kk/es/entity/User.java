package com.kk.es.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-19 17:05
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    int id;
    String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8") //后传前
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") //前传后
    Date birthday;
}
