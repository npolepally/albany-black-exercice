package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PriceListenerImpl implements PriceListener {

    //Requirement 1: Monitors specific stock like "IBM"
    private static String STOCK_MONITOR = "IBM";

    private ExecutionService service;
    private int noOfStock;
    //Requirement 2: impose the price limit...
    private BigDecimal priceLimit;


    /**
     * this method will monitor for specified stock STOCK_MONITOR and the price limit.
     * if this satisfies then it execute the Executive service buy method.
     * @param security name of the stock
     * @param price price of the stock
     */
    @Override
    public void priceUpdate(String security, double price) {
        if (security.equals(STOCK_MONITOR) &&
                new BigDecimal(price).compareTo(priceLimit) < 0 ) {
            service.buy(security, price, noOfStock);
        }
    }
}
