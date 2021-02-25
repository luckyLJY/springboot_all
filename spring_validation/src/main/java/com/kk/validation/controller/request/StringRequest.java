package com.kk.validation.controller.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author luokexiong
 * @version 1.0 2021/2/23
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Task", description = "任务")
public class StringRequest {

    @Size(min = 1, max = 20)
    String size;

    @Length(min = 1, max = 20)
    String length;

    @Length(min = 1, max = 20)
    @NotBlank
    String notNullLength;

    @NotBlank
    String notBlank;
}
