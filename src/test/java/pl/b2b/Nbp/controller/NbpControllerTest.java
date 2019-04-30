package pl.b2b.Nbp.controller;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import pl.b2b.Nbp.TestContext;
import pl.b2b.Nbp.model.Calculator;
import pl.b2b.Nbp.model.GoldRateObject;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@SpringBootTest(classes = TestContext.class)
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc(secure = false, addFilters = false)
public class NbpControllerTest {




    @Autowired
    private MockMvc mvc;

    @Test
    public void requestWithDateErrorTest() throws Exception {
        this.mvc.perform(get("/currency/GBP/2019-04-02/2019-04-0"))
                .andExpect(jsonPath("message").value("Wrong date in Url"));
    }


    @Test
    public void currencyCodeErrorTest() throws Exception {
        this.mvc.perform(get("/currency/GB/2019-04-02/2019-04-06"))
                .andExpect(jsonPath("message").value("Wrong currency code"));
    }

    @Test
    public void dateRangeErrorTest() throws Exception {
        this.mvc.perform(get("/currency/USD/2018-10-02/2019-02-10"))
                .andExpect(jsonPath("message").value("Date range should be less than 93 days"));
    }

    @Test
    public void currencyExchangeRateTest() throws Exception {
        this.mvc.perform(get("/currency/USD/2019-04-01/2019-04-05"))
                .andExpect(jsonPath("rates[0].mid").value("3.8267"))
                .andExpect(jsonPath("rates[0].effectiveDate").value("2019-04-01"))
                .andExpect(jsonPath("rates[4].mid").value("3.8215"))
                .andExpect(jsonPath("rates[4].effectiveDate").value("2019-04-05"));
    }

    @Test
    public void showValueForSingleCurrencyTest() throws Exception {
        this.mvc.perform(get("/code/AUD"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("code").value("AUD"));
    }

    @Test
    public void currencyCalculatorTest() throws Exception {
        this.mvc.perform(get("/value/GBP/100"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("currencyUnits").value("100.0"))
                .andExpect(jsonPath("rates.currency").value("funt szterling"))
                .andExpect(jsonPath("rates.code").value("GBP"));
    }

    @Test
    public void goldPriceCalculatorTest() throws Exception {
        this.mvc.perform(get("/gold/2019-04-29/7"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("goldUnits").value("7.0"))
                .andExpect(jsonPath("value").value("1113.0"))
                .andExpect(jsonPath("goldRate.data").value("2019-04-29"))
                .andExpect(jsonPath("goldRate.cena").value("159.0"));
    }


    @Test
    public void currencyParameterTest() throws IOException {
        String uri = "http://localhost:8088/value/GBP/10";
        HttpUriRequest request = new HttpGet(uri);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String jsonFromResponse = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Calculator response = mapper.readValue(jsonFromResponse, Calculator.class);
        Assert.assertEquals(response.getCurrencyUnits().toString(), "10.0");
        Assert.assertEquals(response.getRates().getCode(), "GBP");
        Assert.assertEquals(response.getRates().getCurrency(), "funt szterling");
    }

    @Test
    public void goldParameterTest() throws IOException {
        String uri = "http://localhost:8088//gold/2019-04-26/10";
        HttpUriRequest request = new HttpGet(uri);
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);
        String jsonFromResponse = EntityUtils.toString(httpResponse.getEntity());
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GoldRateObject response = mapper.readValue(jsonFromResponse, GoldRateObject.class);
        Assert.assertEquals(response.getGoldUnits().toString(), "10.0");
        Assert.assertEquals(response.getValue().toString(), "1587.0");
        Assert.assertEquals(response.getGoldRate().getData(), "2019-04-26");
        Assert.assertEquals(response.getGoldRate().getCena().toString(), "158.7");
    }
}


