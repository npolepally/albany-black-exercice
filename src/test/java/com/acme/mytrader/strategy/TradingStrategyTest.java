package com.acme.mytrader.strategy;

import com.acme.mytrader.domain.TradeRequest;
import com.acme.mytrader.domain.TradingException;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceSource;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;


@RunWith(DataProviderRunner.class)
public class TradingStrategyTest {


    PriceSource priceSource = mock(PriceSource.class);
    ExecutionService executionService = mock(ExecutionService.class);

    private TradingStrategy tradingStrategy;

    @Before
    public void setUp() {
        tradingStrategy = new TradingStrategy(priceSource, executionService);

    }

    @Test
    @UseDataProvider("data")
    public void shouldProcessTrades(String stockName, int noOfStock, double stockPriceLimit, double stockPrice, int noOfInvocations) throws Exception{
        //given
        TradeRequest request = TradeRequest.builder()
                .stockName(stockName)
                .stockPriceLimit(stockPriceLimit)
                .stockPrice(stockPrice)
                .noOfStock(noOfStock)
                .build();

        doNothing().when(priceSource).addPriceListener(Mockito.isA(PriceListener.class));
        doNothing().when(executionService).buy(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyInt());

        //when
        tradingStrategy.processTrade(request);

        // verify
        verify(executionService, atLeast(noOfInvocations)).buy(Mockito.anyString(), Mockito.anyDouble(), Mockito.anyInt());
        verify(priceSource, atLeastOnce()).addPriceListener(any(PriceListener.class));
    }

    @Test(expected = TradingException.class)
    public void shouldThrowExceptionWhenTradingRequestIsNull() throws Exception {

        //when
        tradingStrategy.processTrade(null);

    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
                {"IBM", 100, 55.0, 34.0, 1}, // price is less than limit for monitored stock
                {"IBM", 100, 55.0, 55.0, 0}, // Price is not less than limit for monitored stock
                {"APL", 100, 35.0, 55.0, 0} // Not monitored stock
        };
    }
}
