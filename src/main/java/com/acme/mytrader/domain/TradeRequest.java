package com.acme.mytrader.domain;


import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TradeRequest {
    private String stockName;
    private double stockPrice;
    private int noOfStock;
    private double stockPriceLimit;
}
