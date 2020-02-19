package com.akash.github.rediscache.dtos;

import com.akash.github.rediscache.annotations.ChecksumParam;
import lombok.Data;

@Data
public class Address {
    private String city;
    private String state;
    @ChecksumParam(order = 4, name = "pinCode")
    private String pinCode;
}
