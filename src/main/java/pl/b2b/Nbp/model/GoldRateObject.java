package pl.b2b.Nbp.model;

public class GoldRateObject {

    private Double goldUnits;
    private Double value;
    private GoldRate goldRate;

    public GoldRateObject() {
    }

    public Double getGoldUnits() {
        return goldUnits;
    }

    public void setGoldUnits(Double goldUnits) {
        this.goldUnits = goldUnits;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public GoldRate getGoldRate() {
        return goldRate;
    }

    public void setGoldRate(GoldRate goldRate) {
        this.goldRate = goldRate;
    }
}
