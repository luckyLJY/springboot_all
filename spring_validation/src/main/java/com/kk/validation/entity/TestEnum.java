package com.kk.validation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonFormat(shape =JsonFormat.Shape.OBJECT)
public enum TestEnum {
    ENABLE((byte) 1, "xxx1"),
    DISABLE((byte) 0, "xxx0");

    private final byte value;
    private final String msg;
}
