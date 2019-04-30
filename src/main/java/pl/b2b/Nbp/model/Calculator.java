package pl.b2b.Nbp.model;

public class Calculator {

    private Double currencyUnits;
    private Double currencyValueInPln;
    private Rates rates;

    public Calculator() {
    }

    public Double getCurrencyUnits() {
        return currencyUnits;
    }

    public void setCurrencyUnits(Double currencyUnits) {
        this.currencyUnits = currencyUnits;
    }


    public Rates getRates() {
        return rates;
    }

    public void setRates(Rates rates) {
        this.rates = rates;
    }

    public Double getCurrencyValueInPln() {
        return currencyValueInPln;
    }

    public void setCurrencyValueInPln(Double currencyValueInPln) {
        this.currencyValueInPln = currencyValueInPln;
    }
}
