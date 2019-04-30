package pl.b2b.Nbp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.b2b.Nbp.model.Calculator;
import pl.b2b.Nbp.model.CurrencyWithDatesObject;
import pl.b2b.Nbp.model.GoldRate;
import pl.b2b.Nbp.model.GoldRateObject;
import pl.b2b.Nbp.model.Rate;
import pl.b2b.Nbp.model.Rates;
import pl.b2b.Nbp.validation.NbpApiValidation;

import java.util.List;
import java.util.Optional;

@Service
public class NbpApiService {

    @Autowired
    private RestTemplate restTemplate;


    public List<Rates> getAllCurrenciesMid() {
        Rate[] result;
        ResponseEntity<Rate[]> exchange = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/tables/a", Rate[].class);
        result = exchange.getBody();
        List<Rates> ratesList = result[0].getRates();

        return ratesList;
    }

    public Rates getSingleCurrency(String code) {
        NbpApiValidation.validateCurrencyCode(code);
        List<Rates> ratesList = getAllCurrenciesMid();

        Optional<Rates> result = ratesList.stream().filter(s -> s.getCode().contains(code)).findFirst();
        if (result.isPresent()) {
            return result.get();
        } else return null;
    }

    public CurrencyWithDatesObject getSingleCurrencyInSpecificDates(String code, String start, String end) {
        CurrencyWithDatesObject datesObject;
        NbpApiValidation.validateDateFormat(start);
        NbpApiValidation.validateDateFormat(end);
        NbpApiValidation.validateDateRange(start, end);
        NbpApiValidation.validateCurrencyCode(code);

        ResponseEntity<CurrencyWithDatesObject> exchange = restTemplate.getForEntity("http://api.nbp.pl/api/exchangerates/rates/a/" + code + "/" + start + "/" + end, CurrencyWithDatesObject.class);
        datesObject = exchange.getBody();
        int listSize = datesObject.getCurrencyWithDatesList().size();
        double avg = 0;
        for (int i = 0; i < listSize; i++) {
            avg += datesObject.getCurrencyWithDatesList().get(i).getMid();
        }
        avg = avg / datesObject.getCurrencyWithDatesList().size();
        datesObject.setAverageValueInSpecifiedDates(avg);

        Double firstRecord = datesObject.getCurrencyWithDatesList().get(0).getMid();
        Double lastRecord = datesObject.getCurrencyWithDatesList().get(listSize - 1).getMid();

        Double change = ((lastRecord - firstRecord) / firstRecord) * 100;

        datesObject.setChangeInPercent(change);

        return datesObject;
    }


    public Calculator currencyCalculator(String code, Double currencyUnits) {
        NbpApiValidation.validateCurrencyCode(code);
        Calculator calculator = new Calculator();
        calculator.setRates(getSingleCurrency(code));
        calculator.setCurrencyUnits(currencyUnits);
        calculator.setCurrencyValueInPln(currencyUnits * calculator.getRates().getMid());

        return calculator;
    }

    public GoldRateObject goldRateInSpecificDate(String data, Double units) {
        NbpApiValidation.validateDateFormat(data);
        GoldRate[] goldRate;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoldRate[]> exchange = restTemplate.getForEntity("http://api.nbp.pl/api/cenyzlota/" + data, GoldRate[].class);
        goldRate = exchange.getBody();

        GoldRateObject goldRateObject = new GoldRateObject();
        goldRateObject.setGoldRate(goldRate[0]);
        goldRateObject.setGoldUnits(units);
        goldRateObject.setValue(goldRate[0].getCena() * units);

        return goldRateObject;
    }
}
