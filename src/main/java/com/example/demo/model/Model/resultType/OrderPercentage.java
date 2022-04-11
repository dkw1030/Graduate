package com.example.demo.model.Model.resultType;

import lombok.Data;

@Data
public class OrderPercentage {
    private int producing;
    private int delivering;
    private int finish;
    private int fin;

    private int orderConfirmed;
    private int orderRejected;
    private int orderCompleted;
    private int orderFailed;
}
