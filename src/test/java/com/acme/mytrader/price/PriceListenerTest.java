package com.acme.mytrader.price;


import com.acme.mytrader.domain.TradeRequest;
import com.acme.mytrader.execution.ExecutionService;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@RunWith(DataProviderRunner.class)
public class PriceListenerTest {

    private ExecutionService executionService = mock(ExecutionService.class);

    private PriceListener priceListener;

    @Before
    public void setUp() {
        priceListener = new PriceListenerImpl(executionService, 100, BigDecimal.valueOf(55.0));
    }

    @Test
    @UseDataProvider("data")
    public void shouldCheckPriceForMonitoredStockAndBuy(String stockName, double stockPrice, int noOfInvocations) {
        //given
        doNothing().when(executionService).buy(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyInt());

        //when
        priceListener.priceUpdate(stockName, stockPrice);

        // verify
        verify(executionService, atLeast(noOfInvocations)).buy(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyInt());
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"IBM", 54.0, 1}, // price is below the limit
                {"IBM", 55.0, 0}, // price is above the limit
                {"APL", 35.0, 0}  // not monitored stock.
        };
    }

}
