package com.acme.mytrader.strategy;


import com.acme.mytrader.domain.TradeRequest;
import com.acme.mytrader.domain.TradingException;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceListenerImpl;
import com.acme.mytrader.price.PriceSource;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */

@AllArgsConstructor
@Getter
public class TradingStrategy {
    //Requirement 1: Monitors specific stock like "IBM"
    private static String STOCK_MONITOR = "IBM";

    private PriceSource source;
    private ExecutionService service;

    public void processTrade(TradeRequest request) throws TradingException {

        if (Objects.isNull(request)) {
            throw new TradingException("Trading Request cannot be null");
        }
        // create a price listener
        PriceListener priceListener = new PriceListenerImpl(service, request.getNoOfStock(), BigDecimal.valueOf(request.getStockPriceLimit()));
        // add the price listener to price source: NOT SURE THE ROLE ABOUT THIS SOURCE
        source.addPriceListener(priceListener);
        // check the price and buy the stock.
        priceListener.priceUpdate(request.getStockName(), request.getStockPrice());
    }

}
