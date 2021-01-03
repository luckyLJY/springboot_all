package com.kk.cache.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author kk
 * @since 2021-01-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Cache implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String keyName;

    private String valueElement;

    private String className;

    private LocalDateTime expired;

    @TableLogic
    private Boolean status;


}
