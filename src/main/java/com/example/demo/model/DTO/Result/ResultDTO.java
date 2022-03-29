package com.example.demo.model.DTO.Result;

import lombok.Data;

@Data
public class ResultDTO<T> {
    /**
     * code默认成功为0，其余特殊情况特殊考虑
     */
    int code;

    /**
     * 返回前端的数据
     */
    T data;
}
