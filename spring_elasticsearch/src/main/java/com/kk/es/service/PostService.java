package com.kk.es.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kk.es.dao.PostMapper;
import com.kk.es.entity.Post;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: Kk
 * @create: 2020-11-24 16:05
 **/
@Service
public class PostService extends ServiceImpl<PostMapper, Post> {
}
