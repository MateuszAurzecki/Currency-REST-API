package pl.b2b.Nbp.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CurrencyWithDatesObject {

    @JsonProperty("rates")
    private List<CurrencyWithDates> currencyWithDatesList;

    private Double averageValueInSpecifiedDates;
    private Double changeInPercent;


    public CurrencyWithDatesObject() {
    }

    public List<CurrencyWithDates> getCurrencyWithDatesList() {
        return currencyWithDatesList;
    }

    public void setCurrencyWithDatesList(List<CurrencyWithDates> currencyWithDatesList) {
        this.currencyWithDatesList = currencyWithDatesList;
    }

    public Double getAverageValueInSpecifiedDates() {
        return averageValueInSpecifiedDates;
    }

    public void setAverageValueInSpecifiedDates(Double averageValueInSpecifiedDates) {
        this.averageValueInSpecifiedDates = averageValueInSpecifiedDates;
    }

    public Double getChangeInPercent() {
        return changeInPercent;
    }

    public void setChangeInPercent(Double changeInPercent) {
        this.changeInPercent = changeInPercent;
    }
}
