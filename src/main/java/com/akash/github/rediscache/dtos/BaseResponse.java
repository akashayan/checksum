package com.akash.github.rediscache.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse<T, V> {

    private T status;

    private V message;

}
