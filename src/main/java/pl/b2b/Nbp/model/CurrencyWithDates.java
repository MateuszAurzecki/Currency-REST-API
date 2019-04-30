package pl.b2b.Nbp.model;

public class CurrencyWithDates {

    private Double mid;
    private String effectiveDate;

    public CurrencyWithDates() {
    }

    public Double getMid() {
        return mid;
    }

    public void setMid(Double mid) {
        this.mid = mid;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "CurrencyWithDates{" +
                "mid=" + mid +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
