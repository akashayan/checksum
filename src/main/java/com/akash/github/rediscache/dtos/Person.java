package com.akash.github.rediscache.dtos;

import com.akash.github.rediscache.annotations.ChecksumParam;
import lombok.Data;

@Data
public class Person extends Parent{
    @ChecksumParam(order = 1, name = "id")
    private Long id;
    @ChecksumParam(order = 2, name = "name")
    private String name;
    private Address address;
}
