package com.acme.mytrader.execution;

import static java.lang.System.*;


public class ExecutionServiceImpl implements ExecutionService {

    @Override
    public void buy(String security, double price, int volume) {

        out.printf("Buying the trade %s for the price %f and the number %d",
                security,
                price,
                volume);

    }

    @Override
    public void sell(String security, double price, int volume) {
        out.printf("Selling the trade %s for the price %f and the number %d",
                security,
                price,
                volume);
    }
}
