package com.example.demo.model.DTO;

import com.example.demo.model.Model.Page;
import lombok.Data;

import java.util.List;

@Data
public class PageDTO {
    private String url;
    private int tot;
    private int cur;
    private int style;
}
