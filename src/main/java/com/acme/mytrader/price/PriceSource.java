package com.acme.mytrader.price;

import java.util.Optional;

public interface PriceSource {
    void addPriceListener(PriceListener listener);
    void removePriceListener(PriceListener listener);
}
