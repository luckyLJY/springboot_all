package com.kk.validation.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Description:
 *
 * @author luokexiong
 * @version 1.0 2020/12/24
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Task", description = "任务")
public class Task {

    //    @Max(value = 20,message = "任务数大于1小于20")
//    @Min(value = 1, message = "任务必须大于1")
    @Range(min = 1, max = 20, message = "任务大于1小于20")
    //@NotNull(message = "不能为空")
    @ApiModelProperty(value = "任务数", required = true, example = "1")
    Integer limit;

    @Length(min = 1, max = 16, message = "字符串长度大于1小于16")
    //@NotNull(message = "不能为空")
    @ApiModelProperty(value = "任务类型", required = true, example = "VIDEO", notes = "VIDEO/IMAGE")
    String metricsType;

    @Length(min = 1, max = 16, message = "字符串长度大于1小于16")
    //@NotNull(message = "不能为空")
    @ApiModelProperty(value = "是否可见", required = true, example = "PUBLIC", notes = "PUBLIC/PRIVATE")
    String visibleType;

    @Length(min = 1, max = 16, message = "字符串长度大于1小于16")
    @ApiModelProperty(value = "机器描述信息")
    String machine;

    @Max(value = 100)
    @Min(value = 0)
    float floatType;

    @Max(value = 100)
    @Min(value = 0)
    double doubleType;

    @DecimalMax(value = "9999999999.99999999")
    BigDecimal decimal;

    TestEnum testEnum;

    LocalDateTime localDateTime;
   /* @Past
    @NotNull(message = "不能为空")
    @ApiModelProperty(value = "发布时间", required = true)
    LocalDateTime created;*/
}
