package com.akash.github.rediscache.dtos;

import com.akash.github.rediscache.annotations.ChecksumParam;
import lombok.Data;

@Data
public class Parent {
    @ChecksumParam(order = 3, name = "parentId")
    private String parentId;
    private String parentName;
}
