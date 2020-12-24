package com.kk.learning.common;

import cn.hutool.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.HashMap;

/**
 * @description:
 * @author: Kk
 * @create: 2020-12-13 17:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class R {
    private int code;
    private String message;
    private HashMap<String,Object> data=new HashMap<>();

    //成功静态方法
    public static R ok() {
        R r = new R();
        r.setCode(HttpStatus.HTTP_OK);
        r.setMessage("成功");
        return r;
    }

    public static R ok(int code,String message){
        R r=new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    //成功静态方法
    public static R ok(String msg) {
        R r = new R();
        r.setCode(HttpStatus.HTTP_OK);
        r.setMessage(msg);
        return r;
    }

    //失败静态方法
    public static R error(){
        R r=new R();
        r.setCode(HttpStatus.HTTP_BAD_REQUEST);
        r.setMessage("失败");
        return r;
    }

    //失败静态方法
    public static R error(String message){
        R r=new R();
        r.setCode(HttpStatus.HTTP_BAD_REQUEST);
        r.setMessage(message);
        return r;
    }

    //失败静态方法
    public static R error(int code,String message){
        R r=new R();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    public R data(HashMap<String,Object> data){
        this.data=data;
        return this;
    }

    public R data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
}
