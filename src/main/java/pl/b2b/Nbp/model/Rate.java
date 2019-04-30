package pl.b2b.Nbp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Rate {

    @JsonProperty("rates")
    private List<Rates> rates;

    public Rate() {
    }

    public List<Rates> getRates() {
        return rates;
    }

    public void setRateList(List<Rates> rates) {
        this.rates = rates;
    }
}
